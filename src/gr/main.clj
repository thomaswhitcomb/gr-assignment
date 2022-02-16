(ns gr.main)
(require '[clojure.tools.cli :refer [cli]])
(require '[gr.command.handler :as h])

(defn specs []
  [[ "-f" "--file" "Specify a file name"]
   [ "-c" "--column" "Specify a column name" :default "LastName"]
   [ "-o" "--order" "Specify a sort order" :default "asc"]
   [ "-h" "--help" "Print this help" :default false :flag true]])

(defn show-help[]
  (let  [[_ _ msgs] (apply cli [] (specs))]
    (println msgs)))

(defn show-error [s]
  (println s))

(defn valid-column? [s]
  (gr.command.handler/columns s))

(defn valid-order? [s]
  (gr.command.handler/order-set s))

(defn validate-options [parms]
  (let  [[opts args _] (apply cli parms (specs))]
    (cond
      (> (count args) 0)
      (show-error "Warning: extra command line input found")

      (:help opts)
      (show-help)

      (and (:file opts) (valid-column? (:column opts)) (valid-order? (:order opts)))
      opts

      :else
      (show-error "Invalid option."))))

(defn validate-and-execute [parms]
  (if-let [opts (validate-options parms)]
    (if-let [output (h/execute (:file opts) (:column opts) (:order opts))]
      (do (println output) 0)
      1)
    1))

(defn -main [& parms]
  (System/exit
    (validate-and-execute parms)))
