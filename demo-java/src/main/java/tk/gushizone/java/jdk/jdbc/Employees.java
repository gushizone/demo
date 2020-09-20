package tk.gushizone.java.jdk.jdbc;

import lombok.Data;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2020-09-17 23:25
 */
@Data
public class Employees {

    private Integer empNo;

    private Date birthDate;

    private String firstName;

    private String lastName;

    private Object gender;

    private Date hireDate;
}