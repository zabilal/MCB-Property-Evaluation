From openjdk:17
copy ./target/auth-service-1.0.0.jar auth-service.jar
CMD ["java","-jar","auth-service.jar"]

#FROM openjdk:8-jre-alpine
#ENV APP_FILE config-service-1.0-SNAPSHOT.jar
#ENV APP_HOME /app
#EXPOSE 8088
#COPY target/$APP_FILE $APP_HOME/
#WORKDIR $APP_HOME
#ENTRYPOINT ["sh", "-c"]
#CMD ["exec java -jar $APP_FILE"]