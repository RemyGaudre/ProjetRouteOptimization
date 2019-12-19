package Carte;

import java.util.*;

public class Lieu {
	protected int numL;
	protected String nomL;
	protected double abscisse;
	protected double ordonnee;
	protected List<Voie> voies;
	protected boolean mark;
	protected double potentiel;
	protected Voie voieArrivee;
	
	public Lieu(int numL, String nomL, double abscisse, double ordonnee){
		this.numL = numL;
		this.nomL = nomL;
		this.abscisse = abscisse;
		this.ordonnee = ordonnee;
		this.mark = false;
		this.potentiel = -1;
		this.voieArrivee = null;
		this.voies = new ArrayList<>();
	}
	
	public int getNumL(){
		return numL;
	}
	
	public String getNomL(){
		return nomL;
	}
	
	public double getAbscisse(){
		return abscisse;
	}
	
	public double getOrdonnee(){
		return ordonnee;
	}
	
	public void addVoies(Voie v){
		voies.add(v);
	}
	
	public List<Voie> getVoiesSortantes(){
		return voies;
	}
	
	public void initialiseMark(){
		this.mark = false;
	}
	
	public boolean getMark(){
		return this.mark;
	}
	
	public void visiteLieu(){
		this.mark = true;
	}
	
	public void initialisePotentiel(){
		this.potentiel = -1;
	}
	
	public double getPotentiel(){
		return this.potentiel;
	}
	
	public void majPotentiel(double pot){
		this.potentiel = pot;
	}
	
	public void initialiseVoieArrivee(){
		this.voieArrivee = null;
	}
	
	public Voie getVoieArrivee(){
		return this.voieArrivee;
	}
	
	public void majVoieArrivee(Voie voie){
		this.voieArrivee = voie;
	}
	
	public String toString(){
		return (this.numL + " " + this.nomL + " " + this.abscisse + " " + this.ordonnee + " " + this.mark + " " + this.potentiel);
	}
}


