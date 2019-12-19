package Carte;

public class Voie {
	protected int num;
	protected String nom;
	protected Lieu villeDep;
	protected Lieu villeArr;
	protected double dist;
	protected double vitesse;
	static final double prixEssence =  1.5;
	
	public Voie(int num, String nom, Lieu villeDep, Lieu villeArr, double dist, double vitesse){
		this.num = num;
		this.nom = nom;
		this.villeDep = villeDep;
		this.villeArr = villeArr;
		this.dist = dist;
		this.vitesse = vitesse;
	}
	
	public int getNum(){
		return num;
	}
	
	public String getNom(){
		return nom;
	}
	
	public Lieu getVilleDep(){
		return villeDep;
	}
	
	public Lieu getVilleArr(){
		return villeArr;
	}
	
	public double getDist(){
		return dist;
	}
	
	public double getVitesse(){
		return vitesse;
	}
	
	public double getDuree(){
		return vitesse/dist;
	}
	
	public double getPrice(){
		double prix;
		if (getVitesse() < 100){
			prix = getDist() * 6 * prixEssence;
		}else{
			prix = getDist() * 7 * prixEssence;
		}
		return prix;
	}
}
