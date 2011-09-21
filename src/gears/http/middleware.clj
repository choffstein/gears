(ns gears.http.middleware
  (:use [gears.http]
        [mmemail.core]
        [gears.exceptions]
        [sandbar.stateful-session])
  (:require [aws-logging.core :as aws-logging]))

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
  [mailer exception-handler handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
	(do
          (mailer {:subject (str "Exception: " (.toString e)) ;;mail the administrator
                   :body (exception-handler e)})
	  {:status 500
	   :headers {"Content-Type" "text/plain"}
	   :body "We're sorry, something went wrong.  An administrator has been informed."})))))

(defn wrap-email-on-exception
  "`mailbox-info` follows the configuration of https://github.com/slagyr/mmemail and must contain connection information"
  [mailbox-info exception-handler]
  (partial wrap-exception  (create-mailer mailbox-info) exception-handler))

(defn wrap-track
  "Requires that sandbar's wrap-stateful-session be utilized to enable session data.
   Stores user action history based on the request."
  [handler]
  (fn [req]
    (do
      (let [current-history (or (session-get :track-history) [])
            current-action {:ip (ip req)
                          :uri (:uri req)
                          :method (request-method req)}]
        (session-put! :track-history (cons current-action current-history)))
      (handler req))))
