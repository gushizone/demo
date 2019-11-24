package tk.gushizone.jdk8.defaultmethod;

/**
 * 车
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:41
 */
public interface Vehicle {

    /**
     * 在接口中使用 default 就可以添加实现方法了
     */
    default void print(){
        System.out.println("Vehicle：默认方法调用！");
    }

    /**
     * jdk8后，可以在接口中添加静态的实现方法（静态默认方法，🤣不能用default修饰）
     */
    static  void blowHorn(){
        System.out.println("Vehicle：静态方法调用！");
    }

}
