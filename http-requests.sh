curl --data-binary @table.txt -X POST http://localhost:8080/records
echo
echo
echo  View 1 - sorted by color and then last name ascending
curl http://localhost:8080/records/color
echo
echo
echo  View 2 - sorted by birth date, ascending
curl http://localhost:8080/records/birthdate
echo
echo
echo View 3 - sorted by last name, descending
curl http://localhost:8080/records/name
echo
