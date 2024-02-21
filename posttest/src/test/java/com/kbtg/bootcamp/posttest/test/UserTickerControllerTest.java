package com.kbtg.bootcamp.posttest.test;

import com.kbtg.bootcamp.posttest.userticket.UserTicketController;
import com.kbtg.bootcamp.posttest.userticket.UserTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTickerControllerTest {

    MockMvc mockMvc;

    @Mock
    UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        UserTicketController userTicketController = new UserTicketController(userTicketService);
        mockMvc = MockMvcBuilders.standaloneSetup(userTicketController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} should call buyLotteries and return id userticket")
    void addLotteriesController() throws Exception {

        when(userTicketService.buyLottery(any(),any())).thenReturn("1");
        mockMvc.perform(post("/users/0123456789/lotteries/888888"))
                .andExpect(jsonPath("$.id",is("1")))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("when perform on GET: /users/{userId}/lotteries should call showAllMyLotteries and return lottery detail")
    void showAllMyLotteriesController() throws Exception {

        when(userTicketService.showAllMyLotteries(any()))
                .thenReturn(new HashMap<String, Object>(){{
            put("tickets",List.of("012345","888888"));
            put("count", 2);
            put("cost", 150);
        }});
        mockMvc.perform(get("/users/0123456789/lotteries")
                )
                .andExpect(jsonPath("$.tickets[0]",is("012345")))
                .andExpect(jsonPath("$.tickets[1]",is("888888")))
                .andExpect(jsonPath("$.count",is(2)))
                .andExpect(jsonPath("$.cost",is(150)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} should call refundLotteries and return ticketId")
    void refundLotteriesController() throws Exception {

        when(userTicketService.refundLottery(any(),any()))
                .thenReturn("888888");
        mockMvc.perform(delete("/users/0123456789/lotteries/888888")
                )
                .andExpect(jsonPath("$.ticket",is("888888")))
                .andExpect(status().isAccepted());
    }
}
