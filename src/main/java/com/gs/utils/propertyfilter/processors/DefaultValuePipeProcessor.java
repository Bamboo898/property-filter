package com.gs.utils.propertyfilter.processors;

/**
 *
 * 默认值处理类。用于在传入值为null时返回一个特定的值
 *
 * 用法: amount | defaultVal:'0.0' 结果: 0.0
 *
 * 参数1: 默认值
 *
 */

@Processor("defaultVal")
public class DefaultValuePipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {
		return input == null ? params[0] : input;
	}

}
