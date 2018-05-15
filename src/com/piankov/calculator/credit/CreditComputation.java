package com.piankov.calculator.credit;

import com.piankov.calculator.constant.StringConstant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreditComputation {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final int PERCENT_ROUNDING_SCALE = 6;
    private static final int USER_ROUNDING_SCALE = 2;
    private static final int ONE_DAY = 1;
    private static final int MONTH = 29;

    public List<Period> compute(Map<String, String[]> map) {
        Credit credit = buildCredit(map);
        Period period = firstPeriod(credit);

        List<Period> periods = new ArrayList<>();

        for (int i = 0; i < 60; i++) {
            periods.add(period);
            period = nextPeriod(credit, period);

            if (isCreditPaidOut(period)) {
                period.setSumAfterMonthPayment(BigDecimal.ZERO);
                periods.add(period);
                break;
            }
        }

        return periods;
    }

    private Credit buildCredit(Map<String, String[]> map) {
        Credit credit = new Credit();

        credit.setDateOfIssue(LocalDate.parse(map.get("date")[0], DateTimeFormatter.ofPattern(StringConstant.DATE_PATTERN)));
        credit.setStartSum(new BigDecimal(map.get("start")[0]));
        credit.setPercent(new BigDecimal(map.get("percent")[0]));
        credit.setMonthPayment(new BigDecimal(map.get("payment")[0]));

        return credit;
    }

    private Period firstPeriod(Credit credit) {
        Period firstPeriod = new Period();

        firstPeriod.setStartOfPeriod(credit.getDateOfIssue());
        firstPeriod.setEndOfPeriod(credit.getDateOfIssue().plusDays(MONTH));
        firstPeriod.setStartSum(credit.getStartSum());
        firstPeriod.setSumWithPercent(countPercent(credit.getStartSum(), credit.getPercent()));
        firstPeriod.setSumAfterMonthPayment(firstPeriod.getSumWithPercent().subtract(credit.getMonthPayment()));

        return firstPeriod;
    }

    private Period nextPeriod(Credit credit, Period previousPeriod) {
        Period nextPeriod = new Period();

        LocalDate startOfPeriod = previousPeriod.getEndOfPeriod().plusDays(ONE_DAY);
        BigDecimal sumWithPercent = countPercent(previousPeriod.getSumAfterMonthPayment(), credit.getPercent());

        nextPeriod.setStartOfPeriod(startOfPeriod);
        nextPeriod.setEndOfPeriod(startOfPeriod.plusDays(MONTH));
        nextPeriod.setStartSum(previousPeriod.getSumAfterMonthPayment());
        nextPeriod.setSumWithPercent(sumWithPercent);
        nextPeriod.setSumAfterMonthPayment(sumWithPercent.subtract(credit.getMonthPayment()));

        return nextPeriod;
    }

    private boolean isCreditPaidOut(Period lastPeriod) {
        int comparison = lastPeriod.getSumAfterMonthPayment().compareTo(BigDecimal.ZERO);
        return comparison < 1;
    }

    private BigDecimal countPercent(BigDecimal sum, BigDecimal percent) {
        BigDecimal multiplier = percent.divide(ONE_HUNDRED, PERCENT_ROUNDING_SCALE, RoundingMode.HALF_UP).add(BigDecimal.ONE);
        return sum.multiply(multiplier).setScale(USER_ROUNDING_SCALE, RoundingMode.HALF_UP);
    }
}
