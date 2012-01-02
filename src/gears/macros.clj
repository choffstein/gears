(defmacro while-failure
  "A macro that keeps executing arguments until success is hit.  Standard failure
   is identified as nil, but can be rebound.

   => (while-failure nil? (identity nil) (identity nil) (identity 5) (identity 6) (identity nil))
   5"
  ([failure-fn x] x)
  ([failure-fn x & more]
     `(let [x# ~x]
        (if (~failure-fn x#)
          (while-failure ~failure-fn ~@more)
          x#))))

(defmacro until-success
  "A macro that keeps executing arguments until success is hit.  Standard failure
   is identified as nil, but can be rebound.

   => (until-success #(not (nil? %)) (identity nil) (identity nil) (identity 5) (identity 6) (identity nil))
   5"
  ([success-fn x] x)
  ([success-fn x & more]
     `(let [x# ~x]
        (if (~success-fn x#)
          x#
          (until-success ~success-fn ~@more)))))
