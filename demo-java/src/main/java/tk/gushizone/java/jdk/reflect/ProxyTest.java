package tk.gushizone.java.jdk.reflect;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代理 是一种基础的设计模式，通过它可以有效的扩展对象的功能或做一些额外的操作。
 * <p>
 * JDK 提供了 java.lang.reflect.Proxy 类，其利用反射可以简单的完成动态代理。
 *
 * @author gushizone@gmail.com
 * @date 2019-05-17 11:24
 */
public class ProxyTest {

    /**
     * JDK动态代理只能代理接口
     * <p>
     * 获取代理类： Proxy.newProxyInstance(...) ，需要：
     * 1. 类的加载器，
     * 2. 希望代理的接口列表，
     * 3. InvocationHandler调用处理器的实现。
     */
    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        List<String> list = new ArrayList<>();

        Class<?> clazz = list.getClass();

        List<String> proxyInstance = (List<String>) Proxy.newProxyInstance(
                clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
                    /**
                     * 代理调用被代理对象的方法时，会通过调用此方法来实现。
                     * @param proxy 调用该方法的代理实例
                     * @param method 被代理方法
                     * @param args 被代理方法的入参
                     * @return 期望：被代理方法的返回值
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 单纯代理的常规写法：需要被代理的对象
                        return method.invoke(list, args);
                    }
                });

        proxyInstance.add("abc");

        System.out.println(list); // abc
    }

    /**
     * 动态代理 - cglib
     */
    @Test
    @SuppressWarnings("unchecked")
    public void cglibTest() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ArrayList.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("args: " + Arrays.asList(args));
                return proxy.invokeSuper(obj, args);
            }
        });

        List<String> list = (List<String>) enhancer.create();
        list.add("abc");

        System.out.println(list);
    }

    // todo - 静态代理
}

