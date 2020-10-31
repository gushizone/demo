package tk.gushizone.java.concurrent.juc.threadpool.util;

import org.junit.Test;

public class ThreadPoolCoreTest {


  @Test
  private void test1() {

    // cpu核心数 或 逻辑线程数（超线程数）
    int i = Runtime.getRuntime().availableProcessors();
    System.out.println(i);
  }



}
