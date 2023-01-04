package ui;

import association.Association;
import association.Evenement;
import association.GestionEvenements;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Classe controleur de l'interface du gestionnaire d'évenement et de membre
 * d'une association, permet par diff�rents boutons et zone de texte de 
 * cr�er des membres, des �venements, de les suspprimer et de les afficher.
 * Les membres peuvent �tre �galement inscrit � des �vnement.
 *
 * @author Hippolyte Jean, Ange Leyrit, Océane Pezennec, Adil Zaim
 */
public class Controleur implements Initializable {
  
  /**
   * Zone de saisie/affichage de l'adresse d'un membre.
   * 
   */
  @FXML
  private TextField entreAdresseMembre;
  
  /**
   * Zone de saisie/affichage de l'âge d'un membre.
   * 
   */
  @FXML
  private TextField entreAgeMembre;
  
  /**
   * Zone de saisie/affichage de la date d'un évenement.
   * 
   */
  @FXML
  private TextField entreeDateEvt;
  
  /**
   * Zone de saisie/affichage de la durée d'un évenement.
   * 
   */
  @FXML
  private TextField entreeDureeEvt;
  
  /**
   * Zone de saisie/affichage de l'heure de début d'un évenement.
   * 
   */
  @FXML
  private TextField entreeHeureEvt;
  
  /**
   * Zone de saisie/affichage du lieu de déroulementt d'un évenement.
   * 
   */
  @FXML
  private TextField entreeLieuEvt;
  
  /**
   * Zone de saisie/affichage du nombre de participant max pouvant s'inscrire à un
   * évenement.
   * 
   */
  @FXML
  private TextField entreeMaxParticipantsEvt;
  
  /**
   * Zone de saisie/affichage du nom d'un évenement.
   * 
   */
  @FXML
  private TextField entreeNomEvt;
  
  /**
   * Zone de saisie/affichage du nom d'un membre.
   * 
   */
  @FXML
  private TextField entreeNomMembre;
  
  /**
   * Zone de saisie/affichage du prenom d'un membre.
   * 
   */
  @FXML
  private TextField entreePrenomMembre;
  
  /**
   * Zone de titre au dessus de l'affichage de la liste d'evenement.
   * 
   */
  @FXML
  private Label labelListeAfficheeEvt;
  
  /**
   * Zone de titre au dessus de l'affichage de la liste de membres.
   * 
   */
  @FXML
  private Label labelListeAfficheeMembre;
  
  /**
   * Liste des evenements qui va contenir les evenement de l'association
   * en cours de gestions par l'application. Va �tre utiliser pour
   * l'affichage de la liste des �v�nements dans la zone pr�vues � cette effet.
   * 
   */
  @FXML
  private ListView<Evenement> listeEvenements;
  
  /**
   * Liste des membres qui va contenir les membres de l'association
   * en cours de gestions par l'application. Va �tre utiliser pour
   * l'affichage de la liste des membres dans la zone pr�vues � cette effet.
   * 
   */
  @FXML
  private ListView<Membre> listeMembres;
  
  /**
   * Zone de texte pour afficher des messages � destination de l'utilisateur.
   * 
   */
  @FXML
  private TextArea message;
  
  @FXML
  private Font x1;
  
  @FXML
  private Color x2;
  
  /**
   * Gestionnaire de l'association qui sera utiliser par l'interface, comprend
   * un gestionnaire d'évement et un gestionnaire de membre.
   * 
   */
  private Association gestionnaireAssociation;
  
  /**
   * Si un membre est sélectionné dans la liste, affiche ses informations
   * personnelles dans les 4 champs en haut � droite de la fenêtre.
   *
   */
  @FXML
  void actionBoutonAfficherMembreSelectionneMembre(ActionEvent event) {
    Membre memb = listeMembres.getSelectionModel().getSelectedItem();
    
    if (memb != null) {
      this.entreAdresseMembre.setText(memb.info.getAdresse());
      this.entreAgeMembre.setText(Integer.toString(memb.info.getAge()));
      this.entreeNomMembre.setText(memb.info.getNom());
      this.entreePrenomMembre.setText(memb.info.getPrenom());
    } else {
      this.message.setText("Veuillez sélectionner un membre dans la liste !");
    }
  }
  
