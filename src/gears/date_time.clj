(ns gears.date-time
  (:require [clj-time.core :as time]
            [clj-time.format :as format]
            [clj-time.coerce :as coerce]))

(defn todays-date-as-str
  "Today's date as a yyyy-MM-dd string, unless a format is provided."
  ([] (todays-date-as-str "yyyy-MM-dd"))
  ([format] (let [custom-formatter (format/formatter format)
                  today (time/now)]
              (format/unparse custom-formatter today))))

(defn current-time-as-str
  "The current time in yyyy-MM-dd'T'HH:mmZ unless a format is provided."
  ([] (todays-date-as-str "yyyy-MM-dd'T'HH:mmZ")))

(defn date-to-string
  "Convert a Java Date object to a yyyy-MM-dd formatted string, unless a format is provided."
  ([date] (date-to-string date "yyyy-MM-dd"))
  ([date format]  (let [custom-formatter (format/formatter format)]
                    (format/unparse custom-formatter date))))

(defn string-to-date
  "Convert a formatted string to a Java Date object.  If no format is given,
   automatically attempts to convert with yyyy-MM-dd and MM/dd/yyyy formats."
  [date-string & given-formats]
  (let [formats (or given-formats ["MM/dd/yyyy" "yyyy-MM-dd"])
        date-objects (filter identity (map #(try (format/parse (format/formatter %)
                                                               date-string)
                                                 (catch Exception e nil)) formats))]
    (if (empty? date-objects)
        (throw (java.text.ParseException. (str "Unable to parse string: " date-string) 0))
        (first date-objects))))

(defn date-to-long [date]
  (coerce/to-long date))

(defn long-to-date [long]
  (coerce/from-long long))
