package com.gs.utils.propertyfilter.processors;

/**
 *
 * 默认值处理类。用于在传入值为null时返回一个特定的值<br />
 *<br />
 * 用法: amount | defaultVal:'0.0' 结果: 0.0<br />
 *<br />
 * 参数1: 默认值<br />
 * @since 1.0
 * @author leitao
 *
 */

@Processor("defaultVal")
public class DefaultValuePipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {
		return input == null ? params[0] : input;
	}

}
