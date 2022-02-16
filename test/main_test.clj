(ns main-test)
(require '[clojure.test :refer :all])
(require '[gr.main :as m])

(deftest  test-validate-options

  ; successful help
  (is (nil? (m/validate-options ["-h"])))

  ; successful help long form
  (is (nil? (m/validate-options ["--help"])))

  ; failure - extra command line
  (is (nil? (m/validate-options ["-h" "blah"])))

  ; successful file spec
  (is (some? (m/validate-options ["-f" "name"])))

  ; failure - extra command line value
  (is (nil? (m/validate-options ["-f" "name" "extra"])))

  ; successful file spec
  (is (some? (m/validate-options ["--file" "name"])))

  ; failure with extra command line value on file spec
  (is (nil? (m/validate-options ["--file" "name" "blah"])))

  ; successful set
  (is (some? (m/validate-options ["--file" "name" "--column"  "FavoriteColor" "--order"  "asc"])))

  ; successful set
  (is (some? (m/validate-options ["--file" "name" "--column"  "LastName" "--order"  "dsc"])))

  ; unsuccessful set with extra param
  (is (nil? (m/validate-options ["--file" "name" "--column"  "LastName" "--order"  "dsc" "extra"])))

  ; unsuccessful set with unknown option
  (is (nil?
       (try
         (m/validate-options
           ["--file" "name"
            "--column"  "LastName"
            "--order"  "dsc" "--whatever"])
         (catch Exception e nil))))

  ; unsuccessful set which unknown option and value.
  (is (nil?
        (try
          (m/validate-options
            ["--file" "name"
             "--column"  "LastName"
             "--order"  "dsc"
             "--whatever" "whatever"])
          (catch Exception e nil))))
)
(deftest test-column-settings
  (is (some? (m/valid-column? "FavoriteColor")))
  (is (some? (m/valid-column? "LastName")))
  (is (some? (m/valid-column? "FirstName")))
  (is (some? (m/valid-column? "DateOfBirth")))
  (is (some? (m/valid-column? "Email")))
  (is (nil?  (m/valid-column? "blah")))
)
(deftest test-sort-order
  (is (some? (m/valid-order? "asc")))
  (is (some? (m/valid-order? "dsc")))
  (is (nil?  (m/valid-order? "xyz")))
)
