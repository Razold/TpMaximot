package fr.eni.poo.tp.maximot;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;


public class Maximot {
	

	private static final String NOM_FICHIER_DICTIONNAIRE = "./dictionnaire.txt";
	private static final int NB_MOTS_MAX = 22506;
	
	public static void main(String[] args) {

		
		//int nombreAleatoire-generateurAleatoire.nextInt(10);
		// un mot est tiré au sort dans le dictionnaire
		char[]  lettresMotTireAuSort;
	 
		lettresMotTireAuSort=tirerMotAleatoirement();
		
		System.out.println("lettresMotTireAuSort = " +Arrays.toString(lettresMotTireAuSort));	
		/**
		 * Selectionne un mot aléatoirement dans le dictionnaire	
		 * @return Les caractères sélectionnés
		 * Le dictionnaire ne doit comporter qu'un seul mot par ligne
		 */	
		// melanger le mot choisi
		char[] motMelange=melanger(lettresMotTireAuSort);
		
		String motMelangeString = new String ( motMelange ); // "transformer" un char[] en String
		// OU
		// String motMelange = String.valueOf(motMelange);
		
		
		
		System.out.println("Mot mélangé :" + motMelangeString+"**") ; // Pour DEBUG
		
		afficher(motMelange) ;
		
		// Demande à l'utilisatuer qu'il saisisse sa proposition de mot
		char[] motSaisi;
		motSaisi = saisieUtilisateur();
		
		// Vérification de la correspondance des lettres
		boolean motSaisiContientLesLettres = bonnesLettres_2(motMelange, motSaisi);
		System.out.println("motSaisiContientLesLettres ? -> " + motSaisiContientLesLettres);
		
		// vérifier que le mot propose par l'utilisateur est dans le dictionnaire
		boolean estDansLeDico = dansleDico(motSaisi);
		System.out.println("Mot dans le dictionnaire ? --> " + estDansLeDico);
	}

