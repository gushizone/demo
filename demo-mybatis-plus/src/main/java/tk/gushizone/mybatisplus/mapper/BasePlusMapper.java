package tk.gushizone.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.logging.LogFactory;

import java.util.Collection;

/**
 * @author gushizone
 * @date 2022/8/24 10:36
 */
public interface BasePlusMapper<T> extends BaseExMapper<T> {

    int DEFAULT_BATCH_SIZE = 1000;

    default LambdaQueryChainWrapper<T> lambdaQuery() {
        return ChainWrappers.lambdaQueryChain(this);
    }

    default boolean saveBatch(Collection<T> list) {
        return saveBatch(list, DEFAULT_BATCH_SIZE);
    }

    default boolean saveBatch(Collection<T> list, int batchSize) {
        // todo 不可行, 这个 this 为代理对象
        String sqlStatement =SqlHelper.getSqlStatement(this.getClass(), SqlMethod.INSERT_ONE);
        return SqlHelper.executeBatch(this.currentModelClass(), LogFactory.getLog(getClass()), list, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
//        return SqlHelper.executeBatch(this.currentModelClass(), LogFactory.getLog(getClass()), list, batchSize, (sqlSession, entity) -> this.insert(entity));
    }

    default Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BasePlusMapper.class, 0);
    }
}
