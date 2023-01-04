package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



/**
 * Tests JUnit de la classe {@link association.Evenement Evenement}.
 *
 * @author Eric Cariou
 * @see association.Evenement
 */
class TestEvenement {
  
  /**
   * Un événement classique.
   */
  private Evenement eventClassique;
  
  
  /**
   * Instancie un événement classique pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    eventClassique = new Evenement("Nouvel événement", "Brest",
        LocalDateTime.of(2024, Month.of(12), 20, 12, 00), 2, 50,
        new ArrayList<InterMembre>());
  }
  
  
  /**
   * Ne fait rien apr�s les tests : � modifier au besoin.
   *
   * @throws Exception ne peut pas �tre lev�e ici
   */
  @AfterEach
  void tearDown() throws Exception {}
  
  
  /**
   * V�rifie que l'on peut positionner une date d'événement.
   */
  @Test
  void testChangementDate() {
    eventClassique.setDate(LocalDateTime.of(2023, Month.of(3), 13, 20, 45));
    assertEquals(eventClassique.getDate(),
        LocalDateTime.of(2023, Month.of(3), 13, 20, 45));
  }
  
  
  /**
   * V�rifie que l'on peut positionner une durée d'événement.
   */
  @Test
  void testChangementDuree() {
    eventClassique.setDuree(20);
    assertEquals(eventClassique.getDuree(), 20);
    eventClassique.setDuree(-10);
    assertTrue(eventClassique.getDuree() >= 0);
  }
  
  
  /**
   * V�rifie qu'un lieu n'est pas null quand on cr�e un événement.
   */
  @Test
  void testLieuNonNull() {
    assertTrue(eventClassique.getLieu() != null);
  }
  
  /**
   * V�rifie qu'on ne peut pas positionner un lieu null sur un événement
   * existant.
   */
  @Test
  void testSetterLieuNull() {
    eventClassique.setLieu(null);
    assertTrue(eventClassique.getLieu() != null);
  }
  
  
  
  /**
   * V�rifie qu'un nom n'est pas null quand on cr�e un événement.
   */
  @Test
  void testNomNonNull() {
    assertTrue(eventClassique.getNom() != null);
  }
  
