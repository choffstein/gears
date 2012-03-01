(defproject gears "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.3.0-beta1"]
                 [clj-time "0.3.3"]
                 [org.clojure/algo.generic "0.1.0"]]

  :dev-dependencies [[lein-marginalia "0.7.0-SNAPSHOT"]
                     [lein-clj-doc-test "0.0.1-SNAPSHOT"]]

  :repositories {"nfr-releases" "s3p://newfound-mvn-repo/releases/"
                 "nfr-snapshots" "s3p://newfound-mvn-repo/snapshots/"})
