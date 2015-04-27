#Read Me
To run the application you have two options. First one is running as a standalone application.
Second one is creating a web archive and deploying in a web container like tomcat.

##  Required software
- JDK 1.6 or upper

## Relieving URL from TAP to sample application

SMS
: http://{host}:{port of the sample app}/mo-receiver
USSD
: http://{host}:{port of the sample app}/mo-ussd

## Start-up port of sample app
- 5555

##  Create Standalone application

#### Note : Build the following api to download the dependencies.
samples/tap-app-api

- for linux users  run following commands.
- cd bin
- sh create_standalone.sh
- cd ../target/stand-alone/bin/
- sh start-app.sh

#### NOTE : As a time consuming, developers can start the sample application by running one script.
    --> Go to samples/hello-world-ant/
    --> Run start.sh script (e.g. sh start.sh)


- for windows users  run following commands.
- cd bin
- create_standalone.bat
- cd ..\target\stand-alone\bin
- start-app.bat


##  Create a web archive to deploy in a web container
for linux users  run following commands. tap-hello-world-app.war will be created in target folder.
- cd bin
- sh create_war.sh

for windows users  run following commands.
- cd bin
- create_war.bat

