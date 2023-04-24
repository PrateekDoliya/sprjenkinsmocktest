FROM openjdk:17
ENV PATH "$PATH:/static/image"
ADD target/spring-jenkins-mock.jar spring-jenkins-mock.jar
ENTRYPOINT [ "java" , "-jar", "spring-jenkins-mock.jar"]