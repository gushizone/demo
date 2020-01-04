package tk.gushizone.java.lombok.constructor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-12-29 23:32
 */
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {

    @NonNull
    private Integer id;

    private String name;

    private String password;

}