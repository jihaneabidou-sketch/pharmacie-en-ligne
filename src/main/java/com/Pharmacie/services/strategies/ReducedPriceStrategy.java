package com.Pharmacie.services.strategies;

import org.springframework.stereotype.Component;

@Component("REDUCED")
public class ReducedPriceStrategy implements PriceCalculationStrategy {
    @Override
    public double calculatePrice(double baseAmount) {
        return baseAmount * 0.85; // 15% de réduction
    }
}