(ns aoc.core
  (:gen-class)
  (:require
   [aoc.day1 :as day1]
   [aoc.day2 :as day2]
   [aoc.day3 :as day3]
   [aoc.day4 :as day4]))

(defn -main
  [& args]
  (let [day (first args)
        message (case day
                  "day1" 
                  day1/make-message
                  "day2"
                  day2/message
                  "day3"
                  day3/message
                  "day4"
                  day4/message
                  "Meow")]
    (println message)
    (shutdown-agents)))
