package com.kbtg.bootcamp.posttest.test;

import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTicketServiceTest {
    private UserTicketService userTicketService;

    @Mock
    private UserTicketRepository userTicketRepository;

    @Mock
    private LotteryRepository lotteryRepository;

    @BeforeEach
    void setUp() {
        userTicketService = new UserTicketService(lotteryRepository,userTicketRepository);
    }

    @Test
    @DisplayName("when buy lottery that have amount>1 must return Id of create Userticket and amount of stock minus 1")
    public void buyLotteriesValidPurchase(){

        Lottery lottery = new Lottery();
        lottery.setAmount(1);
        UserTicket userTicket = new UserTicket();
        userTicket.setId(1);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.of(lottery));
        Mockito.when(userTicketRepository.save(any())).thenReturn(userTicket);
        String expected = "1";
        String actual = userTicketService.buyLottery("0123456789", "888888");
        assertEquals(expected, actual);
        assertEquals(lottery.getAmount(), 0);
    }

    @Test
    @DisplayName("when buy lottery sold out should throw NotFoundException with Lottery already sold out")
    public void buyLotteriesSoldoutThrowLotterySoldOut(){

        Lottery lottery = new Lottery();
        lottery.setAmount(0);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.of(lottery));
        var exception = assertThrows(NotFoundException.class,() -> userTicketService.buyLottery("0123456789", "888888"));
        assertEquals("Lottery already sold out", exception.getMessage());
    }

    @Test
    @DisplayName("when buy lottery that don't have ticketId throw NotFoundException")
    public void buyLotteriesThatNotHaveTickerIdThrowDontHaveTicket(){
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class,() -> userTicketService.buyLottery("0123456789", "888888"));
        assertEquals("Don't have this Lottery Ticket", exception.getMessage());
    }

    @Test
    @DisplayName("when show my Lotteries should return tickets,count,cost")
    public void showMyLotteries(){
        Lottery lottery1 = new Lottery();
        lottery1.setTicket("111111");
        lottery1.setAmount(1);
        lottery1.setPrice(80);
        Lottery lottery2 = new Lottery();
        lottery2.setTicket("123456");
        lottery2.setAmount(1);
        lottery2.setPrice(150);
        UserTicket userTicket1 = new UserTicket();
        userTicket1.setTicketId(lottery1);
        UserTicket userTicket2 = new UserTicket();
        userTicket2.setTicketId(lottery2);
        List<UserTicket> userTickets = List.of(userTicket1, userTicket2);
        when(userTicketRepository.findByUserId(anyString())).thenReturn(userTickets);

        HashMap<String, Object> result = userTicketService.showAllMyLotteries("0123456789");
        List<String> expectedTickets = List.of("111111", "123456");
        assertEquals(expectedTickets, result.get("tickets"));
        assertEquals(2, result.get("count"));
        assertEquals(230, result.get("cost"));
    }

    @Test
    @DisplayName("when refund Lottery should return ticket of lottery and amount of lotteries must plus total lottery refund")
    public void refundLotteries(){
        Lottery lottery = new Lottery();
        lottery.setTicket("123456");
        lottery.setAmount(0);
        UserTicket userTicket = new UserTicket();
        userTicket.setUserId("0123456789");
        userTicket.setTicketId(lottery);
        List<UserTicket> userTickets = List.of(userTicket);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.of(lottery));
        when(userTicketRepository.findByTicketIdAndUserId(lottery,"0123456789")).thenReturn(userTickets);

        String result = userTicketService.refundLottery("0123456789", "123456");
        assertEquals("123456", result);
        assertEquals(1, lottery.getAmount());
    }

    @Test
    @DisplayName("when refund Lottery but User didn't buy Lottery throw NotFoundException with User didn't buy this Lottery")
    public void refundLotteriesNotPurchase(){
        Lottery lottery = new Lottery();
        lottery.setTicket("123456");
        lottery.setAmount(0);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.of(lottery));
        when(userTicketRepository.findByTicketIdAndUserId(lottery,"0123456789")).thenReturn(new ArrayList<>());
        var exception = assertThrows(NotFoundException.class, () -> userTicketService.refundLottery("0123456789", "123456"));
        assertEquals("User didn't buy this Lottery", exception.getMessage());
    }

    @Test
    @DisplayName("when refund Lottery but Lottery is not have throw NotFoundException with Don't have this Lottery Ticket")
    public void refundLotteriesNoLotteryId(){

        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.empty());
        var exception = assertThrows(NotFoundException.class, () -> userTicketService.refundLottery("0123456789", "123456"));
        assertEquals("Don't have this Lottery Ticket", exception.getMessage());
    }
}
