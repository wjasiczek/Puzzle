package jasiczek.si.puzzle;
import java.util.ArrayList;
import java.util.List;

import klesk.math.search.AStarSearcher;
import klesk.math.search.State;

public class PuzzleSolver extends AStarSearcher {

	public PuzzleSolver(State aInitialState, boolean aIsStopAfterFirstSolution, boolean aIsStopAfterSecondSolution) {
		super(aInitialState, aIsStopAfterFirstSolution, aIsStopAfterSecondSolution);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public boolean isSolution(State aState) {
		return (aState.getH() == 0);
	}
	

	@Override
	public void buildChildren(State aParent) {
		List<State> children = new ArrayList<State>();
		PuzzleState sParent = (PuzzleState)aParent;
		int i = 0;
		int j = 0;
		int n2 = (sParent.getN() + 2) * (sParent.getN() + 2); //rozmiar tablicy + 2 miejsca na zera
		for (int k = 0; k < n2; k++) {
			i = k/(sParent.getN()+2);
			j = k%(sParent.getN()+2);
			if (sParent.getBoard()[i][j] == 9) {				
				break;
			}
		}
		
		byte bufor;
		
		if (sParent.getBoard()[i-1][j] != 0) {
			PuzzleState child = new PuzzleState(sParent);
			bufor = child.getBoard()[i-1][j];
			child.setBoard(bufor, i, j);
			child.setBoard((byte)9, i-1, j);
			child.computeHeuristicGrade();
			//child.computeHeuristicManhattan();
			child.setDepth(child.getDepth()+1);
			child.setRootMove(child.getRootMove()+bufor);
			children.add(child);
		}
		
		if (sParent.getBoard()[i][j+1] != 0) {
			PuzzleState child = new PuzzleState(sParent);
			bufor = child.getBoard()[i][j+1];
			child.setBoard(bufor, i, j);
			child.setBoard((byte)9, i, j+1);
			child.computeHeuristicGrade();
			//child.computeHeuristicManhattan();
			child.setDepth(child.getDepth()+1);
			child.setRootMove(child.getRootMove()+bufor);
			children.add(child);
		}
		
		if (sParent.getBoard()[i+1][j] != 0) {
			PuzzleState child = new PuzzleState(sParent);
			bufor = child.getBoard()[i+1][j];
			child.setBoard(bufor, i, j);
			child.setBoard((byte)9, i+1, j);
			child.computeHeuristicGrade();
			//child.computeHeuristicManhattan();
			child.setDepth(child.getDepth()+1);
			child.setRootMove(child.getRootMove()+bufor);
			children.add(child);
		}
		
		if (sParent.getBoard()[i][j-1] != 0) {
			PuzzleState child = new PuzzleState(sParent);
			bufor = child.getBoard()[i][j-1];
			child.setBoard(bufor, i, j);
			child.setBoard((byte)9, i, j-1);
			child.computeHeuristicGrade();
			//child.computeHeuristicManhattan();
			child.setDepth(child.getDepth()+1);
			child.setRootMove(child.getRootMove()+bufor);
			children.add(child);
		}
		
		sParent.setChildren(children);
		System.out.print("+");
	}
	
	public void printSolution(PuzzleState result) {
		System.out.println(result);
		int wiersz9 = result.getN();
		int kolumna9 = result.getN();
		for (int i = (result.getRootMove().length() - 1); i >= 0; i--) {
			byte element = Byte.valueOf(result.getRootMove().substring(i, i+1));
			int wiersz = 0;
			int kolumna = 0;
			int n2 = (result.getN() + 2) * (result.getN() + 2); //rozmiar tablicy + 2 miejsca na zera
			for (int k = 0; k < n2; k++) {
				wiersz = k/(result.getN()+2);
				kolumna = k%(result.getN()+2);
				if (result.getBoard()[wiersz][kolumna] == element) {				
					break;
				}
			}
			result.setBoard(element, wiersz9, kolumna9);
			result.setBoard((byte)9, wiersz, kolumna);
			wiersz9 = wiersz;
			kolumna9 = kolumna;
			System.out.println("\n" + result + "\n");
		}
	}
	
	
	public static void main(String[] args) {
		PuzzleState puzzle = new PuzzleState();
		PuzzleSolver solver = new PuzzleSolver(puzzle, true, true);//stop po pierwszym, stop po drugim
		
		System.out.println("Heurystyka puzzli nie na swoim miejscu");
		System.out.println(puzzle);
		System.out.println(puzzle.computeHeuristicGrade());
		
		long t1 = System.currentTimeMillis();
		solver.doSearch();
		long t2 = System.currentTimeMillis();
		System.out.println("\nTime: " + (t2 - t1) + "\n");
		
		solver.printSolution((PuzzleState)solver.getSolutions().get(0));
		System.out.println("Odwiedzonych: " + solver.getClosed().size());
		System.out.println("Liczba wykonanych krokow: " + solver.getSolutions().get(0).getRootMove().length());
		
		/*System.out.println("Heurystyka odleglosci puzzli od swojego miejsca");
		System.out.println(puzzle.computeHeuristicManhattan());
		
		t1 = System.currentTimeMillis();
		solver.doSearchManhattan();
		t2 = System.currentTimeMillis();
		System.out.println("Time: " + (t2 - t1) + "\n");
		
		solver.printSolution((PuzzleState)solver.getSolutions().get(0));
		
		System.out.println("visited: " + solver.getClosed().size());*/
		
	}

}
