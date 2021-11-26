package tk.gushizone.zookeeper.config;

import lombok.Data;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/25 5:29 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zookeeper", ignoreInvalidFields = true)
public class ZookeeperConfig {

    private String address;

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework getZkClient() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3, 5000);
        return CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
    }


}
