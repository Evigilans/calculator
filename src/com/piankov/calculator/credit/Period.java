package com.piankov.calculator.credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.piankov.calculator.constant.StringConstant.DATE_PATTERN;

public class Period {
    private LocalDate startOfPeriod;
    private LocalDate endOfPeriod;
    private BigDecimal startSum;
    private BigDecimal sumWithPercent;
    private BigDecimal sumAfterMonthPayment;

    public LocalDate getStartOfPeriod() {
        return startOfPeriod;
    }

    public void setStartOfPeriod(LocalDate startOfPeriod) {
        this.startOfPeriod = startOfPeriod;
    }

    public LocalDate getEndOfPeriod() {
        return endOfPeriod;
    }

    public void setEndOfPeriod(LocalDate endOfPeriod) {
        this.endOfPeriod = endOfPeriod;
    }

    public BigDecimal getStartSum() {
        return startSum;
    }

    public void setStartSum(BigDecimal startSum) {
        this.startSum = startSum;
    }

    public BigDecimal getSumWithPercent() {
        return sumWithPercent;
    }

    public void setSumWithPercent(BigDecimal sumWithPercent) {
        this.sumWithPercent = sumWithPercent;
    }

    public BigDecimal getSumAfterMonthPayment() {
        return sumAfterMonthPayment;
    }

    public void setSumAfterMonthPayment(BigDecimal sumAfterMonthPayment) {
        this.sumAfterMonthPayment = sumAfterMonthPayment;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Period period = (Period) object;

        if (startOfPeriod != null ? !startOfPeriod.equals(period.startOfPeriod) : period.startOfPeriod != null)
            return false;
        if (endOfPeriod != null ? !endOfPeriod.equals(period.endOfPeriod) : period.endOfPeriod != null) return false;
        if (startSum != null ? !startSum.equals(period.startSum) : period.startSum != null) return false;
        if (sumWithPercent != null ? !sumWithPercent.equals(period.sumWithPercent) : period.sumWithPercent != null)
            return false;
        return sumAfterMonthPayment != null ? sumAfterMonthPayment.equals(period.sumAfterMonthPayment) : period.sumAfterMonthPayment == null;
    }

    @Override
    public int hashCode() {
        int result = startOfPeriod != null ? startOfPeriod.hashCode() : 0;
        result = 31 * result + (endOfPeriod != null ? endOfPeriod.hashCode() : 0);
        result = 31 * result + (startSum != null ? startSum.hashCode() : 0);
        result = 31 * result + (sumWithPercent != null ? sumWithPercent.hashCode() : 0);
        result = 31 * result + (sumAfterMonthPayment != null ? sumAfterMonthPayment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period: [" + startOfPeriod.format(DateTimeFormatter.ofPattern(DATE_PATTERN)) +
                ", " + endOfPeriod.format(DateTimeFormatter.ofPattern(DATE_PATTERN)) +
                ", " + startSum +
                ", " + sumWithPercent +
                ", " + sumAfterMonthPayment +
                ']';
    }
}
