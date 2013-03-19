package org.caofei;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.caofei.bean.Person;
import org.xml.sax.SAXException;

public class C {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person person = new Person();

		try {
			String[] xmls = { "/validation.xml", "/validator-rules.xml" };
			InputStream[] uri = new InputStream[xmls.length];
			for (int i = 0; i < xmls.length; i++) {
				// uri[i] = IOUtils.toInputStream(xmls[0],"UTF-8");
				uri[i] = C.class.getResourceAsStream(xmls[i]);
			}

			ValidatorResources resources;
			resources = new ValidatorResources(uri);
			Validator validator = new Validator(resources, "Person");
			validator.setParameter(Validator.BEAN_PARAM, person);
			ValidatorResults validatorResults = validator.validate();
			printResult(validatorResults,resources);
//			System.out.println(validatorResults.getResultValueMap());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printResult(ValidatorResults validatorResults, ValidatorResources resources) {
		Set<String> names = validatorResults.getPropertyNames();
		for (String key : names) {
			ValidatorResult vr = validatorResults.getValidatorResult(key);
			Iterator actions = vr.getActions();
			while (actions.hasNext()) {
				String object = (String) actions.next();
				ValidatorAction va = resources.getValidatorAction(object);
				System.out.println(vr.isValid(object));
			}
		}
	}

}
