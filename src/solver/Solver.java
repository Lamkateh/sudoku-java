package solver;

import java.util.ArrayList;

import sudoku.Grid;

public class Solver {
	private Grid grid;
	
	public Solver() {}
	
	public Solver(Grid grid) {
		this.grid = grid;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	/**
	 * Permet d'obtenir le temps d'exécution d'une méthode d'instance
	 * 
	 * @param obj Instance
	 * @param funcName Nom de la méthode
	 * @return long Temps d'exécution en millisecondes
	 * @throws Exception
	 */
	public static long testExecutionTime(Object obj, String funcName) throws Exception {
		long t0 = System.currentTimeMillis();
		obj.getClass().getDeclaredMethod(funcName).invoke(obj);
		return System.currentTimeMillis() - t0;
	}
	
	/**
	 * Permet d'obtenir un entier aléatoire entre deux bornes
	 * 
	 * @param min Borne inférieure
	 * @param max Borne supérieure
	 * @return int Nombre aléatoire
	 */
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	/**
	 * Permet de remplir les cases où une seule valeur est possible
	 * 
	 * @return
	 */
	private int naiveSolveCellByCell() {
		int possibleValueCounter = 0;
		int lastPossibleValue = 0;
		int setCaseCounter = 0;
		boolean newSetCase = true;
		while(!grid.isGridCorrect() && newSetCase) {
			newSetCase = false;
			for (int row = 0; row < grid.getGrid().length; row++) {
				for (int column = 0; column < grid.getGrid().length; column++) {
					if(grid.getCell(row, column) != 0) continue;
					possibleValueCounter=0;
					for (int value = 1; value < 10; value++) {
						if(grid.isValuePossible(row, column, value)) {
							possibleValueCounter++;
							lastPossibleValue=value;
							if(possibleValueCounter>1) break;
						}
					}
					if(possibleValueCounter==1) {
						grid.setCell(row, column, lastPossibleValue);
						setCaseCounter++;
						newSetCase = true;
					}
				}
			}
		}
		return setCaseCounter;
	}
	
	
	/**
	 * Permet de remplir les cases étant la seule possibilité d'une valeur pour une même ligne
	 * 
	 * @return
	 */
	private int naiveSolveRowByRow() {
		int possiblePositionCounter = 0;
		int lastPossiblePosition = 0;
		int setCaseCounter = 0;
		boolean newSetCase = true;
		while(!grid.isGridCorrect() && newSetCase) {
			newSetCase = false;
			for (int row = 0; row < grid.getGrid().length; row++) {
				for (int value = 1; value < 10; value++) {
					possiblePositionCounter=0;
					for (int column = 0; column < grid.getGrid().length; column++) {
						if(grid.getCell(row, column) != 0) continue;
						if(grid.isValuePossible(row, column, value)) {
							lastPossiblePosition = column;
							possiblePositionCounter++;
							if(possiblePositionCounter>1) break;
						}
					}
					if(possiblePositionCounter==1) {
						grid.setCell(row, lastPossiblePosition, value);
						setCaseCounter++;
						newSetCase = true;
					}
				}
			}
		}
		return setCaseCounter;
	}
	
	/**
	 * Permet de remplir les cases étant la seule possibilité d'une valeur pour une même colonne
	 * 
	 * @return
	 */
	private int naiveSolveColumnByColumn() {
		int possiblePositionCounter = 0;
		int lastPossiblePosition = 0;
		int setCaseCounter = 0;
		boolean newSetCase = true;
		while(!grid.isGridCorrect() && newSetCase) {
			newSetCase = false;
			for (int column = 0; column < grid.getGrid().length; column++) {
				for (int value = 1; value < 10; value++) {
					possiblePositionCounter=0;
					for (int row = 0; row < grid.getGrid().length; row++) {
						if(grid.getCell(row, column) != 0) continue;
						if(grid.isValuePossible(row, column, value)) {
							lastPossiblePosition = row;
							possiblePositionCounter++;
							if(possiblePositionCounter>1) break;
						}
					}
					if(possiblePositionCounter==1) {
						grid.setCell(lastPossiblePosition, column, value);
						setCaseCounter++;
						newSetCase = true;
					}
				}
			}
		}
		return setCaseCounter;
	}
	
	/**
	 * Permet de remplir les cases étant la seule possibilité d'une valeur pour un même carré
	 * 
	 * @return
	 */
	private int naiveSolveSquareBySquare() {
		int possiblePositionCounter = 0;
		int lastPossibleColumn = 0;
		int lastPossibleRow = 0;
		int setCaseCounter = 0;
		boolean newSetCase = true;
		while(!grid.isGridCorrect() && newSetCase) {
			newSetCase = false;
			//parcours des carré
			for (int r0 = 0; r0 < grid.getGrid().length; r0+=3) {
				for (int c0 = 0; c0 < grid.getGrid().length; c0+=3) {
					for (int value = 1; value < 10; value++) {
						//parcours du carré
						for (int row = r0; row < r0+3; row++) {
							for (int column = c0; column < c0+3; column++) {
								if(grid.getCell(row, column) != 0) continue;
								if(grid.isValuePossible(row, column, value)) {
									lastPossibleRow = row;
									lastPossibleColumn = column;
									possiblePositionCounter++;
									if(possiblePositionCounter>1) break;
								}
							}
						}
						if(possiblePositionCounter==1) {
							grid.setCell(lastPossibleRow, lastPossibleColumn, value);
							setCaseCounter++;
							newSetCase = true;
						}
					}
				}
			}
		}
		return setCaseCounter;
	}
	
	/**
	 * Permet de résoudre une grille de sudoku de manière "naive", tel qu'un humain le ferait généralement
	 * 
	 * @return
	 */
	public int naiveSolve() {
		int setCaseCounter = 0;
		int setCaseCounterLast = 0;
		boolean newSetCase = true;
		while(!grid.isGridCorrect() && newSetCase) {
			newSetCase = false;
			setCaseCounterLast = setCaseCounter;
			setCaseCounter += this.naiveSolveCellByCell();
			setCaseCounter += this.naiveSolveColumnByColumn();
			setCaseCounter += this.naiveSolveRowByRow();
			setCaseCounter += this.naiveSolveSquareBySquare();
			if(setCaseCounterLast < setCaseCounter) newSetCase = true;
		}
		return setCaseCounter;
	}
	
	/**
	 * Permet de compter les cases vides d'une grille
	 * 
	 * @return int Nombre de cases à remplir
	 */
	public int countCellToFill() {
		int counter = 0;
		for (int row = 0; row < this.grid.getGrid().length; row++) {
			for (int column = 0; column < this.grid.getGrid().length; column++) {
				if(this.grid.getGrid()[row][column] == 0) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	/**
	 * Renvoie un parcours des cases à remplir dans la grille.
	 * Ce parcours est linéaire en incrémentant les lignes puis les colonnes.
	 * 
	 * @return ArrayList Parcours des cases à remplir
	 */
	private ArrayList<Integer[]> getSimplePath() {
		ArrayList<Integer[]> path = new ArrayList<Integer[]>();
		for (int row = 0; row < this.grid.getGrid().length; row++) {
			for (int column = 0; column < this.grid.getGrid().length; column++) {
				if(this.grid.getGrid()[row][column] == 0) {
					path.add(new Integer[] {row, column});
				}
			}
		}
		return path;
	}
	
	/**
	 * Renvoie un parcours des cases à remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilités par cases de façon croissante
	 * 
	 * @return ArrayList Parcours des cases à remplir
	 */
	private ArrayList<Integer[]> getPathByPossibilitiesAsc() {
		ArrayList<Integer[]> path = new ArrayList<Integer[]>();
		for (int counter = 1; counter < 10; counter++) {
			for (int row = 0; row < this.grid.getGrid().length; row++) {
				for (int column = 0; column <  this.grid.getGrid().length; column++) {
					if(this.grid.countPossibleValues(row, column) == counter && this.getGrid().getCell(row, column) == 0) {
						path.add(new Integer[] {row, column});
					}
				}
			}
		}
		return path;
	}
	
	/**
	 * Renvoie un parcours des cases à remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilités par cases de façon décroissante
	 * 
	 * @return ArrayList Parcours des cases à remplir
	 */
	private ArrayList<Integer[]> getPathByPossibilitiesDesc() {
		ArrayList<Integer[]> path = new ArrayList<Integer[]>();
		for (int counter = 9; counter > 0; counter--) {
			for (int row = 0; row < this.grid.getGrid().length; row++) {
				for (int column = 0; column <  this.grid.getGrid().length; column++) {
					if(this.grid.countPossibleValues(row, column) == counter && this.getGrid().getCell(row, column) == 0) {
						path.add(new Integer[] {row, column});
					}
				}
			}
		}
		return path;
	}
	
	/**
	 * Renvoie un parcours des cases à remplir dans la grille.
	 * Ce parcours est linéaire en incrémentant les lignes puis les colonnes.
	 * 
	 * @return int[][] Parcours des cases à remplir
	 */
	private int[][] getSimpleArrayPath() {
		int[][] path = new int[countCellToFill()][2];
		int pathPosition = 0;
		for (int row = 0; row < this.grid.getGrid().length; row++) {
			for (int column = 0; column < this.grid.getGrid().length; column++) {
				if(this.grid.getGrid()[row][column] == 0) {
					path[pathPosition][0] = row;
					path[pathPosition][1] = column;
					pathPosition++;
				}
			}
		}
		return path;
	}
	
	/**
	 * Renvoie un parcours des cases à remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilités par cases de façon croissante
	 * 
	 * @return int[][] Parcours des cases à remplir
	 */
	private int[][] getArrayPathByPossibilitiesAsc() {
		int[][] path = new int[countCellToFill()][2];
		int pathPosition = 0;
		for (int counter = 1; counter < 10; counter++) {
			for (int row = 0; row < this.grid.getGrid().length; row++) {
				for (int column = 0; column <  this.grid.getGrid().length; column++) {
					if(this.grid.countPossibleValues(row, column) == counter && this.getGrid().getCell(row, column) == 0) {
						path[pathPosition][0] = row;
						path[pathPosition][1] = column;
						pathPosition++;
					}
				}
			}
		}
		return path;
	}
	
	
	/**
	 * Renvoie un parcours des cases à remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilités par cases de façon décroissante
	 * 
	 * @return int[][] Parcours des cases à remplir
	 */
	private int[][] getArrayPathByPossibilitiesDesc() {
		int[][] path = new int[countCellToFill()][2];
		int pathPosition = 0;
		for (int counter = 9; counter > 0; counter--) {
			for (int row = 0; row < this.grid.getGrid().length; row++) {
				for (int column = 0; column <  this.grid.getGrid().length; column++) {
					if(this.grid.countPossibleValues(row, column) == counter && this.getGrid().getCell(row, column) == 0) {
						path[pathPosition][0] = row;
						path[pathPosition][1] = column;
						pathPosition++;
					}
				}
			}
		}
		return path;
	}
	
	/**
	 * Résoud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilités de façon croissante sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean possibilitiesAscBacktrackingArray() {
		return backtracking(getArrayPathByPossibilitiesAsc());
	}
	
	/**
	 * Résoud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilités de façon décroissante sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean possibilitiesDescBacktrackingArray() {
		return backtracking(getArrayPathByPossibilitiesDesc());
	}
	
	/**
	 * Résoud une grille de sudoku en utilisant le backtracking avec un parcours linéaire sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean simpleBacktrackingArray() {
		return backtracking(getSimpleArrayPath());
	}
	
	/**
	 * Résoud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilités de façon croissante sous forme d'une ArrayList
	 * 
	 * @return
	 */
	public boolean possibilitiesAscBacktracking() {
		return backtracking(getPathByPossibilitiesAsc());
	}
	
	/**
	 * Résoud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilités de façon décroissante sous forme d'une Arraylist
	 * 
	 * @return
	 */
	public boolean possibilitiesDescBacktracking() {
		return backtracking(getPathByPossibilitiesDesc());
	}
	
	/**
	 * Résoud une grille de sudoku en utilisant le backtracking avec un parcours linéaire sous forme d'une ArrayList
	 * 
	 * @return
	 */
	public boolean simpleBacktracking() {
		return backtracking(getSimplePath());
	}
	
	/**
	 * Permet de résoudre une grille de sudoku en utilisant la force brute.
	 * 
	 * @param path Parcours des cases à remplir
	 * @return boolean Validité de la grille
	 */
	private boolean backtracking(int[][] path) {
		return backtracking(path, 0);
	}
	
	/**
	 * Permet de résoudre une grille de sudoku en combinant la résolution naive puis un backtracking utilisant un parcours en fonction du nombre de possibilités de façon croissante sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean solve() {
		this.naiveSolve();
		return this.possibilitiesAscBacktrackingArray();
	}
	
	/**
	 * Permet de résoudre une grille de sudoku en utilisant la force brute.
	 * 
	 * @param path Parcours des cases à remplir
	 * @return boolean Validité de la grille
	 */
	private boolean backtracking(ArrayList<Integer[]> path) {
		int value = 0, pathPosition = 0, row = -1, column = -1;
		
		while(!grid.isGridCorrect()) {
			
			row = path.get(pathPosition)[0];
			column = path.get(pathPosition)[1];
			
			value = this.grid.getGrid()[row][column]+1;
			
			while(!grid.isValuePossible(row, column, value) || value > 9) {
				value++;
				if(value>9) {
					value=0;
					pathPosition-=2;
					break;
				}
			}
			this.grid.getGrid()[row][column]=value;
			pathPosition++;
			if(pathPosition == path.size()) break;
			if(pathPosition == -1) return false;
		}
		return true;
	}
	
	/**
	 * Permet de résoudre une grille de sudoku en utilisant la force brute.
	 * 
	 * @param path Parcours des cases à remplir
	 * @param pathPosition Position de début du parcours
	 * @return boolean Validité de la grille
	 */
	private boolean backtracking(int[][] path, int pathPosition) {
		int value = 0;
		int row = -1, column = -1;
		
		while(!grid.isGridCorrect()) {
			row = path[pathPosition][0];
			column = path[pathPosition][1];
			
			value = this.grid.getGrid()[row][column]+1;
			
			while(!grid.isValuePossible(row, column, value) || value > 9) {
				value++;
				if(value>9) {
					value=0;
					pathPosition-=2;
					break;
				}
			}
			this.grid.getGrid()[row][column]=value;
			pathPosition++;
			if(pathPosition == path.length) break;
			if(pathPosition == -1) return false;
		}
		return true;
	}
	
	/**
	 * Permet de compter le nombre de solutions possibles d'une grille non remplie
	 * 
	 * @return int Nombre de solutions
	 */
	public int countSolutions() {
		int row = 0, column = 0, currentValue = 0, newValue = 0;
		boolean tryNewSol = false;
		int[][] path = getArrayPathByPossibilitiesAsc();
		int pathSize = path.length, countSolutions = 0, pathPosition = 0;
		
		while(backtracking(path, pathPosition)) {
			pathPosition = pathSize-1;
			//recherche du prochain sur le dernier element
			while(!tryNewSol) {
				row = path[pathPosition][0];
				column = path[pathPosition][1];
				currentValue = this.getGrid().getCell(row, column);
				newValue = currentValue+1;
				
				while(!grid.isValuePossible(row, column, newValue) || newValue > 9) {
					newValue++;
					if(newValue>9) {
						this.getGrid().setCell(row, column, 0);
						pathPosition-=1;
						row = path[pathPosition][0];
						column = path[pathPosition][1];
						newValue=this.getGrid().getCell(row, column)+1;
					}
				}
				this.getGrid().setCell(row, column, newValue);
				tryNewSol = true;
			}
			countSolutions++;
			tryNewSol = false;
		}
		return countSolutions;
	}
	
}
