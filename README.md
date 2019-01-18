# Step-By-Step Guide for a up and running system

This guide is an instruction on how to get a working system of the
proposed solution.

## Pre-requirements

To deploy the web application to the servers, then Apache Maven needs to
be installed on the computer to build and deploy the application.  
  
Maven can be downloaded from here:  
<http://maven.apache.org/download.cgi>  
  
Maven installation guide here:  
<http://maven.apache.org/install.html>  
  
The proposed solution is hosted on GitHub and can be cloned here:  
<https://github.com/beemmess/thesis>  
  
A Docker account is needed to use the service, here is the signup
link:  
<https://hub.docker.com/signup>

## Step 1 - Ubuntu Server

The specifications of servers have been described in
[\[sec:ubuntuServer\]](#sec:ubuntuServer), and is reccomended to use
similar specification of servers. For this guide, two servers are needed
to be configured to run the microservices.  
To begin with, a remote access to the servers are needed, with a ssh
command:

    ssh user@hbl-wildfly.compute.dtu.dk
    ssh user@hbl-cassandra.compute.dtu.dk

After a connection has been established to both of them in separate
Terminal windows then there is a need for opening ports on both Ubuntu
servers if those ports are unopened. The list below is a list of
commands of opening these ports:  

    sudo ufw allow 5000/tcp
    sudo ufw allow 5445/tcp
    sudo ufw allow 5455/tcp
    sudo ufw allow 7000/tcp
    sudo ufw allow 7001/tcp
    sudo ufw allow 7199/tcp
    sudo ufw allow 8080/tcp
    sudo ufw allow 9042/tcp
    sudo ufw allow 9090/tcp
    sudo ufw allow 9990/tcp

## Step 2 - Docker

Docker is required on both machines, if there is no docker installed
then please follow the instruction in the Docker documentation for
installation guide as the tutorial there is up to date if there are any
changes in installing Docker. The docker installation instruction can be
found here:  
<https://docs.docker.com/install/linux/docker-ce/ubuntu/>

### **Processing Server**

Login into docker by entering the following command and follow the
instructions from Docker:  
  

    docker login

  
  
Pull configured Wildfly Server from Docker Cloud\[1\]:  
  

    docker pull beemmess/wildfly

  
  
Pull Python Web client for data processing from Docker Cloud\[2\]:  
  

    docker pull beemmess/dataprocessing

  
  
Run the following command to run the WildFly Application Server
container with few ports open for services to reach the Docker
container:

1.  <https://cloud.docker.com/repository/docker/beemmess/wildfly>

2.  <https://cloud.docker.com/repository/docker/beemmess/dataprocessing>

docker run -d -p 9990:9990 -p8080:8080 -p5445:5445 -p5455:5455 --name wildfly beemmess/wildfly

  
  
Run the following command to run the Cassandra database container, with
a port opened for services to reach the Docker container:  
  

    docker run -d -p 9042:9042 --name cassandra cassandra

## Step 3 - Deploy

There are few things needed to be adjusted in the project as the IP
address of the servers needs to configured in the `thesis` project
cloned from GitHub earlier.

  - **Processing Server Web Application**
    
      - Navigate to ProcessingServer/src/main/java/api/PathConstants
        file and change the IP address in `REMOTE_SERVER_IP` value to
        **Database Server IP address**. This is so that processing
        server can send messages to the database server.
    
      - Navigate to ProcessingServer/pom.xml file and change the IP
        address of the host in the swagger-maven-plugin which is found
        in build plugins to the **Processing Server IP address**.
    
      - Navigate to ProcessingServer/src/webapp/SwaggerUI/index.html
        file and change the IP address on line 43 to the **Processing
        Server IP address**.

  - **Database Server Web Application**
    
      - Navigate to DataBaseServer/src/main/java/beans/PathConstants
        file and change the IP address in `REMOTE_SERVER_IP` value to
        **Processing Server IP address**. This is so that database
        server can send messages to the processing server.

### **Deploy processing server application**

Navigate to the `ProcessingServer` directory in the cloned `thesis`
repository from GitHub and then enter the following
    command:  
  

    mvn install wildfly:deploy -Dusername=<USER> -Dpassword=<PASSWORD> -Dhostname=hbl-wildfly.compute.dtu.dk

  
  
Where the hostname parameter is the IP address of the `Processing
Server`

### **Deploy database server application**

Navigate to the `DataBaseServer` directory in the cloned `thesis`
repository from GitHub and then enter the following
    command:  
  

    mvn install wildfly:deploy -Dusername=<USER> -Dpassword=<PASSWORD> -Dhostname=hbl-cassandra.compute.dtu.dk

  
  
Where the hostname parameter is the IP address of the `Database Server`
USER and PASSWORD are available up on request
