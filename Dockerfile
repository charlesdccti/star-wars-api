FROM openjdk:8-alpine

MAINTAINER Deyve Vieira

RUN apk update && apk add bash

RUN mkdir -p /opt/app

ENV PROJECT_HOME /opt/app

COPY target/star-wars-api.jar $PROJECT_HOME/star-wars-api.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-jar","./star-wars-api.jar"]