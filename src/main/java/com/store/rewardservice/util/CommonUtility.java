package com.store.rewardservice.util;

import java.time.LocalDate;

public class CommonUtility {

    public static LocalDate converFormattedLocalDate(String inputDate) {

        return LocalDate.parse(inputDate);

    }
}
