package tk.gushizone.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * @author gushizone
 * @date 2022/10/17 14:49
 */
public class GenericEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    public GenericEvent(T source) {
        super(source);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }
}
