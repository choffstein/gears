(ns gears.http)

(defn ip
  "Pull the ip address from a given request"
  [request]
  (or ((:headers request) "x-forwarded-for")
      (:remote-addr request)))

(defn referrer
  "Find the referrer from a given request"
  [request]
  ((:headers request) "referer")) ;;yes, referer SHOULD be spelled wrong

(defn request-method
  "Get the request method for a given request"
  [request]
  (:request-method request))

(defn query-string
  "Get the query-string for a given request"
  [request]
  (:query-string request))

(defn query-string-to-keywords
  "Take the query-string and parse it into keywords"
  [query-string]
  (into {} (for [[_ k v] (re-seq #"([^&=]+)=([^&]+)" req)]
             [(keyword k) v])))
