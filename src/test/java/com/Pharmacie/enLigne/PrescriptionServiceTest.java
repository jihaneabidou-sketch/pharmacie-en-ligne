package com.Pharmacie.enLigne;

import com.Pharmacie.enLigne.models.Prescription;
import com.Pharmacie.enLigne.models.PrescriptionStatus;
import com.Pharmacie.enLigne.models.User;
import com.Pharmacie.repositories.PrescriptionRepository;
import com.Pharmacie.services.PrescriptionService;
import com.Pharmacie.services.observers.EmailNotificationObserver;
import com.Pharmacie.services.strategies.PriceCalculationStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)

@Service
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private EmailNotificationObserver notificationObserver;

    @Mock(name = "insuranceStrategy")
    private PriceCalculationStrategy insuranceStrategy;

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Test
    public void testUpdateStatusToPreteWithInsurance() {
        // Given
        User patient = new User("jean.dupont@email.com", "password", "Jean Dupont", "0612345678", com.Pharmacie.enLigne.models.Role.ROLE_PATIENT);
        Prescription prescription = new Prescription(patient, null, "/uploads/ord.pdf");
        prescription.setId(1L);

        Mockito.when(prescriptionRepository.findById(1L)).thenReturn(Optional.of(prescription));
        Mockito.when(insuranceStrategy.calculatePrice(100.0)).thenReturn(30.0);
        Mockito.when(prescriptionRepository.save(any(Prescription.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // When
        Prescription result = prescriptionService.updateStatusAndCalculatePrice(1L, PrescriptionStatus.PRETE, 100.0, true);

        // Then
        assertEquals(PrescriptionStatus.PRETE, result.getStatus());
        assertEquals(30.0, result.getTotalAmount());
        Mockito.verify(notificationObserver, Mockito.times(1)).onStatusChanged(result);
    }
}