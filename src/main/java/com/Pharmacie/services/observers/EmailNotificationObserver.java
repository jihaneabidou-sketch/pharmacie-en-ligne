package com.Pharmacie.services.observers;

import com.Pharmacie.models.Prescription;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements PrescriptionObserver {
    @Override
    public void onStatusChanged(Prescription prescription) {
        // Log ou simulation d'envoi d'email asynchrone
        System.out.println("[Notification EMAIL] Envoyée à " + prescription.getPatient().getEmail() 
            + " : Votre ordonnance N°" + prescription.getId() + " est désormais : " + prescription.getStatus());
    }
}