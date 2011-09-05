(ns gears.tests)

(defn true-for-all
  "Checks to see if `pred` is true for all elements in `coll`"
  [pred coll]
  (reduce (fn [acc e] (and (pred e) acc)) true coll))

(defn false-for-all
  "Checks to see if `pred` is false for all elements in `coll`"
  [pred coll]
  (reduce (fn [acc e] (and (not (pred e)) acc)) true coll))

(defn true-for-any
  "Checks to see if `pred` is true for any element in `coll`"
  [pred coll]
  (not (false-for-all pred coll)))

(defn false-for-any
  "Checks to see if `pred` is false for any element in `coll`"
  [pred coll]
  (not (true-for-all pred coll)))
