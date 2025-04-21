package com.gestion_de_campagnes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_de_campagnes.dto.DonDTO;
import com.gestion_de_campagnes.model.Campagne;
import com.gestion_de_campagnes.repository.CampagneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CampagneRepository campagneRepository;

    private Campagne campagneActive;
    private Campagne campagneInactive;

    @BeforeEach
    void setUp() {
        // Créer une campagne active (date actuelle entre date début et fin)
        campagneActive = new Campagne();
        campagneActive.setNom("Campagne Test Active");
        campagneActive.setObjectifMontant(new BigDecimal("10000.00"));
        campagneActive.setDateDebut(LocalDate.now().minusDays(10));
        campagneActive.setDateFin(LocalDate.now().plusDays(10));
        campagneActive = campagneRepository.save(campagneActive);

        // Créer une campagne inactive (date fin dans le passé)
        campagneInactive = new Campagne();
        campagneInactive.setNom("Campagne Test Inactive");
        campagneInactive.setObjectifMontant(new BigDecimal("5000.00"));
        campagneInactive.setDateDebut(LocalDate.now().minusDays(30));
        campagneInactive.setDateFin(LocalDate.now().minusDays(10));
        campagneInactive = campagneRepository.save(campagneInactive);
    }

    @Test
    void testEnregistrerDonPourCampagneActive() throws Exception {
        // Création d'un DTO pour le don
        DonDTO donDTO = new DonDTO();
        donDTO.setNomDonateur("Jean Dupont");
        donDTO.setMontant(new BigDecimal("100.00"));

        // Envoi de la requête POST
        mockMvc.perform(post("/api/campagnes/{id}/dons", campagneActive.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomDonateur").value("Jean Dupont"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.montant").value(100.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomCampagne").value("Campagne Test Active"));
    }

    @Test
    void testEnregistrerDonPourCampagneInactive() throws Exception {
        // Création d'un DTO pour le don
        DonDTO donDTO = new DonDTO();
        donDTO.setNomDonateur("Paul Martin");
        donDTO.setMontant(new BigDecimal("50.00"));

        // Envoi de la requête POST pour une campagne inactive
        mockMvc.perform(post("/api/campagnes/{id}/dons", campagneInactive.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Impossible de faire un don à une campagne inactive"));
    }

    @Test
    void testEnregistrerDonAvecMontantInvalide() throws Exception {
        // Création d'un DTO pour le don avec un montant invalide (négatif)
        DonDTO donDTO = new DonDTO();
        donDTO.setNomDonateur("Sophie Lefebvre");
        donDTO.setMontant(new BigDecimal("-10.00"));

        // Envoi de la requête POST avec des données invalides
        mockMvc.perform(post("/api/campagnes/{id}/dons", campagneActive.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.montant").exists());
    }

    @Test
    void testEnregistrerDonPourCampagneInexistante() throws Exception {
        // Création d'un DTO pour le don
        DonDTO donDTO = new DonDTO();
        donDTO.setNomDonateur("Marie Durand");
        donDTO.setMontant(new BigDecimal("75.00"));

        // Envoi de la requête POST avec un ID de campagne inexistant
        mockMvc.perform(post("/api/campagnes/{id}/dons", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(donDTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Campagne non trouvée avec l'ID : 999"));
    }
}
