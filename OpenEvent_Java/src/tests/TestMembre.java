package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*import tests.TestEvenement;
import tests.TestInformationPersonnelle;*/

/**
 * Tests JUnit de la classe {@link association.Membre Membre}.
 *
 * @author Oceane Pezennec
 * @see association.Membre
 */
public class TestMembre {

  /**
   * Une information compl�te : pr�nom, nom, adresse et age.
   */
  private InformationPersonnelle infoComplete;
  
  /**
   * Un evenement :nom, lieu, date,duree,nbParticipantsMax,participants.
   */
  private List<Evenement> evtsAvenir;
  
  /**
   * L'attribut Membre prend en paramètre la classe InformationPersonnelle et Evenement.
   */
  private InterMembre memb;
  /**
   * Instancie une information basique et une compl�te pour les tests.
   *
   * @throws Exception ne peut pas �tre lev�e ici
   */
  
  @BeforeEach
  void setUp() throws Exception {
    infoComplete = new InformationPersonnelle("Skywalker", "Luke", "Planete Tatooine", 20);
    evtsAvenir = new ArrayList<Evenement>();
    Evenement geekcon = new Evenement("Geekcon", "Brest",
        LocalDateTime.of(2022, Month.of(12), 20, 12, 00), 2, 1000,
        new ArrayList<InterMembre>());
    Evenement geekcon1 = new Evenement("Convention SF", "Brest",
            LocalDateTime.of(2022, Month.of(12), 12, 12, 00), 2, 1000,
            new ArrayList<InterMembre>());
    evtsAvenir.add(geekcon);
    evtsAvenir.add(geekcon1);
    memb = new Membre(infoComplete, evtsAvenir);
  }
  
  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @AfterEach
  void tearDown() throws Exception {
    
  }
  
  /**
   * Vérifie que l'on peut positionner les informations pour un membre.
   */
  @Test
  void testDefinirInformationPersonnnelle() {
    memb.definirInformationPersonnnelle(infoComplete);
    assertEquals(memb.getInformationPersonnelle().getNom(), "Skywalker");
    assertEquals(memb.getInformationPersonnelle().getPrenom(), "Luke");
    assertTrue(memb.getInformationPersonnelle().getAdresse() != null); 
    assertTrue(memb.getInformationPersonnelle().getAge() >= 0);

  }
  

  /**
   * V�rifie qu'on ne peut récupérer des informations sur un membre.
   */
  @Test
  void testGetInformationPersonnelle() {
    InformationPersonnelle a = memb.getInformationPersonnelle();
    assertEquals(a.getNom(), "Skywalker");
    assertEquals(a.getPrenom(), "Luke");
    assertTrue(a.getAdresse() != null); 
    assertTrue(a.getAge() >= 0);

  }
 
  /**
   * Vérifie que la fonction ensembleEvenements permet de manipuler correctement les listes.
   *Pour le test, nous creons un nouvel evenement, 
   *on vérifie que cet evenement n'existe pas dans la liste, 
   *on le rajoute pour le membre a, puis on vérifie que le membre est bien inscrit.
   *on le supprime puis on vérifie que le membre n'est plus inscrit.   
   */
  @Test
  void testensembleEvenements() {
    List<Evenement> a = memb.ensembleEvenements();
    Evenement events = new Evenement("Convention SF10", "Paris",
            LocalDateTime.of(2012, Month.of(12), 12, 12, 00), 2, 240,
            new ArrayList<InterMembre>());
    assertFalse(a.contains(events));
    a.add(events);
    assertTrue(a.contains(events));
    a.remove(events);
    assertFalse(a.contains(events));

  }
  /**
   * Vérifie que les parametres des constructeurs sont valides.
   */
  
  @Test
  void testConstructeur() {
    InterMembre membre = new Membre(new InformationPersonnelle("Vador", "Dark", null, -30));
    assertEquals(membre.getInformationPersonnelle().getNom(), "Vador");
    assertEquals(membre.getInformationPersonnelle().getPrenom(), "Dark");
    assertTrue(membre.getInformationPersonnelle().getAdresse() != null); 
    assertTrue(membre.getInformationPersonnelle().getAge() >= 0);
    assertTrue(membre.ensembleEvenements().size() == 0);

  }
}
