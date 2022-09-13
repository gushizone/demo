package tk.gushizone.java.jdk.io.mode.socket;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author gushizone
 * @date 2022/9/6 11:53
 */
public class AioSocketTest {

    public static final int PORT = 4343;

    /**
     * Java 1.7 提供了 AIO 实现
     */
    @SneakyThrows
    public static void main(String[] args) {
        // AIO线程复用版
        Thread sThread = new Thread(new Runnable() {
            @Override
            public void run() {
                AsynchronousChannelGroup group;
                try {
                    group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(4));
                    AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
                    server.accept(null, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {
                        @Override
                        public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
                            server.accept(null, this); // 接收下一个请求
                            try {
                                Future<Integer> f = result.write(Charset.defaultCharset().encode("你好，世界"));
                                f.get();
                                System.out.println("服务端发送时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                result.close();
                            } catch (InterruptedException | ExecutionException | IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
                        }
                    });
                    group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sThread.start();

        Thread.sleep(1_000);

        // Socket 客户端
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        Future<Void> future = client.connect(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
        future.get();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void attachment) {
                System.out.println("客户端打印：" + new String(buffer.array()));
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                exc.printStackTrace();
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(10 * 1000);
    }

}
