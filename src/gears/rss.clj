(ns gears.rss
  ;;(:use clojure.contrib.prxml)
  )

(comment
  (defn- articles [data]
  (reduce
   (fn [feed v]
     (conj feed
           [:item
            [:title (:title v)]
            [:url (:url v)]
            [:description (:description v)]]))
   () data))

(defn rss-stream [title link description data]
  "Creates an rss-stream for series of data.
   `title` is the title of the stream.
   `link` is the URL of the stream host.
   `description` is the description of the stream.
   `data` is a list of maps with :title, :url, and :description fields.
   Example:
      `(rss-stream \"My Blog\" \"http://someurl.com\" \"A description of my blog\"
                   [{:title \"First Post!\" :url \"http://someurl.com/first-post\"
                     :description \"The first post for my blog!\"}
                    {:title \"Post Numero Dos!\" :url \"http://someurl.com/post-numero-dos\"
                     :description \"My second post!  I discuss my travels in Spain.\"}])`"
  (with-out-str
    (prxml [:decl! {:version "1.0"}]
           [:rss {:version "2.0"}
            [:channel
             [:title title]
             [:link link]
             [:description description]
             (articles data)]])))
)
