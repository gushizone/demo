package tk.gushizone.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/11 3:08 下午
 * @see Aspect 定义切面
 */
@Aspect
@Component
public class WatchAspect {


    /**
     * 定义切点
     * <a>https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pointcuts</a>
     * <p>
     * Spring AOP 支持以下用于切入点表达式的 AspectJ 切入点指示符 (PCD)：
     * - execution: 用于匹配方法执行连接点。这是使用 Spring AOP 时要使用的主要切入点指示符。
     * - within：将匹配限制为某些类型内的连接点（使用 Spring AOP 时在匹配类型中声明的方法的执行）。
     * - this: 限制匹配连接点（使用 Spring AOP 时的方法执行），其中 bean 引用（Spring AOP 代理）是给定类型的实例。
     * - target: 限制匹配连接点（使用 Spring AOP 时的方法执行），其中目标对象（被代理的应用程序对象）是给定类型的实例。
     * - args: 限制匹配连接点（使用 Spring AOP 时的方法执行），其中参数是给定类型的实例。
     * - @target: 将匹配限制为连接点（使用 Spring AOP 时的方法执行），其中执行对象的类具有给定类型的注释。
     * - @args：限制匹配连接点（使用 Spring AOP 时的方法执行），其中传递的实际参数的运行时类型具有给定类型的注释。
     * - @within: 限制匹配到具有给定注解的类型中的连接点（使用 Spring AOP 时，在具有给定注解的类型中声明的方法的执行）。
     * - @annotation：将匹配限制为连接点的主题（在 Spring AOP 中运行的方法）具有给定注释的连接点。
     * <p>
     * - 可以配合逻辑符（ && ,||, ! ）使用
     */
    @Pointcut("@annotation(tk.gushizone.spring.aop.Watcher))")
    private void pointcut() {
    }

    /**
     * 定义通知
     * <a>https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-advice</a>
     * 一般使用环绕通知即可，可以使用 切点表达式 或 切点方法 指定切点。
     * <p>
     * - @Before: 前置通知，在目标方法之前执行的通知，但不能阻止连接点前的执行（除非它抛出一个异常）
     * - @AfterReturning: 返回通知，目标方法正常完成后执行的通知
     * - @AfterThrowing: 异常通知，在目标方法抛出异常退出时执行的通知
     * - @After: 后置通知，当目标方法后执行通知（不论时正常返回还是异常通知）
     * - @Around: 环绕通知，包围目标方法的通知，在其前后都可以执行自定义行为
     *
     * @param pjp 连接点
     * @return 返回结果
     */
    @Around("pointcut()")
    public Object annotation(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("pjp.getArgs() : " + Arrays.toString(pjp.getArgs()));
        System.out.println("pjp.getThis() : " + pjp.getThis());
        System.out.println("pjp.getTarget() : " + pjp.getTarget());
        System.out.println("pjp.getSignature() : " + pjp.getSignature());
        System.out.println("pjp.toString() : " + pjp.toString());

        // 前置 ...
        System.out.println("前置 ...");
        // 放行
        Object retVal = pjp.proceed();

        System.out.println("后置 ...");

        return retVal;
    }

}
