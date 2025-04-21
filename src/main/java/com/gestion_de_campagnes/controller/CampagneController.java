package com.gestion_de_campagnes.controller;

import com.gestion_de_campagnes.repository.CampagneRepository.CampagneResume;
import com.gestion_de_campagnes.service.ServiceCampagne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/campagnes")

public class CampagneController {

    private final ServiceCampagne serviceCampagne;

    @Autowired
    public CampagneController(ServiceCampagne serviceCampagne) {
        this.serviceCampagne = serviceCampagne;
    }

    @GetMapping("/actives")
    @Operation(
        summary = "Récupérer les campagnes actives",
        description = "Récupère la liste des campagnes dont la date actuelle est comprise entre la date de début et la date de fin",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Liste des campagnes actives récupérée avec succès",
                content = @Content(schema = @Schema(implementation = CampagneResume.class))
            )
        }
    )
    public ResponseEntity<List<CampagneResume>> getCampagnesActives() {
        List<CampagneResume> campagnes = serviceCampagne.getCampagnesActives();
        return ResponseEntity.ok(campagnes);
    }
}
