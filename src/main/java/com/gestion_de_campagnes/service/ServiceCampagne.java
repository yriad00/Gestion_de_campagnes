package com.gestion_de_campagnes.service;

import com.gestion_de_campagnes.repository.CampagneRepository;
import com.gestion_de_campagnes.repository.CampagneRepository.CampagneResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceCampagne {
    
    private final CampagneRepository campagneRepository;
    
    @Autowired
    public ServiceCampagne(CampagneRepository campagneRepository) {
        this.campagneRepository = campagneRepository;
    }
    
    /**
     * Récupère toutes les campagnes actives (date actuelle entre date de début et date de fin)
     * @return Liste des campagnes actives avec projection CampagneResume
     */
    @Transactional(readOnly = true)
    public List<CampagneResume> getCampagnesActives() {
        return campagneRepository.findActiveCampagnes(LocalDate.now());
    }
}
