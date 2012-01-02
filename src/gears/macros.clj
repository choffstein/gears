(def ^:dynamic *failure-pattern* nil)

(defmacro until-success
  "A macro that keeps executing arguments until success is hit.  Standard failure
   is identified as nil, but can be rebound.

   => (until-success nil nil 5 6 nil)
   5

   => (binding [*failure-pattern* false] (until-success false (identity false) nil 6))
   nil"
  ([x] x)
  ([x & more] `(let [x# ~x]
                 (if (= *failure-pattern* x#)
                   (until-success ~@more)
                   x#))))
