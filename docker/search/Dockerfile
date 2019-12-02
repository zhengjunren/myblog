FROM openjdk:8u232-jdk

RUN mkdir -p /user/local/myblog

COPY  search-0.0.1-SNAPSHOT.jar /user/local/myblog

WORKDIR /user/local/myblog

CMD java -jar search-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

EXPOSE 8500
