package com.gs.utils.propertyfilter.processors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 此类通过扫描带有<code>@Processor</code>的类
 * 来构造一个key为processor name，value为
 * 处理器Class的map，再通过getPipeProcessor
 * 方法从map中找到对应的处理器，若未发现对应的处理
 * 器Class则抛出异常，若找到则根据是否有
 * <code>@Componet</code>注解来决定是new一个实
 * 例还是从Spring容器中拿取实例。
 *
 * @since 1.0
 * @author leitao
 *
 */
public class ProcessorBeanFactory {

    private static final String PIPE_PROCESSOR_PKG = "com.gs.utils.propertyfilter.processors";

    private Map<String, Class> mapping;

    private ProcessorBeanFactory(){}

    public static ProcessorBeanFactory getInstance() {
        return PipeProcessorFactoryInstance.instance;
    }

    private static class PipeProcessorFactoryInstance {
        private static ProcessorBeanFactory instance = new ProcessorBeanFactory();
    }

    /**
     * 根据参数 name 从IOC容器中拿PipeProcessor
     * 或直接创建一个对象
     *
     * @param name  过滤器名称
     * @param applicationContext Spring的ApplicationContext实例
     * @return PipeProcessor    过滤器处理器
     * @throws PipeProcessException
     */
    public PipeProcessor getPipeProcessor(String name, ApplicationContext applicationContext) throws PipeProcessException {
        if (!mapping.containsKey(name)) {
            throw new RuntimeException("Not found pipe processor");
        }

        Class clazz = mapping.get(name);
        String springBeanName = getSpringBeanName(clazz);

        if (StringUtils.isNotBlank(springBeanName)
                && applicationContext == null) {
            throw  new PipeProcessException("Spring ApplicationContext Cannot be null!");
        }

        if (StringUtils.isNotBlank(springBeanName)) {
            return (PipeProcessor) applicationContext.getBean(springBeanName);
        } else {
            try {
                return (PipeProcessor) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new PipeProcessException("Create processor instance exception ", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new PipeProcessException("Create processor instance exception ", e);
            }
        }
    }

    /**
     * 构造代码块，在new该类时执行。
     * 通过反射扫描指定包下面的类，拿到
     * 有<code>@Processor</code>注解的类。
     */
    {
        mapping = new HashMap<>();

        Reflections reflections = new Reflections(PIPE_PROCESSOR_PKG);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Processor.class);

        for (Class clazz : classes) {
            Processor processorAnno = (Processor) clazz.getAnnotation(Processor.class);
            if (processorAnno != null) {
                String processorName = processorAnno.value();
                mapping.put(processorName, clazz);
            }
        }
    }

    /**
     * 通过<code>@Component</code>注解拿到PipeProcessor
     * 在IOC容器中的名字。
     *
     * @param clazz
     * @return
     */
    private String getSpringBeanName(Class clazz) {
        Component componentAnno = (Component) clazz.getAnnotation(Component.class);
        if (componentAnno != null) {
            return StringUtils.isBlank(componentAnno.value()) ? WordUtils.uncapitalize(clazz.getSimpleName()) : componentAnno.value();
        }
        return null;
    }

}
