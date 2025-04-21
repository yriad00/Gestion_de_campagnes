package com.gestion_de_campagnes.repository;

import com.gestion_de_campagnes.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    

    List<Donation> findByCampagneId(Long campagneId);
}
