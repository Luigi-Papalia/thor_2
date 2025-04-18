FROM openjdk:17-jdk-slim
# missing USER, the container will be run as root, which is a security flaw
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]