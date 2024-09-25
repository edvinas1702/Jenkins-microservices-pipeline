FROM openjdk:17

ARG JAR_PATH=target/*.jar

COPY ${JAR_PATH} api-gateway.jar

ENTRYPOINT ["java","-jar","/api-gateway.jar"]

EXPOSE 9191