package org.apache.hive.driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.cli.CliSessionState;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;

public class HiveSparkDriverMain {
	
	public static void main(String args[]) throws Exception{
		    CliSessionState ss = new CliSessionState(new HiveConf(SessionState.class));
	        HiveConf hiveConf = ss.getConf();
	        //HDFS
	        hiveConf.set("fs.default.name", "hdfs://docker-server-a1:9000");
	        
	        //YARN
	        hiveConf.set("yarn.resourcemanager.address", "docker-server-a1:8032");
	        hiveConf.set("yarn.resourcemanager.scheduler.address", "docker-server-a1:8030");
	        hiveConf.set("yarn.resourcemanager.resource-tracker.address", "docker-server-a1:8031");
	        hiveConf.set("yarn.resourcemanager.admin.address", "docker-server-a1:8033");
	        hiveConf.set("mapreduce.framework.name", "yarn");
	        
	        //SPARK
	        hiveConf.set("hive.execution.engine", "spark");
	        hiveConf.set("spark.home", "/opt/spark-1.4.1-bin-hadoop2.6");
	        hiveConf.set("spark.yarn.jar", "hdfs://docker-server-a1:9000/spark-assembly-1.4.1-hadoop2.6.0.jar");
	     
	        //HIVE METASTORE
	        hiveConf.set("javax.jdo.option.ConnectionURL", "jdbc:mysql://192.168.1.170:3306/metastore_db");
	        hiveConf.set("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
	        hiveConf.set("javax.jdo.option.ConnectionUserName", "root");
	        hiveConf.set("javax.jdo.option.ConnectionPassword", "123456");

	        
	        hiveConf.set("hive.spark.client.server.connect.timeout", "10000000");

	        hiveConf.set("spark.master", "yarn-cluster");
	        
	        SessionState.start(ss);

	        Driver driver = new Driver(hiveConf);
	        //CommandProcessorResponse res = driver.run("SELECT count(column1)+count(column1) FROM table1");
	        CommandProcessorResponse res = driver.run("SELECT table1.column1 FROM table1 inner join table2 on table1.column1=table2.column1");
	        
	        System.out.println("Response Code:" + res.getResponseCode());
	        System.out.println("Error Message:" + res.getErrorMessage());
	        System.out.println("SQL State:" + res.getSQLState());

	        Schema s = driver.getSchema();
	        if (s != null) {
	            List<FieldSchema> schema = s.getFieldSchemas();
	            if ((schema != null) && (!schema.isEmpty())) {
	                for (int pos = 0; pos < schema.size(); pos++) {
	                    System.out.println("Name:" + schema.get(pos).getName() + ", Type:"
	                            + schema.get(pos).getType());
	                }
	            }
	        }

	        ArrayList<String> list = new ArrayList<String>();
	        
	        try {
	        	int count = 0;
	            while (driver.getResults(list)) {
	                for (String r : list) {
	                	count++;
	                    System.out.println(r);
	                }
	                list.clear();
	            }
	            System.out.println(count);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        ss.close();
	}

}
