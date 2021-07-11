package com.store.rewardservice;

import com.store.rewardservice.model.DataSetDto;
import com.store.rewardservice.model.TransactionVO;
import com.store.rewardservice.service.IRewardCalculationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RewardServiceApplicationTests {
    @Autowired
    private IRewardCalculationService iRewardCalculationService;

    @Test
    void contextLoads() {
    }

    @Test
    public void calculateRewardServiceTest() {
        long rewards = iRewardCalculationService.calculateRewardsPoints(200);
        Assertions.assertEquals(250,rewards);
    }

    @Test
    public void getLast3MonthRewardsTest() {
        List<DataSetDto> awardsList = iRewardCalculationService.getLast3MonthsRewardList("C06");
        Assertions.assertNotNull(awardsList);
        Assertions.assertEquals(2,awardsList.size());
    }

    @Test
    public void getTotalRewardsTest() {
        Long totalRewards = iRewardCalculationService.getTotalRewards("C06");
        Assertions.assertEquals( 850,totalRewards);
    }

    @Test
    public void getAllTransactionTest() {
        List<TransactionVO> allTransaction = iRewardCalculationService.getAllTransaction();
        Assertions.assertNotNull(allTransaction);
        Assertions.assertEquals(11, allTransaction.size());
    }

    @Test
    public void addTransaction() {
        TransactionVO txn = TransactionVO.builder().purchasePrice(110L).purChaseDate("2021-07-10").customerId("C010").build();
        iRewardCalculationService.addTransaction(txn);
    }
}
