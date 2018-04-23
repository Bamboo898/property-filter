package com.gs.utils.propertyfilter.processors;

import org.apache.commons.lang.StringUtils;

/**
 *
 * 马赛克处理类。 用于将字符串部分文字转换成 ***
 *
 * 用法: mobileNumber | mask:'3':'4' 结果: 134***4059
 *
 * 参数1: *号前面字符长度
 * 参数2: *号后面字符长度
 *
 */

@Processor("mask")
public class MaskPipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {
		
		if (input == null || StringUtils.isBlank(input.toString())) return input;
		
		int preLength = Integer.valueOf(params[0]);
		int suffixLength = Integer.valueOf(params[1]);
		
		String value;
		
		value = StringUtils.substring(input.toString(), 0, preLength);
		value += "***";
		value += StringUtils.substring(input.toString(), input.toString().length() - suffixLength, input.toString().length());
		
		return value;
	}

}
