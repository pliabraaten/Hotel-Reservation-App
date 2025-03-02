FROM openjdk:17-jdk-alpine

EXPOSE 8080

ADD target/D387_sample_code-0.0.2-SNAPSHOT.jar D387_sample_code-0.0.2-SNAPSHOT.jar

ENTRYPOINT ["sh", "-c","java -jar /D387_sample_code-0.0.2-SNAPSHOT.jar"]
