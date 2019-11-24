package tk.gushizone.java.basic.annotation.orm;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2019-03-16 23:03
 */
public class ORM {

    private static final String SELECT = "SELECT";

    private static final String FROM = "FROM";

    private static final String WHERE = "WHERE";

    private static final String SPLIT_BLANK = " ";

    private static final String EQUALS = "=";

    private static final String AND = "AND";

    public static <T> String query(T obj) throws Exception {
        StringBuilder sql = new StringBuilder();

        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        String BASE_COLUMN_LIST = Arrays.stream(fields)
                .filter(item -> item.isAnnotationPresent(Column.class))
                .map(item -> item.getAnnotation(Column.class).value())
                .collect(Collectors.joining(","));

        String TABLE_NAME = ((Table) clazz.getAnnotation(Table.class)).value();

        sql.append(SELECT).append(SPLIT_BLANK)
                .append(BASE_COLUMN_LIST).append(SPLIT_BLANK)
                .append(FROM).append(SPLIT_BLANK)
                .append(TABLE_NAME);

        StringBuilder condition = new StringBuilder();

        for (Field field : fields) {
            String fieldName = field.getAnnotation(Column.class).value();

            PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
            Method getter = descriptor.getReadMethod();
            Object fieldValue = getter.invoke(obj);

            if (fieldValue == null || (fieldValue instanceof Integer && fieldValue.equals(0))) {
                continue;
            }

            condition.append(SPLIT_BLANK).append(AND).append(SPLIT_BLANK).append(fieldName).append(EQUALS);
            if (fieldValue instanceof String) {
                condition.append("'").append((String) fieldValue).append("'");
            } else if (fieldValue instanceof Integer) {
                condition.append((Integer) fieldValue);
            }
        }

        if (condition.length() > 0) {
            sql.append(SPLIT_BLANK).append(WHERE).append(SPLIT_BLANK).append(condition.substring(4));
        }

        return sql.toString();
    }


}
