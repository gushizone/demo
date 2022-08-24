package tk.gushizone.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import tk.gushizone.mybatisplus.domain.Message;
import tk.gushizone.mybatisplus.service.MessageService;
import tk.gushizone.mybatisplus.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
implements MessageService{

}




