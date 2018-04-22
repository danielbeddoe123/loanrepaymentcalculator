package com.beddoed.loans.domain;

import java.util.Comparator;

public class LowestRateLenderComparator implements Comparator<Lender> {

    @Override
    public int compare(Lender o1, Lender o2) {
        if (o1.getRate() - o2.getRate() < 0) {
            return -1;
        } else if (o1.getRate() - o2.getRate() > 0) {
            return 1;
        }

        if (o1.getAvailable() - o2.getAvailable() < 0) {
            return 1;
        } else if (o1.getAvailable() - o2.getAvailable() > 0) {
            return -1;
        }
        return 0;
    }
}
