package tk.gushizone.mybatis.common.interceptor;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tk.gushizone.mybatis.common.interceptor.MybatisCustomProperties;
import tk.gushizone.mybatis.common.interceptor.QueryRowCountInterceptor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * @author gushizone@gmail.com
 * @date 2020-06-14 21:24
 */
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(MybatisCustomProperties.class)
@AutoConfigureBefore(PageHelperAutoConfiguration.class)
public class QueryRowCountAutoConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

//    @Autowired
//    private MybatisCustomProperties mybatisCustomProperties;


    @Bean
    @ConfigurationProperties(prefix = MybatisCustomProperties.CUSTOM_MYBATIS)
    public Properties queryRowCountProperties() {
        return new Properties();
    }

    @PostConstruct
    public void addCustomInterceptor() {

        QueryRowCountInterceptor interceptor = new QueryRowCountInterceptor();
        interceptor.setProperties(this.queryRowCountProperties());
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
