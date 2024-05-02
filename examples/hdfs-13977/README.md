### Bug Title and Jira link:

* Title: HDFS-13977 -- NameNode can kill itself if it tries to send too many txns to a QJM simultaneously
* Jira report: https://issues.apache.org/jira/browse/HDFS-13977

### Bug Description

* NN will send the EditLog to JN via QJM by RPC. 
* When log output buffer is full, it schedules an auto-sync to flush the logs. This logic is broken in this version
* In addition, the output buffer doesn’t have a configurable cap

### Root Cause

*  The auto-sync logic is broken. And the output buffer cap is not configurable

### Symptoms / Impact:

* Exception in NN and JN. The NN can accumulate a big journal and the RPC will fail due to the size.

### Fix / Patch:

* Fix the auto-sync logic and add a configuration param for the output buffer cap
