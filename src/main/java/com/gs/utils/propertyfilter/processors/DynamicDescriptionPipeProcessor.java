package com.gs.utils.propertyfilter.processors;

import java.util.Locale;

import com.gs.utils.propertyfilter.processors.Exceptions.PipeProcessException;
import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * 国际化处理类。主要用于通过拼接资源key来从国际化资源中拿取值。<br />
 * 该处理类需依赖Spring使用。<br />
 *<br />
 * 用法: orderStatus | dynDesc:'common.orderstatus':'zh_CN' 结果: 已完成<br />
 *<br />
 * 参数1: 国际化资源的Key<br />
 * 参数2: 地区标识<br />
 * @since 1.0
 * @author leitao
 *
 */

@Component
@Processor("dynDesc")
public class DynamicDescriptionPipeProcessor implements PipeProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	public Object process(Object input, String... params) throws PipeProcessException {

		String code = params.length > 0 ? params[0].trim() + "." + input.toString() : input.toString();
		Locale locale = params.length > 1 ? LocaleUtils.toLocale(params[1].trim()) : Locale.SIMPLIFIED_CHINESE;
		
		return applicationContext.getMessage(code, null, locale);
	}

	@Override
	public boolean valueSupported(Object input) {
		return input instanceof String;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