  /**
   * V�rifie qu'on ne peut pas positionner un nom null sur un événement
   * existant.
   */
  @Test
  void testSetterNomNull() {
    eventClassique.setNom(null);
    assertTrue(eventClassique.getNom() != null);
  }
  
  
  /**
   * V�rifie que les param�tres des constructeurs sont correctement gérés.
   */
  @Test
  void testConstructeur() {
    Evenement inf = new Evenement("Deuxième événement", "Plouzané", LocalDateTime.now(), 5,
        0, new ArrayList<InterMembre>());
    
    assertEquals(inf.getNom(), "Deuxième événement");
    assertEquals(inf.getLieu(), "Plouzané");
    assertTrue(inf.getNbParticipantsMax() >= 0);
    assertTrue(inf.getDuree() >= 0);
    assertTrue(inf.getParticipants().size() == 0);
    assertTrue(inf.getDate().compareTo(LocalDateTime.now()) <= 0);
    
    
    Evenement test = new Evenement(null, null, null, 5,
        -100, null);
    
    assertTrue(test.getNom() != null);
    assertTrue(test.getLieu() != null);
    assertTrue(test.getNbParticipantsMax() >= 0);
    assertEquals(test.getDuree(), 5);
    assertTrue(test.getParticipants().size() != -100);
    assertTrue(test.getDate().compareTo(LocalDateTime.now()) <= 0);
    
    Evenement test2;
    try {
      test2 = new Evenement(null, null, LocalDateTime.of(-300, -5, 36, -5, 12), 5,
          -100, null);
    } catch (DateTimeException e) {
      /*
       * ????
       */
      System.out.println("Doit passer paramètre valide à LocalDateTime !");
      
      test2 = new Evenement(null, null, -5, 0, null, 0, 0, -5, 0, null);
    }
    
    assertTrue(test2.getNom() != null);
    assertTrue(test2.getLieu() != null);
    assertTrue(test2.getNbParticipantsMax() >= 0);
    assertTrue(test2.getDuree() >= 0);
    assertEquals(test2.getParticipants().size(), 0);
    assertTrue(test2.getDate().compareTo(LocalDateTime.now()) <= 0);
    
    
  }
  
  
  /**
   * Vérifie que différents événement se chevauche ou non.
   */
  @Test
  void testChevauchement() {
    Evenement event1 = new Evenement("Evenement1", "Brest Arena",
        LocalDateTime.of(2024, Month.of(3), 13, 19, 45), 1, 50,
        new ArrayList<InterMembre>());
    Evenement event2 = new Evenement("Evenement2", "La Carenne",
        LocalDateTime.of(2024, Month.of(3), 13, 21, 45), 3, 75,
        new ArrayList<InterMembre>());
    
    assertTrue(event1.pasDeChevauchementTemps(event2));
    assertTrue(event1.pasDeChevauchementLieu(event2));
    assertTrue(event2.pasDeChevauchementTemps(event1));
    assertTrue(event2.pasDeChevauchementLieu(event1));
    
    Evenement event3 = new Evenement("Evenement3", "Brest Arena",
        LocalDateTime.of(2024, Month.of(3), 13, 20, 45), 2, 75,
        new ArrayList<InterMembre>());
    
    assertFalse(event2.pasDeChevauchementTemps(event3));
    assertTrue(event2.pasDeChevauchementLieu(event3));
    assertFalse(event3.pasDeChevauchementTemps(event2));
    assertTrue(event3.pasDeChevauchementLieu(event2));
    
    assertTrue(event1.pasDeChevauchementTemps(event3));
    assertTrue(event1.pasDeChevauchementLieu(event3));
    assertTrue(event3.pasDeChevauchementTemps(event1));
    assertTrue(event3.pasDeChevauchementLieu(event1));
    
    Evenement event4 = new Evenement("Evenement4", "La Carenne",
        LocalDateTime.of(2024, Month.of(3), 13, 23, 30), 1, 75,
        new ArrayList<InterMembre>());
    
    assertFalse(event2.pasDeChevauchementTemps(event4));
    assertFalse(event2.pasDeChevauchementLieu(event4));
    assertFalse(event4.pasDeChevauchementTemps(event2));
    assertFalse(event4.pasDeChevauchementLieu(event2));
    
    /* 
     * --------------------------------------------------> 
     *                                                 Temps
     *      ============        ============
     *      |  event1  |        |  event2  |
     *      |  lieu1   |        |  lieu2   |
     *      ============        ============
     *                  ============    ============
     *                  |  event3  |    |  event4  |
     *                  |  lieu1   |    |  lieu2   |
     *                  ============    ============
     * 
     */
    
  }
  
  
  
  /**
   * Test de l'ajout et retrait de participants.
   */
  @Test
  void testManipulationParticipants() {
    assertTrue(eventClassique.getParticipants().size() == 0);
    
    InterMembre mb1 = new Membre(new InformationPersonnelle("nom", "prenom"));
    InterMembre mb2 = new Membre(new InformationPersonnelle("nom2", "prenom2"));
    
    assertTrue(mb1.ensembleEvenements().size() == 0);
    assertTrue(mb2.ensembleEvenements().size() == 0);
    
    assertTrue(eventClassique.ajouterParticipant(mb1));
    assertFalse(eventClassique.ajouterParticipant(mb1));
    assertTrue(eventClassique.ajouterParticipant(mb2));
    
    assertTrue(mb1.ensembleEvenements().size() == 1);
    assertTrue(mb2.ensembleEvenements().size() == 1);
    
    assertTrue(mb1.ensembleEvenements().contains(eventClassique));
    assertTrue(mb2.ensembleEvenements().contains(eventClassique));
    
    assertTrue(eventClassique.estParticipant(mb1));
    assertTrue(eventClassique.estParticipant(mb2));
    
    
    assertTrue(eventClassique.getParticipants().size() == 2);
    assertTrue(eventClassique.estParticipant(mb1));
    assertTrue(eventClassique.estParticipant(mb2));
    
    assertTrue(eventClassique.retirerParticipant(mb1));
    assertTrue(eventClassique.retirerParticipant(mb2));
    assertFalse(eventClassique.retirerParticipant(mb2));
    
    assertTrue(eventClassique.getParticipants().size() == 0);
    assertTrue(mb1.ensembleEvenements().size() == 0);
    
  }
  
  
}
