package com.store.rewardservice.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSetDto {
    private Long transactionId;
    private String customerId;
    private LocalDate purChaseDate;
    private Long purchasePrice;
    private Long rewardsPoints;

}