  /**
   * Affiche dans la liste de gauche, les participants inscrits a l'evenement
   * dont les informations sont affichées, dans les 4 champs en 
   * haut � gauche de la fen�tre.
   *
   */
  @FXML
  void actionBoutonAfficherParticipantsEvt(ActionEvent event) {
    this.listeMembres.getItems().clear();
    this.message.setText("");
    if (this.entreeDateEvt.getText().length() != 0
        && this.entreeDureeEvt.getText().length() != 0
        && this.entreeHeureEvt.getText().length() != 0
        && this.entreeLieuEvt.getText().length() != 0
        && this.entreeMaxParticipantsEvt.getText().length() != 0
        && this.entreeNomEvt.getText().length() != 0) {
      
      Evenement evt;
      try {
        // Créer nouvel événement sans l'ajouter dans le gestionnaire des
        // événements courant, et être sûr d'avoir un retour non null
        evt = new GestionEvenements().creerEvenement(
            this.entreeNomEvt.getText(), this.entreeLieuEvt.getText(),
            this.entreeDateEvt.getText(), this.entreeHeureEvt.getText(),
            Integer.parseInt(this.entreeDureeEvt.getText()),
            Integer.parseInt(this.entreeMaxParticipantsEvt.getText()));
        
        Iterator<Evenement> it = this.gestionnaireAssociation
            .gestionnaireEvenements().ensembleEvenements().iterator();
        
        while (it.hasNext()) {
          Evenement eventListe = it.next();
          if (eventListe.equals(evt)) {
            this.labelListeAfficheeMembre
                .setText("Participants de l'événement " + eventListe.getNom());
            Iterator<InterMembre> itmb =
                eventListe.getParticipants().iterator();
            
            while (itmb.hasNext()) {
              this.listeMembres.getItems().add((Membre) itmb.next());
            }
          }
        }
        
      } catch (NumberFormatException e) {
        this.message.setText(
            "Les champs de durée et de nombre maximum de participants doivent contenir un entier!");
      } catch (DateTimeParseException e) {
        message.setText(
            "Le format du champ de la date ou de l'heure de l'événement est invalide!\n"
                + "Veuillez entrer une date de la forme AAAA-MM-JJ\n"
                + "Veuillez entrer une heure de la forme HH:MM");
      }
      
    } else {
      this.message.setText("Tous les champs doivent être remplis !");
    }
  }
  
  
  /**
   * Affiche dans la liste d'affichage de gauche pr�vues a cette effet tous les membres de l’association.
   *
   */
  @FXML
  void actionBoutonAfficherTousMembresMembre(ActionEvent event) {
    this.listeMembres.getItems().clear();
    this.labelListeAfficheeMembre
        .setText("Ensemble des membres de l'Association : ");
    Iterator<InterMembre> it = this.gestionnaireAssociation.gestionnaireMembre()
        .ensembleMembres().iterator();
    
    while (it.hasNext()) {
      this.listeMembres.getItems().add((Membre) it.next());
    }
  }
  
  @FXML
  void actionBoutonEvenementSelectionneEvt(ActionEvent event) {
    Evenement evt = listeEvenements.getSelectionModel().getSelectedItem();
    listeMembres.getItems().clear();
    
    if (evt != null) {
      
      this.entreeNomEvt.setText(evt.getNom());
      this.entreeLieuEvt.setText(evt.getLieu());
      this.entreeDateEvt.setText(evt.getDate().toLocalDate().toString());
      this.entreeHeureEvt.setText(evt.getDate().toLocalTime().toString());
      this.entreeMaxParticipantsEvt
          .setText(Integer.toString(evt.getNbParticipantsMax()));
      this.entreeDureeEvt.setText(Integer.toString(evt.getDuree()));
      
    } else {
      this.message
          .setText("Veuillez sélectionner un événement dans la liste !");
    }
  }
  
