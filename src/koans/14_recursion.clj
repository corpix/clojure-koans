(ns koans.14-recursion
  (:require [koan-engine.core :refer :all]))

(defn has-item? [l v]
  (cond
    (= v (first l)) true
    (not (empty? (rest l))) (has-item? (rest l) v)
    :else false))

(defn is-even-bigint? [n] ;; oh, cmon, this is stupid
  (loop [n   n
         acc true]
    (if (= n 0)
      false
      (recur (dec n) (not acc)))))

(defn recursive-reverse [xs] ;; and this is great, cs classics :)
  (cond
    (empty? xs) (vector) ;; abusing conj specifics, it is working with vectors differently
    true        (conj (recursive-reverse (rest xs)) (first xs))))

(defn factorial-recursive [n]
  (if (zero? n)
    1
    (* n (factorial-recursive (dec n)))))

(defn factorial-iterative [n] ;; this could be improved to be even better in terms of complexity https://cs.stackexchange.com/questions/14456/factorial-algorithm-more-efficient-than-naive-multiplication
  (loop [ctr n
         acc 1]
    (if (zero? ctr)
      acc
      (recur (dec ctr) (* ctr acc)))))

(meditations
  "Recursion ends with a base case"
  (= false (has-item? [] 0))

  "And starts by moving toward that base case"
  (= true (has-item? [3 2 1] 1))

  "Having too many stack frames requires explicit tail calls with recur"
  (= false (is-even-bigint? 100003N))

  "Reversing directions is easy when you have not gone far"
  (= '(1) (recursive-reverse [1]))

  "Yet it becomes more difficult the more steps you take"
  (= '(6 5 4 3 2) (recursive-reverse [2 3 4 5 6]))

  "Simple things may appear simple."
  (= 1 (factorial-recursive 1))

  "They may require other simple steps."
  (= 2 (factorial-recursive 2))

  "Sometimes a slightly bigger step is necessary"
  (= 6 (factorial-recursive 3))

  "And eventually you must think harder"
  (= 24 (factorial-recursive 4))

  "You can even deal with very large numbers"
  (< 1000000000000000000000000N (factorial-recursive 1000N))

  "But what happens when the machine limits you?"
  (< 1000000000000000000000000N (factorial-iterative 100003N)))
