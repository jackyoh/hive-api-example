package org.apache.hive.metadata;

public class TableInfo {
	
	public String parserTableValue(String key, String tableInfo){
		int keyStartIndex = tableInfo.indexOf(key + ":");
		int index = 1;
		StringBuilder value = new StringBuilder();
		
		while(!tableInfo.substring(keyStartIndex, keyStartIndex + index).equals(",")){
			value.append(tableInfo.substring(keyStartIndex, keyStartIndex + index));
			keyStartIndex++;
		}
		
		return value.toString().replaceAll(key + ":", "");
	}	
	
}
