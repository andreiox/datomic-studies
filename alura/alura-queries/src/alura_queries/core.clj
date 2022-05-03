(ns alura-queries.core
  (:use clojure.pprint)
  (:require [alura-queries.db :as db]
            [alura-queries.model :as model]
            [datomic.api :as d]))

(def conn (db/open-connection))
(pprint conn)

(db/create-schema conn)

(def cellphone (model/new-category "cellphone"))
(def pc (model/new-category "pc"))

(pprint @(d/transact conn [cellphone pc]))

(def categories (db/all-categories (d/db conn)))
(pprint categories)

(let [computer (model/new-product "computer" "/new-computer" 2500.10M)
      old-computer (model/new-product "old computer" "/old-computer" 200.10M)
      cellphone (model/new-product "samsung cellphone" "/samsung-cellphone" 200.10M)
      iphone (model/new-product "iphone" "/iphone" 200.10M)
      result @(d/transact conn [computer old-computer cellphone iphone])]
  (pprint result))

(def products (db/all-products (d/db conn)))
(pprint products)

(def db-id (-> products ffirst :db/id))
(pprint (db/one-product-db-id (d/db conn) db-id))

(def product-id (-> products second first :product/id))
(pprint (db/one-product-by-id (d/db conn) product-id))

(pprint @(d/transact conn [[:db/add [:product/id product-id] :product/category [:category/id (:category/id pc)]]]))
(pprint (db/one-product-by-id (d/db conn) product-id))

(pprint (db/products-names-and-categories (d/db conn)))

(pprint (db/products-by-category-name (d/db conn) "pc"))

(pprint (db/products-by-category-name-backwards-nav (d/db conn) "pc"))

(db/delete-db)
