(ns handler-test)
(require '[clojure.test :refer :all])
(require '[gr.command.handler :as h])
(require '[clojure.java.io :as io])

(defn stream1 [] (io/input-stream
               (.getBytes
                 (str
                  "jones,scott,scott_jones@fb.com,blue,11/5/1986"
                   (System/lineSeparator)
                  "smith,bob,bob@fb.com,red,10/10/2000"
                   (System/lineSeparator)
                  "franks,doug,doug@fb.com,blue,12/7/1982"
                   (System/lineSeparator)
                  "washington,george,martha@aol.com,blue,5/8/1786"))))
(def rendering1-view1 (str
                  "franks,doug,doug@fb.com,blue,12/7/1982"
                   (System/lineSeparator)
                  "jones,scott,scott_jones@fb.com,blue,11/5/1986"
                   (System/lineSeparator)
                  "washington,george,martha@aol.com,blue,5/8/1786"
                   (System/lineSeparator)
                  "smith,bob,bob@fb.com,red,10/10/2000"))
(def rendering1-view2 (str
                  "washington,george,martha@aol.com,blue,5/8/1786"
                   (System/lineSeparator)
                  "franks,doug,doug@fb.com,blue,12/7/1982"
                   (System/lineSeparator)
                  "jones,scott,scott_jones@fb.com,blue,11/5/1986"
                   (System/lineSeparator)
                  "smith,bob,bob@fb.com,red,10/10/2000"))
(def rendering1-view3 (str
                  "washington,george,martha@aol.com,blue,5/8/1786"
                   (System/lineSeparator)
                  "smith,bob,bob@fb.com,red,10/10/2000"
                   (System/lineSeparator)
                  "jones,scott,scott_jones@fb.com,blue,11/5/1986"
                   (System/lineSeparator)
                  "franks,doug,doug@fb.com,blue,12/7/1982"))

(deftest  test-execute
  (is (= (h/execute (stream1) "1")
         rendering1-view1))
  (is (= (h/execute (stream1) "2")
         rendering1-view2))
  (is (= (h/execute (stream1) "3" )
         rendering1-view3))

)

(deftest test-view-to-column-numbers
  (is (= (h/view-to-column-numbers "1") [3 0]))
  (is (= (h/view-to-column-numbers "2") [4]))
  (is (= (h/view-to-column-numbers "3") [0]))
)

(deftest test-view-to-comparer
  (is (= ((h/view-to-comparer "1") 1 1) 0))
)

(deftest test-date-transformer
  (is (= (h/date-transformer "06/10/1958") 19581006))
)
