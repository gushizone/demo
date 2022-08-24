package tk.gushizone.mybatisplus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.mybatisplus.domain.Message;
import tk.gushizone.mybatisplus.service.MessageService;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusApplicationTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void test() {

        List<Message> list = messageService.list();

    }



}