(ns gr.handlers.command)

(require '[gr.model.table :as model])
(require '[gr.handlers.util :as util])

(defn execute
  [source view]
  (model/render
   (util/create-view
    (slurp source)
    view)))
