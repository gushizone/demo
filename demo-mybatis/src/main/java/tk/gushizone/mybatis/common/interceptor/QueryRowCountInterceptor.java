package tk.gushizone.mybatis.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * 查询行数统计 - 当查询结果集大于设置线即打印日志
 *
 * @author gushizone@gmail.com
 * @date 2020-06-14 21:25
 * @see Interceptor
 * @see Intercepts 拦截
 * @see Signature 拦截位置 type:作用类 method:作用方法 args:作用的方法参数
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class QueryRowCountInterceptor implements Interceptor {

    private int warnThreshold;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];

        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;

        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        List resultList;
        //rowBounds用参数值，不使用分页插件处理时，仍然支持默认的内存分页
        resultList = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        int cnt = resultList.size();
        if (cnt > warnThreshold) {
            String sqlTemplate = boundSql.getSql();
            //merge in one line
            sqlTemplate = sqlTemplate.replaceAll("\\s+", " ");
            //merge for in (?...)
            sqlTemplate = sqlTemplate.replaceAll("\\([\\s*?\\s*,]*\\s*?\\)", "(?)");
            //merge for insert values(?...)...
            sqlTemplate = sqlTemplate.replaceAll("[\\(?\\)\\s*,]*\\s*\\(?\\)", "(?)");
            //merge for case when end
            sqlTemplate = sqlTemplate.replaceAll("(?i)CASE WHEN\\s+.*?\\s+END", "?");
            log.warn("big sql found(" + cnt + ") : " + sqlTemplate.trim());
        }
        return resultList;
    }

    /**
     * 拦截点判断方法
     *
     * @param target 对象都会经过这个方法
     * @return 如果命中Signature，就会返回代理对象；否则返回原对象(target)
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 可以拿到配置文件的属性值
     *
     * @param properties 配置文件的属性值
     */
    @Override
    public void setProperties(Properties properties) {

        String warnThresholdConfig = properties.getProperty("warnThreshold");
        warnThreshold = Integer.parseInt(warnThresholdConfig);
    }
}
