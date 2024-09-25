FROM openjdk:17

ARG JAR_PATH=target/*.jar

COPY ${JAR_PATH} service-registry.jar

ENTRYPOINT ["java","-jar","/service-registry.jar"]

EXPOSE 8761