package org.apache.hive.thrift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HiveServer2JDBCMain {
	
	public static void main(String args[]) throws Exception{
		//hive.server2.thrift.port
		System.out.println("Hive JDBC TEST11111");		
		System.out.println("Hive JDBC TEST22222");
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.1.170:10000");
		
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("select column1,column2 from table1 where column1 like '%a'");
		
		while(resultSet.next()){
			//System.out.println(resultSet.getString(1) + "    " + resultSet.getString(2));
			System.out.println(resultSet.getString(1));
		}
		conn.close();
	}

}
