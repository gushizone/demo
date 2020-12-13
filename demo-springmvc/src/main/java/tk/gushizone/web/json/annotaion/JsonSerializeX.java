
package tk.gushizone.web.json.annotaion;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import tk.gushizone.web.json.interceptor.JsonSerializerInterceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = JsonSerializerInterceptor.class)
public @interface JsonSerializeX {


}
