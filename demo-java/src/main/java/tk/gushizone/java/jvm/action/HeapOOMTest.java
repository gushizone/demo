package tk.gushizone.java.jvm.action;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 模拟: 堆内存溢出
 *
 * # +HeapDumpOnOutOfMemoryError: OOM 时自动堆 dump (默认在项目根目录)
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOMTest {

    private List<String> oomList = new ArrayList<>();

    public static void main(String[] args) {
        HeapOOMTest oomTest = new HeapOOMTest();
        while (true) {
            oomTest.oomList.add(UUID.randomUUID().toString());
        }
    }
}

