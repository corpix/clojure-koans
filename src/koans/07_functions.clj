(ns koans.07-functions
  (:require [koan-engine.core :refer :all]))

(defn multiply-by-ten [n]
  (* 10 n))

(defn square [n] (* n n))

(defn call [n f] (f n))

(meditations
  "Calling a function is like giving it a hug with parentheses"
  (= 81 (square 9))

  "Functions are usually defined before they are used"
  (= 20 (multiply-by-ten 2))

  "But they can also be defined inline"
  (= 10 ((fn [n] (* 5 n)) 2))

  "Or using an even shorter syntax"
  (= 60 (#(* 15 %) 4))

  "Even anonymous functions may take multiple arguments"
  (= 15 (#(+ %1 %2 %3) 4 5 6))

  "Arguments can also be skipped"
  (= "AACC" (#(str "AA" %2) "bb" "CC"))

  "One function can beget another"
  (= 9 (((fn [] +)) 4 5))

  "Functions can also take other functions as input"
  (= 20 ((fn [f] (f 4 5)) *))

  "Higher-order functions take function arguments"
  (= 25 ((fn [f] (f 5))
         (fn [n] (* n n))))

  "But they are often better written using the names of functions"
  (= 25 (call 5 square))

  "Functions could accept variable number of arguments"
  (= '(1 2 3 4) ((fn [& rest] rest)
                 1 2 3 4))

  "Or have a bunch of required arguments and the rest"
  (= '(1 2 (3 4 5 6)) ((fn [a b & rest] (list a b rest))
                       1 2 3 4 5 6))

  "Functions could accept keyword arguments"
  (= '(1 2) ((fn [& {:keys [a b]}] (list a b))
             :a 1 :b 2))

  "And they could be optional"
  (= '(1 3) ((fn [& {:keys [a b] :or {a 1 b 2}}]
               (list a b))
             :b 3)))
