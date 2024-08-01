# Reproduce HDFS-12998
# Tested node: CloudLab r320

ISSUE_ID=hdfs-12998
COMMIT_HASH=83e2bb98eea45ddcb598080f68a2f69de1f04485
REPO_ROOT=$HOME
HADOOP_HOME=$REPO_ROOT/hadoop
OUTPUT_DIR=$HADOOP_HOME/hadoop-hdfs-project/hadoop-hdfs/target/surefire-reports
OLD_HADOOP_HOME=/proj/osu-nfs-test-PG0/tianxi/git/hadoop
PATCH_HOME=${OLD_HADOOP_HOME}/patch/${ISSUE_ID}
# Some packages are deprecated for this yarn version
MAVEN_OPTIONS=

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

# Install Protobuf 2.5.0
sudo make -C /proj/osu-nfs-test-PG0/tianxi/git/protobuf install
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
# There's only one patch for this bug because there's no deletion or modification of existing code, but adding a class (feature).
# In addition, the tests for buggy and patched are separate (testupSnapshotDiffReportTooBigOld and testupSnapshotDiffReportTooBigNew)
git apply $PATCH_HOME/${ISSUE_ID}.before.after.patch

# Run maven tests
mvn clean
mvn ${MAVEN_OPTIONS} -f ${HADOOP_HOME}/hadoop-hdfs-project/pom.xml test -Dtest=TestSnapshotDiffReport#testupSnapshotDiffReportTooBigOld

# Save the logs
cp -r $OUTPUT_DIR ${REPO_ROOT}/${ISSUE_ID}.before

# Clean up and checkout the after patch
mvn clean
mvn ${MAVEN_OPTIONS} -f ${HADOOP_HOME}/hadoop-hdfs-project/pom.xml test -Dtest=TestSnapshotDiffReport#testupSnapshotDiffReportTooBigNew
cp -r $OUTPUT_DIR ${REPO_ROOT}/${ISSUE_ID}.after