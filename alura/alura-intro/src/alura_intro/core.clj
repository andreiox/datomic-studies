(ns alura-intro.core
  (:use clojure.pprint)
  (:require [alura-intro.db :as db]
            [alura-intro.model :as model]
            [datomic.api :as d]))

(def conn (db/open-connection))
(pprint conn)

(db/create-schema conn)

(let [computer (model/new-product "computer" "/new-computer" 2500.10M)]
  (d/transact conn [computer]))

(def db (d/db conn))

(d/q '[:find ?entity
       :where [?entity :product/name]] db)
