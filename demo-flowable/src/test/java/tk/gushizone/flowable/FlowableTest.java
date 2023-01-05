package tk.gushizone.flowable;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
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
 * 流程的生命周期
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlowableTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * 默认 processes 目录下的流程文件会自动部署
     */
    @Test
    @Order(0)
    public void oneTaskProcess() {

        System.out.println("Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());

        runtimeService.startProcessInstanceByKey("oneTaskProcess");
        System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
    }

    /**
     * 查询流程
     *
     * - ACT_RE_DEPLOYMENT: 流程
     * - ACT_RE_PROCDEF: 流程记录
     * - ACT_GE_BYTEARRAY: 流程文件
     */
    @Test
    @Order(1)
    public void queryProcesses() {


        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        System.out.println("list.size() = " + list.size());
        ProcessDefinition processDefinition = list.get(0);

        System.out.println("processDefinition: " + JSONUtil.toJsonPrettyStr(processDefinition));
//        {
//            "isInserted": false,
//            "suspensionState": 1,
//            "derivedVersion": 0,
//            "isUpdated": false,
//            "resourceName": "~/demo-flowable/target/classes/processes/holiday-request.bpmn20.xml",
//            "originalPersistentState": {
//                "suspensionState": 1,
//                "category": "http://www.flowable.org/processdef"
//            },
//            "isGraphicalNotationDefined": false,
//            "version": 1,
//            "revision": 1,
//            "isDeleted": false,
//            "deploymentId": "23ef2af2-79ed-11ed-bc29-02edd1007814",
//            "name": "请假流程",
//            "tenantId": "",
//            "id": "holidayRequest:1:240523f5-79ed-11ed-bc29-02edd1007814",
//            "category": "http://www.flowable.org/processdef",
//            "hasStartFormKey": false,
//            "key": "holidayRequest"
//        }
    }

    /**
     * 启动流程 和 流程状态暂停(挂起与激活)
     *
     * - ACT_RU_VARIABLE: 流程变量
     */
    @Test
    @Order(2)
    public void runProcesses() {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest",
                new JSONObject()
                        .set("name", "foo")
                        .set("days", "3"));

        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());


        ProcessDefinition processDefinition1 = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();
        // 是否已暂停
        System.out.println("processDefinition.isSuspended() = " + processDefinition1.isSuspended());
        if (!processDefinition1.isSuspended()) {
            repositoryService.suspendProcessDefinitionById(processDefinition1.getId());
            System.out.println("暂停流程: " + processDefinition1.getName());
        }
        ProcessDefinition processDefinition2 = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();
        if (processDefinition2.isSuspended()) {
            repositoryService.activateProcessDefinitionById(processDefinition2.getId());
            System.out.println("激活流程: " + processDefinition2.getName());
        }

    }

    /**
     * 获取任务
     */
    @Test
    @Order(3)
    public void tasks() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("foo")                      // 任务处理人
                .processDefinitionKey("holidayRequest")   // 流程类型
                .list();

        tasks.stream().map(e -> new JSONObject(true)
                .set("id", e.getId())
                .set("name", e.getName())
                .set("processDefinitionId", e.getProcessDefinitionId())
                .set("assignee", e.getAssignee())
                .set("description", e.getDescription())
        ).forEach(System.out::println);
    }

    /**
     * 完成任务
     */
    @Test
    @Order(4)
    public void completeTask() {

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("foo")                      // 任务处理人
                .processDefinitionKey("holidayRequest")   // 流程类型
                .list();

        Task task = tasks.get(0);

//        taskService.complete(task.getId(),
//                new JSONObject().set("approved", false));

        // 流程变量传递
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("approved", false);

        taskService.complete(task.getId(), processVariables);
    }

    /**
     * 获取流程任务的历史数据
     */
    @Test
    @Order(5)
    public void history() {

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holidayRequest")   // 流程类型
                .list();
        ProcessDefinition processDefinition = list.get(0);

        historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId(processDefinition.getId())
                .finished()  // 已经结束的
                .orderByHistoricActivityInstanceEndTime().asc()
                .list().forEach(e -> System.out.println(new JSONObject(true)
                        .set("activityId", e.getActivityId())
                        .set("activityName", e.getActivityName())
                        .set("assignee", e.getAssignee())
                        .set("durationInMillis", e.getDurationInMillis())
                ));

//        {"activityId":"startEvent","durationInMillis":5}
//        {"activityId":"_flow_startEvent__approveTask","durationInMillis":0}
//        {"activityId":"approveTask","activityName":"同意或者拒绝请假","assignee":"foo","durationInMillis":44418}
//        {"activityId":"_flow_approveTask__decision","durationInMillis":0}
//        {"activityId":"decision","durationInMillis":20}
//        {"activityId":"_flow_decision__sendRejectionMail","durationInMillis":0}
//        {"activityId":"sendRejectionMail","activityName":"Send out rejection email","durationInMillis":2}
//        {"activityId":"_flow_sendRejectionMail__rejectEnd","durationInMillis":0}
//        {"activityId":"rejectEnd","durationInMillis":2}
    }



    /**
     * 删除流程
     */
//    @Test
//    @Order(99)
    public void deleteProcesses() {

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