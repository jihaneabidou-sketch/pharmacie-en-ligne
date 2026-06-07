package com.Pharmacie.enLigne.controllers;

import com.Pharmacie.enLigne.models.Pharmacy;
import com.Pharmacie.repositories.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacies")
@CrossOrigin(origins = "*")
public class PharmacyController {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    // Liste toutes les pharmacies actives à proximité
    @GetMapping("/active")
    public ResponseEntity<List<Pharmacy>> getActivePharmacies() {
        return ResponseEntity.ok(pharmacyRepository.findByActiveTrue());
    }

    // Recherche par zone (ex: "Paris")
    @GetMapping("/search")
    public ResponseEntity<List<Pharmacy>> searchByZone(@RequestParam String zone) {
        return ResponseEntity.ok(pharmacyRepository.findByZoneIgnoreCase(zone));
    }
}