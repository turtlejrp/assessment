package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="user_ticket")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    @ManyToOne
    @JoinColumn(name="ticket_id",referencedColumnName = "ticket",nullable = false)
    private Lottery ticketId;

    @NotNull
    @Column(name = "user_id",length = 10, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$")
    private String userId;

    public UserTicket(){}

    public Integer getId() {
        return id;
    }

    public Lottery getTicketId() {
        return ticketId;
    }

    public void setId(Integer id) { this.id = id;}

    public void setTicketId(Lottery ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
