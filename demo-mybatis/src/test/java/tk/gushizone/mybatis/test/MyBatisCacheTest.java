package tk.gushizone.mybatis.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.gushizone.mybatis.MyBatisApplication;
import tk.gushizone.mybatis.dao.MessageMapper;
import tk.gushizone.mybatis.pojo.Message;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gushizone@gmail.com
 * @date 2021/2/7 5:38 下午
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisApplication.class)
public class MyBatisCacheTest {

    /**
     * 可以获取 DefaultSqlSession，是 mybatis 的原生对象
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 获取的是 SqlSessionTemplate ，是 spring 提供的代理对象
     */
    @Autowired
    private SqlSession sqlSession;
    @Resource
    private MessageMapper messageMapper;


    /**
     * 缓存默认配置
     */
    @Test
    public void testDefaultConfig() {

        // 一级缓存作用范围: SESSION
        System.out.println(sqlSession.getConfiguration().getLocalCacheScope());
        // 二级缓存是否启用: true
        System.out.println(sqlSession.getConfiguration().isCacheEnabled());
    }

    /**
     * 测试一级缓存
     * <p>
     * 注意这里的 sqlSession 实际是 SqlSessionTemplate, 需要有事务才能走一级缓存
     */
    @Test
    @Transactional
    public void testFirstBySqlSession() {

        MessageMapper mapper1 = sqlSession.getMapper(MessageMapper.class);
        System.out.println("第一次查询....");
        List<Message> messages1 = mapper1.selectByIds(Lists.newArrayList(1));
        System.out.println(messages1);

//        sqlSession.clearCache();

        // 第二次查询
        System.out.println("第二次查询....");
        MessageMapper mapper2 = sqlSession.getMapper(MessageMapper.class);
        List<Message> messages2 = mapper2.selectByIds(Lists.newArrayList(1));
        System.out.println(messages2);
    }

    @Test
    public void testFirst() {

        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);

        // 关闭一级缓存
//        sqlSession.getConfiguration().setLocalCacheScope(LocalCacheScope.STATEMENT);

        System.out.println("第一次查询....");
        List<Message> messages1 = mapper.selectByIds(Lists.newArrayList(1));
        System.out.println(messages1);

        // TPL (commit, rollback) 会导致缓存失效
//        sqlSession.commit();

        // 第二次查询
        System.out.println("第二次查询....");
        List<Message> messages2 = mapper.selectByIds(Lists.newArrayList(1));
        System.out.println(messages2);

    }

    /**
     * 测试一级缓存
     */
    @Test
    @Transactional
    public void testFirstByMapper() {

        System.out.println("第一次查询....");
        List<Message> messages1 = messageMapper.selectByIds(Lists.newArrayList(1));
        System.out.println(messages1);

        // DML (insert, update, delete) 会导致缓存失效
//        messageMapper.updateByPrimaryKeySelective(Message.builder()
//                .id(-1)
//                .content("测试")
//                .build());

        // 第二次查询
        System.out.println("第二次查询....");
        List<Message> messages2 = messageMapper.selectByIds(Lists.newArrayList(1));
        System.out.println(messages2);
    }

    /**
     * 测试二级缓存
     * <p>
     * 1. mapper.xml 中添加 <cache/> 标签，如果是关联表还要使用 <cache-ref/> 指定被关联的 namespace
     * 2. resultMap 对象需要实现 Serializable 接口
     * 3. 前一个事务已提交
     * <pre>
     * # 大于0时说明走了二级缓存
     * Cache Hit Ratio [tk.gushizone.mybatis.dao.MessageMapper]: 0.5
     * Cache Hit Ratio [tk.gushizone.mybatis.dao.MessageMapper]: 0.66666
     * </pre>
     */
    @Test
    public void testFirstAndSecond() {

        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        MessageMapper mapper1 = sqlSession1.getMapper(MessageMapper.class);

        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        MessageMapper mapper2 = sqlSession2.getMapper(MessageMapper.class);

        System.out.println("第一次查询....");
        List<Message> messages1 = mapper1.selectByIds(Lists.newArrayList(1));
        System.out.println(messages1);

        // 第二次查询
        System.out.println("第二次查询....");
        List<Message> messages2 = mapper1.selectByIds(Lists.newArrayList(1));
        System.out.println(messages2);

        // 一定要前一个事务提交了才能走二级缓存
        sqlSession1.commit();

        // 第三次查询
        System.out.println("第三次查询....");
        List<Message> messages3 = mapper2.selectByIds(Lists.newArrayList(1));
        System.out.println(messages3);
    }

    /**
     * 测试二级缓存，需要关闭事务才能走二级缓存
     */
    @Test
//    @Transactional
    public void testSessionByMapper() {

        System.out.println("第一次查询....");
        List<Message> messages1 = messageMapper.selectByIds(Lists.newArrayList(1));
        System.out.println(messages1);

        // 第二次查询
        System.out.println("第二次查询....");
        List<Message> messages2 = messageMapper.selectByIds(Lists.newArrayList(1));
        System.out.println(messages2);
    }


}
