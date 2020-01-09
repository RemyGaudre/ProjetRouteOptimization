package Carte;

public class Route extends Voie{
	protected double ralentis;
	
	public double getRalentis(){
		return ralentis;
	}
	
	public double getDuree(){
		return dist/vitesse + ralentis/60;
	}
	
	public Route(int num, String nom, Lieu villeDep, Lieu villeArr, double dist, double vitesse, int ralentis){
		super(num, nom, villeDep, villeArr, dist, vitesse);
		this.ralentis = ralentis;
	}
	
	public String toString(){
		return (this.getNum() + " " + this.getNom() + " " + this.villeDep.getNomL() + " " + this.villeArr.getNomL() + " " + 
				this.dist + " " + this.getVitesse() + " " + this.getRalentis() + " " + this.getDuree());
	}
}
