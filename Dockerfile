FROM openjdk:alpine-17

LABEL authors="Denis Solovey"

WORKDIR app

COPY /target/*.jar employee-data-system.jar

ENTRYPOINT ["java", "-jar", "employee-data-system.jar"]