### Bug Title and Jira link:

* Title: HDFS-14370 -- Edit log tailing fast-path should allow for backoff
* Jira report: https://issues.apache.org/jira/browse/HDFS-14370

### Bug Description

* In edit log tailing, the SBNN fetches the edit logs from the JN via RPC
* There’s a fixed delay after each RPC

### Root Cause

*  The RPC is constant so that even when the cluster is idle (no edit logs available), SBNN will still keep sending RPCs to JN at a high rate

### Symptoms / Impact:

* A waste of CPU cycles
* The RPC can block other RPCs of higher importance, hanging the system

### Fix / Patch:

* When the returned RPC response is empty, employ an exponential backoff
