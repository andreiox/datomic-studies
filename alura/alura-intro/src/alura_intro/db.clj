(ns alura-intro.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn open-connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-db []
  (d/delete-database db-uri))

(def schema [{:db/ident :product/name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident :product/slug
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident :product/price
              :db/valueType :db.type/bigdec
              :db/cardinality :db.cardinality/one}])

(defn create-schema
  [conn]
  (d/transact conn schema))
