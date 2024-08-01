import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static org.apache.hadoop.util.Time.monotonicNow;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_DATA_READ_BANDWIDTHPERSEC_KEY;
import static org.junit.Assert.assertTrue;

/**
 * Tests throttle the data transfers related functions.
 */
public class TestDataTransferThrottler {
  private static final Logger LOG = LoggerFactory.getLogger(TestDataTransferThrottler.class);

  /**
   * Test read data transfer throttler.
   */
  @Test
  public void testReadDataTransferThrottler() throws Exception {
    final HdfsConfiguration conf = new HdfsConfiguration();
    try (MiniDFSCluster cluster = new MiniDFSCluster.Builder(conf).build()) {
      cluster.waitActive();
      final DistributedFileSystem fs = cluster.getFileSystem();

      // Create file.
      Path file = new Path("/test");
      long fileLength = 1024 * 1024  * 80;
      DFSTestUtil.createFile(fs, file, fileLength, (short) 1, 0L);
      DFSTestUtil.waitReplication(fs, file, (short) 1);

      // Set dfs.datanode.data.read.bandwidthPerSec.
      long bandwidthPerSec = 1024 * 1024 * 8;
      conf.setLong(DFS_DATANODE_DATA_READ_BANDWIDTHPERSEC_KEY, bandwidthPerSec);

      // Restart the first datanode.
      cluster.stopDataNode(0);
      cluster.startDataNodes(conf, 1, true, null, null);
      DataNode dataNode = cluster.getDataNodes().get(0);

      // Read file with throttler
      long throttleElapsedTime = readAndLogRate(fs, file, "No Throttle");

      // Validate throttling
      long expectedElapsedTime = fileLength / bandwidthPerSec * 1000; // in milliseconds
      long acceptableError = 1000; // 1 second, allowing for a small margin of error
      LOG.info("Throttle test - Expected elapsed time: {} ms, Actual elapsed time: {} ms, Acceptable error: {} ms",
              expectedElapsedTime, throttleElapsedTime, acceptableError);
      assertTrue(throttleElapsedTime >= expectedElapsedTime - acceptableError);
    }
  }

  private long readAndLogRate(DistributedFileSystem fs, Path file, String label) throws Exception {
    final long start = monotonicNow();
    long bytesRead = 0;
    byte[] buffer = new byte[1024 * 1024]; // 1MB buffer

    try (InputStream in = fs.open(file)) {
      int bytes;
      int count = 0;
      while ((bytes = in.read(buffer)) != -1) {
        count++;
        bytesRead += bytes;
        if (count % 16 == 0) {
          long currentTime = monotonicNow();
          long elapsedTime = currentTime - start;

          // Log after each buffer read
          double rate = (bytesRead / 1024.0 / 1024.0) / (elapsedTime / 1000.0); // MB/s
          LOG.info("{} - Time elapsed: {} ms, Bytes read: {} MB, Instantaneous rate: {} MB/s", label, elapsedTime, bytesRead/1024.0/1024.0, rate);
        }
      }
    }
    return monotonicNow() - start;
  }
}
