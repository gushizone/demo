package tk.gushizone.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import tk.gushizone.mybatisplus.domain.Command;
import tk.gushizone.mybatisplus.service.CommandService;
import tk.gushizone.mybatisplus.mapper.CommandMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CommandServiceImpl extends ServiceImpl<CommandMapper, Command>
implements CommandService{

}




