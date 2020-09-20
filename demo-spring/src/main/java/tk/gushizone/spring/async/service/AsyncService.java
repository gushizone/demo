package tk.gushizone.spring.async.service;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-19 22:01
 */
public interface AsyncService {

    List<Integer> asyncMethod();

    Future<List<Integer>> asyncMethodPlus();

}
