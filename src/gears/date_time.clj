(ns gears.date-time
  (:import [java.util.Date]
	   [java.text DateFormat SimpleDateFormat]
	   [java.util.Calendar]
           [java.text.ParseException]))

(defn todays-date
  "Today's date as a yyyy-MM-dd string, unless a format is provided."
  ([] (todays-date "yyyy-MM-dd"))
  ([format] (let [df (new java.text.SimpleDateFormat format)
                  today (new java.util.Date)]
              (.format df today))))

(defn current-time
  "The current time in yyyy-MM-dd'T'HH:mmZ unless a format is provided."
  ([] (current-time "yyyy-MM-dd'T'HH:mmZ"))
  ([format]  (let [df (java.text.SimpleDateFormat. format)]
               (.toString (.format df (java.util.Date.))))))

(defn date-to-string
  "Convert a Java Date object to a yyyy-MM-dd formatted string, unless a format is provided."
  ([date] (date-to-string "yyyy-MM-dd"))
  ([date format]  (let [df (java.text.SimpleDateFormat. format)]
                    (.format df date))))

(defn string-to-date
  "Convert a formatted string to a Java Date object.  If no format is given,
   automatically attempts to convert with yyyy-MM-dd and MM/dd/yyyy formats."
  [date-string & given-formats]
  (let [formats (or given-formats ["MM/dd/yyyy" "yyyy-MM-dd"])
        date-objects (filter identity (map #(try (.parse (java.text.SimpleDateFormat. %)
                                                         date-string)
                                                 (catch Exception e nil)) formats))]
    (if (empty? date-objects)
        (throw (java.text.ParseException. "Unable to parse string" 0))
        (first date-objects))))
