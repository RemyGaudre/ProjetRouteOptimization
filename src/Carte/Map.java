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
		Iterator<Lieu> iter= lieux.iterator();
		while(iter.hasNext() && !(l.equals(lieu.getNomL()))){
			lieu = iter.next();
		}
		return lieu;
	}
}
