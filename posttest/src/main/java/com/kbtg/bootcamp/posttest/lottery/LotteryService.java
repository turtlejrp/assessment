package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    public String addLotteries(LotteryRequest request) throws BadRequestException {

        Optional<Lottery> optionalLottery = lotteryRepository.findByTicket(request.ticket());
        Lottery lottery;
        if(optionalLottery.isPresent()){
            lottery = optionalLottery.get();
            if (lottery.getPrice().equals(request.price())) {
                lottery.setAmount(lottery.getAmount()+ request.amount());
                lotteryRepository.save(lottery);
            } else {
                throw new BadRequestException("Price is not equal to the same price");
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
