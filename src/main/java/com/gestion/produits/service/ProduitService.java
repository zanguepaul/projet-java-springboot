package com.gestion.produits.service;

import com.gestion.produits.modele.Produits;
import com.gestion.produits.persistance.ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitsRepository produitRepository;

    // ===== CRUD DE BASE =====

    public Produits ajouterProduit(Produits produit) {
        return produitRepository.save(produit);
    }

    public List<Produits> obtenirTousLesProduits() {
        return produitRepository.findAll();
    }

    public Optional<Produits> obtenirProduitParId(Long id) {
        return produitRepository.findById(id);
    }

    public Produits modifierProduit(Long id, Produits produitModifie) {
        Produits produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit avec id " + id + " non trouvé"));
        produit.setNom(produitModifie.getNom());
        produit.setPrix(produitModifie.getPrix());
        produit.setQuantite(produitModifie.getQuantite());
        return produitRepository.save(produit);
    }

    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    // ===== RECHERCHES PERSONNALISÉES =====

    public List<Produits> chercherParNom(String nom) {
        return produitRepository.findByNom(nom);
    }

    public List<Produits> chercherParMotCle(String motCle) {
        return produitRepository.findByNomContainingIgnoreCase(motCle);
    }

    public List<Produits> produitsEnPromo(Double prixMax) {
        return produitRepository.findByPrixLessThan(prixMax);
    }

    public List<Produits> produitsHautDeGamme(Double prixMin) {
        return produitRepository.findByPrixGreaterThan(prixMin);
    }

    public List<Produits> produitsDansBudget(Double min, Double max) {
        return produitRepository.findByPrixBetween(min, max);
    }

    public List<Produits> produitsEnStock() {
        return produitRepository.findByQuantiteGreaterThan(0);
    }

    public List<Produits> produitsTriesParPrixCroissant() {
        return produitRepository.findAllByOrderByPrixAsc();
    }

    public List<Produits> produitsRuptureStock() {
        return produitRepository.trouverStockFaible(1);
    }

    public List<Produits> rechercheAvancee(String motCle, Double budgetMax) {
        return produitRepository.chercherParNomEtPrixMax(motCle, budgetMax);
    }

    public Long nombreProduitsAbordables(Double prixMax) {
        return produitRepository.compterProduitsEnDessousDe(prixMax);
    }
}
