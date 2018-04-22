package com.beddoed.loans.service;

import com.beddoed.loans.domain.Lender;
import com.beddoed.loans.domain.RequestedLoanAmountNotAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LendersService {

    public static List<Lender> getAvailableLenders(List<Lender> lenders, double amount) {
        if (lenders == null) {
            throw new IllegalArgumentException("Lenders are null");
        }

        final List<Lender> availableLenders = lenders.stream()
                .filter(lender -> lender.getAvailable() >= amount)
                .collect(Collectors.toList());

        if (availableLenders.isEmpty()) {
            throw new RequestedLoanAmountNotAvailableException("There are no lenders that can pay requested amount");
        }

        return availableLenders;
    }
}
