package com.gestion_de_campagnes.service;

import com.gestion_de_campagnes.repository.CampagneRepository;
import com.gestion_de_campagnes.repository.CampagneRepository.CampagneResume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceCampagneTest {

    @Mock
    private CampagneRepository campagneRepository;

    @Mock
    private CampagneResume campagneResume1;

    @Mock
    private CampagneResume campagneResume2;

    private ServiceCampagne serviceCampagne;

    @BeforeEach
    void setUp() {
        serviceCampagne = new ServiceCampagne(campagneRepository);
        
        // Configuration des mocks
        when(campagneResume1.getId()).thenReturn(1L);
        when(campagneResume1.getNom()).thenReturn("Campagne 1");
        when(campagneResume1.getObjectifMontant()).thenReturn(new BigDecimal("1000.00"));
        
        when(campagneResume2.getId()).thenReturn(2L);
        when(campagneResume2.getNom()).thenReturn("Campagne 2");
        when(campagneResume2.getObjectifMontant()).thenReturn(new BigDecimal("2000.00"));
    }

    @Test
    void testGetCampagnesActives() {
        // Arrange
        List<CampagneResume> expectedCampagnes = Arrays.asList(campagneResume1, campagneResume2);
        when(campagneRepository.findActiveCampagnes(any(LocalDate.class))).thenReturn(expectedCampagnes);

        // Act
        List<CampagneResume> actualCampagnes = serviceCampagne.getCampagnesActives();

        // Assert
        assertThat(actualCampagnes).hasSize(2);
        assertThat(actualCampagnes).containsExactlyElementsOf(expectedCampagnes);
        
        // Vérification détaillée du premier élément
        assertThat(actualCampagnes.get(0).getId()).isEqualTo(1L);
        assertThat(actualCampagnes.get(0).getNom()).isEqualTo("Campagne 1");
        assertThat(actualCampagnes.get(0).getObjectifMontant()).isEqualTo(new BigDecimal("1000.00"));
    }
}
