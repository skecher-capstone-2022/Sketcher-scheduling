FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/scheduling-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]