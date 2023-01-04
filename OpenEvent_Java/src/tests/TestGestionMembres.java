package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import association.GestionMembres;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests JUnit de la classe {@link association.GestionMembres GestionMembres}.
 *
 * @author Adil Zaim
 * @see association.GestionMembres
 */
public class TestGestionMembres {
  /**
   *  Declaration des InformationPersonnelle utiliser pour les mombres.
   *
   */
  InformationPersonnelle inf1;
  InformationPersonnelle inf2;
  InformationPersonnelle inf3;
  InformationPersonnelle inf4;
  InformationPersonnelle inf5;
  InformationPersonnelle inf6;
  InformationPersonnelle inf_mis_a_jour3;
  /**
   *  Declaration des membres utiliser pour les testes.
   *
   */
  InterMembre m1;
  InterMembre m2;
  InterMembre m3;
  InterMembre m4;
  InterMembre m5;
  InterMembre m6;
  InterMembre m_like3;
  InterMembre m_mis_a_jour3;
  
  /**
   * Instancie des mombres pour les tests.
   *
   * @throws Exception ne peut pas �tre lev�e ici
   */
  @BeforeEach
  void setUp() throws Exception {
    
    inf1 =
        new InformationPersonnelle("adil", "zaim", "87 sebastopol Brest", -30);
    m1 = new Membre(inf1);
    
    inf2 = new InformationPersonnelle("oceane", "pezennec", "98 plouzane Brest",
        -30);
    m2 = new Membre(inf2);
    
    inf3 = new InformationPersonnelle("ange", " Le Dark", "CROUS", 17);
    m3 = new Membre(inf3);
    //instance membre avec même informationpersonelle que m3
    m_like3 = new Membre(inf3);
    //instance informationpersonnelle avec meme nom , prenom que l'instance inf3
    inf_mis_a_jour3 = new InformationPersonnelle("ange", " Le Dark", "brest", 88);
    //instance membre avec même nom et prenom que m3 mais avec different age et adresse.
    m_mis_a_jour3 = new Membre(inf_mis_a_jour3);
    
    inf4 = new InformationPersonnelle("Joss", "mouette",
        "85 la lune a cote de la terre", 900);
    m4 = new Membre(inf4);
    
    inf5 = new InformationPersonnelle("Nicolas", "le barse",
        "98 bellevue Brest", 55);
    m5 = new Membre(inf5);
    
    inf6 = new InformationPersonnelle("floriant", "fenoi", "Finland", 2);
    m6 = new Membre(inf6);
  }
  
  /**
   * Ne fait rien apr�s les tests : � modifier au besoin.
   *
   * @throws Exception ne peut pas �tre lev�e ici
   */
  @AfterEach
  void tearDown() throws Exception {}
  
  /**
   * V�rifie que suite a l'ajoute de 5 membres dans l'association est bien
   * correct et que l'ajout d'un membre deja existant n'est pas possible.
   * et que la mis à jour d'un membre a partire d'une instance different est bien pris en compte.
   */
  @Test
  void testajouterMembre() {
    GestionMembres gestioMemb = new GestionMembres();
    //ajout de 5 membre 
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    //ajout d'un membre dont l'objet existe deja dans l'assocation.
    gestioMemb.ajouterMembre(m5);
    //ajout d'un nouveau membre m33 dont les informations personnelles 
    //sont les meme que le membre m3 deja existant.
    gestioMemb.ajouterMembre(m_like3); 
    assertEquals(gestioMemb.ensembleMembres().size(), 5);
    assertEquals(m3.getInformationPersonnelle().getAdresse(), "CROUS");
    assertEquals(m3.getInformationPersonnelle().getAge(), 17);
    //mis a jour de l'adresse et l'age du membre m3 avec une instance diferent qui est m333.
    gestioMemb.ajouterMembre(m_mis_a_jour3);
    assertEquals(m3.getInformationPersonnelle().getAdresse(), "brest");
    assertEquals(m3.getInformationPersonnelle().getAge(), 88);
    assertEquals(gestioMemb.ensembleMembres().size(), 5);
  }
  
  /**
   * V�rifie que la suppression d'un membres dans l'association.
   */
  @Test
  void testsupprimerMembre() {
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    // suuprimer le membre m5
    gestioMemb.supprimerMembre(m5);
    gestioMemb.supprimerMembre(m5);
    assertEquals(gestioMemb.ensembleMembres().size(), 3);
  }
  
  /**
   * V�rifie que m4 est bien le president designer dans l'association.
   */
  @Test
  void testdesignerPresident() {
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    // designerPresident m4
    gestioMemb.designerPresident(m4);
    assertEquals(gestioMemb.president(), m4);
  }
  
  /**
   * V�rifie que le membre m4 est bient le president de l'association.
   */
  @Test
  void testpresident() {
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    gestioMemb.ajouterMembre(m6);
    
    gestioMemb.designerPresident(m4);
    assertEquals(gestioMemb.president(), m4);
  }
  
  /**
   * V�rifie que le president est null si on il n'est pas designoier.
   */
  @Test
  void testpresidentNull() {
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    gestioMemb.ajouterMembre(m6);
    assertEquals(gestioMemb.president(), null);
  }
  
  /**
   * V�rifie que designer un president qui est n'est pas membre n'est pas
   * possible.
   */
  @Test
  void testAjoutPresidentNonMembre() {
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    gestioMemb.designerPresident(m1);
    // gestioMemb.supprimerMembre(m5);
    gestioMemb.designerPresident(m6);
    assertEquals(gestioMemb.president(), m1);
  }
  
  /**
   * V�rifie que l'ensemble des membres ajouter sont correct et que le null
   * n'est pas ajouter.
   */
  @Test
  void testensembleMembres() {
    GestionMembres gestioMemb = new GestionMembres();
    gestioMemb.ajouterMembre(m1);
    gestioMemb.ajouterMembre(m2);
    gestioMemb.ajouterMembre(m3);
    gestioMemb.ajouterMembre(m4);
    gestioMemb.ajouterMembre(m5);
    gestioMemb.ajouterMembre(m6);
    gestioMemb.ajouterMembre(null);
    assertEquals(gestioMemb.ensembleMembres().size(), 6);
  }
  
}
