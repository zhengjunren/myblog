FROM openjdk:8u232-jdk

RUN mkdir -p /user/local/myblog

COPY  admin-0.0.1-SNAPSHOT.jar /user/local/myblog

WORKDIR /user/local/myblog

CMD java -jar admin-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

EXPOSE 8000
