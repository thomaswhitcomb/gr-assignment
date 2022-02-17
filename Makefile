all: test uberjar

.PHONY: test
test:
	clj -X:test

.PHONY: testclj
testclj: test
	@echo ------ color, lastname ascending -------
	@clj -M -m gr.command -f table.txt -v 1
	@echo ------ birth date ascending -------
	@clj -M -m gr.command -f table.txt -v 2
	@echo ------ last name descending -------
	@clj -M -m gr.command -f table.txt -v 3

.PHONY: uberjar
uberjar: test
	clj -M:uberjar

.PHONY: testjar
testjar: uberjar
	@echo ------ color, lastname ascending -------
	@java -cp target/gr-sol.jar clojure.main -m gr.command -f table.txt -v 1
	@echo ------ birth date ascending -------
	@java -cp target/gr-sol.jar clojure.main -m gr.command -f table.txt -v 2
	@echo ------ last name descending -------
	@java -cp target/gr-sol.jar clojure.main -m gr.command -f table.txt -v 3

.PHONY: http
http: test
	clj -M -m gr.http
