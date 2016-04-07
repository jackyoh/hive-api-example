package org.apache.hive.thrift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveServer2JDBCMain {
	
	public static void main(String args[]) throws Exception{
		//hive.server2.thrift.port
		
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.1.183:10000");
		
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from table1");
		
		while(resultSet.next()){
			System.out.println(resultSet.getString(1) + "    " + resultSet.getString(2));
			
		}
		conn.close();
	}

}
