package tk.gushizone.mybatis.dao;

import tk.gushizone.mybatis.pojo.Command;

import java.util.List;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-16 00:13
 */
public interface CommandMapper {

    List<Command> selectCommandWithContent();

}