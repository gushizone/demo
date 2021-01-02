package tk.gushizone.mybatis.utils;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.lang3.StringUtils;
import tk.gushizone.mybatis.utils.enums.SortTypeEnum;
import tk.gushizone.mybatis.utils.model.BasePageReq;
import tk.gushizone.mybatis.utils.model.Page;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:35
 */
@Slf4j
public class PageUtils {

    public static final Integer DO_BATCH_SIZE = 1000;

    public static <E> Page<E> build(List<E> list) {

        PageInfo<E> pageInfo = new PageInfo<>(list);

        return Page.<E>builder()
                .list(pageInfo.getList())
                .total(pageInfo.getTotal())
                .pageNum(pageInfo.getPageNum())
                .pageSize(pageInfo.getPageSize())
                .pages(pageInfo.getPages())
                .build();
    }

    /**
     * 物理分页 - 基于 pageHelper 的分页共通方法，支持排序
     */
    public static <E, T extends BasePageReq> Page<E> doSelectPage(T basePageReq, ISelect select) {

        int pageNum = basePageReq.getPageNum() == null ? 1 : basePageReq.getPageNum();
        int pageSize = basePageReq.getPageSize() == null ? 10 : basePageReq.getPageSize();

        com.github.pagehelper.Page<E> page = PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(basePageReq.getSortFiled())
                && StringUtils.isNotBlank(basePageReq.getSortType())) {

            basePageReq.checkSortType();
            basePageReq.checkSortFiled();
            page.setOrderBy(basePageReq.getSortFiled() + " " + basePageReq.getSortType());
        }

        // 执行分页查询
        PageInfo<E> pageInfo = page.doSelectPageInfo(select);

        return Page.<E>builder()
                .list(pageInfo.getList())
                .total(pageInfo.getTotal())
                .pageNum(pageInfo.getPageNum())
                .pageSize(pageInfo.getPageSize())
                .pages(pageInfo.getPages())
                .build();
    }

    /**
     * 逻辑分页
     */
    @SneakyThrows
    public static <E, T extends BasePageReq> Page<E> doSelectPageByLogic(T basePageReq, Supplier<List<E>> supplier) {

        Integer pageNum = basePageReq.getPageNum();
        Integer pageSize = basePageReq.getPageSize();

        // 全量查询
        List<E> list = supplier.get();
        int total = list.size();
        if (pageNum == 0 || pageSize == 0) {
            return Page.<E>builder()
                    .list(list)
                    .total((long) total)
                    .pageNum(0)
                    .pageSize(0)
                    .pages(0)
                    .build();
        }

        //  计算总页数
        int pages = total / pageSize;
        int plus = total % pageSize == 0 ? 0 : 1;
        pages += plus;
        if (pages <= 0) {
            pages = 1;
        }
        // 当前页数小于1则设置为1
        if (pageNum < 1) {
            pageNum = 1;
        }
        // 总页数小于当前页数，应将当前页数设置为总页数
        if (pages < pageNum) {
            pageNum = pages;
        }

        List<E> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {

            // 排序
            if (StringUtils.isNotBlank(basePageReq.getSortFiled())
                    && StringUtils.isNotBlank(basePageReq.getSortType())) {

                basePageReq.checkSortType();
                basePageReq.checkSortFiled();

                PropertyDescriptor pd = new PropertyDescriptor(basePageReq.getSortFiled(), list.get(0).getClass());
                Method readMethod = pd.getReadMethod();

                list.sort((o1, o2) -> {
                    Object val1;
                    Object val2;
                    try {
                        val1 = readMethod.invoke(o1);
                        val2 = readMethod.invoke(o2);
                    } catch (IllegalAccessException | InvocationTargetException e) {

                        log.error("排序失败，无法获取字段值: ", e);
                        throw new RuntimeException("排序失败，无法获取字段值", e);
                    }
                    Comparator<Object> comparator = ComparatorUtils.nullLowComparator(null);
                    return SortTypeEnum.ASC.getCodeText().equals(basePageReq.getSortType())
                            ? comparator.compare(val1, val2)
                            : comparator.compare(val2, val1);
                });
            }

            // 执行逻辑分页
            int fromIndex = (pageNum - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            result = list.subList(fromIndex, toIndex);
        }

        return Page.<E>builder()
                .list(result)
                .total((long) total)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .pages(pages)
                .build();
    }
}
