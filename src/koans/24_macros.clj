(ns koans.24-macros
  (:require [koan-engine.core :refer :all]))

(defmacro hello [x]
  (str "Hello, " x))

(defmacro infix [form]
  (list (second form)
        (first  form)
        (last   form)))

(defmacro infix-concise [form]
  `(~(second form)
    ~(first  form)
    ~(last   form)))

(defmacro infix-recursive [form]
  (cond
    (not (seq? form)) form
    (= (count form) 1) `(infix-recursive ~(first form))

    ;; require triples to make a user use parens in expressions
    ;; otherwise we need to implement operator precendense ;)
    (not (= (count form) 3))
    (throw (let [expr (seq form)]
             (ex-info
              (format "expression %s should be a list of 3 items" expr)
              {:cause #{:even-arguments-count}
               :expr  expr})))

    :else (let [op (nth form 1)
                x  (nth form 0)
                xs (drop 2 form)]
            `(~op
              (infix-recursive ~x)
              (infix-recursive ~xs)))))

(meditations
  "Macros are like functions created at compile time"
  (= "Hello, Macros!" (hello "Macros!"))

  "I can haz infix?"
  (= 10 (infix (9 + 1)))

  "I can haz recursive infix?"
  (= 20 (infix-recursive ((9 + 1) * 2)))

  "Remember, these are nothing but code transformations"
  (= '(+ 9 1) (macroexpand '(infix (9 + 1))))

  "You can do better than that - hand crafting FTW!"
  (= '(* 10 2) (macroexpand '(infix-concise (10 * 2))))

  "Things don't always work as you would like them to... "
  (= '(+ 10 (2 * 3)) (macroexpand '(infix-concise (10 + (2 * 3)))))

  "Really, you don't understand recursion until you understand recursion"
  (= 36 (infix-recursive (10 + ((2 * 3) + (4 * 5))))))
