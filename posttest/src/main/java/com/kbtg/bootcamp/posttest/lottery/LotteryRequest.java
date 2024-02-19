package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

record LotteryRequest(
        @NotNull
        @Size(min = 6, max = 6)
        String ticket,

        @NotNull
        @Positive
        Double price,

        @NotNull
        @Positive
        Integer amount
) {
}
