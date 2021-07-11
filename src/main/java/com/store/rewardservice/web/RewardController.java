package com.store.rewardservice.web;

import com.store.rewardservice.model.ResultEntityVO;
import com.store.rewardservice.model.TransactionVO;
import com.store.rewardservice.service.IRewardCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    @Autowired
    private IRewardCalculationService iRewardCalculationService;

    @GetMapping("/getRewards/total/{customerId}")
    public ResponseEntity<ResultEntityVO> getTotalRewards(@PathVariable(value = "customerId", required = true) String customerId) {
        Map<String, Long> rewardResponseMap = new HashMap<>();
        rewardResponseMap.put("rewardPoints", iRewardCalculationService.getTotalRewards(customerId));
        return new ResponseEntity<ResultEntityVO>(new ResultEntityVO(HttpStatus.OK.value(), "Success", rewardResponseMap), HttpStatus.OK);
    }

    @GetMapping("/getRewards/last3Months/{customerId}")
    public ResponseEntity<ResultEntityVO> getLast3MonthsRewards(@PathVariable(value = "customerId", required = true) String customerId) {
        Map<String, Long> rewardResponseMap = new HashMap<>();
        return new ResponseEntity<ResultEntityVO>(new ResultEntityVO(HttpStatus.OK.value(), "Success",
                iRewardCalculationService.getLast3MonthsRewardList(customerId)), HttpStatus.OK);
    }

    @GetMapping("/transaction/getAllTransactions")
    public ResponseEntity<ResultEntityVO> getAllTransactions() {
        return new ResponseEntity<ResultEntityVO>(new ResultEntityVO(HttpStatus.OK.value(), "Success",
                iRewardCalculationService.getAllTransaction()), HttpStatus.OK);
    }

    @PostMapping("/getRewards/purchase/addTransaction")
    public ResponseEntity<ResultEntityVO> addCustomerTransaction(@RequestBody TransactionVO transactionVO) {
        Map<String, Long> rewardResponseMap = new HashMap<>();
        iRewardCalculationService.addTransaction(transactionVO);
        return new ResponseEntity<ResultEntityVO>(new ResultEntityVO(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
    }
}
