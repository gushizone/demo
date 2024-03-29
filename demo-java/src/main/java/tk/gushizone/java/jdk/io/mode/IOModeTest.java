package tk.gushizone.java.jdk.io.mode;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * BIO 就是传统的 java.io[1] 包，它是基于流模型实现的，交互的方式是同步、阻塞方式，也就是说在读入输入流或者输出流时，在读写动作完成之前，线程会一直阻塞在那里，它们之间的调用时可靠的线性顺序。它的优点就是代码比较简单、直观；缺点就是 IO 的效率和扩展性很低，容易成为应用性能瓶颈。
 * NIO 是 Java 1.4 引入的 java.nio 包，提供了 Channel、Selector、Buffer 等新的抽象，可以构建多路复用的、同步非阻塞 IO 程序，同时提供了更接近操作系统底层高性能的数据操作方式。
 * AIO 是 Java 1.7 之后引入的包，是 NIO 的升级版本，提供了异步非堵塞的 IO 操作方式，所以人们叫它 AIO（Asynchronous IO），异步 IO 是基于事件和回调机制实现的，也就是应用操作之后会直接返回，不会堵塞在那里，当后台处理完成，操作系统会通知相应的线程进行后续的操作。
 *
 * @author gushizone
 * @date 2022/9/6 11:14
 */
public class IOModeTest {


    @Test
    @SneakyThrows
    public void bioRead() {

        // 添加文件
//        FileWriter fileWriter = new FileWriter(filePath, true);
//        fileWriter.write(Content);
//        fileWriter.close();

        // 读取文件
        FileReader fileReader = new FileReader("README.md");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder bf = new StringBuilder();
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            bf.append(str).append("\n");
        }
        bufferedReader.close();
        fileReader.close();
        System.out.println(bf);
    }

    @Test
    @SneakyThrows
    public void nioRead() {

        // 创建多（单）层目录（如果不存在创建，存在不会报错）
//        new File("D://a//b").mkdirs();

        // 写入文件（追加方式：StandardOpenOption.APPEND）
//        Files.write(Paths.get(filePath), Content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        // 读取文件
        byte[] data = Files.readAllBytes(Paths.get("README.md"));
        System.out.println(new String(data, StandardCharsets.UTF_8));
    }


}
