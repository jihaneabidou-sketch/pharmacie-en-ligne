package com.Pharmacie.services.strategies;

import org.springframework.stereotype.Component;

@Component("STANDARD")
public class StandardPriceStrategy implements PriceCalculationStrategy {
    @Override
    public double calculatePrice(double baseAmount) {
        return baseAmount; // Aucun rabais
    }
}