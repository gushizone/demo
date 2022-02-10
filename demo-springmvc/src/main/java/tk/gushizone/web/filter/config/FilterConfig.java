package tk.gushizone.web.filter.config;

import com.google.common.collect.Lists;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * FilterRegistrationBean 默认是最后的，所以必需都设置 order，否则 "lastFilter" 无法生效。
 *
 * 1. controller 异常不会影响 filter
 * 2. filter 异常会影响 其他filter, 如果希望异常忽略，需要自行 catch
 * 3. chain.doFilter(request, response); 是放行，进入 filter 或 controller
 *
 * @author gushizone@gmail.com
 * @date 2022/2/10 10:29 上午
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<FirstFilter> firstFilterReg() {

        FilterRegistrationBean<FirstFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setName("firstFilter");
        filterReg.setFilter(new FirstFilter());
        filterReg.setUrlPatterns(Lists.newArrayList("/filter/*"));
        filterReg.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterReg;
    }

    @Bean
    public FilterRegistrationBean<LastFilter> lastFilterReg() {

        FilterRegistrationBean<LastFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setName("lastFilter");
        filterReg.setFilter(new LastFilter());
        filterReg.setUrlPatterns(Lists.newArrayList("/filter/*"));
        filterReg.setOrder(Ordered.LOWEST_PRECEDENCE);
        return filterReg;
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> headerFilterReg() {

        FilterRegistrationBean<HeaderFilter> filterReg = new FilterRegistrationBean<>();
        filterReg.setName("headerFilter");
        filterReg.setFilter(new HeaderFilter());
        filterReg.setUrlPatterns(Lists.newArrayList("/filter/*"));
        filterReg.setOrder(0);
        return filterReg;
    }
}
