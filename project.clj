(defproject gears "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [mmemail "1.0.1"]
                 [sandbar/sandbar "0.3.0"]
                 [clj-time "0.3.0"]]
  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]
		     [lein-marginalia "0.6.0"]]
  :jvm-opts ["-agentlib:jdwp=transport=dt_socket,server=y,suspend=n"])
