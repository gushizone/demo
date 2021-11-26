package tk.gushizone.zookeeper.lock;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class InterprocessLock {

    @Autowired
    private CuratorFramework zkClient;

    @SneakyThrows
    public void execute(String lockKey, Runnable runnable) {
        InterProcessMutex lock = null;
        boolean gotLock = false;
        try {
            lock = new InterProcessMutex(zkClient, "/" + lockKey);
            gotLock = lock.acquire(0, TimeUnit.SECONDS);
            if (!gotLock) {
                throw new RuntimeException("服务忙，请稍后重试");
            }
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (gotLock) {
                    lock.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}