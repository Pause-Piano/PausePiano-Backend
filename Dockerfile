FROM tomee:8.0.6-plume

RUN rm -rf /usr/local/tomee/webapps/ROOT
COPY target/pause-piano-backend-1.0-SNAPSHOT.war /usr/local/tomee/webapps/ROOT.war
EXPOSE 8080

CMD ["catalina.sh", "run"]