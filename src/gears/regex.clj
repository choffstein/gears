(ns gears.regex)

(defn matches-any-in?
  "Take a list of regex expressions and see if any of them match the given value"
  [value match-list]
  (reduce (fn [acc e] (or acc (not (nil? (re-find e value))))) false match-list))
