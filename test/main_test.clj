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
  (is (some? (m/validate-options ["--file" "name" "--view"  "1"])))

  ; unsuccessful set with extra param
  (is (nil? (m/validate-options ["--file" "name" "--view"  "2" "extra"])))

  ; unsuccessful via bad view
  (is (nil? (m/validate-options ["--file" "name" "--view"  "4"])))

  ; unsuccessful set with unknown option
  (is (nil?
       (try
         (m/validate-options
          ["--file" "name"
           "--view"  "2"
           "--whatever"])
         (catch Exception e nil))))

  ; unsuccessful set which unknown option and value.
  (is (nil?
       (try
         (m/validate-options
          ["--file" "name"
           "--view"  "2"
           "--whatever" "whatever"])
         (catch Exception e nil)))))
(deftest test-view-settings
  (is (some? (m/valid-view? "1")))
  (is (some? (m/valid-view? "2")))
  (is (some? (m/valid-view? "3")))
  (is (nil?  (m/valid-view? "4"))))
