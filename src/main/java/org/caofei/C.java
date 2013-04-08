package org.caofei;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Msg;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResults;
import org.caofei.bean.Person;
import org.json.JSONObject;
import org.xml.sax.SAXException;

public class C {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MessageUtils.getMessage("HM.BAJ.ERROR.001"));
		Person person = new Person();
		person.setId("dasd");
		person.setName("caof");
		person.setAge("32");
		try {
			String[] xmls = { "/validation.xml", "/validator-rules.xml" };
			InputStream[] uri = new InputStream[xmls.length];
			for (int i = 0; i < xmls.length; i++) {
				// uri[i] = IOUtils.toInputStream(xmls[0],"UTF-8");
				uri[i] = C.class.getResourceAsStream(xmls[i]);
			}

			ValidatorResources resources;
			resources = new ValidatorResources(uri);

			Form form = resources.getForm(Locale.getDefault(), "Person");
//			populateMessage(form);
			List<Field> fields = form.getFields();
			List<Fieldex> fieldexs = new LinkedList<Fieldex>();
			for (Field field : fields) {
				fieldexs.add(new Fieldex(field));
			}
			for (Fieldex fieldex : fieldexs) {
				List<String> detDependencyList = fieldex.getDependencyList();
				for (String dependency : detDependencyList) {
					String msgs = resources.getValidatorAction(dependency).getMsg();
					System.out.println(msgs);
					fieldex.getMessageMap().put(dependency, MessageUtils.getMessage(msgs));
//					Msg msg = new Msg();
//					msg.setResource(false);
//					msg.setKey(MessageUtils.getMessage(msgs));
//					msg.setName("name");
//					fieldex.addMsg(msg);
					
//					System.out.println(dependency);
//					System.out.println(resources.getValidatorAction(dependency).getMsg());
				}
//				System.out.println(field.getMessages());
			}
			System.out.println(JSONObject.wrap(fieldexs));

//			System.out.println(resources.getValidatorAction("required").getJavascript());
//			Validator validator = new Validator(resources, "Person");
//			validator.setParameter(Validator.BEAN_PARAM, person);
//			ValidatorResults validatorResults = validator.validate();
//			printResult(validatorResults,resources);
//			System.out.println(validatorResults.getResultValueMap());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printResult(ValidatorResults validatorResults, ValidatorResources resources) {
		Map rvm = validatorResults.getResultValueMap();
		System.out.println(rvm);
//		Set<String> names = validatorResults.getPropertyNames();
//		for (String key : names) {
//			ValidatorResult vr = validatorResults.getValidatorResult(key);
//			Iterator actions = vr.getActions();
//			while (actions.hasNext()) {
//				String object = (String) actions.next();
//				ValidatorAction va = resources.getValidatorAction(object);
//				System.out.println(vr.isValid(object));
//			}
//		}
	}

}
