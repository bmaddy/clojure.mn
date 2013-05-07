(ns clojuremn.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [clojuremn.homepage :refer [index]]))

(defn handler [req]

  {:status   200
   :headers {"Content-Type" "text/html"}
   :body    (index)})

(def app
  (-> handler
      (wrap-resource "public")
      (wrap-file-info)))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))
