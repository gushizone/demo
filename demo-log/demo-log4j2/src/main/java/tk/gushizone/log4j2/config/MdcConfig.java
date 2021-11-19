package tk.gushizone.log4j2.config;

import cn.hutool.core.net.NetUtil;
import org.slf4j.MDC;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/18 3:20 下午
 */
@Configuration
public class MdcConfig implements EnvironmentAware {

    private Environment environment;

    public static final String SPRING_APPLICATION_NAME = "spring.application.name";

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void init() {
        MDC.put("hostName", NetUtil.getLocalHostName());
        MDC.put("ip", NetUtil.getLocalhostStr());
        MDC.put("appName", environment.getProperty(SPRING_APPLICATION_NAME));
    }
}
