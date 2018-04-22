package com.beddoed.loans.domain;

public class RequestedLoanAmountNotAvailableException extends RuntimeException {
    public RequestedLoanAmountNotAvailableException(String message) {
        super(message);
    }
}
