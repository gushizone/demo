package tk.gushizone.jdk8.methodreference;

import java.util.function.Supplier;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-11-10 23:48
 */
class Car {

    /** 创造 **/
    public static Car create(Supplier<Car> supplier) {
        return supplier.get();
    }

    /** 被碰撞 **/
    public static void collide(Car car) {
        System.out.println("Collided " + car.toString());
    }

    /** 跟随 **/
    public void follow( Car another) {
        System.out.println(this.toString() + " Following the " + another.toString());
    }

    /** 被修理 **/
    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}


