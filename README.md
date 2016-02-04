K# drone_project
## For using Kafka
Assuming that you are in the Kafka repository:
- First, start the Zookeeper server
~~~bash
$ bin/zookeeper-server-start.sh config/zookeeper.properties
~~~
- Then, in another shell, start a Kafka server
~~~bash
$ bin/kafka-server-start.sh config/server.properties
~~~
You can now create topics, either with shell commands or with a Java program.

## Build the Tomcat Server
- Start the server with Tomcat by run the class Drone_control.java on server.

### Test of the Server

- After the server is builded, you may test with the following address: And you can see a welcome page.
- http://localhost:8080/FAAserver/service/drone/welcome
 
# Launch of the drone parts
-Run the classes by this order:
MyKafkaClusters.java
NCommunicatorSimulator.java
NDroneSimulator.java
NTracerSimulator.java