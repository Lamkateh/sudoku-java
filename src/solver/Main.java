package solver;

import sudoku.Grid;

public class Main {

	public static void main(String[] args) throws Exception {
		int[][] tab = {{6,4,0,0,3,0,0,0,7},{5,0,1,0,7,0,9,0,0},{0,0,0,0,0,0,0,1,0},{0,0,4,9,0,8,0,6,0},{0,8,0,0,0,3,0,2,0},{0,0,0,4,0,0,0,0,0},{4,0,0,1,5,7,0,3,0},{2,0,8,3,0,0,0,4,0},{7,5,0,0,0,0,0,9,6}};
		int[][] tab0 = {{0,0,0,0,3,0,0,0,7},{5,0,1,0,7,0,9,0,0},{0,0,0,0,0,0,0,1,0},{0,0,4,9,0,8,0,6,0},{0,8,0,0,0,3,0,2,0},{0,0,0,4,0,0,0,0,0},{4,0,0,1,5,7,0,3,0},{2,0,8,3,0,0,0,4,0},{7,5,0,0,0,0,0,9,6}};
		int[][] tab1 = {{9,0,0,0,0,0,0,5,0},{0,0,0,5,0,6,1,0,0},{0,0,0,1,0,0,0,0,0},{0,7,0,0,0,0,0,0,0},{0,0,0,0,9,0,4,0,0},{0,6,3,0,0,0,0,0,0},{5,0,0,0,2,0,0,0,0},{0,0,0,3,0,0,0,0,6},{0,1,0,0,0,0,0,0,7}};
		int[][] aux = {{1,0,3,8,6,0,5,7,4},{0,9,0,5,0,1,0,0,3},{0,0,0,0,0,0,1,8,0},{0,1,0,0,5,7,3,9,0},{0,7,0,2,0,6,0,1,0},{0,5,2,1,9,0,0,4,0},{0,6,4,0,0,0,0,0,0},{7,0,0,9,0,3,0,2,0},{9,3,1,0,2,4,7,0,8}};
		int[][] counterBruteForce = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,3,0,8,5},{0,0,1,0,2,0,0,0,0},{0,0,0,5,0,7,0,0,0},{0,0,4,0,0,0,1,0,0},{0,9,0,0,0,0,0,0,0},{5,0,0,0,0,0,0,7,3},{0,0,2,0,1,0,0,0,0},{0,0,0,0,4,0,0,0,9}};
		
		Grid g = new Grid(counterBruteForce);
		
		// naif
		
		Solver s0 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("naif");
		System.out.println(Solver.testExecutionTime(s0, "naiveSolve"));
		System.out.println("Grille correcte : " + s0.getGrid().isGridCorrect());
		
		//Test avec parcours sous forme d'arraylist
		//Parcours linéaire
		
		Solver s1 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("simple backtracking");
		System.out.println(Solver.testExecutionTime(s1, "simpleBacktracking"));
		System.out.println("Grille correcte : " + s1.getGrid().isGridCorrect());
		
		
		//Parcours trié par nombre de possibilité descendant
		
		Solver s2 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("Desc backtracking");
		//System.out.println(Solver.testExecutionTime(s2, "possibilitiesDescBacktracking"));
		System.out.println("Grille correcte : " + s2.getGrid().isGridCorrect());
		
		
		//Parcours trié par nombre de possibilité ascendant
		
		Solver s3 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("Asc backtracking");
		System.out.println(Solver.testExecutionTime(s3, "possibilitiesAscBacktracking"));
		System.out.println("Grille correcte : " + s3.getGrid().isGridCorrect());
		
		
		
		
		
		
		
		//Test avec parcours sous forme de tableau
		//Parcours trié par nombre de possibilité ascendant
		
		Solver s4 = new Solver(new Grid(g.duplicateGrid()));
				
		System.out.println("Simple backtracking, array path");
		System.out.println(Solver.testExecutionTime(s4, "simpleBacktrackingArray"));
		System.out.println("Grille correcte : " + s4.getGrid().isGridCorrect());
		
		
		//Parcours trié par nombre de possibilité descendant
		
		Solver s5 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("Desc backtracking, array path");
		//System.out.println(Solver.testExecutionTime(s5, "possibilitiesDescBacktrackingArray"));
		System.out.println("Grille correcte : " + s5.getGrid().isGridCorrect());
				
				
		//Parcours trié par nombre de possibilité ascendant
		
		Solver s6 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("Asc backtracking, array path");
		System.out.println(Solver.testExecutionTime(s6, "possibilitiesAscBacktrackingArray"));
		System.out.println("Grille correcte : " + s6.getGrid().isGridCorrect());
		
		
		
		
		
		
		//Naif + parcours trié par nombre de possibilité ascendant (tableau)
		Solver s7 = new Solver(new Grid(g.duplicateGrid()));
		
		System.out.println("solve");
		System.out.println(Solver.testExecutionTime(s7, "solve"));
		System.out.println("Grille correcte : " + s7.getGrid().isGridCorrect());
	}

}