	/**
	 * Retourne vrai si les lettres utilisées
	 *  par le joueur sont bien toutes parmi 
	 *  les lettres tirées.
	 *  
	 * @param lettresTirage
	 * @param motPropose
	 * @return
	 */
	/*private static boolean bonnesLettres(char[] lettresTirage, char[] motPropose){
		boolean lettresPresentes = false;
		boolean lettreCourante = false;
		//Pour chaque lettre de motPropose vérifier si elle se trouve dans lettreTirage
		if(motPropose.length <= lettresTirage.length) {
			for(int indiceMotPropose = 0;indiceMotPropose < motPropose.length;indiceMotPropose++) {
				lettreCourante = false;
				for(int indiceLettresTirage = 0;indiceLettresTirage < lettresTirage.length;indiceLettresTirage++){
					if(motPropose[indiceMotPropose] == lettresTirage[indiceLettresTirage]){
						//Si on decouvre qu'une lettre est à la fois parmi les lettres tirées et dans le mot proposé
						//On le signale en mettant lettreCourante à vrai et on sort de la boucle
						//je change la lettre tirée sélectionnée pour ne plus la réutiliser par la suite
						lettresTirage[indiceLettresTirage]='\u0000';//Détruire la lettre pour indiquer qu'elle a été utilisée
						lettreCourante=true;
						indiceLettresTirage = lettresTirage.length;
					}
				}
				if(lettreCourante== false){
					//Si la lettre sélectionnée du mot proposé n'est pas parmi les lettres tirées alors le mot proposé n'est pas bon
					//On le signale en mettant lettresPresentes à false et en sortant de la grande boucle
					lettresPresentes=false;
					indiceMotPropose = motPropose.length;
				}else {
					//Si la lettre sélectionnée du mot proposé est  parmi les lettres tirées 
					//On le signale en mettant lettresPresentes à true et en continuant dans la grande boucle en passant à la lettre suivante du mot proposé
					lettresPresentes = true;
				}
			}			
		}		
		return lettresPresentes;
	}*/

private static boolean bonnesLettres_2(char[] lettresTirage, char[] motPropose) {
		
		boolean lettresPresentes = false;
		
		boolean lettreCourantePresente = true;
		
		boolean lettreTrouvee = false;
		
		// les mots ont-ils le même nombre de lettres ?
		if (lettresTirage.length == motPropose.length) {
		
			int indiceMotPropose = 0;
			int indiceTirage = 0;
			
			// Tant que les lettres correspondent et qu'on est pas à la fin du mot proposé, on continue
			while (lettreCourantePresente && indiceMotPropose< motPropose.length) {
				indiceTirage = 0;
				lettreTrouvee = false;
				
				// On parcourt tout le mot à la recherche de la lettre courante
				while  (  !lettreTrouvee && indiceTirage < lettresTirage.length ) 	{
					
					// si on trouve la même lettre...
					if ( motPropose[indiceMotPropose] == lettresTirage[indiceTirage] ) {
						lettreTrouvee = true;
						lettresTirage[indiceTirage] = '\u0000'; // "détruire" la lettre pour indiquer qu'elle a été utilisée
					}
					
					indiceTirage++;
				}
				
				if ( ! lettreTrouvee) {
					lettreCourantePresente = false;
				}
				
				indiceMotPropose++;
			}
			
			// Si on a parcouu les 2 mots et que lettreCourantePresente est true, c'est que toutes les lettres ont été trouvées
			if (lettreCourantePresente) { 
				lettresPresentes = true;
			}
			
		}
		
		return lettresPresentes;
		
	}
		
	
	/**
 * Melanger le mot lettresMotTireAuSort
 * 
 * @param lettresMotTireAuSort
 * 
 * @return
 */
private static char[] melanger(char[] motTire) {
	char[] lettresMelangees = new char[50];
	int nbaleatoire;
	
	//String chaineTiree = new String(motTire);
	 int longueurMot= motTire.length;
	// int longueurMot= motTire.length;
	boolean estAbsent;
	int compteur=0;
	Random melangeurAleatoire = new Random();
	int[]positionLettresRentrees= new int[motTire.length];//On créé un tableau d'int contenant les positions des lettres de MotTire déjà rentrées dans lettresMelangees 
	//On verifie que nbaleatoire n'est pas contenue dans positionsLettresRentrées	
	for(int i =0; i < longueurMot; i++){
		estAbsent= true;
	 //nbaleatoire = melangeurAleatoire.nextInt(motTire.length);
		
	do {
		System.out.println("estAbsent do while vaut " +estAbsent);
		estAbsent = true;
		System.out.println("motTire = " +Arrays.toString(motTire));	
		System.out.println("lettresMelangees = " +Arrays.toString(lettresMelangees));	
		nbaleatoire = melangeurAleatoire.nextInt(motTire.length);//on choisie un nombre aléatoire situé entre 0 et la taille du mot à mélanger -1
		System.out.println("nombre aleatoire = " +nbaleatoire);	
		System.out.println("lettre à la  position du nombre aleatoire = " +motTire[nbaleatoire]);

			System.out.println("la longueur de positionLettresRentrees vaut "+positionLettresRentrees.length+" la longueur de MotTire vaut "+motTire.length);	
						
			while(compteur < positionLettresRentrees.length -1){
				System.out.println("estAbsent while vaut " +estAbsent);
				System.out.println("compteur vaut "+compteur+ " positionlettreRentrees["+compteur+"] vaut "+positionLettresRentrees[compteur]+" nbaleatoire vaut "+nbaleatoire);	
				System.out.println("On examine les nombres de  positionLettresRentrees un par un");	
				if(positionLettresRentrees[compteur] == nbaleatoire){
					System.out.println("on verifie la position de la lettre");	
					estAbsent = false;
					compteur = positionLettresRentrees.length;
				}
		compteur++;
		}
			compteur =0;
		System.out.println("estAbsent fin boucle = " +estAbsent);
		
	}while(estAbsent != true);
	
	 positionLettresRentrees[i]=nbaleatoire;
	 lettresMelangees[i] = motTire[nbaleatoire];//On choisie la lettre à la position du nombre aléatoire 

		//On affecte la lettre choisie au hasard au futur mot

	// lettresMelangees[i] = motTire[nbaleatoire];
	 
	//n.replace(motTire, newChar);
	//System.out.printf("lettresMelangees[] vaut %s \n", lettresMelangees);//On affiche le futur mot pour voir comment il évolue
	System.out.println("lettresMelangees = " +Arrays.toString(lettresMelangees));	
	
	 //motTire.replace();
	//On supprime la lettre choisie pour ne plus la piochée à nouveau
	//Arrays.copyOf(original, newLength) permet de copier le mot en reduisant sa longueur et en ne prenant pas les lettres situées après la position newLength
	//Arrays.copyOfRange(original, from, to) permet de copier le mot en extrayant une sous-chaine
	
	
	//String n = new String(motTire);
	 
	}
		
	return(lettresMelangees);
}

