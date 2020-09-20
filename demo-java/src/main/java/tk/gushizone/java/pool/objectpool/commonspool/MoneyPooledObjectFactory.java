package tk.gushizone.java.pool.objectpool.commonspool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class MoneyPooledObjectFactory implements PooledObjectFactory<Money> {

    public static final Logger LOGGER = LoggerFactory.getLogger(MoneyPooledObjectFactory.class);

    /**
     * 创建一个对象实例,并将其包装为一个 PooledObject
     */
    @Override
    public PooledObject<Money> makeObject() throws Exception {
        DefaultPooledObject<Money> object = new DefaultPooledObject<>(
                new Money("USD", new BigDecimal("1"))
        );
        LOGGER.info("makeObject..state = {}", object.getState());
        return object;
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<Money> p) throws Exception {
        LOGGER.info("destroyObject..state = {}", p.getState());
    }

    /**
     * 校验对象,确保对象池返回的对象是 OK 的
     */
    @Override
    public boolean validateObject(PooledObject<Money> p) {
        LOGGER.info("validateObject..state = {}", p.getState());
        return true;
    }

    /**
     * 重新初始化对象
     */
    @Override
    public void activateObject(PooledObject<Money> p) throws Exception {
        LOGGER.info("activateObject..state = {}", p.getState());
    }

    /**
     * 取消初始化对象
     */
    @Override
    public void passivateObject(PooledObject<Money> p) throws Exception {
        LOGGER.info("passivateObject..state = {}", p.getState());
    }
}
