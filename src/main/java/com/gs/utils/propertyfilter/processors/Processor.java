package com.gs.utils.propertyfilter.processors;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 该注解类用于标识过滤器类
 *
 * @since 1.0
 * @author leitao
 */

@Retention(RUNTIME)
@Target(TYPE)
public @interface Processor {

	String value();
	
}
