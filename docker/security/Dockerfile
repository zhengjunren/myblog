FROM openjdk:8u232-jdk

RUN mkdir -p /user/local/myblog

COPY  security-0.0.1-SNAPSHOT.jar /user/local/myblog

WORKDIR /user/local/myblog

CMD java -jar security-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

EXPOSE 8000
