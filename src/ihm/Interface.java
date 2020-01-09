package ihm;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import org.xml.sax.SAXException;

import Carte.*;
import solveur.*;
/**
 * Prototype d'interface graphique :<br>
 * - charge une image {@code .jpg} de carte (en param&egrave;tre du main)<br>
 * - lui superpose une grille (X,Y) en vue du rep&eacute;rage de lieux par leurs coordonn&eacute;es pour en faire un
 * plan "cliquable"<br>
 * - ou permet de saisir un lieu directement par son nom.<br>
 * Ce prototype ne fait que r&eacute;afficher les coordonn&eacute;es cliqu&eacute;es et le nom du lieu saisi dans une
 * zone de texte (resultTextArea).<br>
 * <br>
 * 
 * Usage : {@code java ihm.Proto fichierImage.jpg}
 * 
 * @author Bernard.Carre -at- polytech-lille.fr
 * 
 */

@SuppressWarnings("serial")
public class Interface extends JFrame {

	/**
	 * Pr&eacute;cision de la grille de rep&eacute;rage.
	 * 
	 */
	protected static final int DELTA = 20;
	/**
	 * Taille du plan.
	 */
	protected int hauteurPlan, largeurPlan;

	/**
	 * Plan "cliquable" = image + grille.
	 */
	protected ImageCanvas canvas = new ImageCanvas();
	/**
	 * Vue "scrollable" du plan.
	 */
	protected ScrollPane mapView = new ScrollPane();
	/**
	 * Panel d'interaction avec l'utilisateur
	 */
	protected InteractionPanel interact = new InteractionPanel();
	
	protected Dijkstra map;

