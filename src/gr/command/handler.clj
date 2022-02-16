(ns gr.command.handler)
(require '[gr.model.table :as model])

(def ascending "asc")
(def descending "dsc")

(def order-set #{ascending descending})

(def _order
   {ascending compare
    descending #(compare %2 %1)})

(def _order-by-type
  {:alpha       _order
   :date-mmyydd _order})

(def columns
  {"LastName"      {:pos 0 :type :alpha}
   "FirstName"     {:pos 1 :type :alpha}
   "Email"         {:pos 2 :type :alpha}
   "FavoriteColor" {:pos 3 :type :alpha}
   "DateOfBirth"   {:pos 4 :type :date-mmyydd}})

(defn execute
  "Take an inputStream, File, URL, filename,etc for table content, a column name and an ordering ('asc' or 'dsc'), read the stream/file, sort it and return a string representing the table"
  [source label order]
  (let [content (slurp source)
        table (model/new content)
        metadata (columns label)]
    (->>
      (model/order
        table
        (metadata :pos)
        ((_order-by-type (metadata :type)) order))
      (model/render))))
