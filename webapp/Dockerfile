FROM openjdk:8-jre
VOLUME /tmp
ADD target/webapp-*.war webapp.war

EXPOSE 8080
EXPOSE 50050

ENTRYPOINT ["java", "-jar", "webapp.war"]