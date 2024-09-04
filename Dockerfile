FROM openjdk:17

# tzdata 패키지를 설치하여 타임존을 설정
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y tzdata
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080