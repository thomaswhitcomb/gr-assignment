(ns gr.command.handler)
(require '[gr.model.table :as model])
(require '[clojure.string :as str])

(def _order
   {:asc compare
    :dsc #(compare %2 %1)})

(defn date-transformer
  "Take a string in dd/mm/yyyy format and make it an integer from yyyymmdd"
  [s]
  (->
    s
    (str/split #"/")
    (reverse)
    (str/join)
    (java.lang.Integer.)))

(def _columns
  {:last_name
   {:pos 0 :type :alpha       :label "LastName"      :transformer identity}

   :first_name
   {:pos 1 :type :alpha       :label "FirstName"     :transformer identity}

   :email
   {:pos 2 :type :alpha       :label "Email"         :transformer identity}

   :favorite_color
   {:pos 3 :type :alpha       :label "FavoriteColor" :transformer identity}

   :date_of_birth
   {:pos 4 :type :date-mmyydd :label "DateOfBirth"   :transformer date-transformer}})

(def views
  {"1" {:sort-by [:favorite_color :last_name] :order :asc}
   "2" {:sort-by [:date_of_birth] :order :asc}
   "3" {:sort-by [:last_name] :order :dsc}})

(defn _view-to-column-mapper
  [view kw]
  (map #(kw (_columns %)) (:sort-by (views view))))

(defn view-to-column-transformers
  "Take a view identifier and returns a seq of column value transformers"
  [view]
  (_view-to-column-mapper view :transformer))

(defn view-to-column-numbers
  "Take a view identifier and return a seq of column numbers"
  [view]
  (_view-to-column-mapper view :pos))

(defn view-to-comparer
  "Take a view identifier and return the compare function"
  [view]
  (_order (:order (views view))))

(defn execute
  "Take an inputStream, File, URL, filename,etc for table content and a view id, read the stream/file and return a string representing the requested table view"
  [source view]
  (let [content (slurp source)
        table (model/new content)]
    (->>
      (model/order
        table
        (view-to-column-numbers view)
        (view-to-column-transformers view)
        (view-to-comparer view))
      (model/render))))
