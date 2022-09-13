package tk.gushizone.java.jdk.io;

import org.junit.Test;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author gushizone
 * @date 2022/9/13 18:13
 */
public class ZeroCopyTest {


    /**
     * FileChannel的map方法产生的MappedByteBuffer FileChannel提供了map()方法，
     * 该方法可以在一个打开的文件和MappedByteBuffer之间建立一个虚拟内存映射，MappedByteBuffer继承于ByteBuffer；
     * 该缓冲器的内存是一个文件的内存映射区域。
     * map方法底层是通过 mmap 实现的，因此将文件内存从磁盘读取到内核缓冲区后，用户空间和内核空间共享该缓冲区。
     */
    @Test
    public void mmap() {
        try {
            FileChannel readChannel = FileChannel.open(Paths.get("./cscw.txt"), StandardOpenOption.READ);
            FileChannel writeChannel = FileChannel.open(Paths.get("./siting.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            MappedByteBuffer data = readChannel.map(FileChannel.MapMode.READ_ONLY, 0, 1024 * 1024 * 40);
            //数据传输
            writeChannel.write(data);
            readChannel.close();
            writeChannel.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 如果底层系统支持 sendfile,可以使用以下 API (Kafka 底层原理)
     *
     * FileChannel的transferTo、transferFrom 如果操作系统底层支持的话，t
     * ransferTo、transferFrom也会使用相关的零拷贝技术来实现数据的传输。
     */
    @Test
    public void sendfile() {
        try {
            FileChannel readChannel = FileChannel.open(Paths.get("./cscw.txt"), StandardOpenOption.READ);
            FileChannel writeChannel = FileChannel.open(Paths.get("./siting.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            long len = readChannel.size();
            long position = readChannel.position();
            //数据传输
            readChannel.transferTo(position, len, writeChannel);
            //效果和transferTo 一样的
            //writeChannel.transferFrom(readChannel, position, len, );
            readChannel.close();
            writeChannel.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
