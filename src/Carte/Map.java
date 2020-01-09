package Carte;
import java.util.*;

public class Map {
	protected List<Lieu> lieux;
	
	public Map(){
		lieux = new ArrayList<>();
	}
	
	public void ajoutLieu(Lieu l){
		this.lieux.add(l);
	}
	
	public void ajoutVoie(Voie v){
		v.getVilleDep().addVoies(v);
	}
	
	public Lieu getLieu(int i){
		return lieux.get(i);
	}
	
	public List<Lieu> listLieux(){
		return lieux;
	}
	
	public int getN(){
		return lieux.size();
	}
	
	public Lieu rechercheLieu(String l){
		Lieu lieu = lieux.get(0);
		Lieu r = null;
		boolean trouve = false;
		Iterator<Lieu> iter= lieux.iterator();
		while(iter.hasNext() && !trouve){
			if(lieu.getNomL().equals(l)){
				trouve = true;
				r = lieu;
			}
			lieu = iter.next();
		}
		if(!trouve && lieu.getNomL().equals(l))
			r = lieu;
		return r;
	}
	
	public Lieu rechercheLieu(int x, int y){
		Lieu lieu = lieux.get(0);
		Lieu r = null;
		boolean trouve = false;
		Iterator<Lieu> iter= lieux.iterator();
		while(iter.hasNext() && !trouve){
			if(x == lieu.getAbscisse() && y == lieu.getOrdonnee()){
				trouve = true;
				r = lieu;
			}
			lieu = iter.next();
		}
		return r;
	}
	
}
	
