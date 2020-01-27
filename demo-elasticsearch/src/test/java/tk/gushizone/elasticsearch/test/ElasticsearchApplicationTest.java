package tk.gushizone.elasticsearch.test;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import tk.gushizone.elasticsearch.dao.ItemRepository;
import tk.gushizone.elasticsearch.pojo.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author gushizone@gmail.com
 * @date 2019-09-14 14:49
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchApplicationTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * 创建索引
     */
    @Test
    public void testIndex() {
        // 删除索引
        elasticsearchTemplate.deleteIndex("item");

        // 创建索引
        elasticsearchTemplate.createIndex(Item.class);
        // 配置索引
        elasticsearchTemplate.putMapping(Item.class);
    }

    /**
     * 新增、修改
     */
    @Test
    public void testSave() {

        Item item = new Item(1L, "小米手机", " 手机",
                "小米", 3499.00, "http://image.leyou.com/13123.jpg");

        itemRepository.save(item);


        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为手机", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));

        itemRepository.saveAll(list);


        List<Item> testList = new ArrayList<>();
        testList.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        testList.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        testList.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        testList.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        testList.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));

        itemRepository.saveAll(testList);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {

        itemRepository.deleteById(1L);

        itemRepository.deleteAll();
    }

    /**
     * 基本查询
     *
     * <pre>
     * item : Optional[Item(id=1, title=小米手机7, category=手机, brand=小米, price=3299.0, images=http://image.leyou.com/13123.jpg)]
     * list : [Item(id=3, title=华为META10, category=手机, brand=华为, price=4499.0, images=http://image.leyou.com/13123.jpg), Item(id=4, title=小米Mix2S, category=手机, brand=小米, price=4299.0, images=http://image.leyou.com/13123.jpg), Item(id=2, title=坚果手机R1, category=手机, brand=锤
     * item1 : [Item(id=1, title=小米手机7, category=手机, brand=小米, price=3299.0, images=http://image.leyou.com/13123.jpg), Item(id=4, title=小米Mix2S, category=手机, brand=小米, price=4299.0, images=http://image.leyou.com/13123.jpg)]
     * </pre>
     */
    @Test
    public void testFind() {

        Optional<Item> item = itemRepository.findById(1L);
        log.warn("item : {}", item);

        Iterable<Item> list = itemRepository.findAll(Sort.by("price").descending());
        log.warn("list : {}", Lists.newArrayList(list));

        // TODO 类似 JPA
        List<Item> item1 = itemRepository.findByTitle("小米");
        log.warn("item1 : {}", item1);
    }

    /**
     * 高级查询 - 匹配查询
     *
     * <pre>
     * Iterable : [Item(id=1, title=小米手机7, category=手机, brand=小米, price=3299.0, images=http://image.leyou.com/13123.jpg), Item(id=2, title=坚果手机R1, category=手机, brand=锤子, price=3699.0, images=http://image.leyou.com/13123.jpg)]
     * </pre>
     */
    @Test
    public void testQuery() {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "手机");
        Iterable<Item> items = itemRepository.search(matchQueryBuilder);
        log.warn("Iterable : {}", Lists.newArrayList(items));
    }


    /**
     * 高级查询 - 自定义查询
     *
     * <pre>
     * totalPages : 1,
     * totalElements : 2,
     * content : [Item(id=1, title=小米手机7, category=手机, brand=小米, price=3299.0, images=http://image.leyou.com/13123.jpg), Item(id=2, title=坚果手机R1, category=手机, brand=锤子, price=3699.0, images=http://image.leyou.com/13123.jpg)]
     * </pre>
     */
    @Test
    public void testNativeQuery() {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "手机"));

        Page<Item> itemPage = itemRepository.search(queryBuilder.build());
        log.warn("totalPages : {}, totalElements : {}, content : {}",
                itemPage.getTotalPages(),
                itemPage.getTotalElements(),
                itemPage.getContent());
    }


    /**
     * 高级查询 - 分页查询
     *
     * <pre>
     * totalPages : 3,
     * totalElements : 5,
     * content : [Item(id=2, title=坚果手机R1, category=手机, brand=锤子, price=3699.0, images=http://image.leyou.com/13123.jpg), Item(id=1, title=小米手机7, category=手机, brand=小米, price=3299.0, images=http://image.leyou.com/13123.jpg)]
     * </pre>
     */
    @Test
    public void testPage() {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // FIXME 页面从 0 开始
        queryBuilder.withQuery(QueryBuilders.matchQuery("category", "手机"))
                .withPageable(PageRequest.of(1, 2))
                .withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));

        Page<Item> itemPage = itemRepository.search(queryBuilder.build());
        log.warn("totalPages : {}, totalElements : {}, content : {}",
                itemPage.getTotalPages(),
                itemPage.getTotalElements(),
                itemPage.getContent());
    }

    /**
     * 高级查询 - 聚合
     *
     * <pre>
     * 华为，共2台
     * 小米，共2台
     * 锤子，共1台
     * </pre>
     */
    @Test
    public void testAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());

        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            log.warn("{}, 共{}台",
                    bucket.getKeyAsString(),
                    bucket.getDocCount());
        }

    }

    /**
     * 高级查询 - 嵌套聚合
     *
     * <pre>
     * 华为, 共2台， 平均售价 : 3649.0
     * 小米, 共2台， 平均售价 : 3799.0
     * 锤子, 共1台， 平均售价 : 3699.0
     * </pre>
     */
    @Test
    public void testSubAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());

        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {

            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            log.warn("{}, 共{}台， 平均售价 : {}",
                    bucket.getKeyAsString(),
                    bucket.getDocCount(),
                    avg.getValue());
        }

    }
}
