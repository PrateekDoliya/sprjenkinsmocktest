FROM openjdk:17
EXPOSE 8081
ADD target/spring-jenkins-mock.jar spring-jenkins-mock.jar
ENTRYPOINT [ "java" , "-jar", "spring-jenkins-mock.jar"]