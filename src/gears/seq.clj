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

(defn drop-nth
  "Drop the nth item from coll

   => (drop-nth [1 2 3 4 5 6] 4)
   [1 2 3 4 6]
   => (drop-nth [] 5)
   []
   => (drop-nth [1] 2)
   [1]"
  [coll index]
  (keep-indexed (fn [i item] (if (not (= index i)) item nil)) coll))


(defn every-other
  "Take every other element in `coll`

  => (every-other [])
  []
  => (every-other [1])
  [1]
  => (every-other [1 2])
  [1]
  => (every-other [1 2 3])
  [1 3]"
  [coll]
  (take-nth 2 coll))

(defn every-even-index
  "Takes all the even entries of `coll`

  => (every-even-index [])
  []
  => (every-even-index [1])
  []
  => (every-even-index [1 2])
  [2]
  => (every-even-index [1 2 3])
  [2]"
  [coll]
  (every-other (drop 1 coll)))

(defn every-odd-index
  "Takes all the odd entries of `coll`

  => (every-odd-index [])
  []
  => (every-odd-index [1])
  [1]
  => (every-odd-index [1 2])
  [1]
  => (every-odd-index [1 2 3])
  [1 3]"
  [coll]
  (every-other coll))
