package com.gs.utils.propertyfilter.processors;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * Collection转换成字符串的处理类。用于将一个collection中的对象的某个属性值提取出来，并转换成以 "," 分隔的字符串
 *
 * 用法: users | coll2Str:'userName' 结果: 李四, 王五, 赵六
 *
 * 参数1: 要提取的属性名
 *
 */

@Processor("coll2Str")
public class CollectionToStringPipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {
		
		if (!(input instanceof Collection)) {
			return input;
		}
		
		try {
			Collection collection = (Collection) input;
			
			String field = params[0];
			
			StringBuffer val = new StringBuffer();
			
			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				val.append(PropertyUtils.getProperty(iterator.next(), field));
				if (iterator.hasNext()) {
					val.append(", ");
				}
			}
			
			return val.toString();
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new PipeProcessException(e.getMessage(), e);
		}
	}
}
