package tk.gushizone.mongodb.test;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.mongodb.MongodbApplication;
import tk.gushizone.mongodb.dao.CommentRepository;
import tk.gushizone.mongodb.pojo.Comment;

import java.util.List;
import java.util.Optional;

/**
 * spring-data-mongodb
 *
 * @author gushizone@gmail.com
 * @createTime 2019-10-20 20:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongodbApplication.class)
public class MongodbApplicationTest {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 删除
     */
    @Test
    public void deleteById(){
        commentRepository.deleteById("1");
    }

    /**
     * 新建、修改
     */
    @Test
    public void save(){
        Comment comment = new Comment();
        comment.set_id("1");
        comment.setContent("测试内容");
        comment.setPublishtime(new Date());
        comment.setUserid("123");
        comment.setNickname("abc");
        comment.setVisits(0);
        comment.setThumbup(0);
        comment.setShare(0);
        comment.setComment(0);
        comment.setState("01");
        commentRepository.save(comment);
    }

    /**
     * 查询单个
     */
    @Test
    public void findById(){
        Optional<Comment> comment = commentRepository.findById("1");
        comment.ifPresent(System.out::println);
    }

    /**
     * 查询多个
     */
    @Test
    public void findAll(){
        List<Comment> list = commentRepository.findAll();
        System.out.println(list);
    }

}
