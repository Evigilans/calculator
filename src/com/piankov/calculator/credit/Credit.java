package com.piankov.calculator.credit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.piankov.calculator.constant.StringConstant.DATE_PATTERN;

public class Credit {
    private LocalDate dateOfIssue;
    private BigDecimal percent;
    private BigDecimal startSum;
    private BigDecimal monthPayment;

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public BigDecimal getStartSum() {
        return startSum;
    }

    public void setStartSum(BigDecimal startSum) {
        this.startSum = startSum;
    }

    public BigDecimal getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(BigDecimal monthPayment) {
        this.monthPayment = monthPayment;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Credit credit = (Credit) object;

        if (dateOfIssue != null ? !dateOfIssue.equals(credit.dateOfIssue) : credit.dateOfIssue != null) return false;
        if (percent != null ? !percent.equals(credit.percent) : credit.percent != null) return false;
        if (startSum != null ? !startSum.equals(credit.startSum) : credit.startSum != null) return false;
        return monthPayment != null ? monthPayment.equals(credit.monthPayment) : credit.monthPayment == null;
    }

    @Override
    public int hashCode() {
        int result = dateOfIssue != null ? dateOfIssue.hashCode() : 0;
        result = 31 * result + (percent != null ? percent.hashCode() : 0);
        result = 31 * result + (startSum != null ? startSum.hashCode() : 0);
        result = 31 * result + (monthPayment != null ? monthPayment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "dateOfIssue=" + dateOfIssue.format(DateTimeFormatter.ofPattern(DATE_PATTERN)) +
                ", percent=" + percent +
                ", startSum=" + startSum +
                ", monthPayment=" + monthPayment +
                '}';
    }
}
