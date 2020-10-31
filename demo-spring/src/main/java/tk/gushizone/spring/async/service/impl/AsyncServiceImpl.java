package tk.gushizone.spring.async.service.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
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
@Service
@Scope
public class AsyncServiceImpl implements AsyncService {


    @Async
    @Override
    @SneakyThrows
    public void asyncMethodNoReturn() {
        List<Integer> list = Lists.newArrayList(2);

//        Thread.sleep(3_000);

        int i = 1/0;
    }


    @Async
    @Override
    @SneakyThrows
    public List<Integer> asyncMethod() {
        List<Integer> list = Lists.newArrayList(2);

        Thread.sleep(3_000);

//        int i = 1/0;
        return list;
    }

    @Async
    @Override
    @SneakyThrows
    public Future<List<Integer>> asyncMethodPlus() {
        List<Integer> list = Lists.newArrayList(2);

//        this.asyncMethod();

        Thread.sleep(3_000);

//        int i = 1/0;
        return new AsyncResult<>(list);
    }
}
