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
            (value-at board [x y])))))

(defn valid-values-for [board coord]
  (let [val (value-at board coord)]
    (if (not= val 0)
      #{}
      (set/difference all-values (set/union
                                   (block-values board coord)
                                   (row-values board coord)
                                   (col-values board coord))))))

(defn get-all-board-values [board]
  (into #{}
        (for [x (range 0 9)
              y (range 0 9)]
          (value-at board [x y]))))

(defn filled? [board]
  (not (contains? (get-all-board-values board) 0)))

(defn rows [board]
  (for [x (range 0 9)]
    (row-values board [x 0])))

(defn all-valid [set-values]
  (not (contains? set-values false)))

(defn valid-rows? [board]
  (all-valid (into #{} (for [row (rows board)]
                         (= all-values row)))))

(defn cols [board]
  (for [y (range 0 9)]
    (col-values board [0 y])))

(defn valid-cols? [board]
  (all-valid (into #{} (for [col (cols board)]
                         (= all-values col)))))

(defn blocks [board]
  (for [x (range 0 9 3)
        y (range 0 9 3)]
    (block-values board [x y])))

(defn valid-blocks? [board]
  (all-valid (into #{} (for [block (blocks board)]
                         (= all-values block)))))

(defn valid-solution? [board]
  (and
    (valid-rows? board)
    (valid-cols? board)
    (valid-blocks? board)))

(defn set-value-at [board coord new-value]
  (assoc-in board coord new-value))

(defn find-empty-point [board]
  (first (filter
           (fn [x] (not (has-value? board x)))
         (coord-pairs (range 0 9)))))

(defn solve-helper [board current-solution]
  (if (filled? current-solution)
    (if (valid-solution? current-solution)
      current-solution
      [])
    (let [next-coord (find-empty-point current-solution)]
      (for [canditate (valid-values-for current-solution next-coord)
            solution (solve-helper board (set-value-at current-solution next-coord canditate))]
        solution))))

(defn solve [board]
  (solve-helper board board))


(def sudoku-board-test
  (board [[8 3 0 0 0 7 0 0 5]
          [0 0 0 0 0 0 2 0 0]
          [0 4 9 0 0 0 0 6 3]
          [0 0 0 0 8 0 0 9 6]
          [0 5 6 0 9 4 0 0 7]
          [3 0 0 0 0 0 0 0 0]
          [2 6 0 0 0 0 4 0 0]
          [0 0 0 0 0 0 0 0 0]
          [0 0 3 0 4 0 7 0 2]]))


