package tk.gushizone.elasticsearch.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * TODO  一般只要指定String是 text or keyword ，其他会自动识别。
 *
 * @author gushizone@gmail.com
 * @date 2019-09-14 14:36
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)
public class Item {

    @Id
    Long id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String title;

    /**
     * 分类
     */
    @Field(type = FieldType.Keyword)
    String category;

    /**
     * 品牌
     */
    @Field(type = FieldType.Keyword)
    String brand; 

    /**
     * 价格
     */
    Double price; 

    /**
     * 图片地址
     */
    @Field(type = FieldType.Keyword, index = false)
    String images; 
}
