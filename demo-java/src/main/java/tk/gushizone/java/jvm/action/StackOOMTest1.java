package tk.gushizone.java.jvm.action;


/**
 * 栈越小,可容纳栈帧越少
 *
 * <p> stack length (栈深) </p>
 * 默认配置：17343
 * Xss144k：773
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