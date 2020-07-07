package ca.jrvs.apps.trading.model;

public class Account implements Entity<Integer> {

    private Integer id;
    private Integer traderId;
    private Double amount;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setID(Integer id) {
        this.id = id;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}