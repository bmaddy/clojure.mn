(defproject clojuremn "1.0.0-SNAPSHOT"
  :description "The web site for Clojure dot MN, the Minnesota Clojure group."
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.1.8"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [hiccup "1.0.3"]]
  :min-lein-version "2.0.0"
  :main clojuremn.core
  :profiles {:dev {:dependencies [[ring-mock "0.1.4"]]}}
  :exclusions [org.mortbay.jetty/servlet-api])
