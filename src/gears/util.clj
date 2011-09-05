(ns gears.util
  (:import (java.util UUID)))

(defn uuid
  "Construct a unique identifier as a string"
  []
  (.toString (UUID/randomUUID)))
