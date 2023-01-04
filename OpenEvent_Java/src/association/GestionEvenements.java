package association;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Définit les actions pour la gestion des événementd'une association.
 *
 * @author Hippolyte Jean
 */
public class GestionEvenements
    implements InterGestionEvenements, java.io.Serializable {
  
  
  /**
   * Constructeur d'un gestionnaire d'événements.
   *
   * @param listeEvenement Liste d'événements
   */
  public GestionEvenements(List<Evenement> listeEvenement) {
    super();
    this.setListeEvenements(listeEvenement);
  }
  
  
  /**
   * Constructeur d'un gestionnaire d'événements.
   *
   */
  public GestionEvenements() {
    this(new ArrayList<Evenement>());
  }
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Ensemble des Evenement existant.
   */
  private List<Evenement> listeEvenements;
  
  
  /**
   * Modifie la liste des événements.
   *
   * @param liste Nouvelle liste des événements
   */
  public void setListeEvenements(List<Evenement> liste) {
    if (liste == null) {
      this.listeEvenements = new ArrayList<Evenement>();
    } else {
      this.listeEvenements = liste;
    }
  }
  
  
  
  /**
   * Crée un nouvel événement. Plusieurs vérifications sont effectuées : que les
   * dates et heures sont cohérentes et qu'il n'y a pas un chevauchement sur la
   * même période avec un autre événement dans le même lieu. Si il existe déjà
   * un événement ayant le même nom et prenant place au même endroit que ceux du
   * nouvel événement, alors les informations du premier son mise à jour avec
   * celle du second.
   *
   * @param nom le nom de l'événement
   * @param lieu le lieu
   * @param jour le jour dans le mois (nombre de 0 à 31)
   * @param mois le mois dans l'année
   * @param annee l'année
   * @param heure l'heure de la journée (nombre entre 0 et 23)
   * @param minutes les minutes de l'heure (nombre entre 0 et 59)
   * @param duree la durée (en minutes)
   * @param nbParticipants le nombre maximum de participants (0 signifie un
   *        nombre quelconque)
   * @return l'événement créé ou <code>null</code> en cas de problème
   *         (paramètres non valides)
   */
  public Evenement creerEvenement(String nom, String lieu, int jour, Month mois,
      int annee, int heure, int minutes, int duree, int nbParticipants) {
    
    Evenement newEvent = new Evenement(nom, lieu, duree, jour, mois, annee,
        heure, minutes, nbParticipants, new ArrayList<InterMembre>());
    
    if (newEvent.getDate().isBefore(LocalDateTime.now())) {
      return null;
    }
    
    Iterator<Evenement> it = this.ensembleEvenements().iterator();
    
    Evenement event;
    
    while (it.hasNext() && newEvent != null) {
      event = it.next();
      if (newEvent.equals(event)) {
        
        Iterator<Evenement> itBis = this.listeEvenements.iterator();
        
        while (itBis.hasNext()) {
          Evenement e = itBis.next();
          if (!e.equals(newEvent) && !e.pasDeChevauchementLieu(newEvent)) {
            return null;
          }
        }
        
        event.setDate(newEvent.getDate());
        event.setDuree(newEvent.getDuree());
        if (newEvent.getNbParticipantsMax() >= event.getParticipants().size()
            || newEvent.getNbParticipantsMax() == 0) {
          event.setNbParticipantsMax(newEvent.getNbParticipantsMax());
        }
        return null;
      } else if (!newEvent.pasDeChevauchementLieu(event)) {
        return null;
      }
    }
    this.listeEvenements.add(newEvent);
    
    return newEvent;
  }
  
  
  
  /**
   * Crée un nouvel événement. Plusieurs vérifications sont effectuées : que les
   * dates et heures sont cohérentes et qu'il n'y a pas un chevauchement sur la
   * même période avec un autre événement dans le même lieu. Si il existe déjà
   * un événement ayant le même nom et prenant place au même endroit que ceux du
   * nouvel événement, alors les informations du premier son mise à jour avec
   * celle du second.
   *
   * @param nom le nom de l'événement
   * @param lieu le lieu de l'événement
   * @param date la date de l'événement
   * @param heure l'heure de l'événement
   * @param duree la durée (en minutes)
   * @param nbParticipants le nombre maximum de participants (0 signifie un
   *        nombre quelconque)
   * @return l'événement créé ou <code>null</code> en cas de problème
   *         (paramètres non valides)
   */
  public Evenement creerEvenement(String nom, String lieu, String date,
      String heure, int duree, int nbParticipants) {
    
    LocalDateTime d =
        LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(heure));
    
    return this.creerEvenement(nom, lieu, d.getDayOfMonth(), d.getMonth(),
        d.getYear(), d.getHour(), d.getMinute(), duree, nbParticipants);
  }
  
  /**
   * Supprime un événement. Les membres qui étaient inscrits sont
   * automatiquement désinscrits de l'événement supprimé. Si l'événement
   * n'existait pas, la méthode ne fait rien.
   *
   * @param evt l'événement à supprimer.
   */
  public void supprimerEvenement(Evenement evt) {
    if (this.ensembleEvenements().contains(evt)) {
      Iterator<InterMembre> it = evt.getParticipants().iterator();
      InterMembre mb;
      while (it.hasNext()) {
        mb = it.next();
        this.annulerEvenement(evt, mb);
      }
      
      evt.getParticipants().clear();
      
      this.ensembleEvenements().remove(evt);
    }
  }
  
  /**
   * Renvoie l'ensemble des événements de l'association.
   *
   * @return l'ensemble des événements
   */
  public List<Evenement> ensembleEvenements() {
    return this.listeEvenements;
  }
  
  /**
   * Renvoie l'ensemble des événements à venir de l'association.
   *
   * @return l'ensemble des événements à venir
   */
  public List<Evenement> ensembleEvenementAvenir() {
    List<Evenement> eventsFutur = new ArrayList<Evenement>();
    
    Iterator<Evenement> it = this.ensembleEvenements().iterator();
    Evenement event;
    
    while (it.hasNext()) {
      event = it.next();
      if (event.getDate().isAfter(LocalDateTime.now())) {
        eventsFutur.add(event);
      }
    }
    
    return eventsFutur;
  }
  
  /**
   * Un membre est incrit à un événement.
   *
   * @param evt l'événement auquel s'inscrire
   * @param mbr le membre qui s'inscrit
   * @return <code>true</code> s'il n'y a pas eu de problème, <code>false</code>
   *         si l'événement est en conflit de calendrier avec un événement
   *         auquel est déjà inscrit le membre ou si le nombre de participants
   *         maximum est déjà atteint
   */
  public boolean inscriptionEvenement(Evenement evt, InterMembre mbr) {
    if (evt.getParticipants().size() < evt.getNbParticipantsMax()
        && this.ensembleEvenementAvenir().contains(evt)) {
      
      Iterator<Evenement> it = mbr.ensembleEvenementsAvenir().iterator();
      Evenement event;
      
      while (it.hasNext()) {
        event = it.next();
        
        if (!evt.pasDeChevauchementTemps(event)) {
          return false;
        }
        
      }
      
      evt.ajouterParticipant(mbr);
      
      return true;
    }
    return false;
  }
  
  /**
   * Désincrit un membre d'un événement.
   *
   * @param evt l'événement auquel se désinscrire
   * @param mbr le membre qui se désincrit
   * @return si le membre était bien inscrit à l'événement, renvoie
   *         <code>true</code> pour préciser que l'annulation est effective,
   *         sinon <code>false</code> si le membre n'était pas inscrit à
   *         l'événement
   */
  public boolean annulerEvenement(Evenement evt, InterMembre mbr) {
    if (this.ensembleEvenements().contains(evt) && evt.estParticipant(mbr)
        && mbr.ensembleEvenements().contains(evt)) {
      
      evt.getParticipants().remove(mbr);
      mbr.ensembleEvenements().remove(evt);
      
      return true;
    }
    
    return false;
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(listeEvenements);
  }
  
  
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
    GestionEvenements other = (GestionEvenements) obj;
    
    Iterator<Evenement> itThis = this.listeEvenements.iterator();
    Iterator<Evenement> itOther;
    Evenement evtThis;
    Evenement evtOther;
    
    while (itThis.hasNext()) {
      evtThis = itThis.next();
      itOther = other.listeEvenements.iterator();
      while (itOther.hasNext()) {
        evtOther = itOther.next();
        if (evtThis.equals(evtOther)) {
          return false;
        }
      }
    }
    
    return true;
  }
  
  
  @Override
  public String toString() {
    return "GestionEvenements [listeEvenements=" + listeEvenements + "]";
  }
  
  
  
}
