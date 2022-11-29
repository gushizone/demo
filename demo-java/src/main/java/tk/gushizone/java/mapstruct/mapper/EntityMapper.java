package tk.gushizone.java.mapstruct.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tk.gushizone.java.mapstruct.dto.EntityDto;
import tk.gushizone.java.mapstruct.pojo.Entity;

/**
 * @author gushizone
 * @date 2022/10/5 00:44
 */
@Mapper
public interface EntityMapper extends BaseMapper<Entity, EntityDto> {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);
}
