### Bug Title and Jira link:

* Title: HADOOP-11354 -- ThrottledInputStream doesn't perform effective throttling
* Jira report: https://issues.apache.org/jira/browse/HADOOP-11354

### Bug Description

* ThrottledInputStream is used to throttle the read bw of some operations, e.g., ExportSnapshot which is to transfer snapshot from one HDFS cluster to another
* The ThrottledInputStream is implemented by inject a sleep when the effective BW is greater than targeted BW

### Root Cause

* The logic of throttling should use while loop to ensure the throttled BW is lower than threshold, but it’s using if clause where the delay is only applied once.

### Symptoms / Impact:

* The throttling is not effective when the transfer size is large (throttling is applied less frequently)

### Fix / Patch:

* Simply change the `if` condition to `while` loop
