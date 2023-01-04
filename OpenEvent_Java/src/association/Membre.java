package association;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Classe décrivant l'ensemble des informations d'un membre et les évenements
 * auquel il va ou a participé.
 *
 * @author Océane Pezennec
 */

public class Membre implements InterMembre, Serializable {
  /**
    * Version de la classe.
    */
  private static final long serialVersionUID = 1L;
  
  
  // Attributs -------------------------------------------------
  /**
   * Les évènements associé au membre (peuvent être modifiés).
   */
  public List<Evenement> evt;

  /**
   * Les évènements associé au membre (peuvent être modifiés).
   */
  public List<Evenement> evtAvenir;
  /**
   * Les informations du membre (peuvent être modifiés).
   */
  public InformationPersonnelle info;

  // Constructeurs -------------------------------------------------

  /**
   * Constructeur public.
   *
   * @param info les informations du membre.
   */
  public Membre(InformationPersonnelle info) {
    this.info = info;
    this.evt = new ArrayList<Evenement>();
    this.evtAvenir = new ArrayList<Evenement>();
  }

  /**
   * Constructeur public.
   *
   * @param info les informations du membre.
   * @param evt la liste des évènements du membre.
   * 
   */
  public Membre(InformationPersonnelle info, List<Evenement> evt) {
    this.info = info;
    this.evt = evt;
    this.evtAvenir = new ArrayList<Evenement>();
  }

  /**
   * La liste des évènements auquel le membre est inscrit ou a participé.
   *
   * @return la liste des évènements du membre
   */

  public List<Evenement> ensembleEvenements() {

    return this.evt;
  }

  /**
   * La liste des évènements auquel le membre est inscrit et qui n'a pas encore
   * eu lieu.
   *
   * @return la liste des évènements à venir du membre.
   */
  public List<Evenement> ensembleEvenementsAvenir() {
    List<Evenement> eventFuturs = new ArrayList<Evenement>();
    
    Iterator<Evenement> it = this.ensembleEvenements().iterator();
    
    while (it.hasNext()) {
      Evenement e = it.next();
      if (e.getDate().isAfter(LocalDateTime.now())) {
        eventFuturs.add(e);
      }
    }

    return eventFuturs;

  }

  /**
   * Définit les informations personnelles du membre.
   *
   * @param info les informations personnelles du membre.
   */
  public void definirInformationPersonnnelle(InformationPersonnelle info) {
    this.info = info;

  }

  /**
   * Renvoie les informations personnelles du membre.
   *
   * @return l'objet contenant les informations personnelles du membre ou
   *         <code>null</code> si elles n'ont pas été définies.
   */
  public InformationPersonnelle getInformationPersonnelle() {
    return this.info;
  }

  // toString, equals & HashCode
  // --------------------------------------------------

  /**
   * Cette fonction permet la sérialisation de la classe Membre.
   *
   * @return un entier.
   */
  @Override
  public int hashCode() {
    return Objects.hash(info, evt);
  }
  /**
   * Cette fonction permet d'afficher le nom, le prenom, l'age et l'adresse du membre.
   *
   * @return un String
   */

  @Override
  public String toString() {
    return this.info.getPrenom() + " " + this.info.getNom() + " age : " 
      + this.info.getAge() +  " adresse : " + this.info.getAdresse();
  }
  /**
   * Cette fonction permet de vérifier que les informations (nom et prénom) de deux membres
   *en faisant appel à la méthode equals de la classe
   *InformationsPersonnelles.
   *
   * @param obj (à la rigueur un membre).
   * @return un boolean, vrai si égaux, faux sinon.
   */

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Membre)) {
      return false;
    }
    Membre a = (Membre) obj;
    return (this.info).equals(a.info);

  }

}
