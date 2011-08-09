(ns gears.http.middleware)

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
