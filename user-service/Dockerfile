FROM openjdk:17

ARG JAR_PATH=target/*.jar

COPY ${JAR_PATH} user-service.jar

ENTRYPOINT ["java","-jar","/user-service.jar"]

EXPOSE 9002