	//Le résultat du tirage est affiché
	public static void afficher(char[] motMelange) {
		// Pour chaque lettre du tableau...
		for (int indiceLettreAAfficher = 0; indiceLettreAAfficher < motMelange.length; indiceLettreAAfficher++) {
			// ..., l'afficher
			System.out.print( motMelange[ indiceLettreAAfficher ]   );
		}
	}



	/*
	//Le mot tiré au sort est affiché
	 * 	Créer saisieUtilisateur() qui ne prend aucun parametre et qui renvoie la saisie de l'utilisateur sous la forme d'un char[]
	//Le joueur saisie une proposition*/
	public static char[]saisieUtilisateur(){
		char[] motSaisie;
		Scanner scan = new Scanner(System.in);
		String userInput=scan.nextLine();
		motSaisie= userInput.toUpperCase().toCharArray();
		scan.close();
		return motSaisie;
	}

	//On vérifie si le mot proposé est présente dans le dictionnaire
	private static boolean dansleDico(char[] motPropose){
		
		char[] motLu;
		//Ouvrir le dictionnaire
		boolean estDansLeDictionnaire= false;
		int compteurDeLettres =0; 
		
		try(FileInputStream fis = new FileInputStream(NOM_FICHIER_DICTIONNAIRE);
				Scanner scaneur = new Scanner(fis);)
		{
			// pour chaque mot du dictionaire...
			for (int i=0 ;  i < NB_MOTS_MAX   ; i++  ) {
				motLu = scaneur.nextLine().toUpperCase().toCharArray(); // !! Fonctionne car 1 ligne = 1 mot dans le dictionnaire	
				// vérifier que chacune de ses lettres est identique aux letrres du mot proposé
				
				// Imterrompre la recherche si les mots sont de taille différentes
				if (motPropose.length != motLu.length) {
					continue;   // interrompt le flux d'exécution pour passer à la prochaine itération de la boucle
				}
				
				// vérifer les lettres une à une
				compteurDeLettres = 1;
				for (int indiceLettresAComparer = 0; indiceLettresAComparer < motPropose.length; indiceLettresAComparer++) {
					
					if (motPropose[indiceLettresAComparer] != motLu[indiceLettresAComparer] ) {
						break; // sortir de cette boucle
					}
					compteurDeLettres ++;
				}
				
				// Si on arrive ici et qu'on a comparé toutes les lettres du mot proposé...
				if (compteurDeLettres >= motPropose.length) {
					//...c'est qu'il est identique au mot du dictionnaire
					estDansLeDictionnaire = true;	
				}
			}
		}catch(FileNotFoundException fnfe) {
			System.err.println("Le fichier "+NOM_FICHIER_DICTIONNAIRE+" n'a pas été trouvé.");
			fnfe.printStackTrace();
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return estDansLeDictionnaire;
	}

	private static char[] tirerMotAleatoirement() {
	
		char[] MotTire=new char[50];// Détecter la taille maximale d'un mot du dictionnaire
		
		//Générer un nombre aléatoire X
		Random generateur = new Random();//
		int indiceAleatoire = generateur.nextInt(NB_MOTS_MAX)+1;//TODO Détecter le nombre de mots maximum
		String motLu = null;//OK car l'algorithme assure que motLu est toujours valorisé (donc MotTire= motLu.toUpperCase().toCharArray() ne déclenchera pas de NullPointerException) 
		//Ouvrir le dictionnaire
		try(FileInputStream fis = new FileInputStream(NOM_FICHIER_DICTIONNAIRE);
				Scanner scaneur = new Scanner(fis);)
		{
			
			//Aller chercher le Xème mot du dictionnaire
			// -Lire les X premiers mots du dictionnaire
			for(int i=0; i < indiceAleatoire;i++){
				motLu=scaneur.nextLine();//Il fonctionne car dans ce cas un mot par ligne dans le dictionnaire
			}
		}catch(FileNotFoundException fnfe) {
			
			fnfe.printStackTrace();
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	
		
	
		
		//Transformer le mot en un tableau de caractères
		MotTire= motLu.toUpperCase().toCharArray();
		
	
		
	
		return MotTire;	
	}
}
