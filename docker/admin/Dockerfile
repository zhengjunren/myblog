FROM openjdk:8u232-jdk

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone

RUN mkdir -p /user/local/myblog

COPY  admin-0.0.1-SNAPSHOT.jar /user/local/myblog

WORKDIR /user/local/myblog

CMD java -jar admin-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

EXPOSE 8000
