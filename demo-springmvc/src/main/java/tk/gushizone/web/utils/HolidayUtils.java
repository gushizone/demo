package tk.gushizone.web.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 节假日工具类（法定节假日+周末，不进行调休判断）
 *
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:19
 */
@Slf4j
@SuppressWarnings("all")
public class HolidayUtils {


    /**
     * FIXME : 免费版每秒调用超过2次就会被封禁1天
     * 节假日api: http://tool.bitefu.net/jiari/?d=2021,2022
     * <pre>
     * {
     *     "2021": {
     *         "1001": 2,
     *          ...
     *         "0921": 2
     *     },
     *     "2022": false
     * }
     * </pre>
     */
    private static final String API_URL = "http://tool.bitefu.net/jiari/?d=";

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String HOLIDAY_KEY = "HOLIDAY_KEY";

    private static LoadingCache<String, List<LocalDate>> holidayCache = CacheBuilder.newBuilder()
            .initialCapacity(1).maximumSize(1)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, List<LocalDate>>() {
                @Override
                public List<LocalDate> load(String s) throws Exception {
                    return init();
                }
            });

    private static List<LocalDate> init() {

        List<LocalDate> results = Lists.newArrayList();
        try {
            // [2010, 明年]
            List<Integer> years = IntStream.range(2010, LocalDate.now().getYear() + 1).boxed().collect(Collectors.toList());
            ResponseEntity<LinkedHashMap> rspEntity = REST_TEMPLATE.getForEntity(API_URL + StringUtils.join(years, ","), LinkedHashMap.class);
            if (rspEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("节假日查询失败");
            }

            LinkedHashMap<String, Object> body = rspEntity.getBody();
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                Object value = entry.getValue();
                if (!(value instanceof LinkedHashMap)) {
                    continue;
                }
                LinkedHashMap<String, String> valueMap = (LinkedHashMap<String, String>) value;
                for (Map.Entry<String, String> valueEntry : valueMap.entrySet()) {

                    results.add(LocalDate.parse(entry.getKey() + valueEntry.getKey(), DATE_TIME_FORMATTER));
                }
            }
            return results;
        } catch (Exception e) {
            log.error("节假日查询失败", e);
            throw new RuntimeException("节假日查询失败", e);
        }
    }

    /**
     * 判断指定天是否是节假日
     */
    public static boolean isHoliday(LocalDate localDate) {

        try {
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                return true;
            }
            List<LocalDate> holidays = holidayCache.get(HOLIDAY_KEY);
            return holidays.contains(localDate);
        } catch (ExecutionException e) {
            log.error("节假日判断失败", e);
            throw new RuntimeException("节假日判断失败", e);
        }
    }

    /**
     * 统计区间内节假日的天数
     */
    public static int holidayCount(LocalDate startDate, LocalDate endDate) {
        Assert.isTrue(startDate.compareTo(endDate) <= 0, "非法参数：开始时间不能大于结束时间");

        int holidayCount = 0;
        LocalDate tempStartDate = startDate;
        while (tempStartDate.compareTo(endDate) <= 0) {
            if (isHoliday(tempStartDate)) {
                holidayCount++;
            }
            tempStartDate = tempStartDate.plusDays(1);
        }
        return holidayCount;
    }

    /**
     * 节假日顺延，返回真正的结束时间
     */
    public static LocalDate postpone(LocalDate startDate, LocalDate endDate) {
        Assert.isTrue(startDate.compareTo(endDate) <= 0, "非法参数：开始时间不能大于结束时间");

        long betweenDays = endDate.toEpochDay() - startDate.toEpochDay();

        LocalDate tempStartDate = startDate;
        while (betweenDays > 0) {
            if (!isHoliday(tempStartDate)) {
                betweenDays--;
            }
            tempStartDate = tempStartDate.plusDays(1);
        }
        return tempStartDate;
    }
}
