package Carte;

public class Autoroute extends Voie {
	protected double prixPeage;
	
	public double getPrixPeage(){
		return prixPeage;
	}
	
	public Autoroute(int num, String nom, Lieu villeDep, Lieu villeArr, double dist, double vitesse, double prixPeage){
		super(num, nom, villeDep, villeArr, dist, vitesse);
		this.prixPeage = prixPeage;
	}
	
	public double getPrice(){
		double prix;
		if (getVitesse() < 100){
			prix = getDist() * 6 * prixEssence;
		}else{
			prix = getDist() * 7 * prixEssence;
		}
		return prix + getPrixPeage();
	}
	
	public String toString(){
		return (this.num + " " + this.nom + " " + this.villeDep.getNomL() + " " + this.villeArr.getNomL() + " " + 
				this.dist + " " + this.vitesse + " " + this.prixPeage);
	}
}
