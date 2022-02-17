#curl -d "user=user1&pass=abcd" -X POST http://localhost:8080/records
curl --data-binary @table.txt -X POST http://localhost:8080/records
