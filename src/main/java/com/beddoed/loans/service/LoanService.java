package com.beddoed.loans.service;

import com.beddoed.loans.domain.Lender;
import com.beddoed.loans.domain.LoanPaymentResult;
import com.beddoed.loans.domain.LowestRateLenderComparator;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

public class LoanService {

    private static final int LOAN_MONTHS = 36;
    private static final int MONTHS_PER_YEAR = 12;
    private static int LOAN_RATE_NUM_DECIMAL_PLACES = 1;
    private static int REPAYMENT_AMOUNT_NUM_DECIMAL_PLACES = 2;
    private static int REQUESTED_AMOUNT_DECIMAL_PLACES = 0;

    public static LoanPaymentResult calculateLowestRateLoanAmount(List<Lender> lenders, double requestedAmount) {
        lenders.sort(new LowestRateLenderComparator());
        final Lender bestRateLender = lenders.get(0);
        final double bestRate = bestRateLender.getRate();
        double monthlyRate = Math.pow(1 + bestRate, (double)1/MONTHS_PER_YEAR)-1;
        double monthlyRepayment = (monthlyRate * requestedAmount) / (1 - Math.pow(1 + monthlyRate, -LOAN_MONTHS));
        double totalRepayment = monthlyRepayment * LOAN_MONTHS;

        final BigDecimal originalRequestedAmount = valueOf(requestedAmount).setScale(REQUESTED_AMOUNT_DECIMAL_PLACES, ROUND_HALF_UP);
        final BigDecimal monthlyRepaymentAmount = valueOf(monthlyRepayment).setScale(REPAYMENT_AMOUNT_NUM_DECIMAL_PLACES, ROUND_HALF_UP);
        final BigDecimal rate = valueOf(bestRate *100).setScale(LOAN_RATE_NUM_DECIMAL_PLACES, ROUND_HALF_UP);
        final BigDecimal totalRepaymentAmount = valueOf(totalRepayment).setScale(REPAYMENT_AMOUNT_NUM_DECIMAL_PLACES, ROUND_HALF_UP);

        return new LoanPaymentResult(
                originalRequestedAmount,
                rate,
                monthlyRepaymentAmount,
                totalRepaymentAmount);
    }
}
