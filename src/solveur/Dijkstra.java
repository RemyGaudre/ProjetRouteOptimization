package solveur;
import java.util.*;
import java.util.Collections;
import Carte.*;
import Carte.Map;

public class Dijkstra {
	protected Map map;
	
	public Dijkstra(Map map){
		this.map = map;
	}
	
	public List<Voie> pluscourtcheminDistance(Lieu A, Lieu B){
		List<Voie> trajet = new ArrayList<>();
		Lieu l1;
		Iterator<Voie> iter = null;
		triTableau(A);
		initialisation();
		while(!condsortie() && !(B.getMark())){
			l1 = trouveMin();
			l1.visiteLieu();
			iter = l1.getVoiesSortantes().iterator();
			while(iter.hasNext()){
				majDistance(iter.next());
			}
			
		}		
		l1 = B;
		System.out.println("");
		while(l1 != A){
			trajet.add(l1.getVoieArrivee());
			l1 = l1.getVoieArrivee().getVilleDep();
		}

		Collections.reverse(trajet);

		System.out.println(trajet);
		return trajet;
	}
	
	public List<Voie> pluscourtcheminTemps(Lieu A, Lieu B){
		List<Voie> trajet = new ArrayList<>();
		Lieu l1;
		Iterator<Voie> iter=null;
		initialisation();
		triTableau(A);
		while(!condsortie() && !(B.getMark())){
			l1 = trouveMin();
			l1.visiteLieu();
			iter = l1.getVoiesSortantes().iterator();
			while(iter.hasNext()){
				majTemps(iter.next());
			}
			
		}		
		l1 = B;
		while(l1 != A){
			trajet.add(l1.getVoieArrivee());
			l1 = l1.getVoieArrivee().getVilleDep();
		}

		Collections.reverse(trajet);
		return trajet;
	}
	
	public List<Voie> pluscourtcheminprix(Lieu A, Lieu B){
		List<Voie> trajet = new ArrayList<>();
		Lieu l1;
		Iterator<Voie> iter=null;
		initialisation();
		triTableau(A);
		while(!condsortie() && !(B.getMark())){
			l1 = trouveMin();
			l1.visiteLieu();
			iter = l1.getVoiesSortantes().iterator();
			while(iter.hasNext()){
				majPrix(iter.next());
			}
			
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
		double mini=100000;
		Lieu lmini = null;
		Iterator<Lieu> iterL= map.listLieux().iterator();
		while(iterL.hasNext()){
			Lieu l = iterL.next();
			Iterator<Voie> iterV= l.getVoiesSortantes().iterator();
			while(iterV.hasNext()){
				Voie v = iterV.next();
				if(!(v.getVilleArr().getMark()) && (v.getVilleArr().getPotentiel() < mini)){
					lmini = v.getVilleArr();
					mini = lmini.getPotentiel();
				}
					
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
	

}
