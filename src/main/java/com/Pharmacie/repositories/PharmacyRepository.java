package com.Pharmacie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacie.enLigne.models.Pharmacy;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findByZoneIgnoreCase(String zone);
    List<Pharmacy> findByNameContainingIgnoreCase(String name);
    List<Pharmacy> findByActiveTrue();
}