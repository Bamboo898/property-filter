package com.gs.utils.propertyfilter.processors;

public interface PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException;
	
}
