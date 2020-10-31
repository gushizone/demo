package tk.gushizone.java.jdk.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author gushizone@gmail.com
 * @date 2020-10-06 23:32
 */
@Slf4j
public class IOTest {


    @Test
    public void test1() throws IOException {

        BufferedInputStream bi = new BufferedInputStream(new FileInputStream("README.md"));

        byte bytes[] = new byte[1024];
        int len = 0;

        while ((len = bi.read(bytes)) != -1) {

            System.out.println(new String(bytes));
        }

//        while ((len = bi.read(bytes)) != -1) {
//
//            System.out.println(len);
//        }

        bi.close();


    }

    @Test
    public void test2() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("README.md"));

        if (!br.ready()) {

            log.error("展示无法读取");
        }

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }

    @Test
    public void test3() {

    }

    @Test
    public void test4() {

    }


}
