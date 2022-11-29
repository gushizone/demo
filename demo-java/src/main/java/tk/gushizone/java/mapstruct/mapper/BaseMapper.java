package tk.gushizone.java.mapstruct.mapper;

import java.util.List;

/**
 * @author gushizone
 * @date 2022/10/5 00:26
 */
public interface BaseMapper<S, T> {

    T to(S source);

    List<T> to(List<S> sources);

    S from(T target);

    List<S> from(List<T> target);
}
