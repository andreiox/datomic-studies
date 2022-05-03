(ns alura-intro.core
  (:use clojure.pprint)
  (:require [alura-intro.db :as db]
            [alura-intro.model :as model]
            [datomic.api :as d]))

(def conn (db/open-connection))
(pprint conn)

; (db/delete-db)

(db/create-schema conn)

(let [computer (model/new-product "computer" "/new-computer" 2500.10M)
      result @(d/transact conn [computer])
      id (first (vals (:tempids result)))]
  (pprint @(d/transact conn [[:db/add id :product/price 1500.0M]])))

(db/all-products-ids (d/db conn))

(db/all-products-by-attribute (d/db conn) :product/slug "/new-computer")

(pprint (db/all-products-name-and-price (d/db conn)))
