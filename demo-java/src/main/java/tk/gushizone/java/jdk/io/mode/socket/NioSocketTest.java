package tk.gushizone.java.jdk.io.mode.socket;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gushizone
 * @date 2022/9/6 11:46
 */
public class NioSocketTest {

    public static final int PORT = 4343;


    /**
     * NIO 的多路复用就大大提升了服务器端响应高并发的能力。
     * <pre>
     *  首先，通过 Selector.open() 创建一个 Selector，作为类似调度员的角色；
     *  然后，创建一个 ServerSocketChannel，并且向 Selector 注册，通过指定 SelectionKey.OP_ACCEPT，告诉调度员，它关注的是新的连接请求；
     *  Selector 阻塞在 select 操作，当有 Channel 发生接入请求，就会被唤醒；
     *  </pre>
     */
    @SneakyThrows
    public static void main(String[] args) {
        // NIO 多路复用
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 4,
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        threadPool.execute(() -> {
            try (Selector selector = Selector.open();
                 ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    selector.select(); // 阻塞等待就绪的Channel
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        try (SocketChannel channel = ((ServerSocketChannel) key.channel()).accept()) {
                            channel.write(Charset.defaultCharset().encode("你好，世界"));
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(1_000);

        // Socket 客户端（接收信息并打印）
        try (Socket cSocket = new Socket(InetAddress.getLocalHost(), PORT)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            bufferedReader.lines().forEach(s -> System.out.println("NIO 客户端：" + s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
