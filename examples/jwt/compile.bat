: samples
javac src/main/java/com/example/*.java -d bin

: run
java -cp bin com.example.JwtClientExample
