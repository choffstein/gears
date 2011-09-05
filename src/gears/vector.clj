(ns gears.vector
  (:require [gears.tests :as tests]
            [gears.hash-map :as hash-map]))

;; (gen-and-load-class 'IllegalSequence :extends Exception)

(defmulti quack-vec
  "Takes anything that quacks like a vector and turns it into a vector.
   For maps, it means that keys must be integers.
   If not, it will return back the map."
  (fn [coll] (class coll)))

(defmethod quack-vec clojure.lang.PersistentVector [v]
  (vec (map quack-vec v)))

(defmethod quack-vec clojure.lang.PersistentList [l]
  (vec (map quack-vec l)))

(defmethod quack-vec clojure.lang.PersistentArrayMap [m]
  "Takes a map `m` and transforms it into a vector if all the keys are integers.
   Any gaps are filled in with nils.  For example,
   `(quack-vec {0 :a 1 :b 5 :c})` returns `[:a :b nil nil nil :c]`
   This is a deep method, meaning that if will apply quack-vec to all
   values in the map, whether the whole map becomes a vector or not."
  (let [indexes (keys m)]
    (if (tests/true-for-all integer? indexes)
      (vec (map (fn [index] (or (quack-vec (get m index)) nil))
           (range 0 (inc (apply max indexes)))))
      (hash-map/map-to-values quack-vec m))))

(defmethod quack-vec :default [s]
  (if (sequential? s)
    (throw (Exception.
            (str "Illegal sequence of type " (type s) " provided.")))
    s))
