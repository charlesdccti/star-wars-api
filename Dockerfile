FROM openjdk:8-alpine

MAINTAINER Deyve Vieira <deyvedvm@hotmail.com>

RUN apk update && apk add bash

RUN mkdir -p /opt/app

ENV PROJECT_HOME /opt/app

COPY target/star-wars-api.jar $PROJECT_HOME/star-wars-api.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"./star-wars-api.jar"]