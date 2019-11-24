package tk.gushizone.java.basic.enumeration;

import org.junit.Test;

/**
 * @author gushizone@gmail.com
 * @date 2019-04-08 16:35
 */
public class EnumTest {

    /**
     * 来自 Enum 方法
     * @see Enum
     *
     * .ordinal()
     * .name()
     */
    @Test
    public void test1(){
        System.out.println(SimpleEnum.SUCCESS.ordinal());
        System.out.println(SimpleEnum.SUCCESS == SimpleEnum.SUCCESS);

        System.out.println(SimpleEnum.WARNING.name());
        System.out.println(SimpleEnum.WARNING.toString());

        System.out.println(SimpleEnum.ERROR.getDeclaringClass());

        System.out.println(Enum.valueOf(SimpleEnum.class, "ERROR") == SimpleEnum.ERROR);
        System.out.println(SimpleEnum.valueOf(SimpleEnum.class, "ERROR") == SimpleEnum.ERROR);

        System.out.println(SimpleEnum.valueOf("ERROR") == SimpleEnum.ERROR);

//        0
//        true
//        WARNING
//        WARNING
//        class tk.gushizone.java.basic.enumeration.SimpleEnum
//        true
//        true
//        true
    }

    /**
     * 枚举遍历：values()
     */
    @Test
    public void test2(){
        for (DictCode item : DictCode.values()) {
            System.out.println(item.name() + ": " + item.getCodeName() + item.getCode());
        }

//        SUCCESS: 成功0
//        WARNING: 警告1
//        ERROR: 错误-1

        Enum e = DictCode.SUCCESS;
        // Enum.values(); // Cannot resolve method 'values()'
        // e.values(); // Cannot resolve method 'values()'
        for (Enum enumConstant : e.getClass().getEnumConstants()) {
            DictCode item = (DictCode) enumConstant;
            System.out.println(item.name() + ": " + item.getCodeName() + item.getCode());
        }

//        SUCCESS: 成功0
//        WARNING: 警告1
//        ERROR: 错误-1
    }

}
