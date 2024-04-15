FROM --platform=$BUILDPLATFORM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /src/
COPY pom.xml /src/pom.xml
RUN mvn dependency:go-offline

COPY src /src/

RUN mvn --batch-mode clean compile assembly:single

FROM build AS dev-envs
RUN <<EOF
apt-get update
apt-get install -y --no-install-recommends git
EOF

RUN <<EOF
useradd -s /bin/bash -m vscode
groupadd docker
usermod -aG docker vscode
EOF
# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /
CMD ["java", "-jar", "target/ProgrammingTechnologyAuthorizationApplication.jar" ]

FROM eclipse-temurin:21-jdk
ARG DEPENDENCY=/src/target
EXPOSE 8080
COPY --from=build ${DEPENDENCY}/ProgrammingTechnologyAuthorizationApplication.jar /ProgrammingTechnologyAuthorizationApplication.jar
CMD java -jar /ProgrammingTechnologyAuthorizationApplication.jar