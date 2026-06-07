package com.Pharmacie.services;

import com.Pharmacie.enLigne.models.Prescription;
import com.Pharmacie.enLigne.models.PrescriptionStatus;
import com.Pharmacie.repositories.PrescriptionRepository;
import com.Pharmacie.services.observers.EmailNotificationObserver;
import com.Pharmacie.services.strategies.PriceCalculationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private EmailNotificationObserver notificationObserver;

    @Autowired
    @Qualifier("STANDARD")
    private PriceCalculationStrategy standardStrategy;

    @Autowired
    @Qualifier("INSURANCE")
    private PriceCalculationStrategy insuranceStrategy;

    // Accueil Patient (`visily-accueil-patient.jpg`)
    public List<Prescription> getPatientHistory(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    // File d'attente Pharmacien (`visily-tableau-de-bord-pharmacien.jpg`)
    public List<Prescription> getPharmacyQueue(Long pharmacyId, PrescriptionStatus status) {
        return prescriptionRepository.findByPharmacyIdAndStatus(pharmacyId, status);
    }

    // Soumettre une ordonnance (`visily-soumettre-ordonnance.jpg`)
    public Prescription savePrescription(Prescription prescription) {
        prescription.setStatus(PrescriptionStatus.RECUE);
        return prescriptionRepository.save(prescription);
    }

    // Traitement de l'ordonnance (`visily-détail-ordonnance.jpg`)
    public Prescription updateStatusAndCalculatePrice(Long prescriptionId, PrescriptionStatus newStatus, double baseAmount, boolean hasInsurance) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Ordonnance introuvable"));

        prescription.setStatus(newStatus);

        // Application du pattern Strategy lors de la validation finale
        if (newStatus == PrescriptionStatus.PRETE) {
            PriceCalculationStrategy strategy = hasInsurance ? insuranceStrategy : standardStrategy;
            prescription.setTotalAmount(strategy.calculatePrice(baseAmount));
        }

        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        // Alerte via l'Observer
        notificationObserver.onStatusChanged(updatedPrescription);

        return updatedPrescription;
    }

    // Compteurs du tableau de bord pharmacien
    public Map<String, Long> getPharmacyStats(Long pharmacyId) {
        return Map.of(
            "RECUE", prescriptionRepository.countByPharmacyIdAndStatus(pharmacyId, PrescriptionStatus.RECUE),
            "EN_PREPARATION", prescriptionRepository.countByPharmacyIdAndStatus(pharmacyId, PrescriptionStatus.EN_PREPARATION),
            "PRETE", prescriptionRepository.countByPharmacyIdAndStatus(pharmacyId, PrescriptionStatus.PRETE)
        );
    }
}