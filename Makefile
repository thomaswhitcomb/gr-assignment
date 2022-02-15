all: test uberjar

.PHONY: test
test:
	clj -X:test

.PHONY: run
run:
	clj -M -m gr.main

.PHONY: uberjar
uberjar:
	clj -M:uberjar

.PHONY: runjar
runjar:
	java -cp target/gr-sol.jar clojure.main -m gr.main
