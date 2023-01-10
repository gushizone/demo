package tk.gushizone.flowable;

import cn.hutool.json.JSONObject;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * 变量
 *
 * @author gushizone
 * @date 2023/1/5 14:06
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlowableTestV3 {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    public static final String PROCESS_DEFINITION_KEY = "holiday-v3";

    /**
     * 启动流程
     */
    @Test
    @Order(1)
    public void runProcesses() {

        // 流程变量
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY,
                new JSONObject()
//                .set("num", 2)
//                .set("num", 3)
                        .set("assignee0", "u0")
                        .set("assignee1", "u1")
                        .set("assignee2", "u2")
                        .set("assignee3", "u3")
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
    @SneakyThrows
    @Test
    @Order(2)
    public void completeTask() {

        List<String> assignees = Lists.newArrayList("u0", "u1", "u2", "u3");
        int i = -1;
        for (String assignee : assignees) {
            i++;
            // 查看任务表
            System.out.println("=== " + assignee + " tasks ===");
            List<Task> tasks = taskService.createTaskQuery()
                    .taskAssignee(assignee)                      // 任务处理人
                    .processDefinitionKey(PROCESS_DEFINITION_KEY)   // 流程类型
                    .includeProcessVariables() // 包括流程变量
                    .list();
            tasks.stream().map(e -> new JSONObject(true)
                    .set("id", e.getId())
                    .set("name", e.getName())
                    .set("processDefinitionId", e.getProcessDefinitionId())
                    .set("assignee", e.getAssignee())
                    .set("description", e.getDescription())
            ).forEach(System.out::println);
            System.out.println("=========");
            if (CollectionUtils.isEmpty(tasks)) {
                log.warn("当前用户没有任务: {}", assignee);
                continue;
            }

            Task task = tasks.get(0);

            // 流程变量传递
            // 在局部变量和全局变量都存在的时候, 取出的是局部变量
            Map<String, Object> processVariables = task.getProcessVariables();

//            Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();

            if (!processVariables.containsKey("step0")) {
                Integer num = 3;
                System.out.println("设置请假天数: " + num);
                processVariables.put("num", num);
            }
            if (processVariables.containsKey("step0")
                    && !processVariables.containsKey("step1")) {
                Integer num = 2;
                // 本地变量, 局部变量
                // 局部变量(不会传递)
                taskService.setVariableLocal(task.getId(), "num", num);
                System.out.println("局部设置请假天数: " + num);

                // 全局变量
//                taskService.setVariable(task.getId(), "num", num);
//                System.out.println("全局设置请假天数: " + num);
            }
            processVariables.put("step" + i, true);
            taskService.complete(task.getId(), processVariables);
            System.out.println(assignee + " complete");

        }
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
