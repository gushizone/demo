package tk.gushizone.mybatis.service;

import tk.gushizone.mybatis.pojo.Message;

import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2020-06-14 19:30
 */
public interface MessageService {

    List<Message> queryAll();

}
