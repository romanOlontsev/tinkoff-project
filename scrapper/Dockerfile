FROM openjdk:17-slim
COPY target/scrapper-1.0-SNAPSHOT.jar /scrapper.jar
RUN echo 'hello' && ls -lR
ENTRYPOINT exec java -jar /scrapper.jar