package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record LotteryRequest(

        @Pattern(regexp = "^[0-9]{6}$")
        String ticket,

        @NotNull
        @Positive
        Integer price,

        @NotNull
        @Positive
        int amount
) {
}
