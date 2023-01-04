package association;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Classe décrivant la gestion des mombres de l'association.
 *
 * @author Adil Zaim
 */

public class GestionMembres implements InterGestionMembres, Serializable {
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;
  
  // Attributs statiques -------------------------------------------------
  /**
   * Ensemble de tous les membre existants.
   */
  private Set<InterMembre> membre;
  /**
   * Ensemble de tous les membre existants.
   */
  private InterMembre president;
  
  /**
   * Constructeur public qui instancier la liste des membres.
   * 
   */
  
  public GestionMembres() {
    this.membre = new HashSet<>();
  }
  
  /**
   * Ajoute un membre dans l'association. mis à jour l'adresse 
   * et l'age du membre si il est présent dans l'association.
   *
   * @param membre le membre à rajouter ou à mis a jour.
   * @return <code>true</code> si le membre a bien été ajouté,
   *         <code>false</code> si le membre était déjà présent (dans ce cas il
   *         n'est pas ajouté à nouveau mais son adresse et son age est mis à jour)
   */
  @Override
  public boolean ajouterMembre(InterMembre membre) {
    if (membre == null) {
      return false;
    }
    // on modifier l'age et l'adress du memebre si il existe.
    for (InterMembre m : this.membre) {
      m = (Membre) m;
      if (m.equals(membre)) {
        m.getInformationPersonnelle()
            .setAdresse(membre.getInformationPersonnelle().getAdresse());
        m.getInformationPersonnelle()
            .setAge(membre.getInformationPersonnelle().getAge());
        return false;
      }
    }
    this.membre.add(membre);
    return true;
  }
  
  /**
   * Supprime un membre de l'association.
   *
   * @param membre le membre à supprimer
   * @return <code>true</code> si le membre était présent et a été supprimé,
   *         <code>false</code> si le membre n'était pas dans l'association
   */
  
  @Override
  public boolean supprimerMembre(InterMembre membre) {
    if (membre == this.president()) {
      return false;
    }
    if (this.membre.remove(membre)) {
      return true;
    }
    return false;
  }
  
  /**
   * Désigne le président de l'association. Il doit être un des membres de
   * l'association.
   *
   * @param membre le membre à désigner comme président.
   * @return <code>false</code> si le membre n'était pas dans l'association (le
   *         président n'est alors pas positionné), <code>true</code> si le
   *         membre a été nommé président
   */
  @Override
  public boolean designerPresident(InterMembre membre) {
    if (membre == null || !this.membre.contains(membre)) {
      return false;
    }
    this.president = membre;
    return true;
  }
  
  /**
   * Renvoie l'ensemble des membres de l'association.
   *
   * @return l'ensemble des membres de l'association.
   */
  @Override
  public Set<InterMembre> ensembleMembres() {
    return this.membre;
  }
  
  /**
   * Renvoie le président de l'association.
   *
   * @return le membre président de l'association s'il avait été désigné sinon
   *         retourne <code>null</code>
   */
  @Override
  public InterMembre president() {
    return this.president;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(membre, president);
  }
  /**
   * Methode d'�galit� entre deux instance d'Association, va utiliser
   * les methodes equals de la classe membre.
   *
   * @return <code>true</code> si il ya l'instance passé en paramettre 
   *                        est equale a celle qui qui fait appelle cette methode.
   *        <code>false</code> sinon
   */
  @SuppressWarnings("unlikely-arg-type")
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
    GestionMembres other = (GestionMembres) obj;
    for (InterMembre m : membre) {
      m = (Membre) m;
      if (m.equals(other)) {
        return true;
      }
      
    }
    return false;
  }
  
}
