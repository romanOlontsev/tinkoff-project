FROM openjdk:17-slim
ARG NAME
ARG TOKEN
ENV TG_NAME=${NAME}
ENV TG_TOKEN=${TOKEN}
COPY target/bot-1.0-SNAPSHOT.jar /bot.jar
ENTRYPOINT exec java -jar /bot.jar --NAME=${TG_NAME} --TOKEN=${TG_TOKEN}

