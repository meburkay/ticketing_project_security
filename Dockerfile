#This is a different Dockerfile method. We use here mvn commands instead of java.
#From part we take from docker hub we search maven jdk11 image and find a ready image to use and put it here.

FROM amd64/maven:3.8.6-openjdk-11
WORKDIR usr/app
#We use maven this time and it is in pom.xml. Because of that we use root file here .
COPY  .  .
#How do I run springboot application by maven? When we search this we can find below code easily.
ENTRYPOINT ["mvn","spring-boot:run"]