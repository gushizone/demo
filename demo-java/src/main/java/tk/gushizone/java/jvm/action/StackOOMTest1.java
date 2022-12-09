package tk.gushizone.java.jvm.action;


/**
 * 模拟: 栈内存溢出
 *
 * 栈越小,可容纳栈帧越少
 *
 * stack length (栈深)
 * 默认配置 ：18442
 * -Xss160k ：773
 */
public class StackOOMTest1 {

    private int stackLength = 1;

    private void stackLeak() {
        stackLength++;
        this.stackLeak();
    }

    public static void main(String[] args) {
        StackOOMTest1 oom = new StackOOMTest1();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}