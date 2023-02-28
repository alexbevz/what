FROM gradle:8.0.1-jdk17-jammy as build
ENV SERVICE_DIR=/usr/app
RUN mkdir -p $SERVICE_DIR
WORKDIR $SERVICE_DIR
ADD . $SERVICE_DIR
RUN gradle clean bootJar

FROM openjdk:17-jdk-alpine
COPY --from=build /usr/app/build/libs/rancher*.jar /app/runner.jar
ENV SERVICE_PORT=8083
ENV GRPC_PORT=9093
ENTRYPOINT java -jar /app/runner.jar --SERVICE_PORT=${SERVICE_PORT} --GRPC_PORT=${GRPC_PORT}