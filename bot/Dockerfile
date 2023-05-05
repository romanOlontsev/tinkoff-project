FROM openjdk:17-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /bot.jar
ENTRYPOINT exec java -jar /bot.jar --NAME="edu_tinkoff_bot" --TOKEN="5876734662:AAHBqFn5OcruObjXjTOh2PFrU3s5JZc0vfk"