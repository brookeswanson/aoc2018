(ns aoc.day3
  (:require
   [aoc.util :as util]
   [clojure.string :as str]))

(defn make-coordinates
  [[id _ x y w h]]
  {:id id
   :x (read-string x)
   :y (read-string y)
   :width (read-string w)
   :height (read-string h)})

(defn create-points
  [{:keys [x y width height]}]
  (for [i (range x (+ x width))
        j (range y (+ y height))]
    [i j]))

(def coordinates (->> (util/read-input-file "input-day-3.txt")
                      (map #(str/split % #":? |,|x"))
                      (map make-coordinates)))

(def freqs-of-coordinates (->> coordinates
                               (mapcat create-points)
                               frequencies))

(defn overlap? [stuff]
  (= #{1}
     (into #{} (map freqs-of-coordinates (second stuff)))))

(def part1 (->> freqs-of-coordinates 
                vals
                (filter #(> % 1))
                count))

(def part2 (->> coordinates
                (map (juxt :id create-points))
                (filter overlap?)
                ffirst))

(def message (util/make-message "Day 3" part1 part2))
