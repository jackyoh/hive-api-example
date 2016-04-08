package org.apache.hadoop.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.util.StringUtils;
import org.junit.Test;

public class ConfigurationTest {
	//Hadoop org.apache.hadoop.security.authorize.ProxyUsers
	@Test
	public void testProxyUsersPUT(){
		Configuration conf = new Configuration();
		conf.set("hadoop.proxyuser.root.groups", "root");
		conf.set("hadoop.proxyuser.user.groups", "user1,user2,user3,user4,user5");
		
		Map<String, String> maps = conf.getValByRegex("hadoop.proxyuser.[^.]*.groups");
		assertEquals(2, maps.size());
		
		Collection<String> userGroups = StringUtils.getStringCollection(maps.get("hadoop.proxyuser.user.groups"));
		assertEquals(5, userGroups.size());
		
		/*for(Entry<String, String> entry: maps.entrySet()){
			System.out.println(entry.getKey() + "    " + StringUtils.getStringCollection(entry.getValue()));
		}*/		
	}
	@Test
	public void testDoAs(){
		HiveConf hiveConf = new HiveConf();
		String doAs = hiveConf.get("hive.server2.enable.doAs");
		System.out.println(doAs);
	}
}
