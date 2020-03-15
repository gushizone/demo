package tk.gushizone.spring.transaction.propagation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gushizone@gmail.com
 * @date 2020-01-30 21:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    public static final Stock SAMPLE = new Stock(1L, 1L, 100);

    private Long id;

    private Long itemId;

    private Integer stock;
}