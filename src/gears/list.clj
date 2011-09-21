(ns gears.list)

(defn map-list-to-hashmap
  "Takes a function `f` and a list `l` and creates a hash-map with keys l and values `(f l)`

  => (map-list-to-hashmap #(+ % 5) '(1 2 3 4 5))
  {1 6, 2 7, 3 8, 4 9, 5 10}"
  [f l]
  (reduce (fn [m e] (assoc m e (f e))) {} l))
