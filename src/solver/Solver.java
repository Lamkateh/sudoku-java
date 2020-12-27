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
	 * Permet d'obtenir le temps d'ex�cution d'une m�thode d'instance
	 * 
	 * @param obj Instance
	 * @param funcName Nom de la m�thode
	 * @return long Temps d'ex�cution en millisecondes
	 * @throws Exception
	 */
	public static long testExecutionTime(Object obj, String funcName) throws Exception {
		long t0 = System.currentTimeMillis();
		obj.getClass().getDeclaredMethod(funcName).invoke(obj);
		return System.currentTimeMillis() - t0;
	}
	
	/**
	 * Permet d'obtenir un entier al�atoire entre deux bornes
	 * 
	 * @param min Borne inf�rieure
	 * @param max Borne sup�rieure
	 * @return int Nombre al�atoire
	 */
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	/**
	 * Permet de remplir les cases o� une seule valeur est possible
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
	 * Permet de remplir les cases �tant la seule possibilit� d'une valeur pour une m�me ligne
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
	 * Permet de remplir les cases �tant la seule possibilit� d'une valeur pour une m�me colonne
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
	 * Permet de remplir les cases �tant la seule possibilit� d'une valeur pour un m�me carr�
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
			//parcours des carr�
			for (int r0 = 0; r0 < grid.getGrid().length; r0+=3) {
				for (int c0 = 0; c0 < grid.getGrid().length; c0+=3) {
					for (int value = 1; value < 10; value++) {
						//parcours du carr�
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
	 * Permet de r�soudre une grille de sudoku de mani�re "naive", tel qu'un humain le ferait g�n�ralement
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
	 * @return int Nombre de cases � remplir
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
	 * Renvoie un parcours des cases � remplir dans la grille.
	 * Ce parcours est lin�aire en incr�mentant les lignes puis les colonnes.
	 * 
	 * @return ArrayList Parcours des cases � remplir
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
	 * Renvoie un parcours des cases � remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilit�s par cases de fa�on croissante
	 * 
	 * @return ArrayList Parcours des cases � remplir
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
	 * Renvoie un parcours des cases � remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilit�s par cases de fa�on d�croissante
	 * 
	 * @return ArrayList Parcours des cases � remplir
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
	 * Renvoie un parcours des cases � remplir dans la grille.
	 * Ce parcours est lin�aire en incr�mentant les lignes puis les colonnes.
	 * 
	 * @return int[][] Parcours des cases � remplir
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
	 * Renvoie un parcours des cases � remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilit�s par cases de fa�on croissante
	 * 
	 * @return int[][] Parcours des cases � remplir
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
	 * Renvoie un parcours des cases � remplir dans la grille.
	 * Ce parcours se fait en fonction du nombre de possibilit�s par cases de fa�on d�croissante
	 * 
	 * @return int[][] Parcours des cases � remplir
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
	 * R�soud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilit�s de fa�on croissante sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean possibilitiesAscBacktrackingArray() {
		return backtracking(getArrayPathByPossibilitiesAsc());
	}
	
	/**
	 * R�soud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilit�s de fa�on d�croissante sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean possibilitiesDescBacktrackingArray() {
		return backtracking(getArrayPathByPossibilitiesDesc());
	}
	
	/**
	 * R�soud une grille de sudoku en utilisant le backtracking avec un parcours lin�aire sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean simpleBacktrackingArray() {
		return backtracking(getSimpleArrayPath());
	}
	
	/**
	 * R�soud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilit�s de fa�on croissante sous forme d'une ArrayList
	 * 
	 * @return
	 */
	public boolean possibilitiesAscBacktracking() {
		return backtracking(getPathByPossibilitiesAsc());
	}
	
	/**
	 * R�soud une grille de sudoku en utilisant le backtracking avec un parcours en fonction du nombre de possibilit�s de fa�on d�croissante sous forme d'une Arraylist
	 * 
	 * @return
	 */
	public boolean possibilitiesDescBacktracking() {
		return backtracking(getPathByPossibilitiesDesc());
	}
	
	/**
	 * R�soud une grille de sudoku en utilisant le backtracking avec un parcours lin�aire sous forme d'une ArrayList
	 * 
	 * @return
	 */
	public boolean simpleBacktracking() {
		return backtracking(getSimplePath());
	}
	
	/**
	 * Permet de r�soudre une grille de sudoku en utilisant la force brute.
	 * 
	 * @param path Parcours des cases � remplir
	 * @return boolean Validit� de la grille
	 */
	private boolean backtracking(int[][] path) {
		return backtracking(path, 0);
	}
	
	/**
	 * Permet de r�soudre une grille de sudoku en combinant la r�solution naive puis un backtracking utilisant un parcours en fonction du nombre de possibilit�s de fa�on croissante sous forme d'un tableau
	 * 
	 * @return
	 */
	public boolean solve() {
		this.naiveSolve();
		return this.possibilitiesAscBacktrackingArray();
	}
	
	/**
	 * Permet de r�soudre une grille de sudoku en utilisant la force brute.
	 * 
	 * @param path Parcours des cases � remplir
	 * @return boolean Validit� de la grille
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
	 * Permet de r�soudre une grille de sudoku en utilisant la force brute.
	 * 
	 * @param path Parcours des cases � remplir
	 * @param pathPosition Position de d�but du parcours
	 * @return boolean Validit� de la grille
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
