(ns gears.list)

(defn map-list-to-hashmap [l f]
  "Takes a function `f` and a list `l` and creates a hash-map with keys l and values (f l)"
  (reduce (fn [m e] (assoc m e (f e)))))
