/**
* Jeu de Marienbad en joueur contre ordi
* @author N.Pécheul
* @author K.Sterckx
* @author S.Jeffray
*/
class JcO {
    void principal() {
        afficherTitreMarienbad();
        System.out.println("\u001B[34m" + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\u001B[0m");
        System.out.println();
        
        //Saisi des nom des joueurs :
        String joueur = SimpleInput.getString("Nom du joueur : ");
		String ordinateur = "Ordinateur";
		
		//Choix de celui qui joue en premier :
        int debut = SimpleInput.getInt("Choisir qui commence la partie (1 pour joueur / 2 pour ordinateur) : ");
		
        //Saisie du nombre de lignes :
        int nbLignes = SimpleInput.getInt("Nombre de lignes souhaite : ");
        System.out.println();
        //Redemander en cas de valeur incorrecte :
        while(nbLignes < 2 || nbLignes > 15){
            nbLignes = SimpleInput.getInt("Valeur incorrecte. Veuillez rentrer un nombre de lignes compris entre 2 et 15 : ");
        }
        
        //testCreeTab(); //test de la fonction creeTab
        //testMaxTab(); //test de la fonction maxTab
        //testPowerFunction(); //test de la fonction powerFunction
        
        System.out.println("\u001B[34m" + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\u001B[0m");

		partie(nbLignes, debut, joueur, ordinateur); //Déroulement de la partie de Marienbad
    }

	/**
	* Déroulement d'une partie de Marienbad
	* @param nbLignes le nombre de lignes à mettre dans le tableau
	* @param debut le joueur qui commence à jouer
	* @param joueur le joueur
	* @param ordinateur l'ordinateur
	*/
    void partie(int nbLignes, int debut, String joueur, String ordinateur){
		int[] jeu = creeTab(nbLignes);
        boolean fin = false;
        String gagnantProvisoire = "";
        String dernierJoueur = "";
        int tours = 0;
        
        if(debut == 1){
			gagnantProvisoire = joueur;
		}else if (debut == 2){
			gagnantProvisoire = ordinateur;
		}
        //PARTIE :
        while(!fin){

            //Affichage du jeu :
            System.out.println();
            afficheJeu(jeu);
            System.out.println();
            
            //Affichage du nom du joueur qui joue :
            if(gagnantProvisoire == joueur){
			    System.out.println("A votre tour :");
			}else {
				System.out.println("Ordinateur :");
			}
			
			// Mise à jour de dernierJoueur avant d'inverser
            dernierJoueur = gagnantProvisoire;
			
            //Dis si c'est a l'ordi ou a l'utilisateur de jouer
            if(gagnantProvisoire == joueur){
				//Saisie de la ligne et du nombre d'allumettes à enlever :
				tour(jeu);	
			}else{
				strategieGagnante(jeu);//Déroulement de la stratégie gagnante
			}
			System.out.println("\u001B[34m" + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\u001B[0m");

            //Vérifie si le jeu est fini
            for(int i = 0; i < jeu.length; i++){
                if(jeu[i] == 0){
                    nbLignes--;
                }
            }
            if (nbLignes == 0){
                fin = true;
            }else{
                nbLignes = jeu.length;
            }
            tours++;
            
			// Inversion du joueur après chaque tour
			if (gagnantProvisoire == joueur) {
				gagnantProvisoire = ordinateur;
			} else {
				gagnantProvisoire = joueur;
			}
        }

        //Affichage du nom du vainqueur :
        System.out.println();
        System.out.println("Le vainqueur est : " + dernierJoueur);
    }
    
    /**
	* Stratégie gagnante
	* @param jeu le tableau du jeu
	*/
    void strategieGagnante(int[] jeu){
		String tabBinaire = sumBinary(jeu);// somme des binaire sert a verifier la position gagnante
		int ligne = maxTab(jeu);// choix de la ligne que modifie l'ordi
		int temp = jeu[ligne];//sert a reinitialliser la valeur de la ligne pendant la recherche du coup gagnant
		String tempbinaire = sumBinary(jeu);//sert a verifier si le prochain coup met bien l'adverser dans une position perdante
		
		//Si la position est gagnante trouve le coup adapter
		if(posGagnante(tabBinaire)){
			//calcul le nombre d'allumette a prendre
			
			while(posGagnante(tempbinaire)){
				jeu[ligne] = temp;
				jeu[ligne] -= 1+ (int) (Math.random() * (jeu[ligne]));
				tempbinaire = sumBinary(jeu);
			}
			
		//sinon prend un nombre d'allumette au hasard
		}else{
			jeu[ligne] -= 1+ (int) (Math.random() * (jeu[ligne]));
		}
	}

	/**
	* Créé un tableau d'entier impair croissant de taille "taille"
	* @param taille taille du tableau
    * @return t un tableau d'entier impair croissant de taille "taille"
	*/
    int [] creeTab(int taille){
        int [] t = new int [taille];
        int val = 1;
        for(int i = 0; i < taille; i++){
            t[i] = val ;
            val += 2;
        }
        return t;
    }
    
	/**
	* Test la création de tableau
	*/
	void testCreeTab(){
		System.out.println();
		System.out.println("*** testCreeTab()");
		
		//Test basique :
		int[] tab1 = new int[5];
		testCasCreeTab(5, tab1);
		
		//Test avec un tableau vide 
		int[] tab2 = new int[0];
		testCasCreeTab(0, tab2);
		
	}
    
    /**
	* Test la création de tableau et affiche le résultat
	* @param taille la taille du tableau
	* @param tab le tableau créé
	*/
    void testCasCreeTab(int taille, int[] tab){
        // Affichage
		System.out.print("Tableau (");
		displayTab(tab);
		System.out.print(")");
		System.out.print("\t= " + taille + "\t : ");
		
		// Appel
		int resExec = creeTab(taille).length;
				
		// Vérification
		if (resExec == tab.length){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
    }

    /**
	* affiche le jeu
	* @param t tableau
	*/
    void afficheJeu(int [] t){
        for(int i = 0; i < t.length; i++){
            System.out.print(i + " : ");
            for(int j = 0; j < t[i]; j++){
                System.out.print("| ");
            }
            System.out.println("");
        }
    }
   
    /**
	* simule un tour de jeu
    * @param t est le tableau qui defini le jeu 
	*/
    void tour(int [] t ){
        //choisi la ligne
        int ligne = SimpleInput.getInt("Choisissez votre ligne entre 0 et " + (t.length-1) + " :");
        while(ligne < 0 || ligne > t.length-1){
            ligne = SimpleInput.getInt("Numero de ligne inexistant, rechoisissez votre ligne entre 0 et " + (t.length-1) + " :");
        }
        while(t[ligne] == 0){
            ligne = SimpleInput.getInt("Ligne vide, rechoisissez votre ligne entre 0 et " + (t.length-1) + " :");
        }
        //choisi le nombre d'allumette a retirer
        int nbAllumette = SimpleInput.getInt("Choisissez combien d allumette vous retirez entre  1 et " + (t[ligne]) + " :");
        while(nbAllumette < 1 ||  nbAllumette > t[ligne]){
            nbAllumette = SimpleInput.getInt("Nombre incorrect, Choisissez combien d allumette vous retirez entre  1 et " + (t[ligne]) + " :");
        }
        //retire les allumettes
        t[ligne] -= nbAllumette ;
    }
   
    /**
	* fait la somme de la valeur binaire d'un tableau d'entier
	* @param tab un tableau d'entier
    * @return strS une chaine de caractere etant la somme des valeur binaire de tab
	*/
    String sumBinary(int[] tab){
		
		//crée une copie d'un tableau d'entier et met ses valeur sous forme binaire en chaine de caractere
		String[] tabBinaire = new String[tab.length];
		for(int i = 0; i<tab.length; i++){
			tabBinaire[i] = Integer.toBinaryString(tab[i]);
		}
		
		//fais la somme du tableau de binaire
		int s = 0;
		for(int i = 0;i<tabBinaire.length;i++){
			s += Integer.valueOf(tabBinaire[i]);
		}
		String strS = Integer.toString(s);
		return strS ;
	}
	
	
	/**
	* dis si la position actuel est gagnante pour le joueur suivant
	* @param sumBinary une string etant le resultat d'une somme de nombre binaire
    * @return vrai sitout les chiffres de sumBinary sont paires
	*/
	boolean posGagnante(String sumBinary){
		boolean res = false ;
		int j = 0;
		
		//verifie si un des chiffre de la somme des nombre binaire est impair 
		for(int i = 0; i<sumBinary.length();i++){
			j = Integer.valueOf(sumBinary.charAt(i));
			if(j % 2 != 0){
				res = true ;
			}
		}
		return res;
	}
	
	
	/**
	* trouve l'indice du plus grand entier d'un tableau
	* @param tab un tableau d'entier
    * @return max l'indice du plus grand nombre du tableau
	*/
	int maxTab(int[] tab){
		int max = 0;
		for(int i = 1;i< tab.length;i++){
			if(tab[i] > tab[max]){
				max = i;
			}
		}
		return max ;
	}
	
	/**
	* Test la recherche du plus grand entier du tableau
	*/
	void testMaxTab(){
		System.out.println();
		System.out.println("*** testMaxTab()");
		
		//Test basique :
		int[] tab1 = {1, 2, 3, 4};
		testCasMaxTab(tab1, 3);
		
		//Test tableau avec valeurs aléatoires
		int[] tab2 = {5, 45, 6, 1, 0};
		testCasMaxTab(tab2, 1);
		
		//Test avec des valeurs négatives
		int[] tab3 = {61, -1, 2, 0, -45};
		testCasMaxTab(tab3, 0);
		
		//Test avec un tableau rempli de 0
		int[] tab4 = {0, 0, 0};
		testCasMaxTab(tab4, 0);
	}
    
    /**
	* test la recherche du plus grand entier du tableau et affiche le résultat
	* @param tab le tableau créé
	* @param result l'indice avec le plus grand entier du tableau
	*/
    void testCasMaxTab(int[] tab, int result){
        // Affichage
		System.out.print("Tableau (");
		displayTab(tab);
		System.out.print(")");
		System.out.print("\t= " + result + "\t : ");
		
		// Appel
		int resExec = maxTab(tab);
				
		// Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
    }
	
	/**
	* fais le calcul d'une puissance
	* @param base nombre multiplié
	* @param exponent la puissance de base
    * @return result un int resultat de base**exponent
	*/
	static int powerFunction(int base, int exponent) {
		int result = 1;
        for (int i = 0; i < exponent; i++) {
           result = base * result;
        }
       return result;
    }
    
    
    /**
	* Test la recherche du plus grand entier du tableau
	*/
	void testPowerFunction(){
		System.out.println();
		System.out.println("*** testPowerFunction()");
		
		testPowerFunction(2, 3, 8);
		testPowerFunction(-2, 3, -8);
		testPowerFunction(0, 3, 0);
		testPowerFunction(5, 0, 1);
	}
    
    /**
	* test la recherche du plus grand entier du tableau et affiche le résultat
	* @param tab le tableau créé
	* @param result l'indice avec le plus grand entier du tableau
	*/
    void testPowerFunction(int base, int exponent, int result){
        // Affichage
		System.out.print("Valeur : " + base + ", " + "Exposant : " + exponent);
		System.out.print("\t= " + result + "\t : ");
		
		// Appel
		int resExec = powerFunction(base, exponent);
				
		// Vérification
		if (resExec == result){
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
    }
    
    /**
	* Affiche le tableau
	* @param t tableau d’entiers
	*/
	void displayTab(int[] t){
		int i = 0;
		System.out.print("{");
		if(t.length == 0){
			System.out.print("}");
		}else{
			while(i<t.length-1){
				System.out.print(t[i] + ",");
				i=i+1;
			}
			System.out.print(t[i]+"}");
		}
    }
   
    /**
	* affiche le nom du jeu en titre
	*/
    void afficherTitreMarienbad() {
		System.out.println("	   _____                      _            _______                _                 _                 _ ");
        System.out.println("	  (_____)                    | |          |  ___  |              (_)               | |               | |");
        System.out.println("	     _     ____  _   _     _ | |   ____   | | _ | |  ____   ____  _    ____  _____ | | __   ____   _ | |");
        System.out.println("	    | |   / _  )| | | |   / || |  / _  )  | || || | / _  | / ___)| |  / _  )|  _  || | | | / _  | / || |");
        System.out.println("	 ___| |  | (/ / | |_| |  ( (_| |  |(/ /	  | || || |( ( | || |    | | | (/ / | | | || |_| |( ( | |( (_| |");
        System.out.println("	(____/   |____) |_____|   |____|  |____)  |_||_||_| |_||_||_|    |_| |____) |_| |_||____/  |_||_| |____|");
        System.out.println();
    }
}