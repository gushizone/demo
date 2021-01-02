package tk.gushizone.mybatis.utils.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.CollectionUtils;
import tk.gushizone.mybatis.utils.enums.SortTypeEnum;

import java.util.List;

/**
 * 通用的分页查询参数
 *
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:36
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BasePageReq {

    public static final List<String> SORT_FILED = Lists.newArrayList("createTime", "updateTime");

    public static final List<String> SORT_TYPE = Lists.newArrayList(SortTypeEnum.ASC.getCodeText(), SortTypeEnum.DESC.getCodeText());

    public static final Integer MAX_PAGE_SIZE = 5000;
    /**
     * 当前页
     */
    private Integer pageNum = 1;
    /**
     * 每页的数量
     */
    private Integer pageSize = 10;
    /**
     * 排序字段
     */
    private String sortFiled;
    /**
     * 排序类型：asc-升序；desc-降序
     */
    private String sortType = SortTypeEnum.DESC.getCodeText();


    /**
     * 重写此方法用于自定义排序字段校验
     *
     * @return 可以排序的字段
     */
    public List<String> sortFiled() {
        return SORT_FILED;
    }

    /**
     * 校验排序字段
     */
    public void checkSortFiled() {
        if (CollectionUtils.isEmpty(sortFiled())) {
            throw new RuntimeException("不支持排序");
        }
        if (!sortFiled().contains(sortFiled)) {
            throw new RuntimeException("请选择合理的排序字段");
        }
    }

    public void checkSortType() {
        if (!SORT_TYPE.contains(sortType)) {
            throw new RuntimeException("请选择合理的排序类型");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePageReq> T noPage() {
        this.pageNum = 0;
        this.pageSize = 0;
        return (T) this;
    }
}
