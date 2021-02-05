FROM openjdk:8-jdk-alpine
ARG JAR_FILE=employee-service/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx400m","-jar","/app.jar"]