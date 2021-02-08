package tk.gushizone.spring.async.service.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.gushizone.spring.async.service.SyncService;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-19 22:02
 */
@Slf4j
@Service
public class SyncServiceImpl implements SyncService {

    @Override
    public void syncMethodNoReturn() {

        log.warn(Thread.currentThread().getName());
    }

    @Override
    @SneakyThrows
    public List<Integer> syncMethod() {

        List<Integer> list = Lists.newArrayList(3);

        Thread.sleep(1_000);

        return list;
    }
}
