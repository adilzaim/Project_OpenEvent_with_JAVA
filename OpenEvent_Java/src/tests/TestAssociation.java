package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import association.Association;
import association.Evenement;
import association.GestionEvenements;
import association.GestionMembres;
import association.InformationPersonnelle;
import association.Membre;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;






/**
 * Tests JUnit de la classe {@link association.Association Association}.
 *
 * @author Ange Leyrit
 * @see association.Association
 */
public class TestAssociation {
  

  /**
   * Instance de l'association qui va ï¿½tre sauvegarder.
   */
  private Association assoBefore;
  /**
   * Instance d'association qui va charger la sauvagarde de AssoBefore.
   */
  private Association assoAfter;
  
  /**
   * Instancie une information basique et une complï¿½te pour les tests.
   *
   * @throws Exception ne peut pas ï¿½tre levï¿½e ici
   */
  @BeforeEach
  void setUp() throws Exception {
    
    
    Evenement evt1 = new Evenement("Event 1", "Lieu 1", 3, 21, Month.NOVEMBER, 2022, 13,
        25, 100, null);
    Evenement evt2 = new Evenement("Event 2", "Lieu 2", 5, 21, Month.of(3), 2024, 18, 45,
        75, null);
    Evenement evt3 = new Evenement("Event 3", "Lieu 3", 3, 2, Month.of(7), 2025, 12, 00,
        80, null);
    
    List<Evenement> events = new ArrayList<Evenement>();
    
    events.add(evt1);
    events.add(evt2);
    events.add(evt3);
    
    GestionEvenements gestionnaireClassique = new GestionEvenements();
    gestionnaireClassique.setListeEvenements(events);
    
    InformationPersonnelle inf1 =
        new InformationPersonnelle("adil", "zaim", "87 sebastopol Brest", -30);
    Membre m1 = new Membre(inf1);
    
    InformationPersonnelle inf2 = 
        new InformationPersonnelle("oceane", "pezennec", "98 plouzane Brest", -30);
    Membre m2 = new Membre(inf2);
    
    InformationPersonnelle inf3 = new InformationPersonnelle("ange", " Le Dark", "CROUS", 17);
    Membre m3 = new Membre(inf3);
    
    InformationPersonnelle inf4 = new InformationPersonnelle("Joss", "mouette",
        "85 la lune a cote de la terre", 900);
    Membre m4 = new Membre(inf4);
    
    InformationPersonnelle inf5 = new InformationPersonnelle("Nicolas", "le barse",
        "98 bellevue Brest", 55);
    Membre m5 = new Membre(inf5);
    
    InformationPersonnelle inf6 = new InformationPersonnelle("floriant", "fenoi", "Finland", 2);
    Membre m6 = new Membre(inf6);
    
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    gestioMemb.ajouterMembre(m6);
    
    assoBefore = new Association();
    assoBefore.eventAssociation = gestionnaireClassique;
    assoBefore.memberAssociation = gestioMemb;
    assoAfter = new Association();
  }
  
  /**
   * Ne fait rien aprï¿½s les tests : ï¿½ modifier au besoin.
   *
   * @throws Exception ne peut pas ï¿½tre levï¿½e ici
   */
  @AfterEach
  void tearDown() throws Exception {}
  
  /**
   * Vï¿½rifie que la sauvegarde et le chargement 
   * sont cohï¿½rent, en testant leur égalité dans les donnï¿½es qu'ils 
   * sauvegarde/charge.
   *
   * @throws IOException en cas de problï¿½me d'ï¿½criture dans le fichier 
   */
  @Test
  void testSaveAndLoad() throws IOException {
    assoBefore.sauvegarderDonnees("./save_asso.ser");
    assoAfter.chargerDonnees("./save_asso.ser");
    assertEquals(assoBefore, assoAfter);
  }
  
}
