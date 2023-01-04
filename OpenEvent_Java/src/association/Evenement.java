package association;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Classe décrivant l'ensemble des informations concernant un événement.
 *
 * @author Hippolyte Jean
 */
public class Evenement implements java.io.Serializable {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Créé un événement avec toutes ses informations.
   *
   * @param nom Le nom de l'événement
   * @param lieu Le lieu où se tient l'événement
   * @param date La date à laquelle l'événement a lieu
   * @param duree la durée de l'événement
   * @param nbParticipantsMax Le nombre maximum de participants à l'événement
   * @param participants L'ensemble des membres participant à l'événement
   */
  public Evenement(String nom, String lieu, LocalDateTime date, int duree,
      int nbParticipantsMax, List<InterMembre> participants) {
    super();
    this.setNom(nom);
    this.setLieu(lieu);
    this.setDate(date);
    this.setDuree(duree);
    this.setNbParticipantsMax(nbParticipantsMax);
    this.setParticipants(participants);
  }
  
  /**
   * Créé un événement avec toutes ses informations.
   *
   * @param nom Le nom de l'événement
   * @param lieu Le lieu où se tient l'événement
   * @param duree la durée de l'événement
   * @param jour le jour de l'événement
   * @param mois le mois de l'événement
   * @param annee l'année de l'événement
   * @param heure l'heure à laquelle l'événement à lieu
   * @param minutes la minute à laquelle l'événement à lieu
   * @param nbParticipantsMax Le nombre maximum de participants à l'événement
   * @param participants L'ensemble des membres participant à l'événement
   */
  public Evenement(String nom, String lieu, int duree, int jour, Month mois,
      int annee, int heure, int minutes, int nbParticipantsMax,
      List<InterMembre> participants) {
    this(nom, lieu, null, duree, nbParticipantsMax, participants);
    
    if (mois == null) {
      mois = Month.JANUARY;
    }
    LocalDateTime d;
    try {
      d = LocalDateTime.of(annee, mois, jour, heure, minutes);
    } catch (DateTimeException e) {
      d = LocalDateTime.now();
    }
    
    this.setDate(d);
    
  }
  
  /**
   * Le nom de l'événement.
   */
  private String nom;
  
  /**
   * Le lieu où se tient l'événement.
   */
  private String lieu;
  
  /**
   * La date à laquelle l'événement a lieu.
   */
  private LocalDateTime date;
  
  /**
   * La durée de l'événement.
   */
  private int duree;
  
  /**
   * Le nombre maximum de participants à l'événement.
   */
  private int nbParticipantsMax;
  
  /**
   * L'ensemble des membres participant à l'événement.
   */
  private List<InterMembre> participants;
  
  /**
   * Retourne vrai si deux événements ne se chevauchent pas dans le même lieu en
   * même temps.
   *
   * @param evt L'événement à comparer avec celui appelant la méthode
   * 
   * @return Vrai si les deux événements n'ont pas lieu au même endroit et en
   *         même temps, sinon faux
   */
  public boolean pasDeChevauchementLieu(Evenement evt) {
    boolean ret = true;
    if (!(this.pasDeChevauchementTemps(evt))) {
      ret = !this.getLieu().equals(evt.getLieu());
    }
    return ret;
  }
  
  /**
   * Retourne vrai si deux événements ne se chevauchent pas dans le temps
   * (indépendamment du lieu).
   *
   * @param evt L'événement à comparer avec celui appelant la méthode
   * 
   * @return Vrai si les deux événements n'ont pas lieu en même temps, sinon
   *         faux
   */
  public boolean pasDeChevauchementTemps(Evenement evt) {
    LocalDateTime thisFin = this.getDate().plusHours(this.duree);
    LocalDateTime evtFin = evt.getDate().plusHours(evt.duree);
    return thisFin.isBefore(evt.getDate()) || thisFin.isEqual(evt.getDate())
        || evtFin.isBefore(this.getDate()) || evtFin.isEqual(this.getDate());
  }
  
  /**
   * Retourne vrai si le membre passé en paramètre fait partie des participants
   * de l'événement.
   *
   * @param mb Le membre à comparer à ceux présent dans la liste des
   *        participants de l'événement
   * @return Vrai si le membre passé en paramètre est dans la liste des
   *         participants
   */
  public boolean estParticipant(InterMembre mb) {
    Iterator<InterMembre> it = this.participants.iterator();
    InformationPersonnelle infoMb = mb.getInformationPersonnelle();
    InformationPersonnelle info;
    boolean ret = false;
    
    while (it.hasNext() && ret != true) {
      info = it.next().getInformationPersonnelle();
      if (infoMb.equals(info)) {
        ret = true;
      }
    }
    
    return ret;
  }
  
