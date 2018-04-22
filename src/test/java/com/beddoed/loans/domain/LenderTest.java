package com.beddoed.loans.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.beddoed.loans.domain.Lender.LenderBuilder.lenderBuilder;

public class LenderTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfNameIsNull() {
        // Given

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Name cannot be null");

        // When
        lenderBuilder().setName(null).build();
    }

    @Test
    public void shouldThrowExceptionIfNameIsEmpty() {
        // Given

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Name cannot be empty");

        // When
        lenderBuilder().setName("  ").build();
    }

    @Test
    public void shouldThrowExceptionIfRateIsLessThanZero() {
        // Given

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Rate cannot be less than zero");

        // When
        lenderBuilder().setName("Bob").setRate(-0.01).build();
    }

    @Test
    public void shouldThrowExceptionIfAvailableIsLessThanZero() {
        // Given

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Available cannot be less than zero");

        // When
        lenderBuilder().setName("Bob").setRate(0.01).setAvailable(-0.01).build();
    }

    @Test
    public void shouldAcceptValidLender() {
        // Given

        // When
        lenderBuilder().setName("Bob").setRate(0.01).setAvailable(100).build();
    }

}