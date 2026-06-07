package com.Pharmacie.services.observers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.Pharmacie.enLigne.models.Prescription;

@Component
public class EmailNotificationObserver implements PrescriptionObserver {

    @Async // Traitement asynchro
    @Override
    public void onStatusChanged(Prescription prescription) {
        System.out.println("[OBSERVER NOTIFICATION] Email envoyé au patient " + prescription.getPatient().getFullName()
                + " : Votre ordonnance #" + prescription.getId() + " est désormais : " + prescription.getStatus());
    }
}