package tk.gushizone.java.jvm.reference;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 引用类型
 *
 * 意义
 * 1. 可以通过代码决定对象生命周期
 * 2. 有利于垃圾回收
 *
 * @author gushizone@gmail.com
 * @date 2020-08-09 17:35
 */
public class ReferenceTest {


    /**
     * 强引用
     * <p>
     * 1. 只要有强引用与对象关联,对象永远不会被回收,及时OOM
     * 2. 可以手动切断关联,置null
     */
    @Test
    public void strongReference() {

        Foo foo = new Foo();
//        foo = null;
        System.gc();
    }

    @Test
    public void strongReference1() {

        foo();
    }

    private void foo() {
        Foo foo = new Foo();
    }

    /**
     * 软引用
     * <p>
     * 设置最大堆内存 20M
     * VM options : -Xmx20m
     * <p>
     * 只能内存不足时,对象才会被回收
     */
    @Test
    public void softReference() {

        SoftReference<byte[]> softReference = new SoftReference<>(new byte[10 * 1024 * 1024]);
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
        byte[] bytes = new byte[10 * 1024 * 1024];
        System.out.println(softReference.get());
    }

    /**
     * 弱引用
     * <p>
     * 只要gc,就对象会被回收
     */
    @Test
    public void weakReference() {

        WeakReference<byte[]> weakReference = new WeakReference<>(new byte[10 * 1024 * 1024]);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
    }

    /**
     * 虚引用
     * <p>
     * 设置最大堆内存 20M
     * VM options : -Xmx20m
     * <p>
     * 1. 无法通过虚引用获得对象的真实引用
     * 2. 必须配合 ReferenceQueue 使用,对象被回收前,会把虚引用加入与之关联的 ReferenceQueue 中,可以在对象被回收前做必要操作
     */
    @Test
    public void phantomReference() throws InterruptedException {

        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<byte[]> reference = new PhantomReference<byte[]>(new byte[10 * 1024 * 1024], queue);
        System.out.println(reference);

        // 无法通过虚引用获得对象的真实引用
        System.out.println(reference.get());

        System.out.println(queue.poll());
        System.gc();
        Thread.sleep(1000);
        System.out.println(queue.poll());
        byte[] bytes = new byte[10 * 1024 * 1024];
        System.out.println(queue.poll());
    }

}
