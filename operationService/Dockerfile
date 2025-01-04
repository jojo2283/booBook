FROM openjdk:17-jdk-slim

COPY . /app

WORKDIR /app 

RUN  ./gradlew build   

ENTRYPOINT ["java", "-jar" ,"build/libs/operationService-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080