package org.apache.hadoop.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.api.FileSystemPathInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hive.metadata.TableInfo;
import org.junit.Test;

public class HiveDataLocationTest {

	
	//Table(tableName:table1, dbName:default, owner:root, createTime:1460029222, lastAccessTime:0, retention:0, sd:StorageDescriptor(cols:[FieldSchema(name:column1, type:string, comment:null), FieldSchema(name:column2, type:string, comment:null)], location:hdfs://centos:9000/user/hive/warehouse/table1, inputFormat:org.apache.hadoop.mapred.TextInputFormat, outputFormat:org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat, compressed:false, numBuckets:-1, serdeInfo:SerDeInfo(name:null, serializationLib:org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe, parameters:{serialization.format=1}), bucketCols:[], sortCols:[], parameters:{}, skewedInfo:SkewedInfo(skewedColNames:[], skewedColValues:[], skewedColValueLocationMaps:{}), storedAsSubDirectories:false), partitionKeys:[], parameters:{numFiles=1, transient_lastDdlTime=1460029337, totalSize=30}, viewOriginalText:null, viewExpandedText:null, tableType:MANAGED_TABLE)
	@Test
	public void testFileLocationType() throws Exception{
		
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.1.139:10000");
		
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("DESCRIBE EXTENDED table1");
		
		//get metadata data type name
		/*
		   ResultSetMetaData resultMetaData = resultSet.getMetaData();
		   for(int i = 1 ; i < resultMetaData.getColumnCount() ; i++){
		   System.out.println(resultMetaData.getColumnName(i));
		}*/
		//col_name,data_type
		List<String> metaDataResult = new ArrayList<String>();
		while(resultSet.next()){
			metaDataResult.add(resultSet.getString("data_type"));	
		}
		String tableInfoStr = metaDataResult.get(metaDataResult.size() - 1);
		TableInfo tableInfo = new TableInfo();
		String location = tableInfo.parserTableValue("location", tableInfoStr);
		
	
		Configuration config = new Configuration();
		config.set("fs.default.name", "hdfs://centos:9000");
		
		FileSystemPathInfo pathInfo = new FileSystemPathInfo(config);
		String type = pathInfo.getFileType(new Path(location));
		assertEquals("folder", type);
		
		conn.close();
		
	}
}
