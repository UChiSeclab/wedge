### Bug Title and Jira link:

* Title: MAPREDUCE-6622 -- Add capability to set JHS job cache to a task-based limit
* Jira report: https://issues.apache.org/jira/browse/MAPREDUCE-6622

### Bug Description

* The JobHistoryServer is used load the job history files for every finished job in mapreduce
* It has a cache that limits the max number of job entries in the memory
* However, a job can have as many as 100k tasks, making one job entry consume too many memory

### Root Cause

*  Throttling limit (cache capacity) at a higher level (#jobs instead of #tasks)

### Symptoms / Impact:

* Consume too much memories and have high GC time

### Fix / Patch:

* Use Guava’s LoadingCache and use #tasks as the eviction criteria
