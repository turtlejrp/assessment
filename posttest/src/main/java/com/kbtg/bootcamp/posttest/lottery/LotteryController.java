package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LotteryController {

    private final LotteryService lotteryService;


    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("/lotteries")
    public ResponseEntity<Object> getLotteries(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new HashMap<String, List<String>>() {{
                    put("tickets", lotteryService.getLotteries());
                }});
    }

    @PostMapping("/admin/lotteries")
    public ResponseEntity<Object> addLotteries(@Validated @RequestBody LotteryRequest request) throws Exception{
        return ResponseEntity.status(HttpStatus.OK)
                .body(new HashMap<String, String>() {{
                    put("ticket", lotteryService.addLotteries(request));
                }});
    }
}
