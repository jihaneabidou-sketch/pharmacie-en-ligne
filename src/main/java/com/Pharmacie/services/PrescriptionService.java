package com.Pharmacie.services;

import com.Pharmacie.models.Prescription;
import com.Pharmacie.models.PrescriptionStatus;
import com.Pharmacie.repositories.PrescriptionRepository;
import com.Pharmacie.services.observers.PrescriptionObserver;
import com.Pharmacie.services.strategies.PriceCalculationStrategy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final Map<String, PriceCalculationStrategy> pricingStrategies;
    private final List<PrescriptionObserver> observers;

    // Injection automatique de toutes les stratégies et observateurs par Spring
    public PrescriptionService(PrescriptionRepository prescriptionRepository, 
                               Map<String, PriceCalculationStrategy> pricingStrategies,
                               List<PrescriptionObserver> observers) {
        this.prescriptionRepository = prescriptionRepository;
        this.pricingStrategies = pricingStrategies;
        this.observers = observers;
    }

    // Fonctionnalité Pharmacien : Prendre en charge et mettre à jour le statut
    public Prescription updateStatus(Long prescriptionId, PrescriptionStatus newStatus) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Ordonnance introuvable"));

        prescription.setStatus(newStatus);
        Prescription updated = prescriptionRepository.save(prescription);

        // Déclenchement automatique du Pattern Observer
        observers.forEach(observer -> observer.onStatusChanged(updated));

        return updated;
    }

    // Fonctionnalité Pharmacien : Saisie du montant brut + Calcul final avec Strategy
    public Prescription finalizeOrder(Long prescriptionId, double baseAmount, String strategyType) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Ordonnance introuvable"));

        // Sélection dynamique de la stratégie (STANDARD, REDUCED, INSURANCE)
        PriceCalculationStrategy strategy = pricingStrategies.getOrDefault(strategyType.toUpperCase(), 
                pricingStrategies.get("STANDARD"));

        double finalPrice = strategy.calculatePrice(baseAmount);
        prescription.setTotalAmount(finalPrice);
        prescription.setStatus(PrescriptionStatus.PRETE);

        Prescription saved = prescriptionRepository.save(prescription);

        // Notification automatique de fin de préparation
        observers.forEach(observer -> observer.onStatusChanged(saved));

        return saved;
    }
}