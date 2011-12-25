(ns gears.parallel
  (:require  [clojure.algo.generic.math-functions :as math]))

(defn map-reduce [map-fn reduce-fn coll]
  (let [n-cores (.availableProcessors (Runtime/getRuntime))
        coll-groups (partition-all (math/ceil (/ (count coll) n-cores)) coll)]
    (reduce-fn
     (pmap (fn [coll-group]
             (map map-fn coll-group))
           coll-groups))))

(defn map-reduce-concat [map-fn coll]
  (map-reduce (partial apply concat) map-fn coll))

(defn map-reduce-merge [map-fn coll]
  (map-reduce (partial apply merge) map-fn coll))
