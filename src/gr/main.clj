(ns gr.main)
(require '[clojure.tools.cli :refer [cli]])
(require '[gr.command.handler :as h])

(defn specs []
  [[ "-f" "--file" "Specify a file name"]
   [ "-v" "--view" "Specify a view" :default "1"]
   [ "-h" "--help" "Print this help" :default false :flag true]])

(defn show-help[]
  (let  [[_ _ msgs] (apply cli [] (specs))]
    (println msgs)))

(defn show-error [s]
  (println s))

(defn valid-view? [s]
  (gr.command.handler/views s))

(defn validate-options [parms]
  (let  [[opts args _] (apply cli parms (specs))]
    (cond
      (> (count args) 0)
      (show-error "Warning: extra command line input found")

      (:help opts)
      (show-help)

      (and (:file opts) (valid-view? (:view opts)))
      opts

      :else
      (show-error "Invalid option."))))

(defn validate-and-execute [parms]
  (if-let [opts (validate-options parms)]
    (if-let [output (h/execute (:file opts) (:view opts))]
      (do (println output) 0)
      1)
    1))

(defn -main [& parms]
  (System/exit
    (validate-and-execute parms)))
