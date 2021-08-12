package tk.gushizone.redis.limit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gushizone@gmail.com
 * @date 2021/8/12 2:23 下午
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 限流标识前缀
     */
    String prefix() default "limit:";

    /**
     * 限流标识
     */
    String key() default "";

    /**
     * 阈值
     */
    int threshold();

    /**
     * 时间窗口（s）
     */
    int period() default 1;
}
