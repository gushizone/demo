package tk.gushizone.flowable;

import cn.hutool.json.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * 监听器分配
 * 使用 任务监听器, 在任务创建是指定分配人
 *
 * @author gushizone
 * @date 2023/1/5 14:06
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlowableTestV2 {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    public static final String PROCESS_DEFINITION_KEY = "holiday-v2";

    /**
     * 获取任务
     */
    @AfterEach
    public void tasks() {

        List<String> assignees = Lists.newArrayList("foo", "bar");

        for (String assignee : assignees) {

            System.out.println("=== " + assignee + " tasks ===");
            List<Task> tasks = taskService.createTaskQuery()
                    .taskAssignee(assignee)                      // 任务处理人
                    .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                    .list();

            tasks.stream().map(e -> new JSONObject(true)
                    .set("id", e.getId())
                    .set("name", e.getName())
                    .set("processDefinitionId", e.getProcessDefinitionId())
                    .set("assignee", e.getAssignee())
                    .set("description", e.getDescription())
            ).forEach(System.out::println);
            System.out.println("=========");
        }
    }

    /**
     * 启动流程
     */
    @Test
    @Order(1)
    public void runProcesses() {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                new JSONObject()
        );

        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());


        ProcessDefinition processDefinition1 = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();
        System.out.println(">>>> start");
    }

    /**
     * 完成任务
     */
    @Test
    @Order(2)
    public void completeTask0() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("foo")                      // 任务处理人
                .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                .list();

        Task task = tasks.get(0);

        // 流程变量传递
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("step1", true);
        taskService.complete(task.getId(), processVariables);
        System.out.println("foo complete");
    }

    /**
     * 完成任务
     */
    @Test
    @Order(3)
    public void completeTask1() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("bar")                      // 任务处理人
                .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                .list();

        Task task = tasks.get(0);

        // 流程变量传递
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("step2", true);
        taskService.complete(task.getId(), processVariables);
        System.out.println("bar complete");
    }


    /**
     * 删除流程
     */
    @Test
    @Order(99)
    public void deleteProcesses() {

        System.out.println("<<<< end");

        // 普通删除, 已启动的流程无法被删除
//        repositoryService.deleteDeployment("23ef2af2-79ed-11ed-bc29-02edd1007814");
        // 级联删除, 已启动的流程可以被删除
//        repositoryService.deleteDeployment("23ef2af2-79ed-11ed-bc29-02edd1007814", true);


        // 清理数据
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition processDefinition : list) {
            try {
                repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
            }catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
    }
}
