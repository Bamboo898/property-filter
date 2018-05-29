package com.gs.utils.propertyfilter.processors;

import com.gs.utils.propertyfilter.processors.Exceptions.PipeProcessException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *
 * 对Collection进行指定字段的排序, 支持多属性排序。<br>
 * 返回一个List。<br>
 * <br>
 * 用法: users | collSort:'name':'age.desc'<br>
 * <br>
 * 参数1: 要排序的第一个属性(使用默认字典顺序排序)<br>
 * 参数2: 要排序的第二个属性名及排序方式<br>
 * <br>
 * @Since 1.1
 * @author leitao
 *
 */
@Processor("collSort")
public class CollectionSortPipeProcessor implements PipeProcessor {

    @Override
    public Object process(Object input, String... params) throws PipeProcessException {

        List<Object> objects = new ArrayList<Object>((Collection) input);

         Collections.sort(objects, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                try {
                    for (String param : params) {
                        String[] p = param.split("\\.");
                        String filedName = p.length > 0 ? p[0] : param;
                        String orderType = p.length > 1 ? p[1] : null;
                        String o1FieldVal = PropertyUtils.getProperty(o1, filedName).toString();
                        String o2FieldVal = PropertyUtils.getProperty(o2, filedName).toString();

                        if (o1FieldVal.compareTo(o2FieldVal) == 0) {
                            continue;
                        }

                        if (StringUtils.isBlank(orderType)
                                || "asc".equalsIgnoreCase(orderType)) {
                            return o1FieldVal.compareTo(o2FieldVal);
                        } else {
                            return o2FieldVal.compareTo(o1FieldVal);
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getMessage(), e);
                }

                return 0;
            }
        });

        return objects;
    }

    @Override
    public boolean valueSupported(Object input) {
        return input instanceof Collection;
    }

}
