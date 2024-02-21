package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="lottery")
public class Lottery {

    @Id
    @NotNull
    private String ticket;

    @NotNull
    private int price;

    @NotNull
    private int amount;

    public Lottery(){}

    public String getTicket() {
        return ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
