FROM jboss/wildfly
RUN wildfly/bin/add-user.sh -a -u user -p user -g guest --silent
RUN wildfly/bin/add-user.sh -u admin -p admin -g admin --silent
EXPOSE 9990
EXPOSE 9090
ADD standalone-custom.xml wildfly/standalone/configuration/
CMD ["wildfly/bin/standalone.sh","-c","standalone-custom.xml","-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
