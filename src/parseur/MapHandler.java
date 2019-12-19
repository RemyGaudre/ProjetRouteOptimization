package parseur;
import Carte.*;
import org.xml.sax.*;

/**
 * Prototype d'un Handler XML pour SAX: ne fait que tracer (print) les &eacute;l&eacute;ments reconnus.<br>
 * Exemple d'utilisation: voir {@link MapParser}
 * 
 * @author Bernard.Carre -at- polytech-lille.fr
 * 
 **/

public class MapHandler implements ContentHandler {
	/**
	 * Nom de la balise courante.
	 * 
	 **/
	private String currentTag;
	private String objet;
	private Map map = new Map();
	
	public Map getMap(){
		return map;
	}
	/**
	 * Traitement d'ouverture du document XML : balise {@code <docbase>}.
	 * 
	 **/
	public void startDocument() throws SAXException {
		System.out.println("start document...");
	}
	/**
	 * Traitement de fermeture du document XML : balise {@code </docbase>}
	 * 
	 **/

	public void endDocument() throws SAXException {
		System.out.println("\nDocument termine.");
	}

	/**
	 * Traitement de balise ouvrante.<br>
	 * Ici : affiche son nom ({@code localName}) et positionne : {@link #currentTag} = ({@code localName}).
	 *  
	 **/ 
	public void startElement(String namespaceURI, String localName, String rawName, Attributes atts)
			throws SAXException {
		if (localName.equals("ville")){
			objet = "";
			System.out.println("\nVILLE... ");
		}
		else if (localName.equals("route")){
			objet = "";
			System.out.println("\nROUTE... ");
		}
		else if (localName.equals("autoroute")){
			objet = "";
			System.out.println("\nAUTOROUTE... ");
		}
		else // autres elements...
			System.out.println("startElement: " + localName);
		currentTag = localName; // set variable "currentTag"
	}

	/**
	 * Traitement de balise fermante.<br>
	 * Ici: affiche "FIN " + son nom ({@code localName}) et reset de la variable {@link #currentTag}.
	 *  
	 **/
	public void endElement(String namespaceURI, String localName, String rawName) throws SAXException {
		String[] val;
		if (localName.equals("ville")){
			System.out.println(objet);
			val = objet.split("#");
			Lieu l = new Lieu(Integer.parseInt(val[0]), val[1],Double.parseDouble(val[2]),Double.parseDouble(val[3]));
			System.out.println(l);
			map.ajoutLieu(l);
			System.out.println("FIN VILLE.");
		}
		else if (localName.equals("route")){
			System.out.println(objet);
			val = objet.split("#");
			System.out.println("Ville de départ : " + map.rechercheLieu(val[2]));
			Route r = new Route(Integer.parseInt(val[0]), val[1], map.rechercheLieu(val[2]), map.rechercheLieu(val[3]),Double.parseDouble(val[4]),Double.parseDouble(val[5]),Integer.parseInt(val[6]));
			System.out.println(r);
			map.ajoutVoie(r);
			System.out.println("FIN ROUTE.");
		}
		else if (localName.equals("autoroute")){
			val = objet.split("#");
			Autoroute a = new Autoroute(Integer.parseInt(val[0]), val[1], map.rechercheLieu(val[2]), map.rechercheLieu(val[3]),Double.parseDouble(val[4]),Double.parseDouble(val[5]),Double.parseDouble(val[6]));
			map.ajoutVoie(a);
			System.out.println(a);
			System.out.println("FIN AUTOROUTE.");
		}
		else // Autres elements eventuels...
			System.out.println("endElement: " + localName);
		currentTag = null; // reset variable "currentTag"
	}

	/**
	 * Traitement du contenu texte ({@code char[] ch}) d'une balise.<BR>
	 * Ici : l'affiche (si existe, balises terminales d'information).
	 *  
	 **/
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (currentTag != null) {
			if (currentTag.equals("docbase") || currentTag.equals("ville") || currentTag.equals("route")
					|| currentTag.equals("autoroute")) {
				// Enclosing elements : no proper characters description, but introducing elements of sub-content 
				System.out.println("content:");
			} else { 
				// Elements of sub-content
				String subContent = new String(ch, start, length);
				System.out.println("content:" + subContent);
				objet += subContent + "#";
			} 
		}
	}

	// Autres méthodes de ContentHandler: pas de traitement particulier ici.
	/**
	 * NOP
	 *  
	 **/	
	public void startPrefixMapping(String prefix, String uri) {
	}
	/**
	 * NOP
	 *  
	 **/
	public void endPrefixMapping(String prefix) {
	}
	/**
	 * NOP
	 *  
	 **/
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}
	/**
	 * NOP
	 *  
	 **/
	public void processingInstruction(String target, String data) throws SAXException {
	}
	/**
	 * NOP
	 *  
	 **/
	public void skippedEntity(String name) throws SAXException {
	}
	/**
	 * NOP
	 *  
	 **/
	public void setDocumentLocator(Locator locator) {
	}
}
