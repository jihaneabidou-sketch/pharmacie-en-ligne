package com.Pharmacie.enLigne.controllers;

import com.Pharmacie.enLigne.models.Prescription;
import com.Pharmacie.enLigne.models.PrescriptionStatus;
import com.Pharmacie.services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    // Écran : visily-accueil-patient.jpg
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPatientHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(prescriptionService.getPatientHistory(patientId));
    }

    // Écran : visily-soumettre-ordonnance.jpg
    @PostMapping
    public ResponseEntity<Prescription> submitPrescription(@RequestBody Prescription prescription) {
        return ResponseEntity.ok(prescriptionService.savePrescription(prescription));
    }

    // Écran : visily-tableau-de-bord-pharmacien.jpg
    @GetMapping("/pharmacy/{pharmacyId}/queue")
    public ResponseEntity<List<Prescription>> getPharmacyQueue(
            @PathVariable Long pharmacyId, 
            @RequestParam PrescriptionStatus status) {
        return ResponseEntity.ok(prescriptionService.getPharmacyQueue(pharmacyId, status));
    }

    // Écran : visily-tableau-de-bord-pharmacien.jpg (Les badges compteurs du haut)
    @GetMapping("/pharmacy/{pharmacyId}/stats")
    public ResponseEntity<Map<String, Long>> getPharmacyStats(@PathVariable Long pharmacyId) {
        return ResponseEntity.ok(prescriptionService.getPharmacyStats(pharmacyId));
    }

    // Écran : visily-détail-ordonnance.jpg (Bouton "Commencer la préparation" ou "Marquer Prêt")
    @PutMapping("/{id}/status")
    public ResponseEntity<Prescription> updateStatus(
            @PathVariable Long id,
            @RequestParam PrescriptionStatus status,
            @RequestParam(defaultValue = "0.0") double baseAmount,
            @RequestParam(defaultValue = "false") boolean hasInsurance) {
        return ResponseEntity.ok(prescriptionService.updateStatusAndCalculatePrice(id, status, baseAmount, hasInsurance));
    }
}