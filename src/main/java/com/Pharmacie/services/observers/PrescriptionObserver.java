package com.Pharmacie.services.observers;

import com.Pharmacie.enLigne.models.Prescription;

public interface PrescriptionObserver {
    void onStatusChanged(Prescription prescription);
}