  /**
   * Affiche dans la liste d'affichage de droite les �v�nements � venir de l’association.
   *
   */
  @FXML
  void actionBoutonEvenementsFutursAssociation(ActionEvent event) {
    this.labelListeAfficheeEvt
        .setText("Ensemble des événements de l'association.");
    this.listeEvenements.getItems().clear();
    Iterator<Evenement> it = this.gestionnaireAssociation
        .gestionnaireEvenements().ensembleEvenementAvenir().iterator();
    
    while (it.hasNext()) {
      this.listeEvenements.getItems().add(it.next());
    }
  }
  
  /**
   * Affiche dans la liste d'affichage de droite tous les événements à venir du membre dont
   * les informations sont dans les 4 champs en haut � gauche de la fenetre.
   *
   */
  @FXML
  void actionBoutonEvenementsFutursMembre(ActionEvent event) {
    this.message.setText("");
    if (this.entreeNomMembre.getText().length() != 0
        && this.entreePrenomMembre.getText().length() != 0
        && this.entreAgeMembre.getText().length() != 0
        && this.entreAdresseMembre.getText().length() != 0) {
      
      Membre memb;
      boolean trouve = false;
      try {
        memb = new Membre(new InformationPersonnelle(
            this.entreeNomMembre.getText(), this.entreePrenomMembre.getText(),
            this.entreAdresseMembre.getText(),
            Integer.parseInt(this.entreAgeMembre.getText())));
        
        Iterator<InterMembre> itmb = this.gestionnaireAssociation
            .gestionnaireMembre().ensembleMembres().iterator();
        
        InterMembre mb;
        while (itmb.hasNext() && !trouve) {
          mb = itmb.next();
          if (memb.equals(mb)) {
            trouve = true;
            this.labelListeAfficheeEvt.setText("Evenements du membre");
            this.listeEvenements.getItems().clear();
            this.listeEvenements.getItems()
                .addAll(mb.ensembleEvenementsAvenir());
          }
        }
        
        
      } catch (NumberFormatException e) {
        this.message
            .setText("Le chams âge doit contenir un nombre sans virgules");
      }
      if (trouve) {
        this.message.setText("Affichage des événements du membre !");
      } else {
        this.message.setText("Ce membre n'existe pas !");
      }
    } else {
      this.message.setText("Tous les champs doivent être remplis !");
    }
  }
  
  /**
   * Affiche dans la liste de droite tous les evenement du membre dont les
   * informations sont dans les 4 champs en haut � gauche.
   *
   */
  @FXML
  void actionBoutonEvenementsMembreMembre(ActionEvent event) {
    this.message.setText("");
    if (this.entreeNomMembre.getText().length() != 0
        && this.entreePrenomMembre.getText().length() != 0
        && this.entreAgeMembre.getText().length() != 0
        && this.entreAdresseMembre.getText().length() != 0) {
      
      Membre memb;
      boolean trouve = false;
      try {
        memb = new Membre(new InformationPersonnelle(
            this.entreeNomMembre.getText(), this.entreePrenomMembre.getText(),
            this.entreAdresseMembre.getText(),
            Integer.parseInt(this.entreAgeMembre.getText())));
        
        Iterator<InterMembre> itmb = this.gestionnaireAssociation
            .gestionnaireMembre().ensembleMembres().iterator();
        
        InterMembre mb;
        while (itmb.hasNext() && !trouve) {
          mb = itmb.next();
          if (memb.equals(mb)) {
            trouve = true;
            this.labelListeAfficheeEvt.setText("Evenements du membre");
            this.listeEvenements.getItems().clear();
            this.listeEvenements.getItems().addAll(mb.ensembleEvenements());
          }
        }
        
        
      } catch (NumberFormatException e) {
        this.message
            .setText("Le chams âge doit contenir un nombre sans virgules");
      }
      if (trouve) {
        this.message.setText("Affichage des événements du membre !");
      } else {
        this.message.setText("Ce membre n'existe pas !");
      }
    } else {
      this.message.setText("Tous les champs doivent être remplis !");
    }
  }
  
