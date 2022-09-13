package tk.gushizone.java.jdk.io.mode.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author gushizone
 * @date 2022/9/6 11:37
 */
public class BioSocketTest {

    public static final int PORT = 4343;

    /**
     * 在 Java 中，线程的实现是比较重量级的，所以线程的启动或者销毁是很消耗服务器的资源的，
     * 即使使用线程池来实现，使用上述传统的 Socket 方式，当连接数极具上升也会带来性能瓶颈，
     * 原因是线程的上线文切换开销会在高并发的时候体现的很明显，并且以上操作方式还是同步阻塞式的编程，
     * 性能问题在高并发的时候就会体现的尤为明显。
     */
    public static void main(String[] args) {
        // Socket 服务器端（简单的发送信息）
        Thread sThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                while (true) {
                    // 等待连接
                    Socket socket = serverSocket.accept();
                    Thread sHandlerThread = new Thread(() -> {
                        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
                            printWriter.println("hello world！");
                            printWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    sHandlerThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sThread.start();


        // Socket 客户端（接收信息并打印）
        try (Socket cSocket = new Socket(InetAddress.getLocalHost(), PORT)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            bufferedReader.lines().forEach(s -> System.out.println("客户端：" + s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
