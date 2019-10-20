package tk.gushizone.mongodb.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @createTime 2019-10-20 20:50
 */
@Getter
@Setter
@ToString
public class Comment {

    @Id
    private String _id;
    private String content;
    private Date publishtime;
    private String userid;
    private String nickname;
    private Integer visits;
    private Integer thumbup;
    private Integer share;
    private Integer comment;
    private String state;
    private String parentid;
}
