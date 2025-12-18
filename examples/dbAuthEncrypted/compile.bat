

: samples
 javac -d ./bin -cp ./src ./src/com/example/Main.java


: run
java -cp ".;lib/mysql-connector.jar;bin" com.example.Main

