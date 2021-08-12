package tk.gushizone.java.jdk8.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2021-01-02 15:07
 */
public class ModelUtils {


    /**
     * stream flatMap
     */
    public static <T, R> List<R> flatMap(Collection<T> collection, Function<T, List<R>> function) {
        if (CollectionUtils.isEmpty(collection)) {
            return Lists.newArrayList();
        }
        return collection.stream()
                .filter(e -> CollectionUtils.isNotEmpty(function.apply(e)))
                .flatMap(e -> function.apply(e).stream())
                .collect(Collectors.toList());
    }

    /**
     * stream map
     */
    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> function) {
        if (CollectionUtils.isEmpty(collection)) {
            return Lists.newArrayList();
        }
        return collection.stream()
                .filter(e -> e != null && function.apply(e) != null)
                .map(function)
                .collect(Collectors.toList());
    }

    /**
     * stream map，会去重和去null
     */
    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> function) {
        if (CollectionUtils.isEmpty(collection)) {
            return Sets.newHashSet();
        }
        return collection.stream()
                .filter(e -> e != null && function.apply(e) != null)
                .map(function)
                .collect(Collectors.toSet());
    }

    /**
     * stream toMap
     * 避免 null 和 Duplicate key
     */
    public static <T, K> Map<K, T> convertToMap(Collection<T> collection, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream()
                .filter(e -> e != null && keyMapper.apply(e) != null)
                .collect(Collectors.toMap(keyMapper, Function.identity(), (a, b) -> a));
    }

    /**
     * stream toMap
     * 避免 null 和 Duplicate key
     */
    public static <T, K, V> Map<K, V> convertToMap(Collection<T> collection,
                                                   Function<T, K> keyMapper,
                                                   Function<T, V> valueMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream()
                .filter(e -> e != null && keyMapper.apply(e) != null)
                .collect(Collectors.toMap(keyMapper, valueMapper, (a, b) -> a));
    }

    /**
     * stream group
     */
    public static <T, K> Map<K, List<T>> groupToMap(Collection<T> collection, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream()
                .filter(e -> e != null && keyMapper.apply(e) != null)
                .collect(Collectors.groupingBy(keyMapper, Collectors.toList()));
    }

    /**
     * stream group
     */
    public static <T, K, V> Map<K, List<V>> groupToMap(Collection<T> collection,
                                                       Function<T, K> keyMapper,
                                                       Function<T, V> valueMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream()
                .filter(e -> e != null && keyMapper.apply(e) != null)
                .collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return Lists.newArrayList();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * 计数 - 根据 key 去重 去null
     */
    public static <T, R> Long count(Collection<T> collection, Function<T, R> keyMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return NumberUtils.LONG_ZERO;
        }
        return collection.stream()
                .filter(e -> e != null && keyMapper.apply(e) != null)
                .map(keyMapper)
                .distinct()
                .count();
    }

    /**
     * 求和 - BigDecimal
     */
    public static <T> BigDecimal sum(Collection<T> collection, Function<T, BigDecimal> function) {
        if (CollectionUtils.isEmpty(collection)) {
            return BigDecimal.ZERO;
        }
        return collection.stream()
                .filter(e -> e != null && function.apply(e) != null)
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 去重
     */
    public static <T, K> List<T> distinct(Collection<T> collection, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return Lists.newArrayList();
        }
        return collection.stream()
                .filter(e -> e != null && keyMapper.apply(e) != null)
                .filter(distinctByKey(keyMapper))
                .collect(Collectors.toList());

    }

    public static <T, K> Predicate<T> distinctByKey(Function<T, K> keyExtractor) {
        Map<K, Boolean> map = Maps.newConcurrentMap();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
