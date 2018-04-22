package com.beddoed.loans.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Lender {

    private final String name;
    private final double rate;
    private final double available;

    private Lender(String name, double rate, double available) {
        checkNotNullOrEmpty(name);
        checkLessThanZero(rate, "Rate");
        checkLessThanZero(available, "Available");
        this.name = name;
        this.rate = rate;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public double getAvailable() {
        return available;
    }

    private void checkNotNullOrEmpty(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        } else if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    private void checkLessThanZero(double figure, String field) {
        if (figure < 0) {
            throw new IllegalArgumentException(field + " cannot be less than zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Lender lender = (Lender) o;

        return new EqualsBuilder()
                .append(rate, lender.rate)
                .append(available, lender.available)
                .append(name, lender.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(rate)
                .append(available)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Lender{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                ", available=" + available +
                '}';
    }

    public static class LenderBuilder {

        private String name;
        private double rate;
        private double available;

        public static LenderBuilder lenderBuilder() {
            return new LenderBuilder();
        }

        public LenderBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public LenderBuilder setRate(double rate) {
            this.rate = rate;
            return this;
        }

        public LenderBuilder setAvailable(double available) {
            this.available = available;
            return this;
        }

        public Lender build() {
            return new Lender(name, rate, available);
        }
    }
}
