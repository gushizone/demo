package tk.gushizone.flowable;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 *
 *
 * @author gushizone
 * @date 2023/1/5 14:06
 */
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlowableTestV4_0 {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;

    public static final String PROCESS_DEFINITION_KEY = "holiday-v4";


    @Test
    @Order(1)
    public void newUser() {

        List<String> users = Lists.newArrayList("u1", "u2");
        users.forEach(e -> {
            User user = identityService.newUser(e);
            user.setDisplayName(e);
            user.setEmail(e + "@mail.com");
            identityService.saveUser(user);
        });

        List<String> managers = Lists.newArrayList("m1", "m2", "m3", "m4");
        managers.forEach(e -> {
            User user = identityService.newUser(e);
            user.setDisplayName(e);
            user.setEmail(e + "@mail.com");
            identityService.saveUser(user);
        });
    }

    @Test
    @Order(2)
    public void newGroup() {

        List<String> groups = Lists.newArrayList("g1", "g2");
        groups.forEach(e -> {
            Group group = identityService.newGroup(e);
            group.setName(e);
            group.setType("type_" + e);
            identityService.saveGroup(group);
        });
    }

    @Test
    @Order(3)
    public void userGroup() {

        identityService.createMembership("m1", "g1");
        identityService.createMembership("m3", "g1");
        identityService.createMembership("m2", "g2");
        identityService.createMembership("m4", "g2");

        List<User> g1 = identityService.createUserQuery()
                .memberOfGroup("g1")
                .list();
        System.out.println("g1 = " + JSONUtil.toJsonStr(g1));
        List<User> g2 = identityService.createUserQuery()
                .memberOfGroup("g2")
                .list();
        System.out.println("g2 = " + JSONUtil.toJsonStr(g2));
    }


//    @Test
//    @Order(99)
    public void deleteAll() {

        List<Group> groups = identityService.createGroupQuery().list();
        groups.forEach(e -> identityService.deleteGroup(e.getId()));

        List<User> users = identityService.createUserQuery().list();
        users.forEach(e -> identityService.deleteUser(e.getId()));

    }
}
