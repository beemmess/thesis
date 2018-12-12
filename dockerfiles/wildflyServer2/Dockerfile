# Dockerfile for Feature extraction server
FROM jboss/wildfly
RUN wildfly/bin/add-user.sh -a -u user -p user -g guest --silent
RUN wildfly/bin/add-user.sh -u admin -p admin -g admin --silent
ADD standalone2.xml wildfly/standalone/configuration/
COPY standalone.conf wildfly/bin/
USER jboss
CMD ["wildfly/bin/standalone.sh","-c","standalone2.xml","-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

