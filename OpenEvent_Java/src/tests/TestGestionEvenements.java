package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.GestionEvenements;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * Tests JUnit de la classe {@link association.GestionEvenements
 * GestionEvenements}.
 *
 * @author Jean Hippolyte
 * @see association.GestionEvenements
 */
class TestGestionEvenements {
  
  /**
   * Un gestionnaire d'événement sans événements enregistrés.
   */
  private GestionEvenements gestionnaireVide;
  
  /**
   * Un gestionnaire d'événement avec quelques événements enregistrés.
   */
  private GestionEvenements gestionnaireClassique;
  
  
  /**
   * Evenement classique.
   */
  private Evenement evt1;
  
  /**
   * Evenement classique.
   */
  private Evenement evt2;
  
  /**
   * Evenement classique.
   */
  private Evenement evt3;
  
  /**
   * Instancie un gestionnaire d'événements vide pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    gestionnaireVide = new GestionEvenements();
    
    evt1 = new Evenement("Event 1", "Lieu 1", 3, 21, Month.NOVEMBER, 2022, 13,
        25, 100, null);
    evt2 = new Evenement("Event 2", "Lieu 2", 5, 21, Month.of(3), 2024, 18, 45,
        75, null);
    evt3 = new Evenement("Event 3", "Lieu 3", 3, 2, Month.of(7), 2025, 12, 00,
        80, null);
    
    List<Evenement> events = new ArrayList<Evenement>();
    
    events.add(evt1);
    events.add(evt2);
    events.add(evt3);
    
    gestionnaireClassique = new GestionEvenements();
    gestionnaireClassique.setListeEvenements(events);
  }
  
  
  /**
   * Test le positionnement de la liste des événements d'une instance de
   * GestionEvenements.
   */
  @Test
  void testSetterListeEvenement() {
    List<Evenement> l = new ArrayList<Evenement>();
    
    l.add(new Evenement("Event 1", "Lieu 1", 3, 21, Month.NOVEMBER, 2022, 13,
        25, 100, null));
    l.add(new Evenement("Event 2", "Lieu 2", 5, 21, Month.of(3), 2024, 18, 45,
        75, null));
    l.add(new Evenement("Event 3", "Lieu 3", 3, 2, Month.of(7), 2025, 12, 00,
        80, null));
    
    gestionnaireVide.setListeEvenements(l);
    
    assertEquals(gestionnaireVide.ensembleEvenements(), l);
    
  }
  
  
  /**
   * Vérifie que la liste des événements d'une instance de GestionEvenements ne
   * puisse pas être null.
   */
  @Test
  void testListeEvenementNonNull() {
    gestionnaireVide.setListeEvenements(null);
    
    assertTrue(gestionnaireVide.ensembleEvenements() != null);
    assertEquals(gestionnaireVide.ensembleEvenements(),
        new ArrayList<Evenement>());
  }
  
  
  /**
   * Test la récupération d'une liste des événements à venir à partir d'une
   * instance de GestionEvenements.
   */
  @Test
  void testGetterEvenementAvenir() {
    List<Evenement> events = new ArrayList<Evenement>();
    
    events.add(evt1);
    events.add(evt2);
    events.add(evt3);
    
    List<Evenement> eventsFutur = new ArrayList<Evenement>();
    
    
    eventsFutur.add(evt2);
    eventsFutur.add(evt3);
    
    gestionnaireVide.setListeEvenements(events);
    
    assertEquals(gestionnaireVide.ensembleEvenementAvenir(), eventsFutur);
    
  }
  
  
  /**
   * Test la création d'événements à l'aide de la méthode creerEvenement d'une
   * instance de GestionEvenements .
   */
  @Test
  void testCreationEvenement() {
    
    assertEquals(gestionnaireVide.ensembleEvenements().size(), 0);
    
    
    Evenement evenement1 = gestionnaireVide.creerEvenement("Nouvel Event",
        "Lieu", 20, Month.APRIL, 2023, 12, 0, 5, 100);
    
    assertTrue(evenement1 != null);
    assertEquals(gestionnaireVide.ensembleEvenements().size(), 1);
    
    
    Evenement evenement2 = gestionnaireVide.creerEvenement("Event du soir",
        "Autre Lieu", 12, Month.JUNE, 2023, 23, 30, 1, 50);
    
    assertTrue(evenement2 != null);
    assertEquals(gestionnaireVide.ensembleEvenements().size(), 2);
    
    
    Evenement evenement3 = gestionnaireVide.creerEvenement("Autre Event",
        "Lieu", 20, Month.APRIL, 2023, 14, 0, 2, 80);
    
    assertTrue(evenement3 == null);
    assertTrue(gestionnaireVide.ensembleEvenements().size() == 2);
    
  }
  
  
  /**
   * Test la suppression d'événement de la liste des événements d'une instance
   * de GestionEvenement.
   */
  @Test
  void testSuppressionEvenement() {
    assertTrue(gestionnaireClassique.ensembleEvenements().size() > 0);
    
    
    gestionnaireClassique.supprimerEvenement(evt1);
    assertTrue(gestionnaireClassique.ensembleEvenements().size() > 0);
    assertFalse(gestionnaireClassique.ensembleEvenements().contains(evt1));
    
    
    gestionnaireClassique.supprimerEvenement(evt2);
    assertTrue(gestionnaireClassique.ensembleEvenements().size() > 0);
    assertFalse(gestionnaireClassique.ensembleEvenements().contains(evt2));
    
    
    gestionnaireClassique.supprimerEvenement(evt3);
    assertTrue(gestionnaireClassique.ensembleEvenements().size() == 0);
    
    Evenement evt4 = new Evenement("Event 4", "Lieu 4", 3, 2, Month.of(2), 2999,
        12, 00, 75, null);
    
    gestionnaireClassique.supprimerEvenement(evt4);
    assertTrue(gestionnaireClassique.ensembleEvenements().size() == 0);
    
  }
  
  
  /**
   * Vérifie le bon fonctionnement des inscriptions et desinscriptions de membres
   * à un événement d'une instance de GestionEvenements.
   */
  @Test
  void testGestionParticipants() {
    InterMembre mbr = new Membre(new InformationPersonnelle("Nom", "Prenom"));
    
    
    assertFalse(gestionnaireVide.ensembleEvenements().contains(evt1));
    assertTrue(gestionnaireClassique.ensembleEvenements().contains(evt1));
    
    assertFalse(gestionnaireVide.inscriptionEvenement(evt1, mbr));
    assertFalse(gestionnaireClassique.inscriptionEvenement(evt1, mbr));
    
    assertFalse(evt1.estParticipant(mbr));
    assertTrue(evt1.getParticipants().size() == 0);
    assertFalse(mbr.ensembleEvenements().contains(evt1));
    assertEquals(mbr.ensembleEvenements().size(), 0);
    
    assertFalse(gestionnaireVide.ensembleEvenements().contains(evt2));
    assertTrue(gestionnaireClassique.ensembleEvenements().contains(evt2));
    
    assertFalse(gestionnaireVide.inscriptionEvenement(evt2, mbr));
    assertEquals(mbr.ensembleEvenements().size(), 0);
    assertTrue(gestionnaireClassique.inscriptionEvenement(evt2, mbr));
    
    assertTrue(evt2.estParticipant(mbr));
    assertTrue(evt2.getParticipants().size() == 1);
    assertTrue(mbr.ensembleEvenements().contains(evt2));
    assertEquals(mbr.ensembleEvenements().size(), 1);
    
    
    
    assertFalse(gestionnaireVide.annulerEvenement(evt2, mbr));
    assertTrue(gestionnaireClassique.annulerEvenement(evt2, mbr));
    assertFalse(gestionnaireClassique.annulerEvenement(evt1, mbr));
  }
  
  
  /**
   * Vérifie le bon fonctionnement des inscriptions de membres à un événement
   * d'une instance de GestionEvenements.
   */
  @Test
  void testInscriptionParticipants() {
    InterMembre mbr = new Membre(new InformationPersonnelle("Nom", "Prenom"));
        
    Evenement event1 = gestionnaireVide.creerEvenement("Evenement1", "Lieu1", 20,
        Month.DECEMBER, 2022, 12, 0, 2, 40);
    assertTrue(gestionnaireVide.inscriptionEvenement(event1, mbr));
    
    Evenement event2 = gestionnaireVide.creerEvenement("Evenement2", "Lieu2", 20,
        Month.DECEMBER, 2022, 13, 30, 2, 25);
    
    assertFalse(gestionnaireVide.inscriptionEvenement(event2, mbr));
    
    Evenement event3 = gestionnaireVide.creerEvenement("Evenement3", "Lieu2", 20,
        Month.DECEMBER, 2022, 15, 30, 1, 25);
    
    assertTrue(gestionnaireVide.inscriptionEvenement(event3, mbr));

  }
  
  
  
}
