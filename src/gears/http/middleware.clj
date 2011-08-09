(ns gears.http.middleware)

(defn wrap-if [app pred wrapper & args]
  (if pred
    (apply wrapper app args)
    app))

(defn wrap-failsafe [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
	(do
	  {:status 500
	   :headers {"Content-Type" "text/plain"}
	   :body "We're sorry, something went wrong."})))))
