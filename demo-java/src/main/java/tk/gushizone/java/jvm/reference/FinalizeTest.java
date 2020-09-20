package tk.gushizone.java.jvm.reference;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-01 23:51
 */
public class FinalizeTest {

    private static FinalizeTest obj;

    @Override
    protected void finalize() throws Throwable {
        System.out.println("FinalizeTest gc ...");
        obj = this;
    }


    /**
     * 开发中永远不要重写finalize()
     *
     * 1. 已被调用过finalize(),再次回收时不会再调用,而是直接回收
     * 2. 若第二次不手动赋null,则 obj 永不被回收
     */
    public static void main(String[] args) throws InterruptedException {

        obj = new FinalizeTest();

        obj = null;
        System.gc();

        Thread.sleep(1000);
        System.out.println(obj);

        obj = null;
        System.gc();

        Thread.sleep(1000);
        System.out.println(obj);
    }
}
