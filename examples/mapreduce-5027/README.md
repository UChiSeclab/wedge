### Bug Title and Jira link:

* Title: MAPREDUCE-5027 -- Shuffle does not limit number of outstanding connections
* Jira report: https://issues.apache.org/jira/browse/MAPREDUCE-5027

### Bug Description

* ShuffleHandler doesn’t limit on the number of connections.
* When the map output is numerous and the #reducers is large, there could be many connections that put pressure on the available fd

### Root Cause

*  No limit on accepted connections

### Symptoms / Impact:

* File system pressure -- running out of file descriptors

### Fix / Patch:

* ShuffleHandler uses Netty which opens channels upon new incoming connection requests. The patch puts a check in the overridden channelOpen() and close the channel without handing the Netty event to upstream for accpet()
