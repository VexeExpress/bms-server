mvn clean
mvn package
java -jar bms-server-0.0.1-SNAPSHOT.jar
docker build -t bms-server:0.0.1 .


docker run --name bms-server -p 8080:8080 bms-server:0.0.2


docker build -t tuanthanh03/bms-server:0.0.2 .

docker image push tuanthanh03/bms-server:0.0.2
