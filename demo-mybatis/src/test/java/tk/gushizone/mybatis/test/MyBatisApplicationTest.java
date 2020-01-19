package tk.gushizone.mybatis.test;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.mybatis.MyBatisApplication;
import tk.gushizone.mybatis.dao.CommandMapper;
import tk.gushizone.mybatis.dao.MessageMapper;
import tk.gushizone.mybatis.enumeration.CommandEnum;
import tk.gushizone.mybatis.pojo.Command;
import tk.gushizone.mybatis.pojo.Message;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2019-12-15 23:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisApplication.class)
public class MyBatisApplicationTest {

    @Resource
    private MessageMapper messageMapper;
    @Resource
    private CommandMapper commandMapper;

    @Test
    public void testCreate() {
        Message newMessage = Message.builder()
                .command(CommandEnum.JOKE)
                .description("段子描述新增")
                .content("段子内容新增")
                .isDeleted(false)
                .createTime(LocalDateTime.now())
                .updateTime(System.currentTimeMillis() / 1000)
                .build();

        int row = messageMapper.insert(newMessage);
        log.warn("操作记录数：{}, 自增主键：{}。", row, newMessage.getId());
    }

    @Test
    public void testRetrieve() {
        Message message = messageMapper.selectByPrimaryKey(1);
        log.warn(message.toString());

        List<Message> list1 = messageMapper.selectBySearch("日");
        log.warn("模糊查询结果：{}", list1);

        List<Message> list2 = messageMapper.selectByIds(Lists.newArrayList(1, 2, 5));
        log.warn("ids查询结果：{}", list2);

        List<Message> list3 = messageMapper.selectByFilter(CommandEnum.JOKE, "精彩");
        log.warn("多条件查询结果：{}", list3);
    }

    @Test
    public void testUpdate() {

        Message newMessage = Message.builder()
                .id(1)
                .command(CommandEnum.ENTERTAINMENT)
                .description("精彩内容修改")
                .content("精彩内容修改")
                .isDeleted(true)
                .createTime(LocalDateTime.now())
                .updateTime(System.currentTimeMillis() / 1000)
                .build();

        int row = messageMapper.updateByPrimaryKeySelective(newMessage);
        log.warn("操作记录数：{}。", row);
    }

    @Test
    public void testDelete() {
        int row = messageMapper.deleteByPrimaryKey(0);
        log.warn("操作记录数：{}。", row);
    }

    /**
     * 一对多
     */
    @Test
    public void testCollection() {

        List<Command> commands = commandMapper.selectCommandWithContent();
        log.warn(commands.toString());

    }


}
