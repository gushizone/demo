package tk.gushizone.mybatis.dao;

import tk.gushizone.mybatis.pojo.Message;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-16 00:13
 */
public interface MessageMapper {

    int insert(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int deleteByPrimaryKey(Integer id);

}