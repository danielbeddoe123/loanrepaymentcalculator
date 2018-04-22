package com.beddoed.loans.input;

import com.beddoed.loans.input.BasicInputValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BasicInputValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfNullArgumentsSupplied() {
        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Exactly 2 arguments required");

        // When
        BasicInputValidator.validate(null);
    }

    @Test
    public void shouldThrowExceptionIfNoArgumentsSupplied() {
        // Given
        final String[] args = {};

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Exactly 2 arguments required");

        // When
        BasicInputValidator.validate(args);
    }

    @Test
    public void shouldThrowExceptionIfTooManyArgumentsSupplied() {
        // Given
        final String[] args = {"abc", "def", "ghi"};

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Exactly 2 arguments required");

        // When
        BasicInputValidator.validate(args);
    }

    @Test
    public void shouldThrowExceptionIfSecondArgumentIsNotANumber() {
        // Given
        final String[] args = {"abc", "def"};

        // Expect
        expectedException.expect(NumberFormatException.class);
        expectedException.expectMessage("The second argument must be the loan amount as a number");

        // When
        BasicInputValidator.validate(args);
    }

    @Test
    public void shouldThrowExceptionIfSecondArgumentIsLessThan1000() {
        // Given
        final String[] args = {"abc", "999"};

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The second argument must be the loan amount that is greater than or equal to 1000");

        // When
        BasicInputValidator.validate(args);
    }

    @Test
    public void shouldThrowExceptionIfRequestedLoanAmountIsGreaterThan15000() {
        // Given
        final String[] args = {"abc", "15001"};

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The second argument must be the loan amount that is less than or equal to 15000");

        // When
        BasicInputValidator.validate(args);
    }

    @Test
    public void shouldThrowExceptionIfRequestedLoanAmountIsNotInIncrementsOf100() {
        // Given
        final String[] args = {"abc", "1559"};

        // Expect
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The second argument must be the loan amount which must be in increments of 100");

        // When
        BasicInputValidator.validate(args);
    }

    @Test
    public void shouldValidateValidInput() {
        // Given
        final String[] args = {"abc", "10000"};

        // When
        BasicInputValidator.validate(args);
    }
}
