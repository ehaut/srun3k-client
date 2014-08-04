package client.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyRW {
	private Properties props;
	private static PropertyRW reader;
	private static String file;
	
	private PropertyRW() {
		props = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
	}
	public static PropertyRW getInstance(String file){
		PropertyRW.file = file;
		if (reader == null) reader = new PropertyRW();
		return reader;
	}
	public static PropertyRW getInstance(){
		return getInstance(Global.PROPERTYFILE);
	}
	public String getProperty(String key){
		return props.getProperty(key);
	}
	public void setProperty(String key, String value){
		props.setProperty(key, value);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			props.store(out, "powered by szq");
		} catch (Exception e) {
			throw new RuntimeException();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
		
	}
	
}
