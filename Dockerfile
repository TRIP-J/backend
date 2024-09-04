FROM openjdk:17

# tzdata 패키지를 설치하여 타임존을 설정
RUN apt-get update && apt-get install -y tzdata
# 타임존을 Asia/Seoul로 설정
ENV TZ=Asia/Seoul

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080