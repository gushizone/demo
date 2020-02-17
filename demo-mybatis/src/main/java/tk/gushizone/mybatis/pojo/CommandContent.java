package tk.gushizone.mybatis.pojo;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-10 00:02
 */
@Data
public class CommandContent {

    private Integer id;

    private String content;

    private Integer commandId;

    private Date createTime;

    private Date updateTime;

    private Command command;
}