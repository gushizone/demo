package tk.gushizone.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tk.gushizone.mongodb.pojo.Comment;

/**
 * @author gushizone@gmail.com
 * @date 2019-10-20 20:57
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {


}
