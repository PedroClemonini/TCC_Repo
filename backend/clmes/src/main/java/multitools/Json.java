package multitools;
import Model.*;
import java.sql.ResultSet;
import java.util.HashMap;

import com.google.gson.Gson;

public class Json {
	
	public Json() {
	
	}
	
	public Object parse(String payload, String className) {
		
		   try {
		        Class<?> clazz = Class.forName("Model."+className);
		        return new Gson().fromJson(payload, clazz);
		        
		    } catch (ClassNotFoundException e) {
		     
		        e.printStackTrace();
		        return null;
		    }
	}
	
	
	public String parse(Object object) {

		return "";
	}

	public String parse( ResultSet resultSet) {

		return "";
	}
	
	public String parse( HashMap<String, String> hashMap) {

		return "";
	}
	
	public String parse( String[]  values) {

		return "";
	}
	
	public String parse( String[][]  values) {

		return "";
	}	
}
