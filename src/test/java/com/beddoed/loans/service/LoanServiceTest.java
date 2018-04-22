package com.beddoed.loans.service;

import com.beddoed.loans.domain.Lender;
import com.beddoed.loans.domain.LoanPaymentResult;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.beddoed.loans.domain.Lender.LenderBuilder.lenderBuilder;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.*;

public class LoanServiceTest {

    @Test
    public void shouldCalculateLowestRateInterestOnLoanRepayments() {
        //Given
        final double requestedAmount = 1000;
        final double bestRate = 0.07;
        final BigDecimal presentableBestRate = valueOf(7.0).setScale(1, ROUND_HALF_UP);
        final BigDecimal presentableRequestedAmount = valueOf(requestedAmount).setScale(0, ROUND_HALF_UP);
        List<Lender> lenders = new ArrayList<>();
        lenders.add(lenderBuilder().setName("Bob").setRate(bestRate).setAvailable(2000).build());
        lenders.add(lenderBuilder().setName("Alice").setRate(0.09).setAvailable(3000).build());

        // When
        LoanPaymentResult loanPaymentResult = LoanService.calculateLowestRateLoanAmount(lenders, requestedAmount);

        // Then
        final double expectedMonthly = 30.78;
        final double expectedTotal = 1108.04;
        LoanPaymentResult expected = new LoanPaymentResult(presentableRequestedAmount, presentableBestRate, valueOf(expectedMonthly), valueOf(expectedTotal));
        assertEquals("Loan payment result not what was expected", expected, loanPaymentResult);
    }

}