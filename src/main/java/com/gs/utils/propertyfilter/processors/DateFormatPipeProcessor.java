package com.gs.utils.propertyfilter.processors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.LocaleUtils;

/**
 *
 * 日期格式化处理类。用于将日期对象格式化<br />
 *<br />
 * 用法: birthDay | dateFormat:'yyyy-MM-dd' 结果: 1991-01-13<br />
 *<br />
 * 参数1: 日期的目标格式<br />
 * 参数2: 地区标识<br />
 *<br />
 * @since 1.0
 * @author leitao
 */

@Processor("dateFormat")
public class DateFormatPipeProcessor implements PipeProcessor {

	public Object process(Object input, String... params) throws PipeProcessException {
		
		if (input == null) return null;
		
		SimpleDateFormat format = new SimpleDateFormat(params[0].trim());
		if (params.length > 1) {
			format = new SimpleDateFormat(params[0].trim(), LocaleUtils.toLocale(params[1].trim()));
		}
		
		return format.format((Date) input);
	}

}
