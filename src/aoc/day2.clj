(ns aoc.day2
  (:require
   [aoc.util :as util]))

(defn has-exactly-2-or-3
  [counts]
  (or (contains? counts 2)
      (contains? counts 3)))

(def box-numbers (util/read-input-file "input-day-2.txt"))

(def part1
  (let [box-numbers-part1 (->> box-numbers
                               (pmap frequencies)
                               (pmap vals)
                               (pmap set))]
    (* (count (filter #(contains? % 2) box-numbers-part1))
       (count (filter #(contains? % 3) box-numbers-part1)))))

(def part2 "Meow")

(def message (util/make-message "Day 2" part1 part2))
