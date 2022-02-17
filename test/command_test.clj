(ns command-test)
(require '[clojure.test :refer :all])
(require '[gr.command :as c])

(deftest  test-validate-options

  ; successful help
  (is (nil? (c/validate-options ["-h"])))

  ; successful help long form
  (is (nil? (c/validate-options ["--help"])))

  ; failure - extra command line
  (is (nil? (c/validate-options ["-h" "blah"])))

  ; successful file spec
  (is (some? (c/validate-options ["-f" "name"])))

  ; failure - extra command line value
  (is (nil? (c/validate-options ["-f" "name" "extra"])))

  ; successful file spec
  (is (some? (c/validate-options ["--file" "name"])))

  ; failure with extra command line value on file spec
  (is (nil? (c/validate-options ["--file" "name" "blah"])))

  ; successful set
  (is (some? (c/validate-options ["--file" "name" "--view"  "1"])))

  ; unsuccessful set with extra param
  (is (nil? (c/validate-options ["--file" "name" "--view"  "2" "extra"])))

  ; unsuccessful via bad view
  (is (nil? (c/validate-options ["--file" "name" "--view"  "4"])))

  ; unsuccessful set with unknown option
  (is (nil?
       (try
         (c/validate-options
          ["--file" "name"
           "--view"  "2"
           "--whatever"])
         (catch Exception e nil))))

  ; unsuccessful set which unknown option and value.
  (is (nil?
       (try
         (c/validate-options
          ["--file" "name"
           "--view"  "2"
           "--whatever" "whatever"])
         (catch Exception e nil)))))
(deftest test-view-settings
  (is (some? (c/valid-view? "1")))
  (is (some? (c/valid-view? "2")))
  (is (some? (c/valid-view? "3")))
  (is (nil?  (c/valid-view? "4"))))
