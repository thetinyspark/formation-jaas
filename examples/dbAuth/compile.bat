

: samples
 javac -d ./bin -cp ./src ./src/com/example/Main.java


: run
@REM java -cp ".;lib/mysql-connector.jar;bin" com.example.Main
java -cp ".;lib/mysql-connector-java-5.1.49.jar;bin" com.example.Main

