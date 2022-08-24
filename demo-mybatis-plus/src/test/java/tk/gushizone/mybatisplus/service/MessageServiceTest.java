package tk.gushizone.mybatisplus.service;

import cn.hutool.core.date.StopWatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.gushizone.mybatisplus.domain.Message;
import tk.gushizone.mybatisplus.mapper.MessageMapper;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceTest {

    @Resource
    private MessageService messageService;
    @Resource
    private MessageMapper messageMapper;


    private List<Message> getMessages() {
//        int batchSize = 1;
        int batchSize = 10_000;
//        int batchSize = 100_000;
//        int batchSize = 1_000_000;
        List<Message> results = Lists.newArrayListWithExpectedSize(batchSize);
        for (int i = 0; i < batchSize; i++) {

            results.add(Message.builder()
                    .command(i)
                    .description("desc" + i)
                    .content("content" + i)
                    .isDeleted(0)
                    .build());
        }
        return results;
    }

    /**
     * <pre>
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 8882544526  100%  100000
     *
     * rewriteBatchedStatements=true
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 2557058949  100%  100000
     * </pre>
     */
    @Test
    public void batchSave() {
        List<Message> results = getMessages();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start(String.valueOf(results.size()));
        messageService.saveBatch(results);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    /**
     * <pre>
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 5597688238  100%  100000
     * </pre>
     */
    @Test
    public void batchSaveV2() {

        List<Message> results = getMessages();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start(String.valueOf(results.size()));

        List<List<Message>> partition = Lists.partition(results, 1000);
        for (List<Message> list : partition) {
            messageMapper.insertBatchSomeColumn(list);
        }

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }


    @Test
    @Transactional
    public void batchSaveV3() {

//        messageMapper.lambdaQuery().list();

        List<Message> results = getMessages();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start(String.valueOf(results.size()));

        messageMapper.saveBatch(results);

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

}