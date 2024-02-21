package com.kbtg.bootcamp.posttest.test;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LotteryServiceTest {
    private LotteryService lotteryService;

    @Mock
    private LotteryRepository lotteryRepository;

    @BeforeEach
    void setUp() {
        lotteryService = new LotteryService(lotteryRepository);
    }

    @Test
    @DisplayName("when add lottery that don't have ticket it will return ticket ID and lotteryRepository must save 1 time")
    public void addLotteries(){
        String ticket = "123456";
        int price = 80;
        int amount = 1;
        LotteryRequest request = new LotteryRequest(ticket,price,amount);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.empty());

        String actual = lotteryService.addLotteries(request);
        assertEquals(ticket, actual);
        Mockito.verify(lotteryRepository, Mockito.times(1)).save(Mockito.any(Lottery.class));
    }

    @Test
    @DisplayName("when add lottery that already have it will return ticket ID and lottery amount must add")
    public void addLotteriesAlreadyHave(){
        String ticket = "012345";
        int price = 80;
        int amount = 1;
        Lottery lottery = new Lottery();
        lottery.setTicket("012345");
        lottery.setPrice(80);
        lottery.setAmount(2);
        LotteryRequest request = new LotteryRequest(ticket,price,amount);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.of(lottery));
        when(lotteryRepository.save(Mockito.any(Lottery.class))).thenReturn(lottery);

        String actual = lotteryService.addLotteries(request);
        assertEquals(ticket, actual);
        assertEquals(3, lottery.getAmount());
    }

    @Test
    @DisplayName("when add lottery that already have but price mismatch throw BadRequestException and Price is not equal to the same price")
    public void addLotteriesPriceMismatch(){
        String ticket = "012345";
        int price = 100;
        int amount = 1;
        Lottery lottery = new Lottery();
        lottery.setTicket("012345");
        lottery.setPrice(80);
        lottery.setAmount(2);
        LotteryRequest request = new LotteryRequest(ticket,price,amount);
        when(lotteryRepository.findByTicket(anyString())).thenReturn(Optional.of(lottery));
        var exception = assertThrows(BadRequestException.class, () -> lotteryService.addLotteries(request));
        assertEquals("Price is not equal to the same price", exception.getMessage());
    }

    @Test
    @DisplayName("when get lotteries should return List of tickets")
    public void getLotteries() {
        Lottery testLottery1 = new Lottery();
        testLottery1.setTicket("012345");
        Lottery testLottery2 = new Lottery();
        testLottery2.setTicket("888888");
        when(lotteryRepository.findAll()).thenReturn(List.of(testLottery1,testLottery2));

        List<String> expected = Arrays.asList("012345","888888");
        List<String> actual = lotteryService.getLotteries();
        assertEquals(expected, actual);
    }
}
