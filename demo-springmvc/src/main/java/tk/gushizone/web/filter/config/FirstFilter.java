package tk.gushizone.web.filter.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author gushizone@gmail.com
 * @date 2022/2/10 10:08 上午
 */
@Slf4j
public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("================ start");

        try {
            int i = 1 / 0;
        } catch (Exception e) {

            log.error("异常: {}", e.getMessage(), e);
        }
        chain.doFilter(request, response);


        log.info("================ end");
    }
}
