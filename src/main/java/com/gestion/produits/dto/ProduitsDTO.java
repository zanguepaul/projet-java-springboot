package com.gestion.produits.dto;

public class ProduitsDTO {

    private Long id;
    private String nom;
    private Double prix;
    private Integer quantite;
    
    // Champ calculé (pas dans l'entité)
    private String categoriePrix;

    // Constructeurs
    public ProduitsDTO() {
    }

    public ProduitsDTO(Long id, String nom, Double prix, Integer quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        // Catégorie calculée automatiquement
        this.categoriePrix = determinerCategorie(prix);
    }

    // Méthode utilitaire
    private String determinerCategorie(Double prix) {
        if (prix < 50) return "Économique";
        else if (prix < 200) return "Milieu de gamme";
        else return "Haut de gamme";
    }

    // Getters et Setters
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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
        this.categoriePrix = determinerCategorie(prix);
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getCategoriePrix() {
        return categoriePrix;
    }
}