  /**
   * Si un membre est sélectionné dans la liste de gauche et un événement est
   * sélectionné dans la liste de droite, le membre est désinscrit de cet
   * événement.
   *
   */
  @FXML
  void actionBoutonIDesiscrireMembreEvenement(ActionEvent event) {
    Membre mb = listeMembres.getSelectionModel().getSelectedItem();
    Evenement evt = listeEvenements.getSelectionModel().getSelectedItem();
    
    if (mb != null && evt != null) {
      
      boolean ret = this.gestionnaireAssociation.gestionnaireEvenements()
          .annulerEvenement(evt, mb);
      
      if (ret) {
        this.message.setText("Membre à bien été désinscrit !");
      } else {
        this.message.setText("Ce membre n'est pas inscrit à cet événement !");
      }
    } else {
      this.message.setText("Veuillez sélectionner un membre et un événement\n"
          + "dans leur liste respective !");
    }
  }
  
  /**
   * Si un membre est sélectionné dans la liste de gauche et un événement est
   * sélectionné dans la liste de droite, le membre est inscrit à cet événement
   * (dans la limite des places disponibles).
   *
   */
  @FXML
  void actionBoutonInscrireMembreEvenement(ActionEvent event) {
    Membre mb = listeMembres.getSelectionModel().getSelectedItem();
    Evenement evt = listeEvenements.getSelectionModel().getSelectedItem();
    
    if (mb != null && evt != null) {
      
      boolean ret = this.gestionnaireAssociation.gestionnaireEvenements()
          .inscriptionEvenement(evt, mb);
      
      if (ret) {
        this.message.setText("Membre à bien été inscrit !");
      } else {
        this.message.setText("Ce membre ne peux pas être inscrit !\n"
            + "Le nombre maximum de participant est atteint\n"
            + "Ou il y a un conflit avec un autre événement auquel il est inscrit");
      }
    } else {
      this.message.setText("Veuillez sélectionner un membre et un événement\n"
          + "dans leur liste respective !");
    }
  }
  
  /**
   * Efface le contenu des champs d’un événement afin de rajouter un nouvel
   * événement.
   *
   */
  @FXML
  void actionBoutonNouveauEvt(ActionEvent event) {
    this.entreeDateEvt.setText(null);
    this.entreeDureeEvt.setText(null);
    this.entreeHeureEvt.setText(null);
    this.entreeMaxParticipantsEvt.setText(null);
    this.entreeLieuEvt.setText(null);
    this.entreeNomEvt.setText(null);
  }
  
  
  /**
   * Efface le contenu des 4 champs d’un membre afin de rajouter un nouveau
   * membre.
   *
   */
  @FXML
  void actionBoutonNouveauMembre(ActionEvent event) {
    this.message.setText("");
    this.entreAdresseMembre.setText(null);
    this.entreAgeMembre.setText(null);
    this.entreeNomMembre.setText(null);
    this.entreePrenomMembre.setText(null);
  }
  
