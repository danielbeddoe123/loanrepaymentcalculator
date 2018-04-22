package com.beddoed.loans.service;

import com.beddoed.loans.domain.Lender;
import com.beddoed.loans.domain.RequestedLoanAmountNotAvailableException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static com.beddoed.loans.domain.Lender.LenderBuilder.lenderBuilder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LendersServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowIllegalArgumentExceptionIfNullLendersWhenGettingAvailableLenders() {
        // Given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Lenders are null");

        // When
        LendersService.getAvailableLenders(null, 1000);
    }

    @Test
    public void shouldReturnLendersThatHaveMoreAvailableThanRequestedAmount() {
        // Given
        final List<Lender> lenders = new ArrayList<>();
        final Lender lender1 = lenderBuilder().setName("Bob").setRate(0.08).setAvailable(100).build();
        final Lender lender2 = lenderBuilder().setName("Alice").setRate(0.12).setAvailable(200).build();
        final Lender lender3 = lenderBuilder().setName("Tim").setRate(0.01).setAvailable(25).build();

        lenders.add(lender1);
        lenders.add(lender2);
        lenders.add(lender3);

        // When
        final List<Lender> availableLenders = LendersService.getAvailableLenders(lenders, 150);

        // Then
        final ArrayList<Lender> expected = new ArrayList<>();
        expected.add(lender2);
        assertEquals("Expected lenders not satisfied", expected, availableLenders);
    }

    @Test
    public void shouldReturnLendersThatHaveEqualAvailableThanRequestedAmount() {
        // Given
        final List<Lender> lenders = new ArrayList<>();
        final Lender lender1 = lenderBuilder().setName("Bob").setRate(0.08).setAvailable(100).build();
        final Lender lender2 = lenderBuilder().setName("Alice").setRate(0.12).setAvailable(200).build();
        final Lender lender3 = lenderBuilder().setName("Tim").setRate(0.01).setAvailable(150).build();


        lenders.add(lender1);
        lenders.add(lender2);
        lenders.add(lender3);

        // When
        final List<Lender> availableLenders = LendersService.getAvailableLenders(lenders, 150);

        // Then
        final ArrayList<Lender> expected = new ArrayList<>();
        expected.add(lender2);
        expected.add(lender3);
        assertEquals("Expected lenders not satisfied", expected, availableLenders);
    }

    @Test
    public void shouldThrowExceptionIfNoLendersHaveAvailableRequestedAmount() {
        // Given
        final List<Lender> lenders = new ArrayList<>();
        final Lender lender1 = lenderBuilder().setName("Bob").setRate(0.08).setAvailable(100).build();
        final Lender lender2 = lenderBuilder().setName("Alice").setRate(0.12).setAvailable(200).build();
        final Lender lender3 = lenderBuilder().setName("Tim").setRate(0.01).setAvailable(150).build();

        lenders.add(lender1);
        lenders.add(lender2);
        lenders.add(lender3);

        // Expect
        expectedException.expect(RequestedLoanAmountNotAvailableException.class);
        expectedException.expectMessage("There are no lenders that can pay requested amount");

        // When
        LendersService.getAvailableLenders(lenders, 300);
    }

}