package com.Pharmacie.services.observers;

import com.Pharmacie.models.Prescription;

public interface PrescriptionObserver {
    void onStatusChanged(Prescription prescription);
}