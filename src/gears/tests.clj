(ns gears.tests)

(defn true-for-all?
  "Checks to see if `pred` is true for all elements in `coll`

  => (true-for-all? #(= % 5) [5 5 5 5])
  true
  => (true-for-all? #(= % 5) [5 6 5 5])
  false"
  [pred coll]
  (reduce (fn [acc e] (and (pred e) acc)) true coll))

(defn false-for-all?
  "Checks to see if `pred` is false for all elements in `coll`

  => (false-for-all? #(= % 5) [6 6 6 6])
  true
  => (false-for-all? #(= % 5) [5 6 6 6])
  false"
  [pred coll]
  (reduce (fn [acc e] (and (not (pred e)) acc)) true coll))

(defn true-for-any?
  "Checks to see if `pred` is true for any element in `coll`

  => (true-for-any? #(= % 5) [1 2 3 4 5 6])
  true
  => (true-for-any? #(= % 5) [1 2 3 4])
  false"
  [pred coll]
  (not (false-for-all? pred coll)))

(defn false-for-any?
  "Checks to see if `pred` is false for any element in `coll`

  => (false-for-any? #(= % 5) [1 2 3 4 5 6])
  true
  => (false-for-any? #(= % 5) [5 5 5 5])
  false"
  [pred coll]
  (not (true-for-all? pred coll)))
