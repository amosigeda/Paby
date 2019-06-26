package com.wtwd.pet.app.util;

import java.util.Properties;

public class PropDo {
	public String getPropFromFile(String pFile, String pKey) throws Exception {
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader()
				.getResourceAsStream(pFile));
		return pros.getProperty(pKey);
	}	
	
}
