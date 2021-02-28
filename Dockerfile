FROM openjdk:11-jdk-slim
ADD target/CryptoWorld-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar CryptoWorld-0.0.1-SNAPSHOT.jar