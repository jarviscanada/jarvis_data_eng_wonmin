# Linux Cluster Monitoring Agent

## Introduction
The Linux Cluster Monitoring Agent records the hardware specifications of each host machine and monitors their memory usage in real time. The machines are connected under a common network via a network swtich. The data is then stored in a RDBMS database for easy accessibility and maintenance. This in turn allows any team that administrates Linux Clusters to generate reports for future resource planning and tight optimization. The program was implemented using both bash scripts and the PostgreSQL database, while also containing some SQL queries in order to answer possible questions. Utilizing Docker, the end product is lightweight, versatile and easy to use.

## Architecture and Design
<img src="./assets/Architecture.png" height="10%" width="50%">
As noted above in the introduction, the bash scripts are run on separate linux machines, all connected with a network switch. The host information is then sent to the relational database which is parsed by the end user. In the case of the picture, a host is also acting as the server who receives all incoming data. As more machines are added into the cluster, it is safer to prepare a separate server to secure the traffic.

### Tables Explanation
Within the database `host_agent`, there are two tables:`host_info` and `host_usage`. `host_info` stores the hardware specification of the host machine and inserts it the database. It contains information like CPU Architecture, CPU Model, and more. The later table, `host_usage` stores the resource usage data of the host machine. It collects information like the amount of free memory and available disk space.

####`host_info` Contents:
* 

 
