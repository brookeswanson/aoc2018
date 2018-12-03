(ns aoc.day1
  (:require
   [aoc.util :as util]))

(def frequency-list (pmap util/parse-int
                          (util/read-input-file "input-day-1.txt")))

(def part1 (reduce + frequency-list))

(def part2 (->> frequency-list
                (cycle) ;; forever repeat all the things
                (reductions +) ;; lazy sequence of the totals
                (util/repeated) ;; lazy sequence of the repeated elements
                first)) 

(def make-message (util/make-message "Day 1" part1 part2))
