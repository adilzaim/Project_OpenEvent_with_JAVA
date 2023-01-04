package ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *Interfacce pour l'utilisation du gestionnaire d'evenement et 
 *de membre et de l'association.
 *
 * @author Eric Cariou
 */
public final class MainIHM extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      final URL url = getClass().getResource("association.fxml");
      final FXMLLoader fxmlLoader = new FXMLLoader(url);
      final VBox root = (VBox) fxmlLoader.load();
      final Scene scene = new Scene(root, 850, 600);
      primaryStage.setScene(scene);
      primaryStage.setResizable(true);
    } catch (IOException ex) {
      System.err.println("Erreur au chargement: " + ex);
    }
    primaryStage.setTitle("Association");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
