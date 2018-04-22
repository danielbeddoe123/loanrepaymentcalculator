package com.beddoed.loans.input;

import static java.lang.Double.parseDouble;
import static java.lang.String.format;

public class BasicInputValidator {

    private static double MIN_LOAN_AMOUNT = 1000;
    private static double MAX_LOAN_AMOUNT = 15000;
    private static double INCREMENT_AMOUNT = 100;

    public static void validate(String[] args) {
        if (args == null || args.length != 2) {
            throw new IllegalArgumentException("Exactly 2 arguments required");
        }

        try {
            final double loanAmount = parseDouble(args[1]);
            validateLoanAmount(loanAmount);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The second argument must be the loan amount as a number");
        }
    }

    private static void validateLoanAmount(double loanAmount) {
        if (loanAmount < MIN_LOAN_AMOUNT) {
            throw new IllegalArgumentException(format("The second argument must be the loan amount that is greater than or equal to %f", MIN_LOAN_AMOUNT));
        }
        if (loanAmount > MAX_LOAN_AMOUNT) {
            throw new IllegalArgumentException(format("The second argument must be the loan amount that is less than or equal to %f", MAX_LOAN_AMOUNT));
        }

        if (loanAmount % INCREMENT_AMOUNT != 0) {
            throw new IllegalArgumentException(format("The second argument must be the loan amount which must be in increments of %f", INCREMENT_AMOUNT));
        }
    }
}
