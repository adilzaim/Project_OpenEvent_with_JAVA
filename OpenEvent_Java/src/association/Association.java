package association;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;



/**
 * Attribution à une association d'une liste de membre (instance de la classe GestionEvenement)
 * et d'une liste d'evenement (instance de la classe GestionEvenement), permettant de stocker des membres, des �venements et de g�rer
 * l'inscription des ses membres � ses �v�nements.
 *
 * @author Ange Leyrit
 */
public class Association implements InterGestionAssociation, Serializable {
    
  /**
   * Version de la classe, n�cessaire pour la sauvegarde et le chargement des donn�es 
   * d'une instance de la classe Association.
   */
  private static final long serialVersionUID = 1L;
 
  /**
   * Gestionnaire des evenements de l'association.
   */
  public InterGestionEvenements eventAssociation;
  
  /**
   * Gestionnaire des membres de l'association.
   */
  public InterGestionMembres memberAssociation;
 
  
  /**
   * Constructeur de la classe association, permettant la cr�ation d'un gestionnaire de membre d'un
   * gestionnaire d'association, qui sont seront des attributs de l'instance nouvellement cr�er.
   *
   */
  public Association() {
    super();
    this.eventAssociation = gestionnaireEvenements();
    this.memberAssociation = gestionnaireMembre();
  }
  
  
  /**
   * Renvoie le gestionnaire d'événements de l'association. L'objet retourné est
   * unique. Au premier appel de la méthode, il est créé et aux appels suivants,
   * c'est la référence sur cet objet qui est retournée.
   *
   * @return le gestionnaire d'événements de l'association
   */
  public InterGestionEvenements gestionnaireEvenements() {
    if (getEventAssociation() == null) {
      setEventAssociation(new GestionEvenements());
    }
    return getEventAssociation();
  }

  /**
   * Renvoie le gestionnaire de membres de l'association. L'objet retourné est
   * unique. Au premier appel de la méthode, il est créé et aux appels suivants,
   * c'est la référence sur cet objet qui est retournée.
   *
   * @return le gestionnaire de membres de l'association
   */
  public InterGestionMembres gestionnaireMembre() {
    if (getMemberAssociation() == null) {
      setMemberAssociation(new GestionMembres());
    }
    return getMemberAssociation();
  }
  
  /**
   * Enregistre dans un fichier toutes les données de l'association,
   * c'est-à-dire l'ensemble des membres et des événements.
   *
   * @param nomFichier le fichier dans lequel enregistrer les données
   * @throws IOException en cas de problème d'écriture dans le fichier
   */
  public void sauvegarderDonnees(String nomFichier) throws IOException {
    
    FileOutputStream fos = new FileOutputStream(nomFichier);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    
    oos.writeObject(getEventAssociation());
    oos.writeObject(getMemberAssociation());
    oos.close();
    
  }

  /**
   * Charge à partir d'un fichier toutes les données de l'association,
   * c'est-à-dire un ensemble de membres et d'événements. Si des membres et des
   * événements avaient déjà été définis, ils sont écrasés par le contenu trouvé
   * dans le fichier.
   *
   * @param nomFichier le fichier à partir duquel charger les données
   * @throws IOException en cas de problème de lecture dans le fichier
   */
  public void chargerDonnees(String nomFichier) throws IOException {
    
    FileInputStream fis = new FileInputStream(nomFichier);
    ObjectInputStream ois = new ObjectInputStream(fis);
    
    try {
      setEventAssociation((GestionEvenements) ois.readObject());
    } catch (ClassNotFoundException e1) {
      
      e1.printStackTrace();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      setMemberAssociation((GestionMembres) ois.readObject());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ois.close();
    
  }
  
  /**
   * Renvoie le gestionnaire d'evenement
   * de l'association.
   *
   * @return le gestionnaire d'evenement
   */
  private InterGestionEvenements getEventAssociation() {
    return eventAssociation;
  }
  
  /**
   *  Modifie le gestionnaire des evenements de l'association.
   *
   * @param eventAssociation le nouveau gestionnaire ( )
   */
  private void setEventAssociation(GestionEvenements eventAssociation) {
    this.eventAssociation = eventAssociation;
  }
  
  /**
   * Renvoie le gestionnaire de membre
   * de l'association.
   *
   * @return gestionnaire de membre
   */
  private InterGestionMembres getMemberAssociation() {
    return memberAssociation;
  }
  
  /**
   * Modifie le gestionnaire de membre.
   *
   * @param memberAssociation Le nouveau gestionnaire
   */
  private void setMemberAssociation(GestionMembres memberAssociation) {
    this.memberAssociation = memberAssociation;
  }


  @Override
  public String toString() {
    return "Association [eventAssociation=" + eventAssociation
        + ", memberAssociation=" + memberAssociation + "]";
  }


  @Override
  public int hashCode() {
    return Objects.hash(eventAssociation, memberAssociation);
  }

  /**
   * M�thode d'�galit� entre deux instance d'Association, va utiliser
   * les m�thodes equals des classes GestionEvenements et GestionMembres
   * faisant eux m�me appel aux equals des classes Evenement et Membre, pour
   * finir l'equals de membre utilisera l'equals d'Information Personnelle.
   *
   * @return <code>true</code> si il ya une �galit� totale entre les deux instances
   * 		<code>false</code> sinon
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
    Association other = (Association) obj;
    return this.eventAssociation.equals(other.eventAssociation)
        && this.memberAssociation.equals(other.memberAssociation);
  }
  
  

}
