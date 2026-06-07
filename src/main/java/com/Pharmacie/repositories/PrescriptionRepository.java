package com.Pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.enLigne.models.Prescription;
import com.Pharmacie.enLigne.models.PrescriptionStatus;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    List<Prescription> findByPharmacyIdAndStatus(Long pharmacyId, PrescriptionStatus status);

        long countByPharmacyIdAndStatus(Long pharmacyId, PrescriptionStatus status);

}
