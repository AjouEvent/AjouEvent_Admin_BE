FROM openjdk:17-jdk

WORKDIR /app

COPY ./build/libs/*.jar app.jar

EXPOSE 8443

# 실행 명령어는 (환경변수 profile 등은 docker-compose에서 주입)
ENTRYPOINT ["java", "-jar", "app.jar"]
