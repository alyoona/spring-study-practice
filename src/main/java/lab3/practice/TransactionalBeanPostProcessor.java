package lab3.practice;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TransactionalBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> originalClassMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> clazz = bean.getClass();
        if(clazz.isAnnotationPresent(Transactional.class)) {
            originalClassMap.put(beanName, clazz);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = originalClassMap.get(beanName);
        if(clazz != null) {
            return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("<< Start transaction >>");
                    Object result = method.invoke(bean, args);
                    System.out.println("<< End transaction >>");
                    return result;
                }
            });
        }

        return bean;
    }
}
