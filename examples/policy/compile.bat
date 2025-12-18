

: samples
javac -d ./bin -cp ./src ./src/com/example/Main.java

: run
java -cp "bin" -Djava.security.manager ^ -Djava.security.policy=java.policy com.example.Main
: java -cp "bin" -Djava.security.policy=java.policy com.example.Main

