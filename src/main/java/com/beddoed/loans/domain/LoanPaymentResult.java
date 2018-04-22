package com.beddoed.loans.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;

public class LoanPaymentResult {

    private final BigDecimal requestedAmount;
    private final BigDecimal rate;
    private final BigDecimal monthlyRepaymentAmount;
    private final BigDecimal totalRepaymentAmount;

    public LoanPaymentResult(BigDecimal requestedAmount, BigDecimal rate, BigDecimal monthlyRepaymentAmount, BigDecimal totalRepaymentAmount) {
        this.requestedAmount = requestedAmount;
        this.rate = rate;
        this.monthlyRepaymentAmount = monthlyRepaymentAmount;
        this.totalRepaymentAmount = totalRepaymentAmount;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getMonthlyRepaymentAmount() {
        return monthlyRepaymentAmount;
    }

    public BigDecimal getTotalRepaymentAmount() {
        return totalRepaymentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LoanPaymentResult that = (LoanPaymentResult) o;

        return new EqualsBuilder()
                .append(requestedAmount, that.requestedAmount)
                .append(rate, that.rate)
                .append(monthlyRepaymentAmount, that.monthlyRepaymentAmount)
                .append(totalRepaymentAmount, that.totalRepaymentAmount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(requestedAmount)
                .append(rate)
                .append(monthlyRepaymentAmount)
                .append(totalRepaymentAmount)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "LoanPaymentResult{" +
                "requestedAmount=" + requestedAmount +
                ", rate=" + rate +
                ", monthlyRepaymentAmount=" + monthlyRepaymentAmount +
                ", totalRepaymentAmount=" + totalRepaymentAmount +
                '}';
    }
}
