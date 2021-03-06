(ns clojuremn.homepage
  (:require-macros [hiccups.core :as hiccups])
  (:require [hiccups.runtime :as hiccupsrt]
            [cljs.nodejs :as node]
            [cljs.reader :as edn]))

(def fs (node/require "fs"))
(def moment (node/require "moment"))

(defn make-link
  "makes a link to url if privided or looks up a url from the data."
  ([data name] (make-link data name (get-in data [:links name])))
  ([data name url]
     (if url
       [:a {:href url} name]
       name)))

(defn make-link-replacer
  "replaces all (link ...) with an actual call to make-link in a tree of data"
  [data]
  (fn link-replacer [node]
    (cond
      (and (list? node) (= (first node) 'link)) (apply make-link data (rest node))
      (coll? node) (mapv link-replacer node)
      :else node)))


(defn meetings []
  (let [data (edn/read-string (.readFileSync fs "meetings.edn" "utf8"))
        link-replacer (make-link-replacer data)]
    (reverse
     (sort-by :date (for [meeting (data :meetings)]
                   (assoc meeting :desc
                          (map link-replacer (meeting :desc))))))))

;; function because we want to use the *current* time
(defn today []
  (let [offset (if (-> moment .utc .isDST) "-0600" "-0500")]
    (.zone (moment) offset)))

(defn upcoming-meeting [today sorted-meetings]
  (let [today-str (.format today "YYYY-MM-DD")
        next-month-str (.format (.add today "months" 1) "YYYY-MM-DD")]
    (last (filter #(<= today-str (:date %))
                  sorted-meetings))))

(defn past-meetings [today meetings]
  (filter #(< (:date %) (.format today "YYYY-MM-DD")) meetings))

(defn html5 [& content]
  (str "<!DOCTYPE html>"
       "<html>"
       (hiccups/html content)
       "</html>"))

(defn format-datetime [date]
  (let [datetime (moment date "YYYY-MM-DD HH:mm")]
    (if (.isValid datetime)
      (.format datetime "dddd, MMMM Do, YYYY, [at&nbsp;]h:mma")
      (str (-> date
               (moment "YYYY-MM-DD")
               (.format "dddd, MMMM Do, YYYY"))
           ", at&nbsp;7:00pm"))))

(defn index []
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title "Clojure.mn - The Minnesota Clojure User Group"]
    [:link {:rel "stylesheet" :href "http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"}]
    [:link {:rel "stylesheet" :type "text/css" :href "/stylesheets/base.css"}]
    ]
   [:body
    [:div#content.container
     [:div.row
      ;; using "." syntax for 3+ classes doesn't work right in hiccups
      ;; 0.2.0, fixed in 0.3.0
      [:div {:class "col-sm-offset-3 col-sm-6 text-center"}
       [:h1 "Clo" [:em "j"] "ure.mn"]
       [:p [:em "The Minnesota Clojure Users Group"]]

       [:div.panel.panel-default
        [:div.panel-heading [:h2 "We've moved!"]]
        [:div.panel-body
         "We are now hosted at "
         [:a {:href "http://www.meetup.com/clojuremn/"} "Meetup.com"]
         ". Please join our group there for information about "
         [:a {:href "http://www.meetup.com/clojuremn/"} "upcoming"] " and "
         [:a {:href "http://www.meetup.com/clojuremn/events/past/"} "recent"]
         " meetings. This page only exists for historical reasons."]]

       ;;[:h4 "Older meetings (2015 and before):"]

       (comment
         [:p
          "Join our "
          [:a {:href "https://groups.google.com/group/clojuremn/boxsubscribe"} "mailing list" ]
          ". Follow us on "
          [:a {:href "http://twitter.com/clojuremn"} "Twitter"]
          "."
          ]
         [:img {:id "logo" :src "/images/lambda.png"}]


         [:p.text-center "We meet the second Wednesday of the month at 7pm at&nbsp;"
          [:a {:href "http://softwareforgood.com/"} "Software for Good"]
          "."]

         [:p.map
          [:a {:href "https://maps.google.com/maps?q=11+4th+St+NE+%23300+Minneapolis,+MN+55413&hl=en&sll=44.988878,-93.255115&sspn=0.0095,0.02032&vpsrc=0&hnear=11+4th+St+NE,+Minneapolis,+Minnesota+55413&t=m&z=16"} "Map/directions"]
          " (" [:a {:href "http://sfg.io/park"} "parking"] ")"])

       (let [meetings (meetings)]
         (list
          (if-let [{:keys [date desc]} (upcoming-meeting (today) meetings)]
            (let [date-str (format-datetime date)]
              [:div.panel.panel-default
               [:div.panel-heading.text-left [:em "Upcoming Meeting..."]]
               [:div.panel-body
                [:h2 date-str]
                [:p.text-left desc]]]))

[:div.meetings
          (for [{:keys [date desc]} (past-meetings (today) meetings)]
            [:div.meeting
             [:h2 (-> date
                      (moment "YYYY-MM-DD")
                      (.format "MMMM Do, YYYY"))]
             [:p.text-left desc]])]))


       [:p {:class "footer"}
        [:a {:href "https://github.com/bmaddy/clojure.mn"} "Fork this site!"]]]]]
    ]))
