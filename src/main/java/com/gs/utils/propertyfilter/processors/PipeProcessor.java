package com.gs.utils.propertyfilter.processors;

/**
 *
 * 过滤器接口
 *
 * @since 1.0
 * @author leitao
 */

public interface PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException;
	
}
