package solveur;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import Carte.*;
import Carte.Map;
import parseur.MapHandler;

public class Dijkstra {
	protected Map map;
	
	public Dijkstra(String m) throws IOException, SAXException{
		XMLReader reader;
		reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

		// Creation d'un flot XML sur le fichier d'entree
		InputSource input = new InputSource(new FileInputStream(m));

		MapHandler mH = new MapHandler();
    
		// Connexion du ContentHandler
		reader.setContentHandler(mH);
		// Lancement du traitement...
		reader.parse(input);
		this.map = mH.getMap();
	}
	
	public List<Voie> pluscourtcheminDistance(Lieu A, Lieu B) throws trajetInexistantException{
		List<Voie> trajet = new ArrayList<>();
		Lieu l1;
		Iterator<Voie> iter = null;
		triTableau(A);
		initialisation();
		try{
			while(!condsortie() && !(B.getMark())){
				l1 = trouveMin();
				l1.visiteLieu();
				iter = l1.getVoiesSortantes().iterator();
				while(iter.hasNext()){
					majDistance(iter.next());
				}
				
			}
		}catch(NullPointerException ex){
			throw new trajetInexistantException();
		}
		l1 = B;
		while(l1 != A){
			trajet.add(l1.getVoieArrivee());
			l1 = l1.getVoieArrivee().getVilleDep();
		}

		Collections.reverse(trajet);

		return trajet;
	}
	
	public List<Voie> pluscourtcheminTemps(Lieu A, Lieu B) throws trajetInexistantException{
		List<Voie> trajet = new ArrayList<>();
		Lieu l1;
		Iterator<Voie> iter = null;
		triTableau(A);
		initialisation();
		try{
			while(!condsortie() && !(B.getMark())){
				l1 = trouveMin();
				l1.visiteLieu();
				iter = l1.getVoiesSortantes().iterator();
				while(iter.hasNext()){
					majTemps(iter.next());
				}
				
			}
		}catch(NullPointerException ex){
			throw new trajetInexistantException();
		}
		l1 = B;
		while(l1 != A){
			trajet.add(l1.getVoieArrivee());
			l1 = l1.getVoieArrivee().getVilleDep();
		}

		Collections.reverse(trajet);

		return trajet;
	}
	
	public List<Voie> pluscourtcheminPrix(Lieu A, Lieu B) throws trajetInexistantException{
		List<Voie> trajet = new ArrayList<>();
		Lieu l1;
		Iterator<Voie> iter = null;
		triTableau(A);
		initialisation();
		try{
			while(!condsortie() && !(B.getMark())){
				l1 = trouveMin();
				l1.visiteLieu();
				iter = l1.getVoiesSortantes().iterator();
				while(iter.hasNext()){
					majPrix(iter.next());
				}
			}
		}catch(NullPointerException ex){
			throw new trajetInexistantException();
		}
		l1 = B;
		while(l1 != A){
			trajet.add(l1.getVoieArrivee());
			l1 = l1.getVoieArrivee().getVilleDep();
		}

		Collections.reverse(trajet);

		return trajet;
	}
	
	private void initialisation(){
		this.map.getLieu(0).majPotentiel(0);
		this.map.getLieu(0).initialiseMark();
		this.map.getLieu(0).initialiseVoieArrivee();
		for(int i = 1; i<map.getN();i++){
			this.map.getLieu(i).initialiseMark();
			this.map.getLieu(i).initialisePotentiel();
			this.map.getLieu(i).initialiseVoieArrivee();
		}
	}
	
	private void triTableau(Lieu A){
		int i=0;
		boolean trouve = false;
		while((i<map.getN())&&(trouve == false)){
			if(map.getLieu(i) == A){
				trouve = true;
			}
			i++;
		}
		map.listLieux().set(i-1, map.getLieu(0));
		map.listLieux().set(0, A);
	}
	
	private boolean condsortie(){
		boolean s = true;
		Iterator<Lieu> iterL= map.listLieux().iterator();
		while(iterL.hasNext() && (s == true)){
			Lieu l = iterL.next();
			Iterator<Voie> iterV= l.getVoiesSortantes().iterator();
			while(iterV.hasNext() && s==true){
				Voie v = iterV.next();
				if(!(v.getVilleArr().getMark()))
					s = false;					
			}
		}	
		
		return s;
	}
	
	
	private Lieu trouveMin(){
		double mini=-1;
		Lieu lmini = null;
		Iterator<Lieu> iterL= map.listLieux().iterator();
		while(iterL.hasNext()){
			Lieu l = iterL.next();
			if(!(l.getMark()) && l.getPotentiel() != -1 && (mini == -1 || l.getPotentiel() < mini) ){
				lmini = l;
				mini = lmini.getPotentiel();
			}
		}	
		return lmini;
	}
	
	private void majDistance(Voie v){
		double nouveauPot = v.getDist() + v.getVilleDep().getPotentiel();
		if(v.getVilleArr().getPotentiel()== -1 || v.getVilleArr().getPotentiel() > nouveauPot){
			v.getVilleArr().majPotentiel(nouveauPot);
			v.getVilleArr().majVoieArrivee(v);
		}
	}
	
	private void majTemps(Voie v){
		double nouveauPot = v.getDuree() + v.getVilleDep().getPotentiel();
		if(v.getVilleArr().getPotentiel()== -1 || v.getVilleArr().getPotentiel() > nouveauPot){
			v.getVilleArr().majPotentiel(nouveauPot);
			v.getVilleArr().majVoieArrivee(v);
		}
	}
	
	private void majPrix(Voie v){
		double nouveauPot = v.getPrice() + v.getVilleDep().getPotentiel();
		if(v.getVilleArr().getPotentiel()== -1 || v.getVilleArr().getPotentiel() > nouveauPot){
			v.getVilleArr().majPotentiel(nouveauPot);
			v.getVilleArr().majVoieArrivee(v);
		}
	}
	
	public Map getMap(){
		return this.map;
	}
	
	public Lieu rechercheLieu(int x, int y){
		return this.map.rechercheLieu(x, y);
	}
	
	public Lieu rechercheLieu(String l){
		return this.map.rechercheLieu(l);
	}
	public double getDistTotale(List<Voie> T){
		double s = 0;
		Iterator<Voie> iter = T.iterator();
		while(iter.hasNext()){
			s += iter.next().getDist();
		}
		return s;
	}
	public double getDureeTotale(List<Voie> T){
		double s = 0;
		Iterator<Voie> iter = T.iterator();
		while(iter.hasNext()){
			s += iter.next().getDuree();
		}
		return s;
	}
	public double getHeure(double t){
		return Math.floor(t);
	}
	public double getMin(double t){
		return Math.floor((t - getHeure(t))*60);
	}
	public double getSec(double t){
		return Math.ceil((((t - getHeure(t))*60) - getMin(t)) * 60 ); 
	}
	
	public double getCoutTotal(List<Voie> T){
		double s = 0;
		Iterator<Voie> iter = T.iterator();
		while(iter.hasNext()){
			s += iter.next().getPrice();
		}
		return s;
	}
}
