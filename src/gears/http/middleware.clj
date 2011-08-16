(ns gears.http.middleware
  (:use [mmemail.core]
        [gears.exceptions]))

(defn wrap-if
  "Creates a middleware wrapper that applies `wrapper` if `pred` is true"
  [app pred wrapper & args]
  (if pred
    (apply wrapper app args)
    app))

(defn wrap-failsafe
  "Creates a failsafe wrapper that returns a 500 if any error occurs"
  [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
	(do
	  {:status 500
	   :headers {"Content-Type" "text/plain"}
	   :body "We're sorry, something went wrong."})))))

(defn- wrap-exception
  "Creates a failsafe wrapper that emails an administrator and returns a 500 if any error occurs"
  [mailer handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
	(do
          (mailer {:subject (str "Exception: " (.toString e)) ;;mail the administrator
                   :body (stack-trace-as-string e)})
	  {:status 500
	   :headers {"Content-Type" "text/plain"}
	   :body "We're sorry, something went wrong.  An administrator has been informed."})))))

(defn wrap-email-on-exception [mailer]
  ;; mailer must contain mmemail formated details
  (partial wrap-exception mailer))
