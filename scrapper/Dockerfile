FROM openjdk:17-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /scrapper.jar
ENTRYPOINT exec java -jar /scrapper.jar