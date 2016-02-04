# drone_project

## Build the Tomcat Server
- Start the server with Tomcat by run the class Drone_control.java on server.

### Test of the Server

- After the server is builded, you may test with the following address: And you can see a welcome page.
- http://localhost:8080/FAAserver/service/drone/welcome
 
## Launch of the drone parts
- Run the classes by this order:
- MyKafkaClusters.java
- NCommunicatorSimulator.java
- NDroneSimulator.java
- NTracerSimulator.java
- When all of them is launched, press "start" in the console of the NTracerSimulator which will make the drones start working.