package tk.gushizone.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author gushizone
 * @date 2022/8/24 10:36
 */
public interface BaseExMapper<T> extends BaseMapper<T> {

    int insertBatchSomeColumn(List<T> entityList);

}
