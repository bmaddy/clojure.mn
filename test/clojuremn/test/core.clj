(ns clojuremn.test.core
  (:use clojuremn.core
        ring.mock.request  
        clojure.test))

(deftest test-app
  (testing "main route"
           (let [response (app (request :get "/"))]
             (is (= (:status response) 200)))))

