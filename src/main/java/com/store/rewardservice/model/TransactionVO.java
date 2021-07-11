package com.store.rewardservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionVO {
    @JsonIgnore
    private Long id;
    private String customerId;
    private String purChaseDate;
    private Long purchasePrice;
    private Long rewardsPoint;


}
