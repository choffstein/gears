(ns gears.http)

(defn ip [request]
  (or ((:headers request) "x-forwarded-for")
      (:remote-addr request)))
