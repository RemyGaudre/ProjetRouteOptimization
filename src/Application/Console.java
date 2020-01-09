package Application;

import java.io.IOException;

import org.xml.sax.SAXException;

import solveur.*;
import Carte.*;
import java.util.*;


public class Console {
	static Scanner in = new Scanner(System.in);
	public static void main(String argv[]) throws IOException, SAXException {
	    int choix = 0;
		do {
			menu();
			System.out.print("votre choix? ");
			choix = in.nextInt();
			switch (choix) {
			case 1: 
				menuDuree(argv);
				break;
			case 2:
				menuDistance(argv);
				break;
			case 3:
				menuPrix(argv);
			case 0: // quitter
			}
		} while (choix != 0);
		System.out.println("au revoir");
	}

	static void menu() {
		System.out.println(
				"\n1: plus rapide\n2: plus court\n3: moins cher\n0: quitter");
	}

	static void menuDuree(String argv[]) throws IOException, SAXException{
		Dijkstra map = new Dijkstra(argv[0]);
		System.out.print("Ville de départ");
		String d = in.next();
		System.out.print("Ville d'arrivee");
		String a = in.next();
	    try{
	    	List<Voie> trajet = map.pluscourtcheminTemps(map.getMap().rechercheLieu(d), map.getMap().rechercheLieu(a));
		    System.out.println(trajet);
		    double duree = map.getDureeTotale(trajet);
		    System.out.println("Duree du trajet : " + Math.round(map.getHeure(duree)) + "h " + Math.round(map.getMin(duree)) + "min " + Math.round(map.getSec(duree)) + "sec");
		    System.out.println("Distance du trajet : " + map.getDistTotale(trajet) + "km");
		    System.out.println("Prix du trajet : " + map.getCoutTotal(trajet) + " euros");
	    }catch(trajetInexistantException ex){
	    	System.out.println("Aucun chemin trouvé");
	    }
	  }
	
	static void menuDistance(String argv[]) throws IOException, SAXException{
		Dijkstra map = new Dijkstra(argv[0]);
		System.out.print("Ville de départ");
		String d = in.next();
		System.out.print("Ville d'arrivee");
		String a = in.next();
		try{
	    List<Voie> trajet = map.pluscourtcheminDistance(map.getMap().rechercheLieu(d), map.getMap().rechercheLieu(a));
	    System.out.println(trajet);
	    double duree = map.getDureeTotale(trajet);
	    System.out.println("Duree du trajet : " + Math.round(map.getHeure(duree)) + "h " + Math.round(map.getMin(duree)) + "min " + Math.round(map.getSec(duree)) + "sec");
	    System.out.println("Distance du trajet : " + map.getDistTotale(trajet) + "km");
	    System.out.println("Prix du trajet : " + map.getCoutTotal(trajet) + " euros");
		}catch(trajetInexistantException ex){
			System.out.println("Aucun chemin trouvé");
		}
	  }
	
	static void menuPrix(String argv[]) throws IOException, SAXException{
		Dijkstra map = new Dijkstra(argv[0]);
		System.out.print("Ville de départ\n");
		String d = in.next();
		System.out.print("Ville d'arrivee\n");
		String a = in.next();
		try{
	    List<Voie> trajet = map.pluscourtcheminPrix(map.getMap().rechercheLieu(d), map.getMap().rechercheLieu(a));
	    System.out.println(trajet);
	    double duree = map.getDureeTotale(trajet);
	    System.out.println("Duree du trajet : " + Math.round(map.getHeure(duree)) + "h " + Math.round(map.getMin(duree)) + "min " + Math.round(map.getSec(duree)) + "sec");
	    System.out.println("Distance du trajet : " + map.getDistTotale(trajet) + "km");
	    System.out.println("Prix du trajet : " + map.getCoutTotal(trajet) + " euros");
		}catch(trajetInexistantException ex){
	    	System.out.println("Aucun chemin trouvé");
	    }
	  }
	
	
	
}
