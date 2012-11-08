(ns clojuremn.homepage
  (:use [hiccup.core :only [html h]]
        [hiccup.page-helpers :only [doctype include-css]]))

(def common-links {
    "Nick Bauman" "https://github.com/nickbauman/"
    "Ben Peirce" "http://bpeirce.me"
    "Benjamin Ebby" "http://twitter.com/akpanydre"
    "Brian Maddy" "http://twitter.com/bmaddy"
    "Dan Callahan" "http://dancallahan.info/"
    "Tom Marble" "http://twitter.com/tmarble"
    "Ted Naleid" "https://twitter.com/tednaleid"
    "Scott Fritchie" "https://twitter.com/slfritchie"
    "Nate Young" "https://twitter.com/natesbrain"
  })

(defn link
  ([name] (link name (common-links name)))
  ([name url]
    (if url
      [:a {:href url} name]
      name)))


(defn index-body []
  (html
   (doctype :html5)
   [:head
    [:meta {:charset "utf-8"}]
    [:title "Clojure.mn - The Minnesota Clojure User Group"]
    (include-css "/stylesheets/base.css")]
   [:body
    [:div {:id "content"}
     [:h1 "Clo" [:span {:id "clojure-j"} "j"] "ure.mn"]
     [:p {:id "tagline"} "The Minnesota Clojure Users Group"]
     [:p {:class "announcement"}
      "Join our "
      (link "mailing list" "http://groups.google.com/group/clojuremn")
      ". Follow us on "
      (link "Twitter" "http://twitter.com/clojuremn")
      "."
      ]
     [:img {:id "logo" :src "/images/lambda.png"}]

     [:p "We meet the first Wednesday of the month at 7pm at "
      (link "Refactr" "http://refactr.com/contact/")
      "."]





     [:h2 "Next Meeting: Wednesday, December 5th, 2012"]

     [:h2 "November 7th, 2012"]
     [:p
      "Hack Fest - Multiple small groups worked on all sorts of things including "
      (link "Nongrata" "https://github.com/tmarble/nongrata") ", "
      (link "Light Table" "http://www.lighttable.com/") ", "
      (link "Clojure Koans" "http://clojurekoans.com/") ", "
      (link "Conduit" "https://github.com/jduey/conduit") ", "
      "and discussing Erlang's QuickCheck."]

     [:h2 "October 3, 2012"]
     [:p
      "Hack Fest - We went through the " (link "Datomic tutorial" "http://docs.datomic.com/tutorial.html")]

     [:h2 "September 5, 2012"]
     [:p
      (link "Nate Young") " - using clojure at "
      (link "Revelytix" "http://www.revelytix.com/")
      [:br]
      (link "Brian Maddy") " - "
      (link "Tetris" "https://github.com/bmaddy/tetris")
      " with an "
      (link "FRP" "http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.93.8928")
      " structure"]

     [:h2 "August 1, 2012"]
     [:p 
      (link "Ben Peirce") " - "(link "Immutant" "http://immutant.org/")
      [:br]
      (link "Scott Fritchie") " - Creating systems with thousands or millions of actors "]

     [:h2 "June 6, 2012"]
     [:p 
      (link "Nick Bauman")
      " presented his ClojureScript work on the "
      (link "Nongrata" "https://github.com/tmarble/nongrata")
      " project.  We then held lightning talks with "
      (link "Dan Callahan")
      " discussing "
      (link "Persona" "http://identity.mozilla.com/post/18038609895/introducing-mozilla-persona")
      ", "
      (link "Tom Marble")
      " demoing his "
      (link "Raspberry Pi" "http://www.raspberrypi.org/")
      " serving the Nongrata website, and "
      (link "Brian Maddy")
      " talking about "
      (link "core.logic" "https://github.com/clojure/core.logic")
      "."]

     [:h2 "May 2, 2012"]
     [:p
      (link "Dan Callahan")
      " talked about "
      (link "Mozilla Persona" "http://identity.mozilla.com/post/18038609895/introducing-mozilla-persona")
      " and the group hacked on a "
      (link "Noir implementation of that technology" "https://github.com/tmarble/nongrata")
      "."]

     [:h2 "April 4, 2012"]
     [:p
      (link "Ted Naleid")
      " gave his "
      (link "review of Clojure/West 2012" "http://tednaleid.github.com/showoff-clojurewest/#1")
      " and "
      (link "Ben Peirce")
      " talked about "
      (link "some random Clojure news" "/presentations/latest-news/latest-news.html")
      "."]

     [:h2 "March 7, 2012"]
     [:p
      (link "Nick Bauman")
      " talked about "
      (link "redit-s-expression" "http://tinyurl.com/redis-s-expression")
      " and "
      (link "Brian Maddy")
      " talked about "
      (link "Pallet" "https://docs.google.com/file/d/0B593N-xk-VfFVTJtNndQZk1UbFNRc1MzXzFxR21pZw/edit")
      "."]

     [:h2 "February 1, 2012"]
     [:p
      (link "Tom Marble")
      " shared his experiences "
      (link "developing a web application using Noir" "/presentations/noir-is-the-new-black.org")
      " and "
      (link "Benjamin Ebby")
      " talked about "
      (link "using JRuby and Rake with Clojure" "http://outcastgeek.com/blog/build-your-polyglot-app-with-jruby-and-rake.html")
      "."]

     [:h2 "December 7, 2011"]
     [:p 
      (link "Brian Maddy") " and " (link "Ben Peirce") " gave a review of "
      (link "Clojure/conj 2011" "http://clojure-conj.org/")
      " and "
      (link "Dan Callahan")
      " talked about "
      (link "Go" "http://golang.org/")
      "'s approach to concurrency."]

     [:h2 "November 2, 2011"]
     [:p 
      (link "Brian Maddy") " talked about "
      (link "Functional Relational Programming" "https://docs.google.com/a/brianmaddy.com/presentation/pub?id=1kXvRBksA7DtDpie3JOU7vY2VH23yLFpUVFc98BYLKnk&start=false&loop=false&delayms=3000#slide=id.p")
      " and " (link "Ben Peirce") " talked about some tools for command line Clojure programs."]

    [:h2 "October 5, 2011"]
     [:p 
      "Greg Allen talked about Clojure's "
      (link "java interop" "https://docs.google.com/present/edit?id=0AVDE9VMKvEjHZGdrZzJwcjJfMTEyYzZ6NmJwZHE&hl=en_US")
      " and " (link "Ben Peirce") " talked about the relational algebra as its used in Clojure.sets."]

     [:h2 "September 7, 2011"]
     [:p 
      (link "Ben Peirce") " talked about "
      (link "Clojure sequence implementations" "http://bpeirce.me/clojure-sequence-implementations.html")
      " and " (link "Brian Maddy") " talked about "
      (link "ClojureScript" "https://docs.google.com/a/brianmaddy.com/present/view?id=dwqzvn5_14f7nm7ngk")
      "."]

     [:h2 "August 10, 2011"]
     [:p 
      (link "Ben Peirce") " walked through how he used "
      (link "Ring" "https://github.com/mmcgrana/ring")
      " and "
      (link "Hiccup" "https://github.com/weavejester/hiccup")
      " to make "
      (link "this site" "https://github.com/bpeirce/clojure.mn")
      ".  "
      (link "Brian Maddy") " showed how he used "
      (link "Enlive" "https://github.com/cgrand/enlive")
      " for one of his sites."
      ]
      
     [:h2 "July 13, 2011"]
     [:p "We discussed "
      (link "Clojure/conj 2011" "http://clojure-conj.org")
      ", compared the JVM to the CLR, and surveyed the NoSQL landscape."]
     ]

    [:p {:class "footer"}
     (link "Fork this site!" "https://github.com/bmaddy/clojure.mn") ]
    ]))
