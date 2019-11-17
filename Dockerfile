FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/spring-boot-demo.jar spring-boot-demo.jar
ENTRYPOINT ["java","-jar","/spring-boot-demo.jar"]
