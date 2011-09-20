(ns gears.date-time
  (:use [clj-time.core]
        [clj-time.format]
        [clj-time.coerce]))

(defn todays-date-as-str
  "Today's date as a yyyy-MM-dd string, unless a format is provided."
  ([] (todays-date-as-str "yyyy-MM-dd"))
  ([format] (let [custom-formatter (formatter format)
                  today (now)]
              (unparse custom-formatter today))))

(defn current-time-as-str
  "The current time in yyyy-MM-dd'T'HH:mmZ unless a format is provided."
  ([] (todays-date-as-str "yyyy-MM-dd'T'HH:mmZ")))

(defn date-to-string
  "Convert a Java Date object to a yyyy-MM-dd formatted string, unless a format is provided."
  ([date] (date-to-string "yyyy-MM-dd"))
  ([date format]  (let [custom-formatter (formatter format)]
                    (parse custom-formatter date))))

(defn string-to-date
  "Convert a formatted string to a Java Date object.  If no format is given,
   automatically attempts to convert with yyyy-MM-dd and MM/dd/yyyy formats."
  [date-string & given-formats]
  (let [formats (or given-formats ["MM/dd/yyyy" "yyyy-MM-dd"])
        date-objects (filter identity (map #(try (parse (custom-formatter %)
                                                         date-string)
                                                 (catch Exception e nil)) formats))]
    (if (empty? date-objects)
        (throw (java.text.ParseException. "Unable to parse string" 0))
        (first date-objects))))
