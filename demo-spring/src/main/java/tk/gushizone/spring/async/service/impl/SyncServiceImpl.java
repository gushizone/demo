package tk.gushizone.spring.async.service.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import tk.gushizone.spring.async.service.AsyncService;
import tk.gushizone.spring.async.service.SyncService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-19 22:02
 */
@Service
public class SyncServiceImpl implements SyncService {

    @Resource
    private AsyncService asyncService;

    @Override
    @SneakyThrows
    public List<Integer> test() {

        System.out.println(LocalDateTime.now());

        List<Integer> list = Lists.newArrayList(1);

//        List<?> list1 = asyncService.asyncMethod();
        Future<List<Integer>> future = asyncService.asyncMethodPlus();
        List<Integer> list1 = this.syncMethod();

        List<Integer> list2 = future.get();


        List<Integer> future2 = asyncService.asyncMethod();


        System.out.println(LocalDateTime.now());

        return ListUtils.union(list1, list2);
    }

    @Override
    @SneakyThrows
    public List<Integer> syncMethod() {

        List<Integer> list = Lists.newArrayList(3);

        Thread.sleep(1_000);

        return list;
    }
}
