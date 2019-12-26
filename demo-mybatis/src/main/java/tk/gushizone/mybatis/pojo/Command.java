package tk.gushizone.mybatis.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-10 00:02
 */
@Data
public class Command {

    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    private List<CommandContent> commandContents;
}