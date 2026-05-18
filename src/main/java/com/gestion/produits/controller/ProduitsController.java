package com.gestion.produits.controller;

import com.gestion.produits.dto.ProduitsDTO;
import com.gestion.produits.dto.ProduitsMapper;
import com.gestion.produits.modele.Produits;
import com.gestion.produits.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin("*")
public class ProduitsController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private ProduitsMapper produitsMapper;

    @GetMapping
    public List<ProduitsDTO> getAllProduits() {
        List<Produits> produits = produitService.obtenirTousLesProduits();
        return produitsMapper.versDTO(produits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitsDTO> getProduitById(@PathVariable Long id) {
        return produitService.obtenirProduitParId(id)
                .map(produitsMapper::versDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProduitsDTO> creerProduit(@RequestBody ProduitsDTO dto) {
        Produits produit = produitsMapper.versEntite(dto);
        Produits sauvegarde = produitService.ajouterProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(produitsMapper.versDTO(sauvegarde));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitsDTO> modifierProduit(@PathVariable Long id, @RequestBody ProduitsDTO dto) {
        try {
            Produits produit = produitsMapper.versEntite(dto);
            Produits modifie = produitService.modifierProduit(id, produit);
            return ResponseEntity.ok(produitsMapper.versDTO(modifie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        try {
            produitService.supprimerProduit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recherche")
    public List<ProduitsDTO> rechercher(@RequestParam String motCle) {
        List<Produits> produits = produitService.chercherParMotCle(motCle);
        return produitsMapper.versDTO(produits);
    }
}
