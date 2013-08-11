(ns clojuremn.core)

(defn -main [& args]
  (println (apply str (map [\space "world" "hello"] [2 0 1]))))

(set! *main-cli-fn* -main)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
