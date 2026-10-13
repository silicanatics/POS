# Use official Tomcat image as base
FROM tomcat:10.1-jdk17

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/examples /usr/local/tomcat/webapps/docs /usr/local/tomcat/webapps/manager /usr/local/tomcat/webapps/host-manager

# Copy the built WAR file to Tomcat webapps directory
# The WAR will be deployed as the root application
COPY target/pos-api-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port the application listens on
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
