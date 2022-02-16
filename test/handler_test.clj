(ns handler-test)
(require '[clojure.test :refer :all])
(require '[gr.command.handler :as h])
(require '[clojure.java.io :as io])

(defn stream1 [] (io/input-stream
               (.getBytes
                 (str
                   "a|b|c|d|e"
                   (System/lineSeparator)
                   "b|c|d|e|f)"))))
(def rendering1-asc (str
                      "a|b|c|d|e"
                      (System/lineSeparator)
                      "b|c|d|e|f)"))
(def rendering1-dsc (str
                      "b|c|d|e|f)"
                      (System/lineSeparator)
                      "a|b|c|d|e" ))
(defn stream2 [] (io/input-stream
               (.getBytes
                 (str
                  "smith,bob,bob@fb.com,red,10/10/2000"
                   (System/lineSeparator)
                  "jones,scott,scott_jones@fb.com,blue,11/5/1986"
                   (System/lineSeparator)
                  "washington,george,martha@aol.com,blue,5/8/1786"))))
(def rendering2-asc (str
                  "jones,scott,scott_jones@fb.com,blue,11/5/1986"
                   (System/lineSeparator)
                  "smith,bob,bob@fb.com,red,10/10/2000"
                   (System/lineSeparator)
                  "washington,george,martha@aol.com,blue,5/8/1786"))

(deftest  test-execute
  (is (= (h/execute (stream1) "LastName" "asc")
         rendering1-asc))
  (is (= (h/execute (stream1) "LastName" "dsc")
         rendering1-dsc))
  (is (= (h/execute (stream2) "LastName" "asc")
         rendering2-asc))

)
