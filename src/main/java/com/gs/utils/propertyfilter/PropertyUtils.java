package com.gs.utils.propertyfilter;

import java.lang.reflect.InvocationTargetException;

import com.gs.utils.propertyfilter.processors.Exceptions.PipeProcessException;
import com.gs.utils.propertyfilter.processors.ExpressionParser;
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

	public static Object getProperty(Object bean, String expression) throws PipeProcessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return getProperty(bean, expression, null);
	}

	public static Object getProperty(Object bean, String expression, ApplicationContext applicationContext) throws PipeProcessException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ExpressionParser expressionParser = new ExpressionParser(expression);

		String fieldName = expressionParser.getFieldName();
		
		Object value;
		try {
			value = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, fieldName);
		} catch (NestedNullException e) {
			value = null;
		}
		
		if (expressionParser.isNoProcessorDef()) {
			return value;
		}
		
		for (ExpressionParser.ProcessorDef def : expressionParser.getProcessorDefs()) {
			PipeProcessor pipeProcessor = ProcessorBeanFactory.getInstance().getPipeProcessor(def.getName(), applicationContext);

			if (pipeProcessor == null) {
				throw new PipeProcessException("Not found pipe by name: "+def.getName());
			}

			value = pipeProcessor.valueSupported(value) ? pipeProcessor.process(value, def.getParams().toArray(new String[]{})) : value;
		}
		
		return value;
	}

}
