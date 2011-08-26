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
  "Mailer is in https://github.com/slagyr/mmemail format and contains connection information"
  [mailer exception-handler]
  (partial wrap-exception mailer exception-handler))

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
        (session-set! :track-history (cons current-action current-history)))
      (app req))))

(defn- aws-log-connection
  "Logs a connection to Amazon's AWS"
  [log-handler identifier handler]
  (fn [req]
    (do
      ;; we should probably make sure the request isn't for a static file...
      (aws-logging/store-message :connection
				 (log-handler req)
				 identifier)
      (app req))))

(defn- wrap-aws-log-connection
  "Creates a wrapper to log connections to Amazon Web Service.
   Logger is a function that takes a request and returns a string.
   Identifier is the string identifier for the service."
  [logger identifier]
  (partial aws-log-connection log-handler identifier))
