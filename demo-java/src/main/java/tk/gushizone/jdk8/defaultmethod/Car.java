package tk.gushizone.jdk8.defaultmethod;

/**
 * 
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:48
 */
public class Car implements Vehicle, FourWheeler {

    @Override
    public void print(){
        // 使用 super 来调用指定接口的默认方法：
        Vehicle.super.print();
        FourWheeler.super.print();

        Vehicle.blowHorn();
    }

}
