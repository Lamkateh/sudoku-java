package sudoku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import solver.Solver;

public class Grid {
	private int[][] grid = new int[9][9];
	// grid[row][column]
	
	public Grid() {
		this.grid = convertLineToGrid(getLineFromFile("sudokuGridList", Solver.getRandomNumber(1, countLineInFile("sudokuGridList"))));
	}
	
	public Grid(int[][] tab) {
		this.grid = tab;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for (int r = 0; r < grid.length; r++) {
			str += '\n';
			if(r%3==0 && r!=0) str += "------+-------+------\n";
			for (int c = 0; c < grid.length; c++) {
				if(c%3==0 && c!=0) str += "| ";
				str += this.grid[r][c] + " ";
			}
		}
		return str;
	}
	
	public int getCell(int row, int column) {
		return this.grid[row][column];
	}
	
	public void setCell(int row,int column, int value) {
		this.grid[row][column] = value;
	}
	
	/**
	 * Permet de verifier si une ligne est remplie
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return boolean Remplissage de la ligne
	 */
	public boolean isRowFull(int row) {
		for (int c = 0; c < grid.length; c++) {
			if(this.grid[row][c] == 0) return false;
		}
		return true;
	}
	
	/**
	 * Permet de verifier si une colonne est remplie
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return boolean Remplissage de la colonne
	 */
	public boolean isColumnFull(int column) {
		for (int r = 0; r < grid.length; r++) {
			if(this.grid[r][column]==0) return false;
		}
		return true;
	}
	
	/**
	 * Permet de verifier si un carr� est rempli
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return boolean Remplissage du carr�
	 */
	public boolean isSquareFull(int row, int column) {
		int c0 = column - column%3;
		int r0 = row - row%3;
		for (int r = r0; r < 3; r++) {
			for (int c = c0; c < 3; c++) {
				if(this.grid[r][c] == 0) return false;
			}
		}
		return true;
	}
	
	/**
	 * Permet de v�rifier si la ligne associ�e d'une case est correcte
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return boolean Validit� de la ligne
	 */
	public boolean isRowCorrect(int row) {
		if (!isRowFull(row)) return false;
		int[] possibleValues = {1,2,3,4,5,6,7,8,9};
		for (int c = 0; c < grid.length; c++) {
			if(possibleValues[this.getCell(row, c) -1] == 0) {
				return false;
			}
			possibleValues[this.getCell(row, c) -1] = 0;
		}
		return true;
	}
	
	/**
	 * Permet de v�rifier si la colonne associ�e d'une case est correcte
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return boolean Validit� de la colonne
	 */
	public boolean isColumnCorrect(int column) {
		if(!isColumnFull(column)) return false;
		int[] possibleValues = {1,2,3,4,5,6,7,8,9};
		for (int r = 0; r < grid.length; r++) {
			if(possibleValues[this.getCell(r, column) -1] == 0) {
				return false;
			}
			possibleValues[this.getCell(r, column) -1] = 0;
		}
		return true;
	}
	
	/**
	 * Permet de v�rifier si le carr� associ� d'une case est correct
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return boolean Validit� du carr�
	 */
	public boolean isSquareCorrect(int row, int column) {
		if(!isSquareFull(row, column)) return false;
		int[] possibleValues = {1,2,3,4,5,6,7,8,9};
		int c0 = column - column%3;
		int r0 = row - row%3;
		for (int r = r0; r < r0+3; r++) {
			for (int c = c0; c < c0+3; c++) {
				if(possibleValues[this.getCell(r, c) -1] == 0) {
					return false;
				}
				possibleValues[this.getCell(r, c) -1] = 0;
			}
		}
		return true;
	}
	
	/**
	 * V�rifie que la grille donn� est correcte
	 * 
	 * @return boolean Validit� de la grille
	 */
	public boolean isGridCorrect() {
		for (int i = 0; i < grid.length; i++) {
			if(!isRowCorrect(i)) return false;
			if(!isColumnCorrect(i)) return false;
		}
		for (int r = 0; r < grid.length; r+=3) {
			for (int c = 0; c < grid.length; c+=3) {
				if(!isSquareCorrect(r, c)) return false;
			}
		}
		return true;
	}
	
	/**
	 * Recherche une valeur dans une ligne
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param value La valeur que l'on cherche
	 * @return boolean Pr�sence de la valeur dans la ligne
	 */
	public boolean rowHasValue(int row, int value) {
		for (int c = 0; c < grid.length; c++) {
			if(this.getCell(row, c) == value) return true; 
		}
		return false;
	}
	
	/**
	 * Recherche une valeur dans une colonne
	 * 
	 * @param column Colonne de la case cibl�e
	 * @param value La valeur que l'on cherche
	 * @return boolean Pr�sence de la valeur dans la colonne
	 */
	public boolean columnHasValue(int column, int value) {
		for (int r = 0; r < grid.length; r++) {
			if(this.getCell(r, column) == value) return true;
		}
		return false;
	}
	
