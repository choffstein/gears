(ns gears.exceptions
  (:require [clojure.string :as string]))

(defn stack-trace-as-vec
  "Take an exception and create a vector of strings from it"
  [e]
  (reduce #(cons (.toString %2) %1) [] (.getStackTrace e)))

(defn stack-trace-as-string
  "Take an exception and create a stack-trace string from it"
  [e]
  (string/join "\n" (stack-trace-as-vec e)))
