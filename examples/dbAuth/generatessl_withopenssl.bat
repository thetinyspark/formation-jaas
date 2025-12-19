@REM # place-toi dans le dossier C:/mysql/certs
@REM # CA (autorité de certification)
openssl genrsa 2048 > ca-key.pem
openssl req -new -x509 -nodes -days 3650 -key ca-key.pem -out ca.pem -subj "//CN=MySQL-CA"

@REM # Clé privée serveur
openssl genrsa 2048 > server-key.pem

@REM # CSR serveur CN = localhost OBLIGATOIRE
openssl req -new -key server-key.pem -out server-req.pem -subj "//CN=localhost"

@REM # Certificat serveur signé par le CA
openssl x509 -req -in server-req.pem -CA ca.pem -CAkey ca-key.pem -CAcreateserial -out server-cert.pem -days 365

@REM # générer le client trustore avc keytool
keytool -importcert -alias mysql-ca -file C:/mysql/certs/ca.pem -keystore C:/mysql/certs/client-truststore.p12 -storetype PKCS12 -storepass changeit -noprompt