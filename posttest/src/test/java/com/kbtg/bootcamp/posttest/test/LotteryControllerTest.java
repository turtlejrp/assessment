package com.kbtg.bootcamp.posttest.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbtg.bootcamp.posttest.lottery.LotteryController;
import com.kbtg.bootcamp.posttest.lottery.LotteryRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LotteryControllerTest {

    MockMvc mockMvc;

    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries should call addLotteries return list tickets")
    void addLotteriesController() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String ticket = "123456";
        int price = 80;
        int amount = 1;
        String requestBody = objectMapper.writeValueAsString(new LotteryRequest(ticket, price, amount));
        when(lotteryService.addLotteries(any(LotteryRequest.class))).thenReturn(ticket);
        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(requestBody)
                )
                .andExpect(jsonPath("$.ticket",is(ticket)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when perform on GET: /lotteries should call getLotteries and return List of tickets")
    void getLotteries() throws Exception {

        String ticket1 = "000000";
        String ticket2 = "123456";
        when(lotteryService.getLotteries()).thenReturn(List.of(ticket1,ticket2));
        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$.tickets[0]",is(ticket1)))
                .andExpect(jsonPath("$.tickets[1]",is(ticket2)))
                .andExpect(status().isOk());
    }
}
