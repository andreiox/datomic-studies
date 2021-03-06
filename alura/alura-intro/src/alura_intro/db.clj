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

(defn all-products-ids
  [db]
  (d/q '[:find ?entity
         :where [?entity :product/name]] db))

(defn all-products-by-attribute [db attribute value]
  (d/q '[:find ?entidade
         :in $ ?attribute ?value
         :where [?entidade ?attribute ?value]] db attribute value))

(defn all-products-name-and-price
  [db]
  (d/q '[:find ?name ?price
         :keys name price
         :where
         [?e :product/name ?name]
         [?e :product/price ?price]] db))

(defn all-products-explicit
  [db]
  (d/q '[:find (pull ?entity [:product/name :product/slug :product/price])
         :where [?entity :product/name]] db))

(defn all-products
  [db]
  (d/q '[:find (pull ?entity [*])
         :where [?entity :product/name]] db))
