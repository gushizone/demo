package tk.gushizone.web.bind.controller.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author gushizone@gmail.com
 * @date 2021/2/8 4:05 下午
 */
@Data
public class Object4Jdk8Param {

    private Integer id;

    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
