package org.caofei;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.Field;

public class Fieldex extends Field {
	public Fieldex(Field field) {
		try {
			BeanUtils.copyProperties(this, BeanUtils.cloneBean(field));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<String,String> messageMap = new HashMap<String, String>();

	public Map<String, String> getMessageMap() {
		return messageMap;
	}

	public void setMessageMap(Map<String, String> messageMap) {
		this.messageMap = messageMap;
	}


}
