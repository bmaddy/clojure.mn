(ns clojuremn.homepage
  (:require-macros [hiccups.core :as hiccups])
  (:require [hiccups.runtime :as hiccupsrt]
            [cljs.nodejs :as node]
            [cljs.reader :as edn]))

(def fs (node/require "fs"))
(def moment (node/require "moment"))

(defn make-link
  ([data name] (make-link data name (get-in data [:links name])))
  ([data name url]
     (if url
       [:a {:href url} name]
       name)))

(defn meetings []
  (let [data (edn/read-string (.readFileSync fs "meetings.edn" "utf8"))
        link (partial make-link data)]
    (reverse
     (sort-by :date (for [meeting (data :meetings)]
                   (assoc meeting :desc
                          (for [[f & args :as str] (meeting :desc)]
                            (if (= 'link f)
                              (apply link args)
                              str))))))))

;; function because we want to use the *current* time
(defn today []
  (let [offset (if (-> moment .utc .isDST) "-0600" "-0500")]
    (.zone (moment) offset)))

(defn upcoming-meeting [today meetings]
  (let [today-str (.format today "YYYY-MM-DD")
        next-month-str (.format (.add today "months" 1) "YYYY-MM-DD")]
    (first (filter #(and (<= today-str (:date %))
                         (< (:date %) next-month-str))
                   meetings))))

(defn past-meetings [today meetings]
  (filter #(< (:date %) (.format today "YYYY-MM-DD")) meetings))

(defn html5 [& content]
  (str "<!DOCTYPE html>"
       "<html>"
       (hiccups/html content)
       "</html>"))

(defn index []
  (html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title "Clojure.mn - The Minnesota Clojure User Group"]
    [:link {:rel "stylesheet" :type "text/css" :href "/stylesheets/base.css"}]]
   [:body
    [:div {:id "content"}
     [:h1 "Clo" [:span {:id "clojure-j"} "j"] "ure.mn"]
     [:p {:id "tagline"} "The Minnesota Clojure Users Group"]
     [:p {:class "announcement"}
      "Join our "
      [:a {:href "http://groups.google.com/group/clojuremn"} "mailing list" ]
      ". Follow us on "
      [:a {:href "http://twitter.com/clojuremn"} "Twitter"]
      "."
      ]
     [:img {:id "logo" :src "/images/lambda.png"}]

     [:p "We meet the first Wednesday of the month at 7pm at "
      [:a {:href "http://www.smartthings.com/"} "SmartThings"]
      "."]

     [:p.map
      [:a {:href "https://maps.google.com/maps?q=11+4th+St+NE+%23300+Minneapolis,+MN+55413+(SmartThings)&hl=en&sll=44.988865,-93.255102&sspn=0.010638,0.02708&hnear=11+4th+St+NE+%23300,+Minneapolis,+Hennepin,+Minnesota+55413&t=m&z=16&iwloc=A"} "Map/directions"]]


     (let [meetings (meetings)]
       (list*
        (if-let [{:keys [date desc]} (upcoming-meeting (today) meetings)]
          (let [date-str (-> date (moment "YYYY-MM-DD") (.format "dddd, MMMM Do, YYYY"))]
            [:div.upcoming-meeting
             [:h2 "Upcoming Meeting: " date-str ", at 7:00pm"]
             #_[:p desc]]))

        (for [{:keys [date desc]} (past-meetings (today) meetings)]
          [:div.meeting
           [:h2 (-> date
                    (moment "YYYY-MM-DD")
                    (.format "MMMM Do, YYYY"))]
           [:p desc]])))
     ]

    [:p {:class "footer"}
     [:a {:href "https://github.com/bmaddy/clojure.mn"} "Fork this site!"]]
    ]))
