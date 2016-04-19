package org.apache.hadoop.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileSystemPathInfo {

	private Configuration config;
	
	public FileSystemPathInfo(Configuration config){
		this.config = config;
	}
	
	public String getFileType(Path path){
		try {
			FileSystem fs = FileSystem.get(config);
			FileStatus status = fs.getFileStatus(path);
			
			if(status.isDirectory()){
				return "folder";
			}else if(status.isFile()){
				return "file";
			}else{
				return "File path is not folder or file";
			}			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
