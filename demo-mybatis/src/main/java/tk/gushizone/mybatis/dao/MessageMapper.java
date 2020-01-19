package tk.gushizone.mybatis.dao;

import org.apache.ibatis.annotations.Param;
import tk.gushizone.mybatis.enumeration.CommandEnum;
import tk.gushizone.mybatis.pojo.Message;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2019-12-16 00:13
 */
public interface MessageMapper {

    int insert(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int deleteByPrimaryKey(Integer id);

    List<Message> selectBySearch(String search);

    List<Message> selectByIds(List<Integer> ids);

    // TODO 为什么不使用 @Param 也可以？
    // List<Message> selectByFilter(CommandEnum commandEnum, String search);

    List<Message> selectByFilter(@Param("commandEnum") CommandEnum commandEnum, @Param("search") String search);
}