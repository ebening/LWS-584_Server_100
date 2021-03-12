package com.lowes.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.logging.Logger;

public class PropertyUtil {
	private static final Properties props = new Properties();
	private static final Logger log = Logger.getLogger(PropertyUtil.class);
	private static final String CONFIG_URL = "/lws";
	public static final String TEMP_URL = "/uadec/temp";
	static{
		try {
			InputStream is = new FileInputStream(new File(CONFIG_URL+"/application.properties"));
			props.load(is);
		} catch (IOException e) {
			log.error(e);
		} 
	}
	public static String getProperty(String key){
		return props.getProperty(key);
	}
}
