package com.Pharmacie.repositories;

import com.Pharmacie.models.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findByZoneIgnoreCase(String zone);
    List<Pharmacy> findByNameContainingIgnoreCase(String name);
    List<Pharmacy> findByActiveTrue();
}