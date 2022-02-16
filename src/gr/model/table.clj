(ns gr.model.table)

(require '[clojure.string :as str])

(def delims ["|" "," " "])
(defn delim-patt [d] (re-pattern (str "\\" d)))

(defn to-table
  "Take a string and a string delimiter and produce a vector of vectors containing strings delimited by `d`."
  [s d]
  (->> s
      (str/split-lines)
      (map #(str/trim %))
      (map #(str/replace % (delim-patt d) " "))
      (map #(str/split % #"\s+"))))

(defn content-delim
  "Take an input string and search it for delimiter characters defined in `delims`.  Searches for delimiters in order defined by `delims`."
  [s]
  (first
    (filter
      #(str/includes? s (str %))
      delims)))

(defn new
  "Take a string representing rows of delimited tokens.  Each row is ended by a `newline`. Fields in a row are separated by defined delimiters"
  [content]
  (let [d (content-delim content)]
    {:delimiter d
     :source content
     :table (to-table content d)}))

(defn _order [coll col-n comp]
  (sort-by
    #(get % col-n)
    comp
    coll))

(defn order
  "Take a `table`, column integer and a compare function.  Order the table rows by the identified column and compare function."
  [table col-n comp]
  (assoc
    table
    :table (_order (:table table) col-n comp)))

(defn table
  "Pull out the vector of string vectors from the `table`"
  [table]
  (:table table))

(defn _render-rows [table]
  (map
    #(str/join (:delimiter table) %)
    (:table table)))

(defn render
  "Take a table and return a string representing the table rows ."
  [table]
  (str/join
    (System/lineSeparator)
    (_render-rows table)))