	/**
	 * Charge une image de carte et construit l'interface graphique.
	 * 
	 * @param fichierImage
	 *            Nom du fichier image de carte
	 * @throws java.io.IOException
	 *             Erreur d'acc&egrave;s au fichier
	 */
	public Interface(String fichierImage, Dijkstra map) throws java.io.IOException {

		// Chargement de l'image
		Image im = new ImageIcon(fichierImage).getImage();
		hauteurPlan = im.getHeight(this);
		largeurPlan = im.getWidth(this);
		this.map = map;
		// Preparation de la vue scrollable de l'image
		canvas.setImage(im); // image a afficher dans le Canvas
		canvas.addMouseListener(interact.getSelectionPanel()); // notification de clic sur la grille
		mapView.setSize(hauteurPlan + DELTA, largeurPlan + DELTA);
		mapView.add(canvas); // apposition de la vue scrollable sur l'ImageCanvas

		// Construction de l'ensemble
		setLayout(new BorderLayout());
		add(mapView, BorderLayout.CENTER);
		add(interact, BorderLayout.SOUTH);

		// Evenement de fermeture de la fenetre : quitter l'application
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

	/**
	 * Classe utilitaire interne (sous-classe de {@code java.awt.Canvas}) = image de carte + grille "cliquable".
	 * 
	 */
	class ImageCanvas extends Canvas {
		Image image;

		void setImage(Image img) {
			image = img;
			setSize(largeurPlan, hauteurPlan);
			repaint();
		}

		/**
		 * Affiche l'image + la grille.
		 * 
		 */
		public void paint(Graphics g) {
			if (image != null)
				g.drawImage(image, DELTA, DELTA, this);

			// Grille de repérage apposée
			int lignes = hauteurPlan / DELTA;
			int colonnes = largeurPlan / DELTA;
			g.setColor(Color.gray);
			for (int i = 1; i <= lignes; i++) {
				g.drawString("" + i, 0, (i + 1) * DELTA);
				g.drawLine(DELTA, i * DELTA, DELTA + largeurPlan, i * DELTA);
			}
			g.drawLine(DELTA, (lignes + 1) * DELTA, DELTA + largeurPlan, (lignes + 1) * DELTA);
			for (int i = 1; i <= colonnes; i++) {
				g.drawString("" + i, i * DELTA, DELTA / 2);
				g.drawLine(i * DELTA, DELTA, i * DELTA, DELTA + hauteurPlan);
			}
			g.drawLine((colonnes + 1) * DELTA, DELTA, (colonnes + 1) * DELTA, DELTA + hauteurPlan);
		}
	}

	/**
	 * 
	 * Panel d'interaction avec l'utilisateur comprenant: <BR>
	 * - un sous-panel de s&eacute;lection utilisateur : {@link InteractionPanel#selectionPanel} (&agrave; l'&eacute;coute de clics utilisateur)<BR>
	 * - une zone de texte pour l'affichage des r&eacute;sultats : {@link InteractionPanel#resultTextArea}.
	 * 
	 */
	class InteractionPanel extends JPanel {
		/**
		 * sous-panel de s&eacute;lection utilisateur
		 */
		SelectionSubPanel selectionPanel = new SelectionSubPanel();
		/**
		 * zone de texte pour l'affichage des r&eacute;sultats
		 */
		JTextArea resultTextArea = new JTextArea(5, 30);

		InteractionPanel() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(selectionPanel);
			resultTextArea.setEditable(false); // non editable (produit par les resultats de l'application)
			add(resultTextArea);
		}

		/**
		 * 
		 * Sous-panel de s&eacute;lection utilisateur :<BR>
		 * - &agrave; l'&eacute;coute (implements MouseListener) de clics utilisateur sur le plan:
		 * enregistre les coordonn&eacute;es (x,y) et les affiche dans des {@code Label} "X=...", "Y=..."<BR>
		 * - permet de saisir un nom de lieu dans le JTextField "lieu"<BR>
		 * - bouton : "GO!" : reporte les saisies dans la zone "resultTextArea"
		 * 
		 */
		class SelectionSubPanel extends JPanel implements MouseListener {
			/**
			 * Valeurs de x, y cliqu&eacute;s
			 */
			int x, y;
			/**
			 * Affichage de X,Y cliqu&eacute;
			 */
			JLabel xLabel = new JLabel("X =");
			/**
			 * Affichage de X,Y cliqu&eacute;
			 */
			JLabel yLabel = new JLabel("Y =");
			/**
			 * Champ de saisie d'un nom de lieu
			 */
			JTextField lieu = new JTextField(20);
			JTextField lieu2 = new JTextField(20);
			JTextField lieu3 = new JTextField(20);
			
			Lieu Dep,Arr;
			/**
			 * Reporte les saisies dans la zone "resultTextArea"
			 */
			
			JButton depart = new JButton("SET DEPART"); // reporte les saisies dans la zone "resultTextArea"
			JButton arrivee = new JButton("SET ARRIVEE");
			JButton go = new JButton("GO!");
			JRadioButton duree = new JRadioButton("plus rapide",true);
			JRadioButton dist = new JRadioButton("plus court");
			JRadioButton prix = new JRadioButton("moins cher");
			ButtonGroup group = new ButtonGroup();
			
			
			// Construction et ecouteur du bouton "GO!"
			SelectionSubPanel() {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // organisation verticale
				add(xLabel);
				add(yLabel);
				add(new JLabel("LIEU"));
				add(lieu);
				add(depart);
				add(lieu2);
				add(arrivee);
				add(lieu3);
				group.add(duree);
				group.add(prix);
				group.add(dist);
				add(duree);
				add(dist);
				add(prix);
				add(go);
				depart.addActionListener(evt -> {
					lieu2.setText(lieu.getText());
				});
				arrivee.addActionListener(evt -> {
					lieu3.setText(lieu.getText());
				});
				go.addActionListener(evt ->{
					List<Voie> trajet = null;
					if(lieu2.getText().equals("")|| lieu3.getText().equals("")){
						resultTextArea.setText("Les lieux de départ et d'arrivée ne sont pas bien définis.");
					}else {
						Lieu lieuDep = map.getMap().rechercheLieu(lieu2.getText());
						Lieu lieuArr = map.getMap().rechercheLieu(lieu3.getText());
						try{
							if(lieuArr == null || lieuDep == null)
								resultTextArea.setText("Les lieux de départ et d'arrivée ne sont pas dans la base.");
							else if(duree.isSelected()){
								trajet = map.pluscourtcheminTemps(lieuDep, lieuArr);
							}else if(dist.isSelected()){
								trajet = map.pluscourtcheminDistance(lieuDep, lieuArr);
							}else if(prix.isSelected()){
								trajet = map.pluscourtcheminPrix(lieuDep, lieuArr);
							}else 
								resultTextArea.setText("Sélectionnez un type de trajet.");
						}catch(trajetInexistantException ex){
							resultTextArea.setText("Erreur : aucun chemin trouvé entre " + lieuDep.getNomL() + " et " + lieuArr.getNomL() + ".\n");
						}
						
					}
					if(trajet != null){
						String reponse = "Meilleur chemin entre " + trajet.get(0).getVilleDep().getNomL() + " et " + trajet.get(0).getVilleArr().getNomL() + " :\n";
						Iterator<Voie> iter= trajet.iterator();
						Voie v = null;
						while(iter.hasNext()){
							v = iter.next();
							reponse += "- " + v.getNom() + " de " + v.getVilleDep().getNomL() + " à " + v.getVilleArr().getNomL() + "\n";
						}
					    double duree = map.getDureeTotale(trajet);
					    reponse += "Duree du trajet : " + Math.round(map.getHeure(duree)) + "h " + Math.round(map.getMin(duree)) + "min " + Math.round(map.getSec(duree)) + "sec" + "\n" + "Distance du trajet : " + map.getDistTotale(trajet) + "km" + "\n" +"Prix du trajet : " + map.getCoutTotal(trajet) + " euros" + "\n";
					    resultTextArea.setText(reponse);
					    System.out.println("Duree du trajet : " + Math.round(map.getHeure(duree)) + "h " + Math.round(map.getMin(duree)) + "min " + Math.round(map.getSec(duree)) + "sec");
					    System.out.println("Distance du trajet : " + map.getDistTotale(trajet) + "km");
					    System.out.println("Prix du trajet : " + map.getCoutTotal(trajet) + " euros");
					    System.out.println(map.getMap().listLieux());
					}
				});
				
			}

			/**
			 * 
			 * R&eacute;action au clic utilisateur sur la grille (implements MouseListener) <BR>
			 * - methode "void mouseReleased(MouseEvent e)" : s&eacute;lection de coordonn&eacute;es<BR>
			 * - autres méthodes sans effet <BR>
			 * 
			 */
			public void mouseReleased(MouseEvent e) {
				x = e.getX() / DELTA;
				y = e.getY() / DELTA;
				xLabel.setText("X = " + x);
				yLabel.setText("Y = " + y);
				try{
					String l = map.rechercheLieu(x,y).getNomL();
					if(!(l.equals("")))
						lieu.setText(l);
				}catch(NullPointerException ex){
				}
			}

			/**
			 * NOP
			 */
			public void mousePressed(MouseEvent e) {
			}

			/**
			 * NOP
			 */
			public void mouseClicked(MouseEvent e) {
			}

			/**
			 * NOP
			 */
			public void mouseEntered(MouseEvent e) {
			}

			/**
			 * NOP
			 */
			public void mouseExited(MouseEvent e) {
			}
		}

		SelectionSubPanel getSelectionPanel() {
			return selectionPanel;
		}

	}

	/**
	 * 
	 * Usage : {@code java applications.Proto fichierImage.jpg}
	 * 
	 * @param argv[0]
	 *            fichierImage.jpg
	 * @throws java.io.IOException
	 *             Erreur d'acc&egrave;s au fichier
	 * @throws SAXException 
	 */
	public static void main(String argv[]) throws java.io.IOException, SAXException {
		
		if (argv.length != 2)
			System.err.println("usage : java applications.Proto fichierImage.jpg");
		else {

		    Dijkstra map = new Dijkstra(argv[0]);
		    
			Interface window = new Interface(argv[1],map);
			window.setTitle("GPS");
			window.setSize(600, 640);
			window.setVisible(true);

			
		}
	}
}