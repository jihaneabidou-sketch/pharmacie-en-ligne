package com.Pharmacie.services.strategies;

import org.springframework.stereotype.Component;

@Component("INSURANCE")
public class InsurancePriceStrategy implements PriceCalculationStrategy {
    @Override
    public double calculatePrice(double baseAmount) {
        return baseAmount * 0.30; // Le patient ne paie que le ticket modérateur (30%)
    }
}