#!/bin/bash
echo JBOSS_HOME is set to ${JBOSS_HOME}
cd ${JBOSS_HOME}/bin
pwd
bash jboss-cli.sh --connect --commands="beans-queue add --queue-address=testQueue --entries=queue/test,java:jboss/exported/beans/queue/test" || true
