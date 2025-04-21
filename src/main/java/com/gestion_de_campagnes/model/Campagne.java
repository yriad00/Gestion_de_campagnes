package com.gestion_de_campagnes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Campagne {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private BigDecimal objectifMontant;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    @OneToMany(mappedBy = "campagne", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();
    

    public Campagne() {
    }
    
    public Campagne(String nom, BigDecimal objectifMontant, LocalDate dateDebut, LocalDate dateFin) {
        this.nom = nom;
        this.objectifMontant = objectifMontant;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getObjectifMontant() {
        return objectifMontant;
    }

    public void setObjectifMontant(BigDecimal objectifMontant) {
        this.objectifMontant = objectifMontant;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
    

}
