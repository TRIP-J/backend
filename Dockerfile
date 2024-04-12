FROM bellsoft/liberica-openjdk-alpine:17

ARG JAR_FILE=build/libs/*.jar

# ${JAR_FILE}에 의해 지정된 파일(또는 파일들)을
# 도커 이미지 내부에 app.jar라는 이름으로 복사하라는 의미
COPY ${JAR_FILE} app.jar

# wait-for-it.sh
RUN apk update && apk add bash
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

# 컨테이너가 외부에 노출할 포트를 설정
EXPOSE 8081

CMD ["./wait-for-it.sh", "tripjmysql:3306", "-s", "-t", "30", "java", "-jar", "/app.jar"]