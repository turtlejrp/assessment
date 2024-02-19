package com.kbtg.bootcamp.posttest.lottery;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="lottery")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(length = 6, nullable = false)
    @Pattern(regexp = "^[0-9]{6}$")
    private String ticket;

    @NotNull
    private Double price;

    @NotNull
    private Integer amount;

    public String getTicket() {
        return ticket;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