  /**
   * Ajoute un participant à la collection des participants à l'événement.
   *
   * @param mb Le membre à ajouter à la liste des participants de l'événement
   * @return Vrai si le membre ne faisait pas déjà partie de l'événement, sinon
   *         faux
   */
  public boolean ajouterParticipant(InterMembre mb) {
    boolean ret = false;
    if (this.date.isAfter(LocalDateTime.now())) {
      if (this.participants.size() < this.nbParticipantsMax
          && !(this.estParticipant(mb))) {
        this.participants.add(mb);
        mb.ensembleEvenements().add(this);
        ret = true;
      }
    }
    return ret;
  }
  
  /**
   * Retire un participant à la collection des participants à l'événement.
   *
   * @param mb Le membre à retirer de la liste des participants de l'événement
   * @return Vrai si le membre ne a bien été retiré de l'événement, sinon faux
   */
  public boolean retirerParticipant(InterMembre mb) {
    boolean ret = false;
    if (this.estParticipant(mb)) {
      mb.ensembleEvenements().remove(this);
      this.participants.remove(mb);
      ret = true;
    }
    
    return ret;
  }
  
  /**
   * Renvoie le nom de l'événement.
   *
   * @return le nom de l'événement
   */
  public String getNom() {
    return nom;
  }
  
  /**
   * Modifie le nom de l'événement.
   *
   * @param nom Le nouveau nom de l'événement
   */
  public void setNom(String nom) {
    if (nom == null) {
      this.nom = "";
    } else {
      this.nom = nom;
    }
  }
  
  /**
   * Renvoie le lieu de l'événement.
   *
   * @return le lieu de l'événement
   */
  public String getLieu() {
    return lieu;
  }
  
  /**
   * Modifie le lieu de l'événement.
   *
   * @param lieu Le nouveau lieu de l'événement
   */
  public void setLieu(String lieu) {
    if (lieu == null) {
      this.lieu = "";
    } else {
      this.lieu = lieu;
    }
  }
  
  /**
   * Renvoie la date de l'événement.
   *
   * @return la date de l'événement
   */
  public LocalDateTime getDate() {
    return date;
  }
  
  /**
   * Modifie la date de l'événement.
   *
   * @param date La nouvelle date de l'événement
   */
  public void setDate(LocalDateTime date) {
    if (date == null) {
      this.date = LocalDateTime.now();
    } else {
      this.date = date;
    }
  }
  
  /**
   * Renvoie la durée de l'événement.
   *
   * @return la durée de l'événement
   */
  public int getDuree() {
    return duree;
  }
  
  /**
   * Modifie la durée de l'événement.
   *
   * @param duree La nouvelle durée de l'événement
   */
  public void setDuree(int duree) {
    if (duree < 0) {
      this.duree = -duree;
    } else {
      this.duree = duree;
    }
  }
  
  /**
   * Renvoie le nombre de participants de l'événement.
   *
   * @return le nombre de participants de l'événement
   */
  public int getNbParticipantsMax() {
    return nbParticipantsMax;
  }
  
  /**
   * Modifie le nombre maximum de participants de l'événement.
   *
   * @param nbParticipantsMax Le nouveau nombre maximum de participants de
   *        l'événement
   */
  public void setNbParticipantsMax(int nbParticipantsMax) {
    if (nbParticipantsMax < 0) {
      this.nbParticipantsMax = 0;
    } else {
      this.nbParticipantsMax = nbParticipantsMax;
    }
  }
  
  /**
   * Renvoie l'ensemble des participants de l'événement.
   *
   * @return l'ensemble des participants de l'événement
   */
  public List<InterMembre> getParticipants() {
    return participants;
  }
  
  /**
   * Modifie l'ensemble des participants de l'événement.
   *
   * @param participants le nouvel ensemble des participants de l'événement
   */
  public void setParticipants(List<InterMembre> participants) {
    if (participants == null) {
      this.participants = new ArrayList<InterMembre>();
    } else {
      this.participants = participants;
    }
  }
  
  /**
   * Méthode permettant la séréalisation de la classe Evenement.
   */
  @Override
  public int hashCode() {
    return Objects.hash(date, duree, lieu, nbParticipantsMax, nom,
        participants);
  }
  
  
  /**
   * Compare l'instance de Evenement receveur avec l'instance de Evenement passé
   * en paramètre.
   *
   * @param obj L'instance d'Evenement à comparer
   * 
   * @return Renvoie <code>true</code> si les attributs respectif nom et lieu
   *         des deux événement sont identiques, sinon retourne
   *         <code>false</code>.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Evenement other = (Evenement) obj;
    
    return this.nom.equals(other.nom) && this.lieu.equals(other.lieu);
  }
  
  /**
   * Méthode permettant d'afficher une instance de Evenement.
   *
   * @return Un String
   */
  @Override
  public String toString() {
    return this.nom + " à " + lieu + ", le " + date + " (pendant " + duree
        + " heures), " + nbParticipantsMax + " participants maximum";
  }
  
  
}
