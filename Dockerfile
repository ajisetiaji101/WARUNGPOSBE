FROM openjdk:18
WORKDIR /app
COPY ./target/spring-0.0.1-SNAPSHOT.jar /app
EXPOSE 6060

CMD ["java", "-jar", "warungposbe-0.0.1-SNAPSHOT.jar"]