package com.gestion.produits.init;

import com.gestion.produits.modele.Produits;
import com.gestion.produits.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private ProduitService produitService;

    @Override
    public void run(String... args) {

        // N'insérer les données qu'une seule fois
        if (produitService.obtenirTousLesProduits().isEmpty()) {

            produitService.ajouterProduit(new Produits("Ordinateur portable", 1200.0, 10));
            produitService.ajouterProduit(new Produits("Souris sans fil", 25.0, 50));
            produitService.ajouterProduit(new Produits("Clavier mécanique", 89.0, 30));
            produitService.ajouterProduit(new Produits("Écran 24 pouces", 199.0, 15));
            produitService.ajouterProduit(new Produits("Casque audio", 79.0, 25));
            produitService.ajouterProduit(new Produits("Webcam HD", 59.0, 40));
            produitService.ajouterProduit(new Produits("Disque dur externe 1To", 55.0, 60));
            produitService.ajouterProduit(new Produits("Clé USB 32Go", 12.0, 100));
            produitService.ajouterProduit(new Produits("Imprimante multifonction", 250.0, 8));
            produitService.ajouterProduit(new Produits("Tablette graphique", 399.0, 5));

            System.out.println("\n=== 10 PRODUITS AJOUTÉS AVEC SUCCÈS ===\n");
        }

        // ===== TEST DE TOUTES LES MÉTHODES =====

        System.out.println("═══════════════════════════════════════════");
        System.out.println("  TEST DES MÉTHODES DE RECHERCHE JPA");
        System.out.println("═══════════════════════════════════════════\n");

        // 1. Tous les produits triés par prix
        System.out.println("1️⃣  Produits triés par prix croissant :");
        produitService.produitsTriesParPrixCroissant()
                .forEach(p -> System.out.println("   • " + p.getNom() + " → " + p.getPrix() + "€"));

        // 2. Recherche par mot-clé
        System.out.println("\n2️⃣  Produits contenant 'clé' :");
        produitService.chercherParMotCle("clé")
                .forEach(p -> System.out.println("   • " + p.getNom() + " → " + p.getPrix() + "€"));

        // 3. Produits en promo (moins de 100€)
        System.out.println("\n3️⃣  Produits à moins de 100€ :");
        produitService.produitsEnPromo(100.0)
                .forEach(p -> System.out.println("   • " + p.getNom() + " → " + p.getPrix() + "€"));

        // 4. Produits haut de gamme (plus de 300€)
        System.out.println("\n4️⃣  Produits haut de gamme (plus de 300€) :");
        produitService.produitsHautDeGamme(300.0)
                .forEach(p -> System.out.println("   • " + p.getNom() + " → " + p.getPrix() + "€"));

        // 5. Recherche avancée : mot-clé + budget
        System.out.println("\n5️⃣  Recherche avancée ('e' dans le nom, max 100€) :");
        produitService.rechercheAvancee("e", 100.0)
                .forEach(p -> System.out.println("   • " + p.getNom() + " → " + p.getPrix() + "€"));

        // 6. Nombre de produits abordables
        System.out.println("\n6️⃣  Nombre de produits à moins de 50€ : " +
                produitService.nombreProduitsAbordables(50.0));

        // 7. Produits en rupture de stock
        System.out.println("\n7️⃣  Produits avec stock faible (moins de 10) :");
        produitService.produitsRuptureStock()
                .forEach(p -> System.out.println("   • " + p.getNom() + " → Stock: " + p.getQuantite()));

        System.out.println("\n═══════════════════════════════════════════\n");
    }
}