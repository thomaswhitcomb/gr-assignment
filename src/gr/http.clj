(ns gr.http)

(require '[ring.adapter.jetty :as j])
(require '[gr.handlers.http :as h])
(require '[compojure.core :as c])
(require '[compojure.route :as cr])

(defn home
  [request]
  {:body "Guaranteed Rate Homework Assignment"
   :status 200
   :headers {"Content-Type" "text/plain"}})

(defn by-color
  [request]
  (h/execute "1"))

(defn by-birthdate
  [request]
  (h/execute "2"))

(defn by-name
  [request]
  (h/execute "3"))

(c/defroutes routes
  (c/GET "/"                  []           home)
  (c/GET "/records/color"     []           by-color)
  (c/GET "/records/birthdate" []           by-birthdate)
  (c/GET "/records/name"      []           by-name)
  (c/GET "/records"           []           (h/get-records))
  (c/POST "/records"          {body :body} (h/put-records (slurp body)))
  (cr/not-found "Page not found"))

(defn port [parms]
  (if (zero? (count parms))
    8080
    (Integer. (first parms))))

(defn -main [& parms]
  (j/run-jetty routes {:port (port parms)}))
