package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserTicketService {

    private final LotteryRepository lotteryRepository;
    private final UserTicketRepository userTicketRepository;

    public UserTicketService(LotteryRepository lotteryRepository, UserTicketRepository userTicketRepository) {
        this.lotteryRepository = lotteryRepository;
        this.userTicketRepository = userTicketRepository;
    }

    @Transactional
    public String buyLottery(String userId, String ticketId){
        Optional<Lottery> optionalLottery = lotteryRepository.findByTicket(ticketId);
        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            if(lottery.getAmount() > 0){
            lottery.setAmount(lottery.getAmount()-1);
            lotteryRepository.save(lottery);
            UserTicket userTicket = new UserTicket();
            userTicket.setUserId(userId);
            userTicket.setTicketId(lottery);
            UserTicket saveduserTicket = userTicketRepository.save(userTicket);
            return saveduserTicket.getId().toString();
            }else {
                throw new NotFoundException("Lottery already sold out");
            }
        }else {
            throw new NotFoundException("Don't have this Lottery Ticket");
        }
    }

    public HashMap<String, Object>  showAllMyLotteries(String userId){
        List<UserTicket> userTickets = userTicketRepository.findByUserId(userId);
        List<String> tickets = userTickets.stream()
                                .map(userTicket -> userTicket.getTicketId().getTicket())
                                .collect(Collectors.toList());
        Integer count = userTickets.size();
        Integer cost = sumLotteryCost(userTickets);
        return new HashMap<String, Object>(){{
            put("tickets",tickets);
            put("count", count);
            put("cost", cost);
        }};
    }

    public Integer sumLotteryCost(List<UserTicket> userTicketList){
        return userTicketList.stream()
                .mapToInt(userTicket -> userTicket.getTicketId().getPrice())
                .sum();
    }

    @Transactional
    public String refundLottery(String userId, String ticketId){
        Optional<Lottery> optionalLottery = lotteryRepository.findByTicket(ticketId);
        if (optionalLottery.isPresent()) {
            Lottery lottery = optionalLottery.get();
            List<UserTicket> optionalUserTicket = userTicketRepository.findByTicketIdAndUserId(lottery, userId);
            if (!optionalUserTicket.isEmpty()) {
                userTicketRepository.deleteAll(optionalUserTicket);
                lottery.setAmount(lottery.getAmount()+optionalUserTicket.size());
                return lottery.getTicket();
            }else {
                throw new NotFoundException("User didn't buy this Lottery");
            }
        }else {
            throw new NotFoundException("Don't have this Lottery Ticket");
        }
    }
}
