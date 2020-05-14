# Linux Cluster Monitoring Agent

## Introduction
The Linux Cluster Monitoring Agent records the hardware specifications of each host machine and monitors their memory usage in real time. The machines are connected under a common network via a network swtich. The data is then stored in a RDBMS database for easy accessibility and maintenance. This in turn allows any team that administrates Linux Clusters to generate reports for future resource planning and tight optimization. The program was implemented using both bash scripts and the PostgreSQL database, while also containing some SQL queries in order to answer possible questions. Utilizing Docker, the end product is lightweight, versatile and easy to use.

## Architecture and Design
<p align="center">
   <img src="./assets/Architecture.png" height="3%" width="50%">
</p>
As noted above in the introduction, the bash scripts are run on separate linux machines, all connected with a network switch. The host information is then sent to the relational database which is parsed by the end user. In the case of the picture, a host is also acting as the server who receives all incoming data. As more machines are added into the cluster, it is safer to prepare a separate server to secure the traffic.

### Tables Explanation
Within the database `host_agent`, there are two tables: `host_info` and `host_usage`. `host_info` stores the hardware specification of the host machine and inserts it the database. It contains information like CPU Architecture, CPU Model, and more. The later table, `host_usage` stores the resource usage data of the host machine. It collects information like the amount of free memory and available disk space.

#### `host_info` Contents:
* `id`: Host identification number unique to each host
* `hostname`: Name of the host connected to the database
* `cpu_number`: Number of CPUs in the host machine
* `cpu_architecture`: System architecture of the host machine
* `cpu_model`: Model of the host machine's CPU
* `cpu_mhz`: CPU processor of the host machine
* `L2_caches`: Size of the L2 cache which holds data that is likely to be accessed by the CPU next
* `total_mem`: Total memory within the host machine
* `timestamp`: Time at which the data was collected, referred to in UTC timezone

#### `host_usage` Contents:
* `timestamp`: Same as `host_info`, returns the time at which the data was collected
* `host_id`: Host identification number, follows the `id` tag within `host_info`
* `memory_free`: Amount of free memory within the host machine
* `cpu_idle`: Percentage of CPU time of which it was idle
* `cpu_kernel`: Percentage of CPU time of which it was running kernel code
* `disk_io`: Number of disk I/O
* `disk_available`: Amount of disk space available in root directory

### Scripts Explanation
* [a relative link](host_info.sh) is run on every cluster within the network connection to gather the hardware specification of each machine. It is executed only once with the assumption that the host machine's specifications do not change.
* [a relative link](host_usage.sh) is run on every cluster within the network connection to gather the resource usage of each machine. It is executed every minute and stores consecutive data into the database.
* [a relative link](psql_docker.sh) is run to create, start and stop the PostgreSQL instance using docker. Note that the script does not support deletion of docker containers or volumes.
* [a reltive link](ddl.sql) is a SQL script to create the two tables mentioned above: `host_info` and `host_usage`. If the tables already exist, the script will exit with an error.
* [a relative link](queries.sql) contains two SQL queries to assist in database management and information processing. The first query groups hosts by the number of CPUs in their machine and sorts their memory size in descending order. The second query averages the amount of used memory (in percentage) over 5 minute intervals for each host.
