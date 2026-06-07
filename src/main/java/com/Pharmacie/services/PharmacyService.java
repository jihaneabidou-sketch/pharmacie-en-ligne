package com.Pharmacie.services;

import com.Pharmacie.enLigne.models.Pharmacy;
import com.Pharmacie.repositories.PharmacyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    // Injection par constructeur
    public PharmacyService(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    // Récupérer toutes les pharmacies actives
    public List<Pharmacy> getActivePharmacies() {
        return pharmacyRepository.findByActiveTrue();
    }

    // Rechercher les pharmacies par zone (sans faire attention aux majuscules/minuscules)
    public List<Pharmacy> getPharmaciesByZone(String zone) {
        return pharmacyRepository.findByZoneIgnoreCase(zone);
    }
}