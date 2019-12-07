(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all])
  (:require [clojure.string :as string]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
                 [:foo :bar]))

  "Whether in function definitions"
  (= (str "An Oxford comma list of apples, "
          "oranges, "
          "and pears.")
     ((fn [[a b c]] (str "An Oxford comma list of "
                         a ", " b ", and " c "."))
      ["apples" "oranges" "pears"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
       (string/join " aka " (conj aliases (str first-name " " last-name)))))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
       {:original-parts full-name
        :named-parts {:first first-name :last last-name}}))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address city :city state :state} test-address]
       (string/join ", " [street-address city state])))

  "Break up maps by key and getting original map"
  (= test-address
     (let [{} :as all test-address]
       all))

  "Break up maps by key with non existent keys"
  (= nil (let [{does-not-exists :god} test-address] does-not-exists))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (string/join ", " [street-address city state])))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     (let [[[name surname] {:keys [street-address city state]}]
           [["Test" "Testerson"] test-address]]
       (string/join ", " [(str name " " surname) street-address city state]))))
