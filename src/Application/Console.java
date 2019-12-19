package Application;

import java.io.FileInputStream;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import parseur.MapHandler;
import solveur.*;
import Carte.Map;
import Carte.*;
import java.util.*;

public class Console {

	public static void main(String argv[]) throws IOException, SAXException {
	    // Le parseur SAX
	    XMLReader reader;
			reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

	    // Creation d'un flot XML sur le fichier d'entree
	    InputSource input = new InputSource(new FileInputStream(argv[0]));

	    MapHandler mH = new MapHandler();
	    
	    // Connexion du ContentHandler
	    reader.setContentHandler(mH);
	    // Lancement du traitement...
	    reader.parse(input);
	    Map map = mH.getMap();
	    Dijkstra t = new Dijkstra(map);
	    
	    List<Voie> trajet = t.pluscourtcheminDistance(map.rechercheLieu("Lille"), map.rechercheLieu("Calais"));
	    
	    System.out.println(trajet);
	  }
}
