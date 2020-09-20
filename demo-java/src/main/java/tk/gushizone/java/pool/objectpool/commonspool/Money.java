package tk.gushizone.java.pool.objectpool.commonspool;

import lombok.SneakyThrows;

import java.math.BigDecimal;

public class Money {

  @SneakyThrows
  public static Money init() {
    // 假设对象new非常耗时
    Thread.sleep(10L);
    return new Money("USD", new BigDecimal("1"));
  }

  private String type;
  private BigDecimal amount;

  @SneakyThrows
  public Money(String type, BigDecimal amount) {
    // 假设对象new非常耗时
    Thread.sleep(100L);

    this.type = type;
    this.amount = amount;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}