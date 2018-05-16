package com.gs.utils.propertyfilter.processors;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * Collection转换成字符串的处理类。用于将一个collection中的对象的某个属性值提取出来，并转换成以 "," 分隔的字符串。<br />
 *<br />
 * 用法: users | coll2Str:'userName' 结果: 李四, 王五, 赵六<br />
 *<br />
 * 参数1: 要提取的属性名<br />
 *<br />
 * @since 1.0
 * @author leitao
 *
 */

@Processor("coll2Str")
public class CollectionToStringPipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {

		try {
			Collection collection = (Collection) input;
			
			String field = params[0];
			
			List<String> values = new ArrayList();
			
			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
			    Object o = PropertyUtils.getProperty(iterator.next(), field);
			    if (o != null)
                    values.add(o.toString());
			}
			
			return values.stream().collect(Collectors.joining(", "));
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new PipeProcessException(e.getMessage(), e);
		}
	}

    @Override
    public boolean valueSupported(Object input) {
        return input instanceof Collection;
    }
}
