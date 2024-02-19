package com.kbtg.bootcamp.posttest.lottery;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryService {

    private final LotteryRepository lotteryRepository;


    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }


    public List<String> getLotteries(){
        List<Lottery> lotteryList = lotteryRepository.findAll();
        return lotteryList.stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList());
    }

    @Transactional
    public String addLotteries(LotteryRequest request) throws Exception{
        Optional<Lottery> optionalLottery = lotteryRepository.findByTicket(request.ticket());
        Lottery lottery;
        if(optionalLottery.isPresent()){
            lottery = optionalLottery.get();
            if (lottery.getPrice().equals(request.price())) {
                lottery.setAmount(lottery.getAmount()+ request.amount());
                lotteryRepository.save(lottery);
            } else {
                throw new Exception("Invalid price");
            }
        }else {
            lottery = new Lottery();
            lottery.setTicket(request.ticket());
            lottery.setPrice(request.price());
            lottery.setAmount(request.amount());
            lotteryRepository.save(lottery);
        }
        return lottery.getTicket();
    }



}
