package com.kbtg.bootcamp.posttest.userticket;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
public class UserTicketController {
    private final UserTicketService userTicketService;

    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Object> buyLottery(@PathVariable("userId") String userId, @PathVariable("ticketId") String ticketId){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new HashMap<String, String>() {{
                    put("id", userTicketService.buyLottery(userId, ticketId));
                }});
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<Object> showAllMyLotteries(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userTicketService.showAllMyLotteries(userId));
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Object> refundLottery(@PathVariable("userId") String userId, @PathVariable("ticketId") String ticketId){

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new HashMap<String, String>() {{
                    put("ticket", userTicketService.refundLottery(userId, ticketId));
                }});
    }
}
