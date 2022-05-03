(ns alura-queries.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

(def db-uri "datomic:dev://localhost:4334/ecommerce")

(defn open-connection []
  (d/create-database db-uri)
  (d/connect db-uri))

(defn delete-db []
  (d/delete-database db-uri))

(def schema [{:db/ident :product/id
              :db/valueType :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique :db.unique/identity}
             {:db/ident :product/name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident :product/slug
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident :product/price
              :db/valueType :db.type/bigdec
              :db/cardinality :db.cardinality/one}
             {:db/ident :product/category
              :db/valueType :db.type/ref
              :db/cardinality :db.cardinality/one}

             {:db/ident :category/id
              :db/valueType :db.type/uuid
              :db/cardinality :db.cardinality/one
              :db/unique :db.unique/identity}
             {:db/ident :category/name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}])

(defn create-schema
  [conn]
  (d/transact conn schema))

(defn one-product-db-id
  [db id]
  (d/pull db '[*] id))

(defn all-products
  [db]
  (d/q '[:find (pull ?e [*])
         :where [?e :product/name]] db))

(defn one-product-by-id
  [db id]
  (d/pull db '[*] [:product/id id]))

(defn all-categories
  [db]
  (d/q '[:find (pull ?e [*])
         :where [?e :category/id]] db))
