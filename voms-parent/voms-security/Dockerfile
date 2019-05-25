FROM openjdk:11-slim

ENV SPRING_PROFILES_ACTIVE=local,common,common-local

VOLUME /tmp
ARG DEPENDENCY=dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","com.optiva.topup.voms.VomsSecurityApp"]
