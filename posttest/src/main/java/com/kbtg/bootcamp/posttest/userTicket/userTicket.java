package com.kbtg.bootcamp.posttest.userTicket;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="user_ticket")
public class userTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name="ticket_id",nullable = false)
    private Lottery ticketId;

    @NotNull
    @Column(name = "user_id",length = 10, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$")
    private String userId;
}