	/**
	 * Recherche une valeur dans un carr�
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @param value La valeur que l'on cherche
	 * @return boolean Pr�sence de la valeur dans le carr�
	 */
	public boolean squareHasValue(int row, int column, int value) {
		int c0 = column - column%3;
		int r0 = row - row%3;
		for (int r = r0; r < r0 + 3; r++) {
			for (int c = c0; c < c0 + 3; c++) {
				if(this.getCell(r, c) == value) return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @param value La valeur que l'on cherche
	 * @return boolean Possibilit� de placer la valeur � la case indiqu�e
	 */
	public boolean isValuePossible(int row, int column, int value) {
		return !columnHasValue(column, value) && !rowHasValue(row, value) && !squareHasValue(row, column, value);
	}
	
	/**
	 * Permet d'obtenir le nombre de valeurs possibles pour une case donn�e
	 * 
	 * @param row Ligne de la case cibl�e
	 * @param column Colonne de la case cibl�e
	 * @return int Nombre de valeurs possibles 
	 */
	public int countPossibleValues(int row, int column) {
		int counter = 0;
		for (int value = 1; value < 10; value++) {
			if(isValuePossible(row, column, value)) {
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Permet de r�cuperer la ligne du numero demand� d'un fichier sp�cifi�
	 * 
	 * @param filename Chemin du fichier 
	 * @param lineNumber Numero de la ligne voulue
	 * @return
	 */
	private static String getLineFromFile(String filename, int lineNumber) {
		int lineCounter = 1;
		try {
			File gridListFile = new File(filename);
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(gridListFile);
			while(reader.hasNextLine()) {
				if(lineCounter == lineNumber) return reader.nextLine();
				lineCounter++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Permet de compter le nombre de ligne dans un fichier
	 * 
	 * @param filename Chemin du fichier 
	 * @return int Nombre de lignes contenues dans le fichier
	 */
	private static int countLineInFile(String filename) {
		int lineCounter = 0;
		try {
			File gridListFile = new File(filename);
			Scanner reader = new Scanner(gridListFile);
			while(reader.hasNextLine()) {
				reader.nextLine();
				lineCounter++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lineCounter;
	}
	
	/**
	 * Permet de convertir une chaine de caract�res sous forme de tableau repr�sentant une grille de sudoku
	 * 
	 * @param line Chaine de carat�res repr�sentant une grille
	 * @return int[][] Tableau repr�sentant la grille de sudoku
	 */
	private static int[][] convertLineToGrid(String line) {
		int[][] grid = new int[9][9];
		String[] arrLine = line.split(",");
		int indexArrLine = 0;
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid.length; column++) {
				grid[row][column] = Integer.parseInt(arrLine[indexArrLine]);
				indexArrLine++;
			}
		}
		return grid;
	}
	
	/**
	 * Permet de convertir une grille sous forme de chaine de caract�res
	 * 
	 * @param grid Grille de sudoku
	 * @return String La grille sous forme d'une chaine de caract�res
	 */
	private static String convertGridToLine(int[][] grid) {
		String line = "";
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid.length; column++) {
				line += grid[row][column] + ",";
			}
		}
		return line;
	}
	
	/**
	 *  Permet de dupliquer exactement une grille
	 * 
	 * @return int[][] Grille identique � celle donn�e
	 */
	public int[][] duplicateGrid() {
		int[][] newGrid = new int[9][9];
		for (int row = 0; row < newGrid.length; row++) {
			for (int column = 0; column < newGrid.length; column++) {
				newGrid[row][column] = this.getCell(row, column);
			}
		}
		return newGrid;
	}
	
	/**
	 * Permet de supprimer des cellules dans la grille tout en la gardant soluble par un humain avec une unique solution.
	 * Les cellules sont supprim�es de fa�on sym�trique par rapport au centre. Elles sont donc supprim�es par paire, sauf pour la case centrale.
	 * 
	 * @param cellsNumber Le nombre divis� par deux de cases � supprimer dans la grille
	 */
	public void removeCells(int cellsNumber) {
		int column, row, cellNumber, symetricCellNumber = 0;

		for (int i = 0; i < cellsNumber; i++) {
			column = Solver.getRandomNumber(0, 8);
			row = Solver.getRandomNumber(0, 8);
			if(this.grid[row][column] != 0) {
				cellNumber = this.grid[row][column];
				symetricCellNumber = this.grid[8-row][8-column];
				this.grid[row][column] = this.grid[8-row][8-column] = 0;
				Solver s = new Solver(new Grid(this.duplicateGrid()));
				s.naiveSolve();
				if(!s.getGrid().isGridCorrect() && s.countSolutions() == 1) { // v�rification que le sudoku est toujours faisable et que le sudoku ne poss�de qu'une seule solution
					this.grid[row][column] = cellNumber;
					this.grid[8-row][8-column] = symetricCellNumber;
					i--;
				}
			}
		}
	}
	
	/**
	 * Permet de d�terminer si une grille est soluble
	 * 
	 * @return boolean R�solvabilit� de la grille donn�e
	 */
	public boolean isSolveable() {
		Solver s = new Solver(new Grid(this.duplicateGrid()));
		if(s.possibilitiesAscBacktrackingArray()) return true;
		return false;
	}
	
	/**
	 * Permet de g�n�rer al�atoirement une grille remplie et de l'ins�rer dans le fichier sp�cifi�
	 * 
	 * @param filename Chemin du fichier � remplir
	 */
	public void generateFullGrid(String filename) {
		this.grid = new int[9][9];
		int row = 0, column = 0, value = 0;
		while(this.isSolveable()) {
			while(!this.isValuePossible(row, column, value)) {
				row = Solver.getRandomNumber(0, 8);
				column = Solver.getRandomNumber(0, 8);
				value = Solver.getRandomNumber(1, 9);
			}
			this.setCell(row, column, value);
		}
		this.setCell(row, column, 0);
		Solver s = new Solver(this);
		s.possibilitiesAscBacktrackingArray();
		String line = Grid.convertGridToLine(this.getGrid());
		try {
			Writer output = new BufferedWriter(new FileWriter(filename, true));
			output.append(line + "\n");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Grille g�n�r�e");
		return;
	}
}
