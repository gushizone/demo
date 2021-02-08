package tk.gushizone.spring.async.service.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import tk.gushizone.spring.async.service.AsyncService;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-19 22:02
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {


    @Async
    @Override
    @SneakyThrows
    public void asyncMethodNoReturn() {

        log.warn(Thread.currentThread().getName());

        Thread.sleep(1_000);

//        int i = 1 / 0;
    }

    @Async
    @Override
    @SneakyThrows
    public Future<List<Integer>> asyncMethod() {

        log.warn(Thread.currentThread().getName());

        List<Integer> list = Lists.newArrayList(123);

        Thread.sleep(1_000);
        return new AsyncResult<>(list);
    }
}
