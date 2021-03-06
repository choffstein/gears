(ns gears.date-time
  (:require [clj-time.core :as time]
            [clj-time.format :as format]
            [clj-time.coerce :as coerce]))

(defn todays-date ^org.joda.time.DateTime []
  (time/now))

(defn date-to-string
  "Convert a Java Date object to a yyyy-MM-dd formatted string,
   unless a format is provided."
  ^String
  ([^org.joda.time.DateTime date] (date-to-string date "yyyy-MM-dd"))
  ([^org.joda.time.DateTime date ^String format]
     (let [custom-formatter (format/formatter format)]
       (format/unparse custom-formatter date))))

(defn todays-date-as-str
  "Today's date as a yyyy-MM-dd string, unless a format is provided."
   ^String
  ([] (todays-date-as-str "yyyy-MM-dd"))
  ([^String format] (date-to-string (todays-date) format)))

(defn current-time-as-str
  "The current time in yyyy-MM-dd'T'HH:mmZ unless a format is provided."
   ^String
  ([] (todays-date-as-str "yyyy-MM-dd'T'HH:mmZ")))

(defn string-to-date
  "Convert a formatted string to a Java Date object.  If no format is given,
   automatically attempts to convert with yyyy-MM-dd and MM/dd/yyyy formats."
  [^String date-string & given-formats]
  (let [formats (or given-formats ["MM/dd/yyyy" "yyyy-MM-dd"])
        date-objects (filter identity
                             (map #(try (format/parse (format/formatter %)
                                                      date-string)
                                        (catch Exception e nil)) formats))]
    (if (empty? date-objects)
        (throw (java.text.ParseException. (str "Unable to parse string: "
                                               date-string) 0))
        (first date-objects))))

(defn date-to-long ^Long [^org.joda.time.DateTime date]
  (coerce/to-long date))

(defn long-to-date [^Long long]
  (coerce/from-long long))

(defn between?
  "Checks whether a date is within a given range of dates

  => (between? (clj-time.core/date-time 1987) (clj-time.core/date-time 1986)
                                              (clj-time.core/date-time 1990))
  true
  => (between? (clj-time.core/date-time 1994) (clj-time.core/date-time 1986)
                                              (clj-time.core/date-time 1990))
  false"
  [^org.joda.time.DateTime date ^org.joda.time.DateTime begin ^org.joda.time.DateTime end]
  (time/within? (time/interval begin end)
            date))
