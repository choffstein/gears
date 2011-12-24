(defproject gears "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.3.0-beta1"]
                 [clj-time "0.3.3"]]

  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"
                      :exclusions [org.clojure/clojure
                                  org.clojure/clojure-contrib]]
		     [lein-marginalia "0.6.1"]
                     [lein-clj-doc-test "0.0.1-SNAPSHOT"]]

  :repositories {"nfr-releases" "s3p://newfound-mvn-repo/releases/"
                 "nfr-snapshots" "s3p://newfound-mvn-repo/snapshots/"})
