package com.gestion.produits.dto;

import com.gestion.produits.modele.Produits;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProduitsMapper {

    // Convertir Entité → DTO
    public ProduitsDTO versDTO(Produits produit) {
        return new ProduitsDTO(
                produit.getId(),
                produit.getNom(),
                produit.getPrix(),
                produit.getQuantite()
        );
    }

    // Convertir DTO → Entité
    public Produits versEntite(ProduitsDTO dto) {
        Produits produit = new Produits();
        produit.setId(dto.getId());
        produit.setNom(dto.getNom());
        produit.setPrix(dto.getPrix());
        produit.setQuantite(dto.getQuantite());
        return produit;
    }

    // Convertir une liste d'entités en liste de DTOs
    public List<ProduitsDTO> versDTO(List<Produits> produits) {
        return produits.stream()
                .map(this::versDTO)
                .collect(Collectors.toList());
    }
}
