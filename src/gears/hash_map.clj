(ns gears.hash-map
  (:require [gears.list :as list]))

(defn map-to-keys
  "Takes a function `f` and maps it to the keys of m and reconstructs the
   hash-map

  => (map-to-keys #(+ % 1) {1 5, 2 4})
  {2 5, 3 4}"
  [f m]
  (into {} (for [[k v] m] [(f k) v])))

(defn map-to-values
  "Takes a function `f` and maps it to the values of m and reconstructs the
   hash-map

  => (map-to-values #(+ % 1) {1 5, 2 4})
  {1 6, 2 5}"
  [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn merge-maps-by-key
  "Given two maps, merge shared keys into a list.

   => (merge-maps-by-key {:a :b, :c :d} {:a :e, :c :f})
   {:a (:b :e), :c (:d :f)}"
  [& maps]
  (let [all-key-values (set (flatten (map keys maps)))]
    (list/map-list-to-hashmap #(filter identity
                                       (map (fn [m] (get m %)) maps))
                              all-key-values)))

(defn merge-map-seqs-by-key-value
  "Given a list of 'sets' (using that term loosely here, because they
   aren't actual clojure sets, merge them by a given key.  For example:

   => (merge-map-seqs-by-key-value :a [{:a 5 :b 6} {:a 6 :b 7} {:a 8 :b 9}]
                                      [{:a 5 :c 12} {:a 6 :c 14} {:a 8 :c 18}])
   ({:a 8 :b 9 :c 18} {:a 6 :b 7 :c 14} {:a 5 :b 6 :c 12})"
  [key & sets]
  (let [all-key-values (set (flatten (map #(map (fn [m] (get m key)) %) sets)))]
    (reduce
     (fn [acc k] (cons (assoc (apply merge (flatten (map
						     #(map (fn [m] (dissoc m key))
							   (filter (fn [m] (= k (get m key))) %))
						     sets)))
			 key k)
		       acc))
     [] all-key-values)))

(defn map-from-headers-and-rows
  "Take the headers given and construct a map where the keys are the header
   values and the values are the corresponding row values.  For example:

   => (map-from-headers-and-rows [:a :b :c] [[1 2 3][4 5 6]])
   ({:a 1 :b 2 :c 3} {:a 4 :b 5 :c 6})"
  [headers rows]
  {:pre [(> (count headers) 0)
         (apply = (map count rows))
         (= (count headers) (count (first rows)))]}
  (reverse (reduce #(cons (zipmap headers %2) %1) [] rows)))

(defn create-map-from-key-value
  "Given a map, take a given key's value and create a new map using it as the key
   and the rest of the map as the value

  => (create-map-from-key-value {:a :b :c :d :e :f} :a)
  {:b {:c :d :e :f}}"
  [map key]
  {:pre [(contains? map key)]}
  {(get map key) (dissoc map key)})

(defn invert-outer-for-inner-keys
  "Given a map of maps, where the inner maps share keys, invert the inner keys
   for the outter keys and merge the maps]

  => (invert-outer-for-inner-keys {:a {:b :c :e :f} :d {:b :g :h :i}})
  {:b {:a :c :d :g} :e {:a :f} :h {:d :i}}"
  [outer-map]
  (apply (partial merge-with merge)
         (map (fn [outer-key]
                (let [inner-map (get outer-map outer-key)]
                  (into {} (for [[k v] inner-map]
                             [k {outer-key v}])))) (keys outer-map))))

(defn deep-merge
  "Given maps, create a single large map where shared keys are merged,
   not destroyed.  Only works for a map of maps

  => (deep-merge {:a {:b :c} :d {:e :f}} {:a {5 6} :d {7 8 9 10} :e
                 {\"elf\" \"santa clause\"}})
     {:a {:b :c 5 6} :d {:e :f 7 8 9 10} :e {\"elf\" \"santa clause\"}}"
  [& maps]
  (let [all-keys (set (flatten (map keys maps)))]
    (into {} (for [key all-keys]
               [key
                (apply merge (map #(get % key) maps))]))))
