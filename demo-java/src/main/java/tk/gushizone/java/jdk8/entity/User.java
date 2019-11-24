package tk.gushizone.java.jdk8.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author gushizone@gmail.com
 * @date 2019-11-13 11:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;

    private int age;

    private Date birthday;

    private BigDecimal salary;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
