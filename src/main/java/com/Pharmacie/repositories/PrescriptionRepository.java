package com.Pharmacie.repositories;

import com.Pharmacie.models.Prescription;
import com.Pharmacie.models.PrescriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    // Pour l'historique du Patient (trié du plus récent au plus ancien)
    List<Prescription> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    
    // Pour la file d'attente du Pharmacien (filtrer par pharmacie et par statut, ex: RECUE)
    List<Prescription> findByPharmacyIdAndStatus(Long pharmacyId, PrescriptionStatus status);
}