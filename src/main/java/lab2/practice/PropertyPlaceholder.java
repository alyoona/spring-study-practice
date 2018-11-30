package lab2.practice;


import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

public class PropertyPlaceholder implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        PropertiesLoader propertiesLoader = (PropertiesLoader) beanFactory.getBean("propertiesLoader");
        Properties externalPropertiesFile = propertiesLoader.get();

        for(String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                for(Class<?> interfaze : clazz.getInterfaces()) {
                    if(interfaze == Printer.class) {
                        MutablePropertyValues values = beanDefinition.getPropertyValues();
                        for(PropertyValue propertyValue : values.getPropertyValueList() ) {
                            String propertyName = propertyValue.getName();
                            if("message".equals(propertyName)) {
                                TypedStringValue typedStringValue = (TypedStringValue) propertyValue.getValue();
                                String propertyKey = typedStringValue.getValue();
                                String newPropertyValue = externalPropertiesFile.getProperty(propertyKey);
                                values.add(propertyName, newPropertyValue);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
