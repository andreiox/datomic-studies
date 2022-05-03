(ns alura-queries.core
  (:use clojure.pprint)
  (:require [alura-queries.db :as db]
            [alura-queries.model :as model]
            [datomic.api :as d]))

(def conn (db/open-connection))
(pprint conn)

(db/create-schema conn)

(let [computer (model/new-product "computer" "/new-computer" 2500.10M)
      old-computer (model/new-product "old computer" "/old-computer" 200.10M)
      cellphone (model/new-product "samsung cellphone" "/samsung-cellphone" 200.10M)
      iphone (model/new-product "iphone" "/iphone" 200.10M)
      result @(d/transact conn [computer old-computer cellphone iphone])]
  (pprint result))

(def products (db/all-products (d/db conn)))

(pprint products)

(def product-id (-> products
                    first
                    first
                    :db/id))

(pprint (db/one-product (d/db conn) product-id))

(db/delete-db)
