# Reproduce MAPREDUCE-5027
# Tested node: CloudLab r320

# The incomplete patch was to mimic the large memory consumption of some jobs when they have a large number of tasks
# The memory consumption come from the constructor of CompletedJob which calls loadFullHistoryData to load history data from history file
# This patch uses a LoadingCache from the Guava library that will evict objects automatically (and you can't override methods for that)
# And construction of CompletedJob is called when storage.getFullJob() is called, which will construct it and push it to the cache
# The CompletedJob has complex constructor that is hard to construct from the test program. So we're in the delimma of needing a real object of Job and can't use the actual class of CompletedJob in the test

# What we did in the before and after patch is that, we extend the abstract class of Job, rather than CompletedJob. And we add a byte array to mimic the in-memory size of a big Job
# The byte array size is selected to be consistent with the description of Jira
# The logs in Answer.answer() proves that answer() is called when the trigger is actually invoked, not when callback is registered. This suffices our need
# The lesson is that, if we need a 'big' task in terms of memory consumption, but meanwhile it's hard to construct the workload.
# We can use mock to control the size of each 'big' task if the object has a public constructor

ISSUE_ID=mapreduce-5027
COMMIT_HASH=ea7aa7f80f2c7f5a95b8ce09f793d3a6a0748bb2
REPO_ROOT=$HOME
HADOOP_HOME=$REPO_ROOT/hadoop
OUTPUT_DIR=$HADOOP_HOME/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-hs/target/surefire-reports
OLD_HADOOP_HOME=/proj/osu-nfs-test-PG0/tianxi/git/hadoop
PATCH_HOME=${OLD_HADOOP_HOME}/patch/${ISSUE_ID}
# Some packages are deprecated for this yarn version
MAVEN_OPTIONS=
MAVEN_TEST=TestShuffleHandler#testMaxConnectionsOpenFD

# Check the BUILDING.txt of this version and install dependencies
sudo apt -y update
sudo apt -y install maven cmake openjdk-8-jdk docker build-essential autoconf automake libtool cmake zlib1g-dev pkg-config libssl-dev libsasl2-dev libsnappy-dev htop

# Override default Java version to be Java 8
JAVA_8_PATH=/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
if [ -f "$JAVA_8_PATH" ]; then
    # Set OpenJDK 8 as the default Java
    sudo update-alternatives --set java $JAVA_8_PATH
    echo "OpenJDK 8 is set as the default Java version."
else
    echo "OpenJDK 8 is not installed or the path is incorrect."
fi

# Install Protobuf 2.4.1
sudo make -C /proj/osu-nfs-test-PG0/tianxi/tmp/protobuf install
export LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH

# Clone Hadoop repo or to copy the hadoop from NFS (faster)
rm -rf ${HADOOP_HOME}
if [ -d "${OLD_HADOOP_HOME}/.git" ]; then
  cp -r ${OLD_HADOOP_HOME} ${HADOOP_HOME}
else
  git clone https://github.com/apache/hadoop.git $HADOOP_HOME
fi

# Checkout to parent version
cd $HADOOP_HOME && git checkout -f && git checkout ${COMMIT_HASH}^1

rm -rf ${REPO_ROOT}/${ISSUE_ID}.before
rm -rf ${REPO_ROOT}/${ISSUE_ID}.after

# Run tests before patched solution is applied
git apply $PATCH_HOME/${ISSUE_ID}.before.patch

# Install the project first
cd ${HADOOP_HOME} && mvn install -DskipTests=true

# Run maven tests
cd ${HADOOP_HOME}/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-hs
mvn clean
mvn ${MAVEN_OPTIONS} -f ${HADOOP_HOME}/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-hs/pom.xml test -Dtest=${MAVEN_TEST}
# Save the logs
cp -r $OUTPUT_DIR ${REPO_ROOT}/${ISSUE_ID}.before

# Clean up and checkout the after patch
cd ${HADOOP_HOME}
git checkout -f && git apply ${PATCH_HOME}/${ISSUE_ID}.after.patch
cd ${HADOOP_HOME}/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-hs
mvn clean
mvn ${MAVEN_OPTIONS} -f ${HADOOP_HOME}/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-hs/pom.xml test -Dtest=${MAVEN_TEST}
cp -r $OUTPUT_DIR ${REPO_ROOT}/${ISSUE_ID}.after