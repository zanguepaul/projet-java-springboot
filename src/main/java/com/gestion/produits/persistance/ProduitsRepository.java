package com.gestion.produits.persistance;

import com.gestion.produits.modele.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Long> {

    // ===== MÉTHODES DÉRIVÉES (Spring génère la requête automatiquement) =====

    // Chercher par nom exact
    List<Produits>findByNom(String nom);

    // Chercher par nom contenant une chaîne (ignorant la casse)
    List<Produits> findByNomContainingIgnoreCase(String nom);

    // Chercher les produits dont le prix est inférieur à une valeur
    List<Produits> findByPrixLessThan(Double prix);

    // Chercher les produits dont le prix est supérieur à une valeur
    List<Produits> findByPrixGreaterThan(Double prix);

    // Chercher par intervalle de prix
    List<Produits> findByPrixBetween(Double prixMin, Double prixMax);

    // Chercher par nom et prix
    List<Produits> findByNomAndPrix(String nom, Double prix);

    // Chercher les produits en stock (quantité > 0)
    List<Produits> findByQuantiteGreaterThan(Integer quantite);

    // Trier par prix croissant
    List<Produits> findAllByOrderByPrixAsc();

    // Trier par prix décroissant
    List<Produits> findAllByOrderByPrixDesc();


    // ===== MÉTHODES AVEC @QUERY (requêtes personnalisées) =====

    // Recherche avancée : nom contenant + prix max
    @Query("SELECT p FROM Produits p WHERE p.nom LIKE %:nom% AND p.prix <= :prixMax")
    List<Produits> chercherParNomEtPrixMax(@Param("nom") String nom, @Param("prixMax") Double prixMax);

    // Compter les produits par catégorie de prix
    @Query("SELECT COUNT(p) FROM Produits p WHERE p.prix < :prix")
    Long compterProduitsEnDessousDe(@Param("prix") Double prix);

    // Produits avec stock faible (moins que la quantité donnée)
    @Query("SELECT p FROM Produits p WHERE p.quantite < :quantiteMin")
    List<Produits> trouverStockFaible(@Param("quantiteMin") Integer quantiteMin);

    // Mise à jour du prix en masse (pour tous les produits)
    @Query("UPDATE Produits p SET p.prix = p.prix * :coefficient")
    void augmenterPrixPourcentage(@Param("coefficient") Double coefficient);
}