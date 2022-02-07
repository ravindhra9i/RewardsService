package com.store.rewardservice.service.impl;

import com.store.rewardservice.model.DataSetDto;
import com.store.rewardservice.model.TransactionVO;
import com.store.rewardservice.service.IRewardCalculationService;
import com.store.rewardservice.util.CommonUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RewardCalculationImpl implements IRewardCalculationService {

    public static final LinkedList<DataSetDto> dataSetDtoList = new LinkedList<DataSetDto>();
    private static AtomicLong atomicLongCount = new AtomicLong(11);

    @PostConstruct
    public void initDataSetDtos() {
        dataSetDtoList.add(new DataSetDto(1l, "C01", LocalDate.of(2021, 02,
                02), 100L, 0L));
        dataSetDtoList.add(new DataSetDto(2l, "C02", LocalDate.of(2021, 01,
                11), 200L, 250L));
        dataSetDtoList.add(new DataSetDto(3l, "C03", LocalDate.of(2021, 01,
                13), 200L, 250L));
        dataSetDtoList.add(new DataSetDto(4l, "C04", LocalDate.of(2021, 01,
                14), 200L, 250L));
        dataSetDtoList.add(new DataSetDto(5l, "C05", LocalDate.of(2021, 02,
                15), 300L, 450L));
        dataSetDtoList.add(new DataSetDto(6l, "C06", LocalDate.of(2021, 03,
                16), 400L, 850L));
        dataSetDtoList.add(new DataSetDto(7l, "C06", LocalDate.of(2021, 04,
                17), 100L, 0L));
        dataSetDtoList.add(new DataSetDto(8l, "C06", LocalDate.of(2021, 05,
                18), 100L, 0L));
        dataSetDtoList.add(new DataSetDto(9l, "C06", LocalDate.of(2021, 01,
                19), 100L, 0L));
        dataSetDtoList.add(new DataSetDto(10l, "C06", LocalDate.of(2021, 02,
                20), 100L, 0L));
    }

    @Override
    public long calculateRewardsPoints(long price) {
        if (price >= 50 && price <= 100) {
            return price - 50;
        } else if (price > 100) {
            return (((price - 100) * 2) + 50);
        }
        return 0;
    }

    @Override
    public void addTransaction(TransactionVO transactionVO) {

        String customerId = transactionVO.getCustomerId();
        long rewardPoints = calculateRewardsPoints(transactionVO.getPurchasePrice());
        dataSetDtoList.add(new DataSetDto(atomicLongCount.getAndIncrement(), customerId,
                CommonUtility.converFormattedLocalDate(transactionVO.getPurChaseDate()),
                transactionVO.getPurchasePrice(), rewardPoints));
    }

    @Override
    public List<DataSetDto> getLast3MonthsRewardList(String customerId) {
        LocalDate todaysDate = LocalDate.now();
        LocalDate dateThrreMonthBack = todaysDate.minusMonths(3);
        return this.dataSetDtoList.stream().filter(transaction -> (transaction.getPurChaseDate().isBefore(todaysDate) &&
                transaction.getPurChaseDate().isAfter(dateThrreMonthBack)) && transaction.getCustomerId().equalsIgnoreCase(customerId)).collect(Collectors.toList());

    }

    @Override
    public Long getTotalRewards(String customerId) {
        Optional<Long> reduce = this.dataSetDtoList.stream().filter(transaction ->
                transaction.getCustomerId().equalsIgnoreCase(customerId)).map(element -> element.getRewardsPoints()).reduce(Long::sum);
        return reduce.get();
    }

    @Override
    public List<TransactionVO> getAllTransaction() {
        return this.dataSetDtoList.stream().map(element -> {
            return TransactionVO.builder().customerId(element.getCustomerId())
                    .purChaseDate(String.valueOf(element.getPurChaseDate()))
                    .purchasePrice(element.getPurchasePrice()).rewardsPoint(element.getRewardsPoints()).build();
        }).collect(Collectors.toList());
    }

}
