package tk.gushizone.jdk8.defaultmethod;

/**
 * 四轮车
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:41
 */
public interface FourWheeler {

    default void print(){
        System.out.println("FourWheeler : 默认方法调用！");
    }

}
