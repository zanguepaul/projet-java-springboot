package com.gestion.produits.controller;

import com.gestion.produits.modele.Produits;
import com.gestion.produits.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;package com.gestion.produits.controller;

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

    // GET : Tous les produits (avec DTO)
    @GetMapping
    public List<ProduitsDTO> getAllProduits() {
        List<Produits> produits = produitService.obtenirTousLesProduits();
        return produitsMapper.versDTO(produits);
    }

    // GET : Un produit par ID (avec DTO)
    @GetMapping("/{id}")
    public ResponseEntity<ProduitsDTO> getProduitById(@PathVariable Long id) {
        return produitService.obtenirProduitParId(id)
                .map(produitsMapper::versDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST : Ajouter un produit (via DTO)
    @PostMapping
    public ResponseEntity<ProduitsDTO> creerProduit(@RequestBody ProduitsDTO dto) {
        Produits produit = produitsMapper.versEntite(dto);
        Produits sauvegarde = produitService.ajouterProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produitsMapper.versDTO(sauvegarde));
    }

    // PUT : Modifier un produit (via DTO)
    @PutMapping("/{id}")
    public ResponseEntity<ProduitsDTO> modifierProduit(
            @PathVariable Long id,
            @RequestBody ProduitsDTO dto) {
        try {
            Produits produit = produitsMapper.versEntite(dto);
            Produits modifie = produitService.modifierProduit(id, produit);
            return ResponseEntity.ok(produitsMapper.versDTO(modifie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE : Supprimer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        try {
            produitService.supprimerProduit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET : Recherche par mot-clé (avec DTO)
    @GetMapping("/recherche")
    public List<ProduitsDTO> rechercher(@RequestParam String motCle) {
        List<Produits> produits = produitService.chercherParMotCle(motCle);
        return produitsMapper.versDTO(produits);
    }
}
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

    // GET : Tous les produits
    @GetMapping
    public List<Produits> getAllProduits() {
        return produitService.obtenirTousLesProduits();
    }

    // GET : Un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<Produits> getProduitById(@PathVariable Long id) {
        return produitService.obtenirProduitParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST : Ajouter un produit
    @PostMapping
    public ResponseEntity<Produits> creerProduit(@RequestBody Produits produit) {
        Produits nouveau = produitService.ajouterProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
    }

    // PUT : Modifier un produit
    @PutMapping("/{id}")
    public ResponseEntity<Produits> modifierProduit(@PathVariable Long id, @RequestBody Produits produit) {
        try {
            return ResponseEntity.ok(produitService.modifierProduit(id, produit));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE : Supprimer un produit
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) {
        try {
            produitService.supprimerProduit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET : Recherche par mot-clé
    @GetMapping("/recherche")
    public List<Produits> rechercher(@RequestParam String motCle) {
        return produitService.chercherParMotCle(motCle);
    }

    // GET : Produits par fourchette de prix
    @GetMapping("/prix")
    public List<Produits> parPrix(@RequestParam Double min, @RequestParam Double max) {
        return produitService.produitsDansBudget(min, max);
    }
}
