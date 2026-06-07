package com.Pharmacie.enLigne;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Pharmacie.enLigne.models.Pharmacy;
import com.Pharmacie.repositories.PharmacyRepository;
import com.Pharmacie.services.PharmacyService;

@ExtendWith(MockitoExtension.class)
public class PharmacyServiceTest {

    @Mock
    private PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PharmacyService pharmacyService;

    private Pharmacy pharmacyActive1;
    private Pharmacy pharmacyActive2;

    @BeforeEach
    void setUp() {
        pharmacyActive1 = new Pharmacy();
        pharmacyActive1.setId(1L);
        pharmacyActive1.setName("Pharmacie du Centre");
        pharmacyActive1.setZone("Maarif");
        pharmacyActive1.setActive(true);

        pharmacyActive2 = new Pharmacy();
        pharmacyActive2.setId(2L);
        pharmacyActive2.setName("Pharmacie de Garde");
        pharmacyActive2.setZone("Gauthier");
        pharmacyActive2.setActive(true);
    }

    @Test
    void testGetActivePharmacies_ShouldReturnOnlyActiveOnes() {
        // Arrange
        when(pharmacyRepository.findByActiveTrue()).thenReturn(Arrays.asList(pharmacyActive1, pharmacyActive2));

        // Act
        List<Pharmacy> activePharmacies = pharmacyService.getActivePharmacies();

        // Assert
        assertNotNull(activePharmacies);
        assertEquals(2, activePharmacies.size());
        verify(pharmacyRepository, times(1)).findByActiveTrue();
    }
}