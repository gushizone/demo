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
public class CodeEnumTypeHandler<E extends BaseCodeEnum> extends BaseTypeHandler<BaseCodeEnum> {

    private Class<E> type;

    private final E[] enums;

    public CodeEnumTypeHandler(Class<E> typeHandlerClass) {
        if (typeHandlerClass == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = typeHandlerClass;
        this.enums = type.getEnumConstants();
    }

    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseCodeEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer code = rs.getInt(columnName);
        return rs.wasNull() ? null : codeOf(code);
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : codeOf(code);
    }

    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : codeOf(code);
    }

    private E codeOf(Integer code){
        for (E baseEnum : enums) {
            if (baseEnum.getCode().equals(code)) {
                return baseEnum;
            }
        }
        return null;
    }
}