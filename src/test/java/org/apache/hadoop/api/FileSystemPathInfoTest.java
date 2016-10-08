package org.apache.hadoop.api;

import static org.junit.Assert.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

public class FileSystemPathInfoTest {
	//@Test
	public void testFileSystePathInfo(){
		Configuration config = new Configuration();
		config.set("fs.default.name", "hdfs://centos:9000");
		
		FileSystemPathInfo pathInfo = new FileSystemPathInfo(config);
		String type = pathInfo.getFileType(new Path("hdfs://centos:9000/user/hive/warehouse/table1"));
		
		assertEquals("folder", type);
	}

}
