(ns gears.development-mode)

(def production?
     (= "production" (get (System/getenv) "APP_ENV")))

(def development?
     (not production?))
