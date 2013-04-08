package org.caofei;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtils {
	private static ResourceBundle r;
	static{
		Locale locale = Locale.getDefault();
		if(Locale.JAPAN.equals(locale)){
			locale = new Locale("ja");
		}else if(Locale.ENGLISH.equals(locale)){
			locale = new Locale("en");
		}else if(Locale.CHINA.equals(locale)){
			locale = new Locale("ja");
		}else{
			
		}
		r = ResourceBundle.getBundle("hm-message", locale);
	}
	public static String getMessage(String key, String... args){
		return MessageFormat.format(r.getString(key), args);
	}
}
