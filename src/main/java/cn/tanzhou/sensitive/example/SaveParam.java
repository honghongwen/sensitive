package cn.tanzhou.sensitive.example;

import java.math.BigDecimal;

/**
 * @author fengwen
 * @date 2020-12-29
 *
 */
public class SaveParam {

    private Integer id;

    private String name;

    private String title;

    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SaveParam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                '}';
    }
}
