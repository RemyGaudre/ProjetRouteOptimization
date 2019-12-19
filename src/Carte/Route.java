package Carte;

public class Route extends Voie{
	protected int ralentis;
	
	public int getRalentis(){
		return ralentis;
	}
	
	public double getDuree(){
		return vitesse/dist + ralentis;
	}
	
	public Route(int num, String nom, Lieu villeDep, Lieu villeArr, double dist, double vitesse, int ralentis){
		super(num, nom, villeDep, villeArr, dist, vitesse);
		this.ralentis = ralentis;
	}
	
	public String toString(){
		return (this.num + " " + this.nom + " " + this.villeDep.getNomL() + " " + this.villeArr.getNomL() + " " + 
				this.dist + " " + this.vitesse + " " + this.ralentis);
	}
}
