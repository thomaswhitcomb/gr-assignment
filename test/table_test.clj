(ns table-test)
(require '[clojure.test :refer :all])
(require '[gr.model.table :as t])
(require '[clojure.java.io :as io])

; Verify the file content is correctly parsed into a vector of vectors
(deftest  test-to-table
  (is (= (t/to-table "  benimble|jack |   jack@fb.com|mauve |  12/12/2000  " "|")
         [["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]]))
  (is (= (t/to-table "  benimble, jack ,   jack@fb.com,mauve ,  12/12/2000  " ",")
         [["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]]))
  (is (= (t/to-table "  benimble  jack     jack@fb.com mauve    12/12/2000  " " ")
         [["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]]))

)
; Verify that pipe is found first, followed by , followed by " "
(deftest test-content-delimiter-detection
  (is (= (t/content-delim "  abc,d| ") "|"))
  (is (= (t/content-delim "  abc,d ") ","))
  (is (= (t/content-delim "  abcd ") " "))
)

(def table1 {:table [["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]
                     ["finn" "neil" "neil@gmail.com" "green" "3/2/1962"]
                     ["cooper" "alice" "alice@yahoo.com" "black" "1/1/1947"]]
             :delimiter "|"})

; Verify the ordering logic
(deftest test-ordering
  (is (= (:table (t/order table1 0  compare))
         [
          ["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]
          ["cooper" "alice" "alice@yahoo.com" "black" "1/1/1947"]
          ["finn" "neil" "neil@gmail.com" "green" "3/2/1962"]
         ]))
  (is (= (:table (t/order table1 1  compare))
         [
          ["cooper" "alice" "alice@yahoo.com" "black" "1/1/1947"]
          ["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]
          ["finn" "neil" "neil@gmail.com" "green" "3/2/1962"]
          ]))
  (is (= (:table (t/order table1 2  compare))
         [
          ["cooper" "alice" "alice@yahoo.com" "black" "1/1/1947"]
          ["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]
          ["finn" "neil" "neil@gmail.com" "green" "3/2/1962"]
          ]))
  (is (= (:table (t/order table1 3  compare))
         [
          ["cooper" "alice" "alice@yahoo.com" "black" "1/1/1947"]
          ["finn" "neil" "neil@gmail.com" "green" "3/2/1962"]
          ["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]
         ]))

  ; Notice the compare - this is descending
  (is (= (:table (t/order table1 3  #(compare %2 %1)))
         [
          ["benimble" "jack" "jack@fb.com" "mauve" "12/12/2000"]
          ["finn" "neil" "neil@gmail.com" "green" "3/2/1962"]
          ["cooper" "alice" "alice@yahoo.com" "black" "1/1/1947"]
         ]))
)
(deftest test-rendering
  (is (= (t/render table1)
         (str
           "benimble|jack|jack@fb.com|mauve|12/12/2000"
           (System/lineSeparator)
           "finn|neil|neil@gmail.com|green|3/2/1962"
           (System/lineSeparator)
           "cooper|alice|alice@yahoo.com|black|1/1/1947")))
  (is (= (t/render (assoc table1 :delimiter ","))
         (str
           "benimble,jack,jack@fb.com,mauve,12/12/2000"
           (System/lineSeparator)
           "finn,neil,neil@gmail.com,green,3/2/1962"
           (System/lineSeparator)
           "cooper,alice,alice@yahoo.com,black,1/1/1947")))
  (is (= (t/render (assoc table1 :delimiter " "))
         (str
           "benimble jack jack@fb.com mauve 12/12/2000"
           (System/lineSeparator)
           "finn neil neil@gmail.com green 3/2/1962"
           (System/lineSeparator)
           "cooper alice alice@yahoo.com black 1/1/1947")))
)
