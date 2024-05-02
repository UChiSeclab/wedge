### Bug Title and Jira link:

* Title: HDFS-12998 -- Provide an iterator-based listing API for calculating snapshotDiff
* Jira report: https://issues.apache.org/jira/browse/HDFS-12998

### Bug Description

* SnapshotDiff is used to get the difference of two snapshots
* It’s returned in a big RPC (default max entries: 1000) as requested by user

### Root Cause

*  When users are only interested in some report entries, memory spaces are wasted
*  When memory space is scarce, it can potentially cause OOM

### Symptoms / Impact:

* Memory waste & pressure, OOM

### Fix / Patch:

* Add SnapshotDiffReportListingIterator to let users iterate over the report and enable JVM GC to recycle entries once they’re processed
