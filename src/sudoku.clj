(ns sudoku
  (:require [clojure.set :as set]))

(def board identity)

(def all-values #{1 2 3 4 5 6 7 8 9})

(defn value-at [board coord]
  (get-in board coord))

(defn has-value? [board coord]
  (not= (value-at board coord) 0))

(defn row-values [board coord]
  (let [[x _] coord]
    (into #{} (get board x))))

(defn col-values [board coord]
  (let [[_ y] coord]
    (reduce (fn [acc row] (conj acc (get row y))) #{} board)))

(defn coord-pairs [coords]
  (for [x coords
        y coords]
    [x y]))

(defn get-top-left-block-coord [coords]
  (let [x (get coords 0)
        y (get coords 1)]
    [(- x (mod x 3))
     (- y (mod y 3))]))

(defn block-values [board coord]
  (let [top-left (get-top-left-block-coord coord)
        x1 (get top-left 0)
        y1 (get top-left 1)]
    (into #{}
          (for [x (range x1 (+ x1 3))
                y (range y1 (+ y1 3))]
            blockblockasdasdasdasdasd(value-at board [x y])))))

(defn valid-values-for [board coord]
  nil)

(defn filled? [board]
  nil)

(defn rows [board]
  nil)

(defn valid-rows? [board]
  nil)

(defn cols [board]
  nil)

(defn valid-cols? [board]
  nil)

(defn blocks [board]
  nil)

(defn valid-blocks? [board]
  nil)

(defn valid-solution? [board]
  nil)

(defn set-value-at [board coord new-value]
  nil)

(defn find-empty-point [board]
  nil)

(defn solve [board]
  nil)
