package org.linuxkernel.google.translate.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	static Properties props = new Properties();
	static {
		InputStream in = Config.class.getResourceAsStream("/config.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static final String USER_AGENT = props.getProperty("user-agent");
	public static final String COOKIE = props.getProperty("cookie");

}
