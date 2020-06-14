package tk.gushizone.java.jdk8.functioninterface;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author gushizone@gmail.com
 * @date 2020-05-31 22:43
 */
public class FunctionInterfaceUtils {

    public static <T, R> List<R> function(Collection<T> collection, Function<T, R> function) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream()
                .map(function)
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public static <E> void consumer(Consumer<E> consumer, E... array) {
        if (ArrayUtils.isEmpty(array)) {
            return;
        }
        for (E e : array) {
            consumer.accept(e);
        }
    }

    public static <T, C extends Collection<T>> C supplier(Collection<T> collection, Supplier<C> supplier) {
        if (CollectionUtils.isEmpty(collection)) {
            return supplier.get();
        }
        return collection.stream()
                .collect(Collectors.toCollection(supplier));
    }

    public static <T> List<T> predicate(Collection<T> collection, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }


}
