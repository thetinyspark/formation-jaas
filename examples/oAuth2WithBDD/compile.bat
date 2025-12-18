: samples
javac src/main/java/com/example/*.java -d bin

: run
java -cp ".;lib/mysql-connector.jar;bin" com.example.OAuth2ClientExample