(ns clojuremn.core
  (:require [cljs.nodejs :as node]
            [clojuremn.homepage :as homepage]))

(def http (node/require "http"))
(def fs (node/require "fs"))
(def static-server (.-Server (node/require "node-static")))

(def file-server (static-server. "resources/public"))

(defn success [res content-type]
  (.writeHead res 200 (js-obj "Content-Type" content-type)))

(defn get-file [type file req res]
  (success res type)
  (.end res (.readFileSync fs (str "resources/public/" file))))

(defn index [_ res]
  (success res "text/html")
  (.end res (homepage/index)))

(def routes
  {"/" index
   "/images/lambda.png" (partial get-file "image/png" "images/lambda.png")
   "/stylesheets/base.css" (partial get-file "text/css" "stylesheets/base.css")})

(defn handler [req res]
  (println "Request: " (.-url req))
  (if-let [handler (routes (.-url req))]
    (handler req res)
    (.serve file-server req res)))

(defn server [handler port url]
     (-> (.createServer http handler)
         (.listen port url)))

(defn -main [& _]
  (let [server (.createServer http handler)
        port (or (-> js/process .-env .-PORT) 1337)]
    (.listen server port)
    (println (str "Server running on port " port))))

(set! *main-cli-fn* -main)
