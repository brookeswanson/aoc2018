(ns aoc.day4
  (:require
   [aoc.util :as util])
  (:import
   (java.time LocalDateTime Duration)
   (java.time.format DateTimeFormatter)))

(defn during?
  "checks if the sleep is on a guards shift"
  [guard sleep] 
  (and (.isAfter (:sleep-start sleep) (:start guard) )
       (.isBefore (:sleep-start sleep) (:end guard))))

(defn make-description
  [[_ date action]]
  {:date
   (LocalDateTime/parse date
    (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm"))
   :guard (re-find #"\d+" action)})

(defn make-shift
  "given a gaurd with a date make a shift."
  [start]
  {:guard (:guard start)
   :start (:date start) 
   :end (.plusHours (:date start) 2)})

(defn make-sleep-time
  [sleep wake]
  (let [sleep-date (:date sleep)
        wake-date (:date wake)
        sleep-min (.getMinute sleep-date)
        wake-min (.getMinute wake-date)]
    {:sleep-start sleep-date
     :minute-range (range sleep-min wake-min)
     :minutes (- wake-min sleep-min)}))

(defn find-guard-shift-dates
  [sleep-shifts guard]
  (let [relevant-shifts (filter (partial during? guard) sleep-shifts)
        total-sleep-time (reduce + (map :minutes relevant-shifts))
        sleep-counts (mapcat :minute-range relevant-shifts)]
    (assoc guard
           :total-sleep-time total-sleep-time
           :sleep-counts sleep-counts)))

(defn get-totals-of-all-shifts
  [guard]
  (let [shifts
        (frequencies (mapcat :sleep-counts (second guard)))
        max-shift (sort-by second > (map identity shifts))]
    {:guard (read-string (first guard))
     :total-sleep-time-all (reduce + (map :total-sleep-time (second guard)))
     :max-count-of-at-minutes (second (first max-shift)) 
     :max-minute (ffirst max-shift)}))

(def schedule
  (->> (util/read-input-file "input-day-4.txt")
       (map (partial re-matches #"\[([:\s\d-]+)\] (.+)"))
       (map make-description)
       (sort-by :date)))

(def wake-and-sleep-shift
  (->>
   (filter #(not (:guard %))
           schedule)
   (partition 2 2)
   (map #(make-sleep-time (first %) (second %)))))

(defn find-guard-shift
  [sort-key]
  (->> (filter :guard schedule)
       (map make-shift)
       (map (partial find-guard-shift-dates wake-and-sleep-shift))
       (group-by :guard)
       (map get-totals-of-all-shifts)
       (sort-by sort-key)
       reverse
       first))

(defn guard-problem
  [sort-by-key]
  (let [guard (find-guard-shift sort-by-key)]
    (* (:max-minute guard)
       (:guard guard))))

(def part1 (guard-problem :total-sleep-time-all))
(def part2 (guard-problem :max-count-of-at-minutes))

(def message (util/make-message "Day 4" part1 part2))

