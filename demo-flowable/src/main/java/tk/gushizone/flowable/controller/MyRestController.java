package tk.gushizone.flowable.controller;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.gushizone.flowable.dto.TaskRepresentation;
import tk.gushizone.flowable.service.MyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gushizone
 * @date 2022/12/12 14:33
 */
@RestController
public class MyRestController {

    @Autowired
    private MyService myService;

    @PostMapping(value = "/process")
    public void startProcessInstance() {

        myService.startProcess();
    }

    /**
     * http://localhost:8080/tasks?assignee=kermit
     */
    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        return tasks.stream()
                .map(e -> new TaskRepresentation(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }


}