package tk.gushizone.java.concurrent.thread;

import lombok.SneakyThrows;

/**
 * @author gushizone@gmail.com
 * @date 2020-10-18 21:41
 */
public class ThreadPrintTest {


    public static void main(String[] args) {

        Foo foo = new Foo();

        new Thread(() -> {

            for (int i = 1; i <= 10; i = i+2) {
                foo.print(i);
            }

        }).start();


        new Thread(() -> {

            for (int i = 2; i <= 10; i = i+2) {
                foo.print(i);
            }

        }).start();

    }



}

class Foo {

    @SneakyThrows
    synchronized void print(int i) {

        this.notifyAll();
        System.out.println(Thread.currentThread().getName() + ":" + i);
        this.wait();
    }

}
