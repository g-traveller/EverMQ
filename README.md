# EverMQ usage:
This project is under development, it is used to demo how to implement a basic MQ with Netty.


## Description

EverMQ is message queue middleware which based on Netty, It is compatible with different communication
protocol: TCP, HTTP, WebSocket and RPC, Also, It provides high level Java SDK.

###Topic
Topic represent message container, message will be forward to specific topic with topic attribute.
Each topic contain a queue. FIFO

###Message
Message will be forward to given topic, any producer which subscribe this topic will receive message.


## Prerequisite
* Maven 3
* JDK 11

        
## Build
Open terminal, run following command:
```
    mvn clean package -DskipTests
```

## Run
After run, following command to test:
```
curl localhost:8000/actuator/health
```
