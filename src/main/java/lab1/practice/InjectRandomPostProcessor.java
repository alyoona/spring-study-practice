package lab1.practice;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * класс спрингового пост процессора, должен имплементировать интерфейс
 *
 * @see BeanPostProcessor
 * <p>
 * Класс отвечает за логику инжекта случайного числа в поле проаннотированное, специально обученной аннотацией
 */
public class InjectRandomPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectRandom.class)) {
                InjectRandom injectRandomAnnotation = field.getDeclaredAnnotation(InjectRandom.class);
                Class<?> type = injectRandomAnnotation.type();
                field.setAccessible(true);
                Random random = new Random();
                if (int.class == type) {
                    int injectingIntValue = random.nextInt(injectRandomAnnotation.intRangeTo() + 1);
                    ReflectionUtils.setField(field, bean, injectingIntValue);
                } else if (double.class == type) {
                    double injectingDoubleValue = random.nextDouble()*injectRandomAnnotation.doubleRangeTo();
                    ReflectionUtils.setField(field,bean,injectingDoubleValue);
                } else if (String.class == type) {
                    String generatedString = RandomStringUtils.randomAlphanumeric(injectRandomAnnotation.numberOfCharacters());
                    ReflectionUtils.setField(field,bean,generatedString);
                }
                field.setAccessible(false);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
