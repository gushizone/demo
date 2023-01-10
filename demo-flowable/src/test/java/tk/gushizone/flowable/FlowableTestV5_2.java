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
 * 并行网关(多个分支并行, 都走)
 *
 * 注:
 * - 并行网关会忽略条件
 * - 可以分支(fork) 和 汇聚(join)
 *
 * @author gushizone
 * @date 2023/1/5 14:06
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlowableTestV5_2 {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    public static final String PROCESS_DEFINITION_KEY = "holiday-v5_2";

    /**
     * 获取任务
     */
    @AfterEach
    public void tasks() {

        List<String> assignees = Lists.newArrayList( "u1", "u2", "u3", "u4");

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

        // 流程变量
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

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
    public void completeTaskU1() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("u1")                 // 任务处理人
                .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                .list();

        Task task = tasks.get(0);

        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("num", 2);

        // 完成任务
        taskService.complete(task.getId(), processVariables);
        System.out.println("u1 complete");
    }

    /**
     * 完成任务
     */
    @Test
    @Order(3)
    public void completeTaskU2() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("u2")                 // 任务处理人
                .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                .list();

        Task task = tasks.get(0);

        Map<String, Object> processVariables = task.getProcessVariables();

        // 完成任务
        taskService.complete(task.getId(), processVariables);
        System.out.println("u2 complete");
    }

    /**
     * 完成任务
     */
    @Test
    @Order(4)
    public void completeTaskU3() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("u3")                 // 任务处理人
                .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                .list();

        Task task = tasks.get(0);

        Map<String, Object> processVariables = task.getProcessVariables();

        // 完成任务
        taskService.complete(task.getId(), processVariables);
        System.out.println("u3 complete");
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
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
    }
}
