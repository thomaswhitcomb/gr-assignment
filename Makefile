all: test uberjar

.PHONY: test
test:
	clj -X:test

.PHONY: testclj
testclj: test
	@echo ------ view1 -------
	@clj -M -m gr.main -f table.txt -v 1
	@echo ------ view2 -------
	@clj -M -m gr.main -f table.txt -v 2
	@echo ------ view3 -------
	@clj -M -m gr.main -f table.txt -v 3

.PHONY: uberjar
uberjar: test
	clj -M:uberjar

.PHONY: testjar
testjar: uberjar
	@echo ------ view1 -------
	@java -cp target/gr-sol.jar clojure.main -m gr.main -f table.txt -v 1
	@echo ------ view2 -------
	@java -cp target/gr-sol.jar clojure.main -m gr.main -f table.txt -v 2
	@echo ------ view3 -------
	@java -cp target/gr-sol.jar clojure.main -m gr.main -f table.txt -v 3
