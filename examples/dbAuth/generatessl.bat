@REM export du certificat privé et création du keystore
keytool -genkeypair ^
  -alias mysql-server ^
  -keyalg RSA ^
  -keysize 2048 ^
  -validity 365 ^
  -keystore server-keystore.jks ^
  -storepass changeit ^
  -keypass changeit ^
  -dname "CN=localhost, OU=DEV, O=Example, L=Paris, C=FR"

@REM export du certificat public
keytool -exportcert ^
  -alias mysql-server ^
  -keystore server-keystore.jks ^
  -storepass changeit ^
  -file server-cert.crt

@REM   création du truststore et import du certificat public
keytool -importcert ^
  -alias mysql-server ^
  -file server-cert.crt ^
  -keystore client-truststore.jks ^
  -storepass changeit ^
  -noprompt
