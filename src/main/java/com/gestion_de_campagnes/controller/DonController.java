package com.gestion_de_campagnes.controller;

import com.gestion_de_campagnes.dto.DonDTO;
import com.gestion_de_campagnes.service.ServiceDon;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campagnes")

public class DonController {

    private final ServiceDon serviceDon;

    @Autowired
    public DonController(ServiceDon serviceDon) {
        this.serviceDon = serviceDon;
    }

    @PostMapping("/{id}/dons")
    @Operation(
        summary = "Enregistrer un don",
        description = "Enregistre un nouveau don pour une campagne spécifique",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Don enregistré avec succès",
                content = @Content(schema = @Schema(implementation = DonDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Données de don invalides ou campagne inactive"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Campagne non trouvée"
            )
        }
    )
    public ResponseEntity<DonDTO> enregistrerDon(
            @PathVariable("id") Long campagneId,
            @Valid @RequestBody DonDTO donDTO) {
        
        DonDTO donEnregistre = serviceDon.enregistrerDon(campagneId, donDTO);
        return new ResponseEntity<>(donEnregistre, HttpStatus.CREATED);
    }
}
