FROM tomcat:9.0.44

COPY target/pause-piano-backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
#COPY ROOT.xml /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml
EXPOSE 8080

CMD ["catalina.sh", "run"]