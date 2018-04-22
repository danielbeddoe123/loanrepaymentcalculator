package com.beddoed.loans.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.beddoed.loans.domain.Lender.LenderBuilder.lenderBuilder;
import static org.junit.Assert.*;

public class LowestRateLenderComparatorTest {

    @Test
    public void shouldSortByLowestRateFirst() {
        // Given
        final Lender lender1 = lenderBuilder().setName("Bob").setRate(0.05).setAvailable(1000).build();
        final Lender lender2 = lenderBuilder().setName("Alice").setRate(0.1).setAvailable(10000).build();
        final Lender lender3 = lenderBuilder().setName("Tim").setRate(0.01).setAvailable(200).build();

        final List<Lender> lenders = new ArrayList<>();
        lenders.add(lender1);
        lenders.add(lender2);
        lenders.add(lender3);

        // When
        lenders.sort(new LowestRateLenderComparator());

        // Then
        assertEquals("Not in expected position", lender3, lenders.get(0));
        assertEquals("Not in expected position", lender1, lenders.get(1));
        assertEquals("Not in expected position", lender2, lenders.get(2));
    }

    @Test
    public void shouldSortByHighestAvailableIfEqualLowRates() {
        // Given
        final Lender lender1 = lenderBuilder().setName("Bob").setRate(0.05).setAvailable(1000).build();
        final Lender lender2 = lenderBuilder().setName("Alice").setRate(0.01).setAvailable(200).build();
        final Lender lender3 = lenderBuilder().setName("Tim").setRate(0.01).setAvailable(10000).build();

        final List<Lender> lenders = new ArrayList<>();
        lenders.add(lender1);
        lenders.add(lender2);
        lenders.add(lender3);

        // When
        lenders.sort(new LowestRateLenderComparator());

        // Then
        assertEquals("Not in expected position", lender3, lenders.get(0));
        assertEquals("Not in expected position", lender2, lenders.get(1));
        assertEquals("Not in expected position", lender1, lenders.get(2));
    }

}