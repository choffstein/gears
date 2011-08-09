(ns gears.date
  (:import [java.util.Date]
	   [java.text DateFormat SimpleDateFormat]
	   [java.util.Calendar]
           [java.text.ParseException]))

(defn todays-date
  "Today's date as a yyyy-MM-dd string, unless a format is provided"
  [& format]
  (let [format (or (first format) "yyyy-MM-dd")
        df (new SimpleDateFormat format)
        today (new java.util.Date)]
    (.format df today)))

(defn date-to-string
  "Convert a Java Date object to a yyyy-MM-dd formatted string, unless a format is provided"
  ([date] (date-to-string "yyyy-MM-dd"))
  ([date format]  (let [df (SimpleDateFormat. format)]
                    (.format df date))))

(defn string-to-date
  "Convert a formatted string to a Java Date object.  If no format is given,
   automatically attempts to convert with yyyy-MM-dd and MM/dd/yyyy formats."
  [date-string & given-formats]
  (let [formats (or given-formats ["MM/dd/yyyy" "yyyy-MM-dd"])
        date-objects (filter identity (map #(try (.parse (SimpleDateFormat. %) date-string)
                                                 (catch Exception e nil)) formats))]
    (if (empty? date-objects)
        (throw (java.text.ParseException. "Unable to parse string" 0))
        (first date-objects))))
