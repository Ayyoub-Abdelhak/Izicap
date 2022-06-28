FROM openjdk:11
EXPOSE 8080
ADD target/izicap.jar izicap.jar
ENTRYPOINT ["java", "-jar", "/izicap.jar"]