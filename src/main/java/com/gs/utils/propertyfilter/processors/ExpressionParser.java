package com.gs.utils.propertyfilter.processors;

import com.google.common.collect.Lists;
import com.gs.utils.propertyfilter.processors.Exceptions.ExpressionException;
import com.gs.utils.propertyfilter.processors.Exceptions.FieldNameNotFoundException;
import com.gs.utils.propertyfilter.processors.Exceptions.PipeProcessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 表达式解析器。
 *
 * @author leitao
 * @since 1.2
 *
 */
public class ExpressionParser {

    private String expression;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    /**
     *
     * 根据表达式中的 "|" 获得字段名
     *
     * @return 需要处理的字段名
     * @throws PipeProcessException
     */
    public String getFieldName() throws ExpressionException {
        String[] s = expression.split("\\|");
        if (s.length >= 1)
            return StringUtils.trim(s[0]);
        throw new FieldNameNotFoundException("Not Found Field Name");
    }

    /**
     *
     * 根据 "|" 符号分隔成多个子表达式
     *
     * @return ProcessorDef的实例集合
     * @throws PipeProcessException
     */
    public List<ProcessorDef> getProcessorDefs() throws ExpressionException {
        List<ProcessorDef> defs = Lists.newArrayList();
        String[] s = expression.split("\\|");
        if (s.length <= 1)
            return defs;

        for (int i = 1; i < s.length; i++) {
            defs.add(getProcessorDef(StringUtils.trim(s[i])));
        }

        return defs;
    }

    public boolean isNoProcessorDef() {
        String[] s = expression.split("\\|");
        return s.length <= 1;
    }

    /**
     *
     * 根据子表达式创建处理器定义对象
     *
     * @param subExp
     * @return ProcessorDef的实例
     * @throws PipeProcessException
     */
    private ProcessorDef getProcessorDef(String subExp) throws ExpressionException {
        String[] p = subExp.split(":");

        if (p.length <= 0) {
            throw new ExpressionException("Not found pipe name");
        }

        String name = p[0].trim();

        List<String> params = Lists.newArrayList();

        Pattern pattern = Pattern.compile("\'.*?\'");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            params.add(matcher.group(0).replaceAll("\'", ""));
        }

        return new ProcessorDef(name, params);
    }

    /**
     * 处理器定义类
     */
   public class ProcessorDef {
        private String name;            // 执行器名称
        private List<String> params;    // 参数列表

        public ProcessorDef(String name, List<String> params) {
            this.name = name;
            this.params = params;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getParams() {
            return params;
        }

        public void setParams(List<String> params) {
            this.params = params;
        }
    }

}
