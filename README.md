# Lab Exercise 3 - Using Dynamic Vector Clocks to Implement Group Messaging
## CIS 656 – Fall 2017

## Objective
By completing this project, students will learn:

* How to send messaging using UDP datagrams
* How to implement dynamic vector clocks to maintain causal ordering of multicast messages among a dynamic group of peer processes.  


## Problem definition

In this lab exercise you are going to implement vector clocks for a dynamic group of chat processes. Clients will multicast messages via UDP datagrams sent serially. When using UDP datagrams to communicate over a wide area network, it is possible that packets will be lost or arrive at the destination in a different order than what they were originally sent.  

## How to run
Please ensure that _maven_ is installed in your system by running ```mvn -v```.
After that, sitting on the folder where you got this project just run:

```bash
$ mvn package
$ java -jar target/lab3-0.0.1-SNAPSHOT.jar <username>
```

For example:
```
$ java -jar target/lab3-0.0.1-SNAPSHOT.jar Bob
```
