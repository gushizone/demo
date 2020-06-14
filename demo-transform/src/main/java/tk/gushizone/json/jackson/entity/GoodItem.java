package tk.gushizone.json.jackson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.function.Function;

/**
 * @author gushizone@gmail.com
 * @date 2020-03-19 18:38
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GoodItem extends Item {

    private String good;

    private Class clazz;

    private Function function;

}
