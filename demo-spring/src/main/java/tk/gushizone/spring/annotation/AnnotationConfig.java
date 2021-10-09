package tk.gushizone.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tk.gushizone.spring.annotation.bean.AxxBean;
import tk.gushizone.spring.annotation.bean.BxxBean;
import tk.gushizone.spring.annotation.bean.IxxBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/3 12:37 上午
 * @see Configuration 声明配置类，是对 {@link org.springframework.stereotype.Component} 的增强。
 * @see ComponentScan 扫描组件并注册为bean，只能作用在{@link Configuration}类上
 * @see Import 注入配置类，作用在{@link Configuration}类上，相当于 {@code <include/>}
 */
@Configuration
@ComponentScan
@Import(InitConfig.class)
public class AnnotationConfig {

    /**
     * @see Autowired 按类型注入
     * @see Qualifier 修饰或筛选标识，默认是bean的名称，一般配合@Autowired使用，可被@Resource替代
     */
    @Autowired
//    @Qualifier("axxBean")
    public AxxBean a;

    /**
     * @see Resource 按名称注入
     */
    @Resource
    private AxxBean axxBean;

    @Autowired
    private List<IxxBean> ixxBeans;

    @Autowired
    private IxxBean ixxBean;

    /**
     * @see Value ${}，取变量
     */
    @Value("${demo-spring.value}")
    private String appName;

    /**
     * @see Value #{}，支持SpEL，如对象方法等
     * https://docs.spring.io/spring-framework/docs/5.2.16.RELEASE/spring-framework-reference/core.html#expressions
     */
    @Value("#{iaaBean.clazz()}")
    private String clazzName;


    /**
     * @see Bean 标识bean，默认名称为方法名，方法参数会自动按类型注入
     */
    @Bean
    public BxxBean b(AxxBean a) {
        return new BxxBean();
    }


    /**
     * @see PostConstruct 依赖注入后回调
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct...");

        System.out.println("ixxBeans: " + ixxBeans);
        System.out.println("ixxBean: " + ixxBean);

        System.out.println(appName);
        System.out.println(clazzName);
    }

    /**
     * @see PreDestroy 容器销毁前回调
     */
    @PreDestroy
    public void preDestroy() {

        System.out.println("preDestroy...");
    }


}
