package tk.gushizone.java.mapstruct.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tk.gushizone.java.mapstruct.dto.ItemDto;
import tk.gushizone.java.mapstruct.enums.StatusEnum;
import tk.gushizone.java.mapstruct.pojo.Item;

import java.util.List;

/**
 * 默认映射同名自动，如果类型不同会报错，可以忽略字段
 *
 * - 单个，list映射
 * - date 格式化
 * - 枚举等类型转换
 * - 逆映射
 *
 * @author gushizone@gmail.com
 * @date 2021/10/8 2:48 下午
 */
@Mapper(imports = StatusEnum.class)
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "createTime", target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "status", expression = "java(StatusEnum.valueOf(item.getStatus()))")
    ItemDto toDto (Item item);

    List<ItemDto> toDto(List<Item> items);

    /**
     *
     * @see InheritInverseConfiguration 继承反方向的所有配置
     */
    @InheritInverseConfiguration
    @Mapping(source = "status.value", target = "status")
    Item toPojo(ItemDto dto);

    List<Item> toPojo(List<ItemDto> items);

}
