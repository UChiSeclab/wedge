### Bug Title and Jira link:

* Title: HDFS-14973 -- Balancer getBlocks RPC dispersal does not function properly
* Jira report: https://issues.apache.org/jira/browse/HDFS-14973

### Bug Description

* It’s related to HDFS-11384 that finds a corner case where the delay may not enforce the QPS cap
* Because getBlocks() might be execute fast enough that later threads can get scheduled without delay because the delay is applied according to the thread’s number

### Root Cause

*  The first batch of threads do not have delays, leaving threads a chance to slip in without delays, thereby exceeding the QPS cap

### Symptoms / Impact:

* A spike in NN’s call queue and the QPS exceeds the cap

### Fix / Patch:

* Instead of using delay, use the Guava RateLimiter on NameNodeConnector that is singleton shared among Dispatcher threads
