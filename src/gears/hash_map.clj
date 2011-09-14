(ns gears.hash-map
  (:require [gears.list :as list]))

(defn map-to-keys
  "Takes a function `f` and maps it to the keys of m and reconstructs the hash-map"
  [f m]
  (into {} (for [[k v] m] [(f k) v])))

(defn map-to-values
  "Takes a function `f` and maps it to the values of m and reconstructs the hash-map"
  [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn merge-maps-by-key
  "Given two maps, merge shared keys into a list.
   `(merge-maps-by-key {:a :b, :c :d} {:a :e, :c :f})`
   -> `{:a (:b :e), :c (:d :f)}`"
  [& maps]
  (let [all-key-values (set (flatten (map keys maps)))]
    (list/map-list-to-hashmap #(filter identity
                                       (map (fn [m] (get m %)) maps))
                              all-key-values)))

(defn merge-maps-by-key-value
  "Given a list of 'sets' (using that term loosely here, because they aren't actual clojure sets,
   merge them by a given key.  For example:
   `(merge-maps-by-key-value [[{:a 5 :b 6} {:a 6 :b 7} {:a 8 :b 9}] [{:a 5 :c 12} {:a 6 :c 14} {:a 8 :c 18}]] :a)`
   -> `[{:a 5 :b 6 :c 12} {:a 6 :b 7 :c 14} {:a 8 :b 9 :c 18}]`"
  [sets key]
  (let [all-key-values (set (flatten (map #(pmap (fn [m] (get m key)) %) sets)))]
    (reduce
     (fn [acc k] (cons (assoc (apply merge (flatten (map
						     #(map (fn [m] (dissoc m key))
							   (filter (fn [m] (= k (get m key))) %))
						     sets)))
			 key k)
		       acc))
     [] all-key-values)))
