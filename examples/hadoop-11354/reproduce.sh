# Reproduce HADOOP-11354
# Tested node: CloudLab r320

ISSUE_ID=hadoop-11354
COMMIT_HASH=57cb43be50c81daad8da34d33a45f396d9c1c35b
REPO_ROOT=$HOME
HADOOP_HOME=$REPO_ROOT/hadoop
OUTPUT_DIR=$HADOOP_HOME/hadoop-common-project/hadoop-common/target/surefire-reports
OLD_HADOOP_HOME=/proj/osu-nfs-test-PG0/tianxi/git/hadoop
PATCH_HOME=${OLD_HADOOP_HOME}/patch/${ISSUE_ID}
# Some packages are deprecated for this yarn version
MAVEN_OPTIONS=
MAVEN_TEST=TestThrottledInputStream

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
git apply $PATCH_HOME/${ISSUE_ID}.before.patch

# Run maven tests
mvn ${MAVEN_OPTIONS} test -Dtest=${MAVEN_TEST}

# Save the logs
cp -r $OUTPUT_DIR ${REPO_ROOT}/${ISSUE_ID}.before

# Clean up and checkout the after patch
git checkout -f && git apply ${PATCH_HOME}/${ISSUE_ID}.after.patch
mvn clean
mvn ${MAVEN_OPTIONS} test -Dtest=${MAVEN_TEST}
cp -r $OUTPUT_DIR ${REPO_ROOT}/${ISSUE_ID}.after