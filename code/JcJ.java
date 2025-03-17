




/**
* Jeu de Marienbad en joueur contre joueur
* @author N.Pécheul
* @author K.Sterckx
* @author S.Jeffray
*/
class JcJ {
    void principal() {
		afficherTitreMarienbad();
		System.out.println("\u001B[34m" + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\u001B[0m");
        System.out.println();
        
        //Saisi des nom des joueurs :
        String joueur1 = SimpleInput.getString("Nom du joueur 1 : ");
        String joueur2 = SimpleInput.getString("Nom du joueur 2 : ");

        //Saisie du nombre de lignes :
        int nbLignes = SimpleInput.getInt("Nombre de lignes souhaite : ");
        System.out.println();
        //Redemander en cas de valeur incorrecte :
        while(nbLignes < 2 || nbLignes > 15){
            nbLignes = SimpleInput.getInt("Valeur incorrecte. Veuillez rentrer un nombre de lignes compris entre 2 et 15 : ");
        }
        
        //testCreeTab(); //test de la fonction creeTab
        
        System.out.println("\u001B[34m" + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-" + "\u001B[0m");
        
        partie(nbLignes, joueur1, joueur2);
    }
    
	/**
	* Déroulement de la partie
	* @param nbLignes le nombre de lignes dans la partie
    * @param joueur1 le nom du joueur1
    * @param joueur2 le nom du joueur2
	*/
    void partie(int nbLignes, String joueur1, String joueur2){
        int[] jeu = creeTab(nbLignes);
        boolean fin = false;
        String gagnantProvisoire = "";
        int tours = 0;
        //PARTIE :
        while(!fin){
            //Choisi à qui est le tour
            if(tours % 2 == 0){
                gagnantProvisoire = joueur1;
            }else{
                gagnantProvisoire = joueur2;
            }

            //Affichage du jeu :
            System.out.println();
            afficheJeu(jeu);

            //Affichage du nom du joueur qui joue :
            System.out.println(gagnantProvisoire);
        
            //Saisie de la ligne et du nombre d'allumettes à enlever :
            tour(jeu);

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
        }

        //Affichage du nom du vainqueur :
        System.out.println("Le vainqueur est : " + gagnantProvisoire);
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
	* Teste la création du tableau
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
	* test la création de tableau et affiche le résultat
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
