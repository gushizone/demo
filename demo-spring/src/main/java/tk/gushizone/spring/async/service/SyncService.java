package tk.gushizone.spring.async.service;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-19 22:01
 */
public interface SyncService {


    void test0();

    List<Integer> test();

    List<Integer> syncMethod();

}
