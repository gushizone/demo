package tk.gushizone.redis.limit;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2021/8/12 2:29 下午
 */
@Slf4j
@Aspect
@Component
public class AccessLimitAspect {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
    @Autowired
    private RedisScript<Boolean> limitScript;

    @Pointcut("@annotation(tk.gushizone.redis.limit.AccessLimit)")
    public void pointcut() {
        log.info("AccessLimitAspect...");
    }

    @Before("pointcut()")
    public void limit(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        String key = annotation.prefix() + StringUtils.defaultIfBlank(annotation.key(), methodKey(method));

        Boolean ret = redisTemplate.execute(limitScript, Lists.newArrayList(key), annotation.threshold(), annotation.period());
        if (BooleanUtils.isNotTrue(ret)) {
            throw new RuntimeException("服务忙，请稍后重试：limit");
        }

    }

    private String methodKey(Method method) {
        String key = method.getName();

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (ArrayUtils.isNotEmpty(parameterTypes)) {

            String paramType = Arrays.stream(parameterTypes)
                    .map(Class::getName)
                    .collect(Collectors.joining(",", "(", ")"));

            key += paramType;
        }
        return key;
    }


}
