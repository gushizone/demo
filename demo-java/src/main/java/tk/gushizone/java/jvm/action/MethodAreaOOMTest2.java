package tk.gushizone.java.jvm.action;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MethodAreaOOMTest2 {

    /**
     * 因为类信息保存在元空间,所以会报元空间内存溢出
     *
     * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
     *
     * CGLib：https://blog.csdn.net/yaomingyang/article/details/82762697
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Hello.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    System.out.println("Enhanced hello");
                    // 调用Hello.say()
                    return proxy.invokeSuper(obj, args);
                }
            });
            Hello enhancedOOMObject = (Hello) enhancer.create();
            enhancedOOMObject.say();
            System.out.println(enhancedOOMObject.getClass().getName());
        }
    }
}

class Hello {
    public void say() {
        System.out.println("Hello Student");
    }
}