package com.gestion_de_campagnes.repository;

import com.gestion_de_campagnes.model.Campagne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampagneRepository extends JpaRepository<Campagne, Long> {
    
    /**
     * Trouve les campagnes actives (date actuelle entre date de début et date de fin)
     */
    @Query("SELECT c FROM Campagne c WHERE :currentDate BETWEEN c.dateDebut AND c.dateFin")
    List<CampagneResume> findActiveCampagnes(LocalDate currentDate);
    
    /**
     * Projection pour résumé de campagne
     */
    interface CampagneResume {
        Long getId();
        String getNom();
        java.math.BigDecimal getObjectifMontant();
    }
}
