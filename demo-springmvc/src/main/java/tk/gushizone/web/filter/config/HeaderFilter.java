package tk.gushizone.web.filter.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * @author gushizone@gmail.com
 * @date 2022/2/10 10:52 上午
 */
@Slf4j
public class HeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("================ start");

        // 为 response 加 header
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
        responseWrapper.setHeader("Custom-Header", "header-filter");

        chain.doFilter(request, response);


        log.info("================ end");
    }
}
