FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar 
COPY ${JAR_FILE} bankinginquiries.jar
ENTRYPOINT ["java", "-jar", "/bankinginquiries.jar"]