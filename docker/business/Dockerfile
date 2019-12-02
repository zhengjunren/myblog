FROM openjdk:8u232-jdk

RUN mkdir -p /user/local/myblog

COPY  business-0.0.1-SNAPSHOT.jar /user/local/myblog

WORKDIR /user/local/myblog

CMD java -jar business-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

EXPOSE 9000
