### STAGE 1: Build ###
FROM openjdk:8-alpine
ADD target/gateway-MS.jar gateway-MS.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/gateway-MS.jar"]
