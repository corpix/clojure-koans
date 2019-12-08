(ns koans.19-datatypes
  (:require [koan-engine.core :refer :all]))

(defrecord nobel [prize]) ;; this capitalized names in lispy code looks like awful decision, an eyesore, a dirt on the screen
(deftype pulitzer [prize])

(defprotocol award
  (present [this recipient]))

(defrecord oscar [category]
  award
  (present [this recipient]
    (print (str
            "Congratulations on your "
            (:category this)
            " Oscar, " recipient "!"))))

(deftype razzie [category]
  award
  (present [this recipient]
    (print (str
            "You're really the "
            (.category this)
            ", " recipient "... sorry."))))

(meditations
 ;;;; fuckin beautiful, antipattern in the koans https://stuartsierra.com/2015/05/17/clojure-record-constructors
 ;; "Holding records is meaningful only when the record is worthy of you"
 ;; (= __ (.prize (nobel. "peace")))

  "Holding records is meaningful only when the record is worthy of you"
  (= "peace" (-> "peace" ->nobel .prize)) ;; same as (.prize (->nobel "peace"))

  "Types are quite similar"
  (= "literature" (-> "literature" ->pulitzer .prize))

  "Records may be treated like maps"
  (= "physics" (-> "physics" ->nobel :prize))

  "While types may not" ;; beacuse it does not implement dome «map related» interface
  (= nil (-> "poetry" ->pulitzer :prize))

  "Further study reveals why"
  (= [true false]
     (map map? [(->nobel    "chemistry")
                (->pulitzer "music")]))

  "Either sort of datatype can define methods in a protocol"
  (= "Congratulations on your Best Picture Oscar, Evil Alien Conquerors!"
     (with-out-str (present (->oscar "Best Picture") "Evil Alien Conquerors")))

  "Surely we can implement our own by now"
  (= "You're really the Worst Picture, Final Destination 5... sorry."
     (with-out-str (present (->razzie "Worst Picture") "Final Destination 5"))))
