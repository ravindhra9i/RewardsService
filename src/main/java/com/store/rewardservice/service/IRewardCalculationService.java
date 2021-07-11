package com.store.rewardservice.service;

import com.store.rewardservice.model.DataSetDto;
import com.store.rewardservice.model.TransactionVO;

import java.util.List;

public interface IRewardCalculationService {
    long calculateRewardsPoints(long price);
    void addTransaction(TransactionVO transactionVO);
    List<DataSetDto> getLast3MonthsRewardList(String customerId);
    Long getTotalRewards(String customerId);
    List<TransactionVO> getAllTransaction();
}
