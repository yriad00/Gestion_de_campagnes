package com.gestion_de_campagnes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DonDTO {
    
    private Long id;
    
    private String nomCampagne;
    private String nomDonateur;
    private BigDecimal montant;
    
    private LocalDate date;
    
    // Constructors
    public DonDTO() {
    }
    
    public DonDTO(Long id, String nomCampagne, String nomDonateur, BigDecimal montant, LocalDate date) {
        this.id = id;
        this.nomCampagne = nomCampagne;
        this.nomDonateur = nomDonateur;
        this.montant = montant;
        this.date = date;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCampagne() {
        return nomCampagne;
    }

    public void setNomCampagne(String nomCampagne) {
        this.nomCampagne = nomCampagne;
    }

    public String getNomDonateur() {
        return nomDonateur;
    }

    public void setNomDonateur(String nomDonateur) {
        this.nomDonateur = nomDonateur;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
