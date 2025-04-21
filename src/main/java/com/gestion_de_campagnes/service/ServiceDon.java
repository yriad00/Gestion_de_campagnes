package com.gestion_de_campagnes.service;

import com.gestion_de_campagnes.dto.DonDTO;
import com.gestion_de_campagnes.exception.ResourceNotFoundException;
import com.gestion_de_campagnes.model.Campagne;
import com.gestion_de_campagnes.model.Donation;
import com.gestion_de_campagnes.repository.CampagneRepository;
import com.gestion_de_campagnes.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ServiceDon {
    
    private final DonationRepository donationRepository;
    private final CampagneRepository campagneRepository;
    
    @Autowired
    public ServiceDon(DonationRepository donationRepository, CampagneRepository campagneRepository) {
        this.donationRepository = donationRepository;
        this.campagneRepository = campagneRepository;
    }
    
    /**
     * Enregistre un nouveau don pour une campagne
     * @param campagneId L'ID de la campagne
     * @param donDTO DTO contenant les informations du don
     * @return Le DTO du don enregistré
     */
    @Transactional
    public DonDTO enregistrerDon(Long campagneId, DonDTO donDTO) {
        // Vérifier si la campagne existe
        Campagne campagne = campagneRepository.findById(campagneId)
                .orElseThrow(() -> new ResourceNotFoundException("Campagne non trouvée avec l'ID : " + campagneId));
        
        // Vérifier si la campagne est active
        LocalDate now = LocalDate.now();
        if (now.isBefore(campagne.getDateDebut()) || now.isAfter(campagne.getDateFin())) {
            throw new IllegalStateException("Impossible de faire un don à une campagne inactive");
        }
        
        // Créer un nouvel objet Don
        Donation don = new Donation();
        don.setCampagne(campagne);
        don.setNomDonateur(donDTO.getNomDonateur());
        don.setMontant(donDTO.getMontant());
        don.setDate(LocalDate.now());
        
        // Sauvegarder le don
        don = donationRepository.save(don);
        
        // Convertir et retourner le DTO
        return convertirEnDTO(don);
    }
    
    /**
     * Convertit une entité Donation en DonDTO
     * @param donation L'entité à convertir
     * @return Le DTO correspondant
     */
    public DonDTO convertirEnDTO(Donation donation) {
        return new DonDTO(
                donation.getId(),
                donation.getCampagne().getNom(),
                donation.getNomDonateur(),
                donation.getMontant(),
                donation.getDate()
        );
    }
}
