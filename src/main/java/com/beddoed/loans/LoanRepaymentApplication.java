package com.beddoed.loans;

import com.beddoed.loans.domain.Lender;
import com.beddoed.loans.domain.LoanPaymentResult;

import java.io.FileNotFoundException;
import java.util.List;

import static com.beddoed.loans.input.BasicInputValidator.validate;
import static com.beddoed.loans.input.LendersFileReader.readLenders;
import static com.beddoed.loans.service.LendersService.getAvailableLenders;
import static com.beddoed.loans.service.LoanService.calculateLowestRateLoanAmount;
import static java.lang.Double.parseDouble;

public class LoanRepaymentApplication {

    private static final String POUND_SIGN = "\u00a3";
    private static final String PERCENT_SIGN = "\u0025";

    public static void main(String[] args) throws FileNotFoundException {
        validate(args);
        final String fileName = args[0];
        final List<Lender> lenders = readLenders(fileName);
        final double requestedAmount = parseDouble(args[1]);
        final List<Lender> availableLenders = getAvailableLenders(lenders, requestedAmount);
        final LoanPaymentResult loanPaymentResult = calculateLowestRateLoanAmount(availableLenders, requestedAmount);

        System.out.println("Requested amount: " + POUND_SIGN + loanPaymentResult.getRequestedAmount());
        System.out.println("Rate: " + loanPaymentResult.getRate() + PERCENT_SIGN);
        System.out.println("Monthly repayment: " + POUND_SIGN + loanPaymentResult.getMonthlyRepaymentAmount());
        System.out.println("Total repayment: " + POUND_SIGN + loanPaymentResult.getTotalRepaymentAmount());
    }
}
