package tk.gushizone.mybatis.enumeration;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-26 15:39
 */
public class BaseEnumTypeHandler<E extends BaseEnum> extends BaseTypeHandler<BaseEnum> {

    private Class<E> type;

    public BaseEnumTypeHandler(Class<E> typeHandlerClass) {
        if (typeHandlerClass == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = typeHandlerClass;
    }

    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer value = rs.getInt(columnName);
        return rs.wasNull() ? null : valueOf(value);
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : valueOf(value);
    }

    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : valueOf(value);
    }

    private E valueOf(Integer value){
        for (E baseEnum : type.getEnumConstants()) {
            if (baseEnum.getValue() == value) {
                return baseEnum;
            }
        }
        return null;
    }
}