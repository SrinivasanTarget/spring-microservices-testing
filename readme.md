# Project Overview:

This application is developed to demonstrate how to write the basic unit tests and integration tests for the microservices.

This application contains 2 micro services - department service and employee service which is developed using java,spring framework.
Employee service will have the employee details and the department service will have the department details of each employee. Each service has couple of GET and POST Api endpoints

Each service has its own controller which interacts with rest clients, service where the business logic resides and the repository to interact with the Mongodb.

# Setup Instructions for the workshop:

**Prerequisites**
*  Java
*  Maven
*  IntelliJ IDEA (Or any IDE of your choice)
*  MongoDB and Mongo compass (optional if your IDE supports database connectivity)
*  Docker (testcontainers image)
*  Postman

# Installation instructions for Mac

*  Install java `https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-macos.htm` and maven `(https://www.baeldung.com/install-maven-on-windows-linux-mac)`

*  Install IntelliJ IDEA edition `(https://www.jetbrains.com/idea/download/#section=mac)`

*  Install Lombok as a plugin in IntelliJ. (Preferences->Plugins)

*  Enable annotation processing in IntelliJ (Preferences->Build,Execution,Deployment ->Annotation Processors and then enable annotation processing)

*  Install Docker using instructions from here `https://docs.docker.com/docker-for-mac/install/`

*  Install Postman `https://www.postman.com/downloads/` and import the collection `https://www.getpostman.com/collections/7b9838617f32e19443b6`

*  Do a git clone on `https://github.com/SrinivasanTarget/spring-microservices-testing.git`

**Installation of mongoDB in Mac:**

*  Follow instructions from `https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/`
*  `brew tap mongodb/brew`
*  `brew install mongodb-community`
*  If mongodb/brew/mongodb-community 4.4.1 is already installed, it's just not linked. You can use `brew link mongodb-community` to link this version.
*  If step 4 does not work, to force the link and overwrite all conflicting files: `brew link --overwrite mongodb-community`
*  Mongodb should be running before we start the string application.To start the services run: `brew services start mongodb-community@4.4`

**Installation of Mongodb Compass:**
*  In order to view the mongo DB and visually explore your data, you can install MongoDB Compass
`https://downloads.mongodb.com/compass/mongodb-compass-1.23.0-darwin-x64.dmg
`
**To start the application:**
*  Import the cloned repo in IntelliJ as Maven project
*  Allow some time for the IDE to resolve the dependencies.
*  Run /employee-service/src/main/java/com/sample/EmployeeApplication.java and src/main/java/com/sample/DepartmentApplication.java or run man spring-boot:run for both the services.  (Employee runs in 8080 and department runs in 8081)

*  You should see this to be sure that spring is properly connected to the mongoDb. 
`INFO 5692 --- [localhost:27017] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:1, serverValue:7}] to localhost:27017
`
*  You should see this to be sure that the employee service is running successfully. 
`INFO 5692 --- [           main] com.sample.EmployeeApplication           : Started EmployeeApplication in 3.403 seconds (JVM running for 9.731
`


# Installation instructions for Windows:

*  Install Java `https://www.oracle.com/java/technologies/javase-jdk15-downloads.html`
and follow these steps to set path `https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.htm` 

*  Install Maven `https://maven.apache.org/download.cgi`. Download the binary zip archive.
Follow instructions from `https://mkyong.com/maven/how-to-install-maven-in-windows/`

*  Install IntelliJ Idea from `https://www.jetbrains.com/idea/download/#section=windows` . You can also use any IDE of your choice
*  Do a git clone on the repo
*  Install Lombok as a plugin in IntelliJ. (File->Settings->Plugins)
*  Enable annotation processing in IntelliJ (File->Setting->Build,Execution,Deployment ->Annotation Processors and then enable annotation processing)

*  Install MongoDB - `https://www.mongodb.com/try/download/community?tck=docs_server` and follow instructions from `https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/`
*  Download the complete installation in case you need the MongoDB Compass too. Else you can download a custom installation. You can select Run the service as Network Service user installation.

*  Install docker desktop for windows `https://www.docker.com/products/docker-desktop`
*  In case you don't have a linux kernel installed already, install the linux kernel package from here.
`https://docs.microsoft.com/en-us/windows/wsl/install-win10#step-4---download-the-linux-kernel-update-package`

*  Install Postman (`https://www.postman.com/downloads/`) and import the collection `https://www.getpostman.com/collections/7b9838617f32e19443b6`


**To start the application:**
*  Import the cloned repo in IntelliJ as Maven project
*  Allow some time for the IDE to bring up the dependencies.
*  Run /employee-service/src/main/java/com/sample/EmployeeApplication.java and src/main/java/com/sample/DepartmentApplication.java or run man spring-boot:run for both the services.  (Employee runs in 8080 and department runs in 8081)

*  You should see this to be sure that spring is properly connected to the mongoDb. 
`INFO 5692 --- [localhost:27017] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:1, serverValue:7}] to localhost:27017
`
*  You should see this to be sure that the employee service is running successfully. 
`INFO 5692 --- [           main] com.sample.EmployeeApplication           : Started EmployeeApplication in 3.403 seconds (JVM running for 9.731
`