  /**
   * Efface de la liste des événements l’événement dont les informations sont
   * affichées dans les 4 champs en haut � droite.
   *
   */
  @FXML
  void actionBoutonSupprimerEvt(ActionEvent event) {
    this.message.setText("");
    if (this.entreeDateEvt.getText().length() != 0
        && this.entreeDureeEvt.getText().length() != 0
        && this.entreeHeureEvt.getText().length() != 0
        && this.entreeLieuEvt.getText().length() != 0
        && this.entreeMaxParticipantsEvt.getText().length() != 0
        && this.entreeNomEvt.getText().length() != 0) {
      
      Evenement evt;
      try {
        // Créer nouvel événement sans l'ajouter dans le gestionnaire des
        // événements courant, et être sûr d'avoir un retour non null
        evt = new GestionEvenements().creerEvenement(
            this.entreeNomEvt.getText(), this.entreeLieuEvt.getText(),
            this.entreeDateEvt.getText(), this.entreeHeureEvt.getText(),
            Integer.parseInt(this.entreeDureeEvt.getText()),
            Integer.parseInt(this.entreeMaxParticipantsEvt.getText()));
        
        this.gestionnaireAssociation.gestionnaireEvenements()
            .supprimerEvenement(evt);
        
        this.message.setText("L'événement à bien été supprimé !");
        
      } catch (NumberFormatException e) {
        this.message.setText(
            "Les champs de durée et de nombre maximum de participants doivent contenir un entier!");
      } catch (DateTimeParseException e) {
        message.setText(
            "Le format du champ de la date ou de l'heure de l'événement est invalide!\n"
                + "Veuillez entrer une date de la forme AAAA-MM-JJ\n"
                + "Veuillez entrer une heure de la forme HH:MM");
      }
      
    } else {
      this.message.setText("Tous les champs doivent être remplis !");
    }
  }
  
  
  /**
   * Efface de la liste des membres, le membre dont les informations sont
   * affichées dans les 4 champs en hauts � gauche.
   *
   */
  @FXML
  void actionBoutonSupprimerMembre(ActionEvent event) {
    this.message.setText("");
    if (this.entreeNomMembre.getText().length() != 0
        && this.entreePrenomMembre.getText().length() != 0
        && this.entreAgeMembre.getText().length() != 0
        && this.entreAdresseMembre.getText().length() != 0) {
      
      Membre memb;
      try {
        memb = new Membre(new InformationPersonnelle(
            this.entreeNomMembre.getText(), this.entreePrenomMembre.getText(),
            this.entreAdresseMembre.getText(),
            Integer.parseInt(this.entreAgeMembre.getText())));
        
        this.gestionnaireAssociation.gestionnaireMembre().supprimerMembre(memb);
        
        this.message.setText("Le membre à bien été supprimé !");
        
      } catch (NumberFormatException e) {
        this.message
            .setText("Le chams âge doit contenir un nombre sans virgules");
      }
      this.message.setText("Membre bien supprimer !");
    } else {
      this.message.setText("Tous les champs doivent être remplis !");
    }
  }
  
  /**
   * Afficher dans la liste d'affichage des evenements tous les événements de l’association.
   *
   */
  @FXML
  void actionBoutonTousEvenementsAssociationEvt(ActionEvent event) {
    this.labelListeAfficheeEvt
        .setText("Ensemble des événements de l'association.");
    this.listeEvenements.getItems().clear();
    Iterator<Evenement> it = this.gestionnaireAssociation
        .gestionnaireEvenements().ensembleEvenements().iterator();
    
    while (it.hasNext()) {
      this.listeEvenements.getItems().add(it.next());
    }
    
  }
  
  /**
   * Lit les 4 champs d’un membre. Si le membre existait déjà, ses informations
   * personnelles sont mises à jour, sinon, un nouveau membre est créé.
   *
   */
  @FXML
  void actionBoutonValiderMembre(ActionEvent event) {
    
    if ((this.entreeNomMembre.getText().length() != 0
        && this.entreePrenomMembre.getText().length() != 0)
        || this.entreAgeMembre.getText().length() != 0
        || this.entreAdresseMembre.getText().length() != 0) {
      
      
      Membre memb;
      try {
        int age = 0;
        if (this.entreAgeMembre.getText().length() != 0) {
          age = Integer.parseInt(this.entreAgeMembre.getText());
        }
        
        String adresse = "";
        if (this.entreAdresseMembre.getText().length() != 0) {
          adresse = this.entreAdresseMembre.getText();
        }
        
        memb = new Membre(
            new InformationPersonnelle(this.entreeNomMembre.getText(),
                this.entreePrenomMembre.getText(), adresse, age));
        
        if (!this.gestionnaireAssociation.memberAssociation
            .ajouterMembre(memb)) {
          
          this.message.setText("Information mis à jour !");
        } else {
          this.message.setText("Membre ajouter !");
        }
        
      } catch (NumberFormatException e) {
        this.message
            .setText("Le chams âge doit contenir un nombre sans virgules");
      }
    } else {
      this.message.setText("les champs Nom et Prenom doivent être remplis !");
    }
  }
  
