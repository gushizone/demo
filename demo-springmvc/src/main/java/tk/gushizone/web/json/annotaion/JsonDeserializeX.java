package tk.gushizone.web.json.annotaion;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import tk.gushizone.web.json.interceptor.JsonDeserializerInterceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonDeserialize(using = JsonDeserializerInterceptor.class)
public @interface JsonDeserializeX {

    Class<?> rawClass() default Object.class;
}
