FROM maven:3.6.3 AS maven
LABEL MAINTAINER="htyesilyurt@gmail.com"

WORKDIR /opt/sftp-excel-service
COPY . /opt/sftp-excel-service
RUN mvn clean install 

# For Java 11, 
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=sftp-excel-service.jar

WORKDIR /opt/sftp-excel-service

COPY --from=maven /opt/sftp-excel-service/target/${JAR_FILE} /opt/sftp-excel-service/

ENTRYPOINT ["java","-jar","sftp-excel-service.jar"]