(ns gr.handlers.http)
(require '[gr.handlers.util :as util])
(require '[clojure.data.json :as json])

(def records (atom "nil|nil|nil|nil|nil"))

(defn execute
  [view]
  (let [content (util/create-view (deref records) view)]
    {:body (json/write-str content)
     :status 200
     :headers {"Content-Type" "application/json"}}))

(defn put-records
  [s]
  (reset! records  s))

(defn get-records
  []
  (deref records))
