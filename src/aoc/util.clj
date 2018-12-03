(ns aoc.util
  (:require
   [clojure.string :as str]))

(defn read-input-file
  [file-name]
  (-> (clojure.java.io/resource file-name)
      (slurp)
      (str/split-lines)))

(defn parse-int [s]
  (Integer. (re-find #"-?\d+" s)))

(defn repeated [coll]
  ((fn get-repeated [seen xs]
     (lazy-seq
      (when-let [[y & ys] (seq xs)]
        (case (seen y)
          :repeat (get-repeated seen ys)
          :once (cons y (get-repeated (assoc seen y :repeat) ys))
          (get-repeated (assoc seen y :once) ys)))))
   {} coll))
