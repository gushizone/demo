package tk.gushizone.flowable.event;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author gushizone
 * @date 2022/12/12 17:42
 */
@Slf4j
public class SendRejectionMail implements JavaDelegate {

    /**
     * flowable 触发器
     */
    @Override
    public void execute(DelegateExecution execution) {
        log.info(">>>> 模拟: 发拒绝邮件");

        

    }
}
