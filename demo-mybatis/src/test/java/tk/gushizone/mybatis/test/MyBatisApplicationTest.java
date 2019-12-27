package tk.gushizone.mybatis.test;

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
