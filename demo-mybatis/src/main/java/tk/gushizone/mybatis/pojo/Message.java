package tk.gushizone.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import tk.gushizone.mybatis.enumeration.BaseEnumTypeHandler;
import tk.gushizone.mybatis.enumeration.CommandEnum;

import java.time.LocalDateTime;

/**
 * java     mysql
 * Long <-> timestamp
 * Date API <-> timestamp
 * Boolean <-> tinyint
 * enum <-> tinyint
 *
 * @author gushizone@gmail.com
 * @date 2019-12-10 00:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Integer id;

    /**
     * enum -> tinyint : 属性获取即可
     *
     * enum <- tinyint : 需要TypeHandler
     * @see EnumTypeHandler Enum.name()
     * @see EnumOrdinalTypeHandler Enum.ordinal()
     * @see BaseEnumTypeHandler 自定义
     */
    private CommandEnum command;

    private String description;

    private String content;

    /**
     * Boolean <-> tinyint
     *
     * 直接支持 Boolean类型 （0, 1）
     */
    private Boolean isDeleted;

    /**
     * Date API <-> timestamp
     *
     * 直接支持 JDK时间类型（Date、LocalDate、LocalDateTime）
     */
    private LocalDateTime createTime;

    /**
     * Long -> timestamp : 使用 from_unixtime()
     *
     * Long <- timestamp : 使用 unix_timestamp()
     */
    private Long updateTime;

}