package com.gs.utils.propertyfilter.processors;

import com.gs.utils.propertyfilter.processors.Exceptions.PipeProcessException;
import org.apache.commons.lang.StringUtils;

/**
 *
 * 马赛克处理类。 用于将字符串部分文字转换成 ***<br />
 *<br />
 * 用法: mobileNumber | mask:'3':'4' 结果: 134***4059<br />
 *<br />
 * 参数1: *号前面字符长度<br />
 * 参数2: *号后面字符长度<br />
 * @since 1.0
 * @author leitao
 *
 */

@Processor("mask")
public class MaskPipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {

		int preLength = Integer.valueOf(params[0]);
		int suffixLength = Integer.valueOf(params[1]);
		
		String value;
		
		value = StringUtils.substring(input.toString(), 0, preLength);
		value += "***";
		value += StringUtils.substring(input.toString(), input.toString().length() - suffixLength, input.toString().length());
		
		return value;
	}

	@Override
	public boolean valueSupported(Object input) {
		return input instanceof String && !StringUtils.isBlank(input.toString());
	}

}
