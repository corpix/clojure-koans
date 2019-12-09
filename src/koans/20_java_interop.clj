(ns koans.20-java-interop
  (:require [koan-engine.core :refer :all]))

(defn to-upper-case [str] (.toUpperCase str))
(defn get-count     [v]   (.getCount v))
(defn pow           [b e] (Math/pow b e))
(defn make-latch    [n]   (java.util.concurrent.CountDownLatch. n))

(meditations
  "You may have done more with Java than you know"
  (= java.lang.String (class "warfare")) ; hint: try typing (javadoc "warfare") in the REPL
  ;; (javadoc "warfare") => true, WAT? did you mean «type (class "warfare") in the REPL»?

  "The dot signifies easy and direct Java interoperation"
  (= "SELECT * FROM" (to-upper-case "select * from"))

  "But instance method calls are very different from normal functions"
  (= ["SELECT" "FROM" "WHERE"] (map to-upper-case ["select" "from" "where"])) ;; this is why all java should stay out of my code, in separate function

  "Constructing might be harder than breaking"
  (= 10 (let [latch (make-latch 10)] (get-count latch)))

  "Static methods are slashing prices!"
  (== 1024.0 (pow 2 10)))
