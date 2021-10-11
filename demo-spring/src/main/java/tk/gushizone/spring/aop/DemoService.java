package tk.gushizone.spring.aop;

import org.springframework.stereotype.Service;

/**
 * @author gushizone@gmail.com
 * @date 2021/10/11 4:07 下午
 */
@Service
public class DemoService {


    @Watcher
    public void go(String msg) {
        System.out.println("go ...");
    }


}
