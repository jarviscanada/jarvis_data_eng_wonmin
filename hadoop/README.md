# Hadoop/Hive Project

**Table of Contents**
* [Introduction](#Introduction)
* [Hadoop Cluster](#Hadoop-Cluster)
* [Hive Project](#Hive-Project)
* [Improvements](#Improvements)

# Introduction
The Hadoop project is implemented to process enormous amounts of data that will be
streamed from separate data analytics. With the use of Apache Hadoop, the processing of Big Data becomes 
convenient and efficient. A Hadoop  Cluster was first provisioned with Google Cloud Platform, and core Hadoop
components such as HDFS, YARN and MapReduce were evaluated. Then, with the use of Hive and Apache Zeppelin, solutions to
potential business problems were presented with HiveQL.

# Hadoop Cluster
<p align="center">
    <img src="./assets/Cluster_Architecture.png" height="5%" width="75%">
</p>

Following web UIs provided by the Google Cloud Platoform were used to evaluate Big Data tools:

* **YARN Resource Manager**: With YARN, we could verify and keep track of the amount of resources currently
being used in each node.
* **HDFS NameNode**: The NameNode stores relevant file metadata and manages the DataNodes stored in each worker node.
* **MapReduce Job History**: We can view previously submitted MapReduce jobs and keep track of their progress.
* **Zeppelin**: Apache Zeppelin is a web-based notebook that supports interactive data anlytics. The core Hive project was carried
out using Zeppelin

### Harware Specifications
* 2 vCPUs with 12GB Memory
* 100GB Disk Size
* 2 Worker Nodes

# Hive Project
With the Hive project, solutions to business problems are presented using Hive and Apache Zeppelin.
Querying with HiveQL was extensively researched, and its efficiency was tested and optimized. In the end,
the Zeppelin notebook consists of clean, readable solutions to each problem presented to the developer.
<p align="center">
    <img src="./assets/Zep_Notebook.png" height="5%" width="75%">
</p>

# Improvements
1. Further optimization of queries with the use of Spark and Tez
2. Possible implementation of User Defined Functions which might further increase performance
3. Test out the Hive API and build a separated Java application that will handle the querying