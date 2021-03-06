(ns alura-queries.model)

(defn new-product [name slug price]
  {:product/id (java.util.UUID/randomUUID)
   :product/name name
   :product/slug slug
   :product/price price})

(defn new-category [name]
  {:category/id (java.util.UUID/randomUUID)
   :category/name name})
