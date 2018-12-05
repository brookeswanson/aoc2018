(ns aoc.day2
  (:require
   [aoc.util :as util]
   [clojure.data :as data]))

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

(defn diff-between
  [s1 s2]
  (->>
   (data/diff (vec s1) 
              (vec s2))
   (map (partial remove nil?))))

(defn only-one-diff?
  [d1]
  (= 1
     (count (first (last d1)))
     (count (second (last d1)))))

(defn make-diff
  [data]
  (->> (for [d1 data
             d2 data]
         [d1 d2 (diff-between d1 d2)])
       (filter only-one-diff?)
       (first)
       (last)
       (last)
       (apply str)))

(def part2 (make-diff box-numbers))

(def message (util/make-message "Day 2" part1 part2))
