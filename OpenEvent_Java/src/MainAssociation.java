import java.io.IOException;



/**
 * Classe Main du projet Java.
 *
 * @author Eric Cariou
 * @see association.InformationPersonnelle
 */
public class MainAssociation {
  /**
   * Méthode main de la classe main permmetant d'afficher des info de debug.
   *
   * @author Eric Cariou
   * @see association.InformationPersonnelle
   */
  public static void main(String[] args) {
    System.out.println("Ca marche !");
    System.out.println("\nAppuyez sur Entrée pour terminer le programme ...");
    try {
      System.in.read();
    } catch (IOException e) {
      System.err.println("Vous avez réussi à casser le clavier : " + e);
    }
  }
  
}
