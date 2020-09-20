package tk.gushizone.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.mybatis.MyBatisApplication;
import tk.gushizone.mybatis.common.interceptor.QueryRowCountAutoConfiguration;
import tk.gushizone.mybatis.dao.MessageMapper;
import tk.gushizone.mybatis.pojo.Message;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2019-12-15 23:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisApplication.class)
public class MyBatisInterceptorTest {

    @Resource
    private MessageMapper messageMapper;

    @Test
    public void simpleCase() {

        PageHelper.startPage(1, 5);

        List<Message> messages = messageMapper.selectByIds(Lists.newArrayList());

        PageInfo pageInfo = new PageInfo<>(messages);

    }



}
