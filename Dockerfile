###############################################################################
#. Base Image Selection
#jdk image is used as the base image for building and running a Spring Boot application.
#base image to serve as the foundation for the other build stages in
# this file.
#
#For a Spring Boot application, the base image should be a JDK, not Alpine Linux, because you need Java to build and run your app.
#FROM eclipse-temurin:17-jdk AS build is correct for the build stage.
#do not need a separate base stage with Alpine.

# By specifying the "latest" tag, it will also use whatever happens to be the
# most recent version of that image when you build your Dockerfile.
# If reproducibility is important, consider using a versioned tag
# (e.g., alpine:3.17.2) or SHA (e.g., alpine@sha256:c41ab5c992deb4fe7e5da09f67a8804a46bd0592bfdf0b1847dde0e0889d2bff).
FROM gradle:8.12.1-jdk21 AS builder
WORKDIR /home/gradle/project

# copy root Gradle metadata so multi-project build and pluginManagement work
COPY settings.gradle settings.gradle
COPY gradle/ gradle/
COPY gradle.properties gradle.properties
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat

COPY gradle/wrapper gradle/wrapper
# optional: cache Gradle dependencies between builds
ENV GRADLE_USER_HOME=/cache/.gradle
VOLUME /cache/.gradle

# copy only this service sources (build context must be repo root)
COPY Sazzler-Product-Service/ Sazzler-Product-Service/
# copy dependent local projects so project(':util') and project(':api-definition') are available
COPY util/ util/
COPY api-definition/ api-definition/
WORKDIR /home/gradle/project

# ensure wrapper is executable and build only the module
RUN chmod +x ./gradlew
# Use the image's Gradle to avoid wrapper download inside the container
RUN gradle :Sazzler-Product-Service:clean :Sazzler-Product-Service:build --no-daemon -x test

# runtime stage
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=builder /home/gradle/project/Sazzler-Product-Service/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
