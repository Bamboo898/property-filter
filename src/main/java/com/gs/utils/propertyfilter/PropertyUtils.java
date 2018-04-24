package com.gs.utils.propertyfilter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.gs.utils.propertyfilter.processors.PipeProcessException;
import com.gs.utils.propertyfilter.processors.PipeProcessor;
import com.gs.utils.propertyfilter.processors.ProcessorBeanFactory;
import org.apache.commons.beanutils.NestedNullException;
import org.springframework.context.ApplicationContext;

/**
 *
 * getProperty为主入口
 *
 * @since 1.0
 * @author leitao
 *
 */
public class PropertyUtils {

	public static Object getProperty(Object bean, String name) throws PipeProcessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return getProperty(bean, name, null);
	}

	public static Object getProperty(Object bean, String name, ApplicationContext applicationContext) throws PipeProcessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String[] s = name.split("\\|");
		
		Object value;
		try {
			value = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, s[0].trim());
		} catch (NestedNullException e) {
			value = null;
		}
		
		if (s.length <= 1) {
			return value;
		}
		
		for (int i = 1; i < s.length; i++) {
			value = process(s[i].trim(), value, applicationContext);
		}
		
		return value;
	}

	private static Object process(String expression, Object input, ApplicationContext applicationContext) throws PipeProcessException {
		String[] p = expression.split(":");

		if (p.length <= 0) {
			throw new PipeProcessException("Not found pipe name");
		}

		String name = p[0].trim();
		PipeProcessor pipeProcessor = ProcessorBeanFactory.getInstance().getPipeProcessor(name, applicationContext);

		if (pipeProcessor == null) {
			throw new PipeProcessException("Not found pipe by name: "+name);
		}

		List<String> params = Lists.newArrayList();

		Pattern pattern = Pattern.compile("\'.*?\'");
		Matcher matcher = pattern.matcher(expression);
		while (matcher.find()) {
			params.add(matcher.group(0).replaceAll("\'", ""));
		}

		return pipeProcessor.process(input, params.toArray(new String[]{}));

	}

}
