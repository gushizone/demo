package tk.gushizone.mybatis.common.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gushizone@gmail.com
 * @date 2020-06-14 21:21
 */
@Data
@ConfigurationProperties(prefix = MybatisCustomProperties.CUSTOM_MYBATIS)
public class MybatisCustomProperties {

    public static final String CUSTOM_MYBATIS = "custom-mybatis";

    private int warnThreshold = 1000;
}
