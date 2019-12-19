package parseur;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;

/**
 * Exemple d'application ({@code main}) utilisant {@link MapHandler}.<br>
 * 
 * Usage : {@code java parseur.MapParser fichier.xml}
 * 
 * @author Bernard.Carre -at- polytech-lille.fr
**/


public class MapParser {

	/**
	 * Usage : {@code java parseur.MapParser fichier.xml}
	 * 
	 * @param argv[0] fichier.xml
	 * @throws java.io.IOException Erreur d'acc&egrave;s au fichier.
	 * @throws org.xml.sax.SAXException Erreur de parsing.
	**/
  public static void main(String argv[]) throws IOException, SAXException {
    System.out.println("analyse de "+argv[0]+"...");

    // Le parseur SAX
    XMLReader reader;
		reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

    // Creation d'un flot XML sur le fichier d'entree
    InputSource input = new InputSource(new FileInputStream(argv[0]));

    // Connexion du ContentHandler
    reader.setContentHandler(new MapHandler());
    // Lancement du traitement...
    reader.parse(input);

  }
}
