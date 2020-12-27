package game;

import java.util.Scanner;
import sudoku.Grid;

public class Game {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Grid g = new Grid(); 
		
		
		//Configuration de la partie
		System.out.println("Configuration de la partie : ");
		int errorMax = -2;
		int errorCounter = 0;
		int difficulty = 0;
		int roundCounter = 1;
		String str;
		while(difficulty < 1 || difficulty > 3) {
			System.out.println("Choisissez la difficulté de la grille : 1 (facile), 2 (moyen), 3 (difficile)");
			difficulty = sc.nextInt();
			if(difficulty < 1 || difficulty > 3) System.out.println("Saisie invalide.");
		}
		if (difficulty == 1) {
			g.removeCells(23);
		} else if (difficulty == 2) {
			g.removeCells(25);
		} else {
			g.removeCells(27);
		}
		while(errorMax < -1) {
			System.out.println("Nombre d'erreurs autorisées (illimité : -1) : ");
			errorMax = sc.nextInt();
			if(errorMax < -1) System.out.println("Saisie invalide.");
		}
		
		
		//boucle de jeu
		while(!g.isGridCorrect()) {
			System.out.println(g);
			str = "Tour : " + roundCounter + " | Erreur : " + errorCounter + "/" + errorMax;
			System.out.println(str);
			roundCounter++;
			int row = -1;
			while(row < 0 || row > 8) {
				System.out.println("Entrez la ligne (1-9) : ");
				row = sc.nextInt()-1;
				if(row < 0 || row > 8) {
					System.out.println("Saisie de la ligne invalide.");
				}
			}
			int column = -1;
			while(column < 0 || column > 8) {
				System.out.println("Entrez la colonne (1-9) : ");
				column = sc.nextInt()-1;
				if(column < 0 || column > 8) {
					System.out.println("Saisie de la colonne invalide.");
				}
			}
			
			if(g.getCell(row, column) !=  0) {
				System.out.println("Cette case est déjà remplie.");
				continue;
			}
			System.out.println("Entrez la valeur (1-9) : ");
			int value = sc.nextInt();
			if(g.isValuePossible(row, column, value)) {
				g.setCell(row, column, value);
			} else {
				System.out.println("Vous avez fait une erreur !");
				errorCounter++;
				if(errorCounter > errorMax) {
					System.out.println("La partie est perdue.");
					break;
				}
			}
		}
		sc.close();
		
		System.out.println("+-------------------------------------+");
		System.out.println("| Bravo ! Vous avez résolu la grille. |");
		System.out.println("+-------------------------------------+");
	}

}
