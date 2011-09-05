(ns gears.seq
  (:require [gears.hash-map :as hash-map]))

(defmulti traverse
  "Traverse a collection of collections, applying a function to the elements of that collectin.
   By default, applies to values, not keys, of a map"
  (fn [f coll] (class coll)))
(defmethod traverse clojure.lang.PersistentVector [f v] (map (partial traverse f) v))
(defmethod traverse clojure.lang.PersistentList [f l] (map (partial traverse f) l))
(defmethod traverse clojure.lang.PersistentArrayMap [f m] (hash-map/map-to-values (partial traverse f) m))
;; Should this map a set to a list, or back to a set?  What if the function creates two identical values from within the set?  For now, we'll keep it a list, since that is what clojure's map function does
(defmethod traverse clojure.lang.PersistentHashSet [f s] (map (partial traverse f) s))
(defmethod traverse :default [f s] (if (sequential? s)
                                     (map (partial traverse f) s)
                                     (f s)))
