### Bug Title and Jira link:

* Title: HDFS-11384 -- Add option for balancer to disperse getBlocks calls to avoid NameNode's rpc.CallQueueLength spike
* Jira report: https://issues.apache.org/jira/browse/HDFS-11384

### Bug Description

* Hadoop uses Java NIO for IPC communications
* The socket listener and reader are synchronized so that newly connected sockets are not enqueued until the reader finishes with the last socket

### Root Cause

*  The over synchronization requires frequent lock and unlock

### Symptoms / Impact:

* A slower/busier reader can stall the listener. A heavy load can only produce 20% ~ 30% CPU util

### Fix / Patch:

* Employ a thread-safe queue to store pending connections to avoid frequent back-and-forth locking
