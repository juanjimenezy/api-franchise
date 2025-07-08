FROM amazoncorretto:21
WORKDIR /app
COPY applications/app-service/build/libs/api-franchise.jar api-franchise.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-franchise.jar"]