  /**
   * Lit les 4 champsen hauts � gauche d'un evenement. Si l’événement existait déjà, ses
   * informations sont mises à jour, sinon, un nouvel événement est créé.
   *
   */
  @FXML
  void actionBoutonValiderEvt(ActionEvent event) {
    this.message.setText("");
    if (this.entreeDateEvt.getText().length() != 0
        && this.entreeDureeEvt.getText().length() != 0
        && this.entreeHeureEvt.getText().length() != 0
        && this.entreeLieuEvt.getText().length() != 0
        && this.entreeMaxParticipantsEvt.getText().length() != 0
        && this.entreeNomEvt.getText().length() != 0) {
      
      Evenement evt;
      try {
        evt = this.gestionnaireAssociation.eventAssociation.creerEvenement(
            this.entreeNomEvt.getText(), this.entreeLieuEvt.getText(),
            this.entreeDateEvt.getText(), this.entreeHeureEvt.getText(),
            Integer.parseInt(this.entreeDureeEvt.getText()),
            Integer.parseInt(this.entreeMaxParticipantsEvt.getText()));
        if (evt != null) {
          this.message.setText("Evénement ajouté !");
        } else {
          this.message.setText(
              "Création impossible, veuillez vérifier la disponibilité du lieux choisi !");
        }
        
      } catch (NumberFormatException e) {
        this.message.setText(
            "Les champs de durée et de nombre maximum de participants doivent contenir un entier!");
      } catch (DateTimeParseException e) {
        message.setText(
            "Le format du champ de la date ou de l'heure de l'événement est invalide!\n"
                + "Veuillez entrer une date de la forme AAAA-MM-JJ\n"
                + "Veuillez entrer une heure de la forme HH:MM");
      }
      
    } else {
      this.message.setText("Tous les champs doivent être remplis !");
    }
  }
  
  
  /**
   * Affiche quelques informations � propos de l'application OpenEvent.
   *
   */
  @FXML
  void actionMenuApropos(ActionEvent event) {
    JFrame jj = new JFrame();
    
    JDialog jd = new JDialog(jj);
    
    jd.setLayout(new FlowLayout());
    
    jd.setBounds(500, 800, 900, 300);
    
    JLabel jlabel =
        new JLabel("Dans le cadre de l'UE Conception d'applications, "
            + "l'interface propose la possibilit� de gestionner les �v�nements et les membres. ");
    
    JLabel test = new JLabel(
        "Auteurs: Ange Leyrit, Hippolyte Jean, Adil Zaim et Oc�ane Pezennec.");
    
    jd.add(jlabel);
    jd.add(test);
    
    jd.setVisible(true);
  }
  
  /**
   * Charge les membres et les événements de l’association à partir d’un fichier
   * une fois chargé, les deux listes d'affichage affichent tous les membres et tous les
   * événements.
   *
   */
  @FXML
  void actionMenuCharger(ActionEvent event) throws IOException {
    try {
      this.gestionnaireAssociation.chargerDonnees("save_asso.ser");
    } catch (FileNotFoundException e) {
      this.message.setText("Il n'existe pour l'instant aucun fichier de sauvegarde !");
    }
  }
  
  /**
   * Réinitialise l’association (efface tous les événements et membres chargés
   * enmémoire).
   *
   */
  @FXML
  void actionMenuNouveau(ActionEvent event) {
    this.gestionnaireAssociation = new Association();
    listeEvenements.getItems().clear();
    listeMembres.getItems().clear();
    this.entreeDateEvt.setText(null);
    this.entreeDureeEvt.setText(null);
    this.entreeHeureEvt.setText(null);
    this.entreeMaxParticipantsEvt.setText(null);
    this.entreeLieuEvt.setText(null);
    this.entreeNomEvt.setText(null);
    this.entreAdresseMembre.setText(null);
    this.entreAgeMembre.setText(null);
    this.entreeNomMembre.setText(null);
    this.entreePrenomMembre.setText(null);
  }
  
  /**
   * Ferme l’application.
   */
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    Platform.exit();
  }
  
  /**
   * Sauvegarde les membres et les événements de l’association dans un fichier.
   */
  @FXML
  void actionMenuSauvegarder(ActionEvent event) throws IOException {
    this.gestionnaireAssociation.sauvegarderDonnees("save_asso.ser");
  }
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("Initialisation de l'interface");
    this.gestionnaireAssociation = new Association();
  }
  
}
