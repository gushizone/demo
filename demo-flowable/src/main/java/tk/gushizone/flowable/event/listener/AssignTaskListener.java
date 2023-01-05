package tk.gushizone.flowable.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gushizone
 * @date 2023/1/5 15:00
 */
@Slf4j
//@Component
public class AssignTaskListener implements TaskListener {

    /**
     * 任务监听器
     */
    @Override
    public void notify(DelegateTask delegateTask) {

        log.info("delegateTask, name: {}, eventName: {}", delegateTask.getName(), delegateTask.getEventName());

        Map<String, Object> variables = delegateTask.getVariables();
        if (!variables.containsKey("step1")) {
            delegateTask.setAssignee("foo");
        } else {
            delegateTask.setAssignee("bar");
        }
    }
}
