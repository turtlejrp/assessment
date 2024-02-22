package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    List<UserTicket> findByTicketIdAndUserId(Lottery ticketId, String userId);

    List<UserTicket> findByUserId(String userId);
}
