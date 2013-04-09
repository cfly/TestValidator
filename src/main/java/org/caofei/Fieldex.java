package org.caofei;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Var;

public class Fieldex extends Field {
	public Fieldex(Field field) {
		try {
			Field fieldClone = (Field) field.clone();
			BeanUtils.copyProperties(this, fieldClone);
			Map varMap = fieldClone.getVars();
			Set<String> keySet = varMap.keySet();
			for (String key : keySet) {
				this.addVar((Var) varMap.get(key));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
