package tk.gushizone.zookeeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.zookeeper.lock.InterprocessLock;

/**
 * @author gushizone@gmail.com
 * @date 2021/11/25 5:39 下午
 */
@Slf4j
@RestController
@RequestMapping("zookeeper")
public class ZookeeperController {

    @Autowired
    private InterprocessLock lock;


    @PostMapping("/lock")
    public String lock() {
        log.info("===> 进入方法");
        lock.execute("lock_demo", () -> {
            log.info("获取锁");

            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("释放锁");
        });
        log.info("===> 结束方法");
        return "end";
    }



}
