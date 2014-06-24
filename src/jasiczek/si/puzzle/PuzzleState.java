package jasiczek.si.puzzle;
import java.lang.Math;
import klesk.math.search.StateImpl;
import java.util.Random;

public class PuzzleState extends StateImpl{
	private int n;
	private byte[][] board;
	private double manhattanHeuristic;
	public PuzzleState(int n) {
		super(null);
		rootMove = "";
		depth = 0;
		this.n = n;
		board = new byte[n+2][n+2]; //obudowujemy zerami
		for (int i = 0; i < n+2; i++) {
			for (int j = 0; j < n+2; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	public void randomString(String txt) {
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < n+1; j++) {
				if (i == 0 || j == 0 || i == n+1 || j == n+1) {
					board[i][j] = 0;
				}
				else {
					int k = (i-1) * n + (j-1);
					board[i][j] = Byte.valueOf(txt.substring(k, k+1));
				}
			}
		}
		Random generator = new Random(System.currentTimeMillis());
		int x = n;
		int y = n;
		int moves, direction;
		moves = generator.nextInt(50)+10;
		for (int i = 0; i < moves; i++) {
			direction = generator.nextInt(3);
			switch (direction) {
			case 0: {
				if (board[x][y-1] != 0) {
					board[x][y] = board[x][y-1];
					board[x][y-1] = 9;
					y = y - 1;
					break;
				}
			}
			case 1: {
				if (board[x+1][y] != 0) {
					board[x][y] = board[x+1][y];
					board[x+1][y] = 9;
					x = x + 1;
					break;
				}			
			}
			case 2: {
				if (board[x][y+1] != 0) {
					board[x][y] = board[x][y+1];
					board[x][y+1] = 9;
					y = y + 1;
					break;
				}
			}
			case 3: {
				if (board[x-1][y] != 0) {
					board[x][y] = board[x-1][y];
					board[x-1][y] = 9;
					x = x - 1;
					break;
				}
			}
			}
		}
		System.out.println(moves);
	}
	
	
	public PuzzleState(PuzzleState parent) {
		super(parent);
		this.n = parent.n;
		this.rootMove = parent.getRootMove();
		this.depth = parent.depth;
		board = new byte[n+2][n+2];
		for (int i = 0; i < n+2; i++) {
			for (int j = 0; j < n+2; j++) {
				board[i][j] = parent.board[i][j];
			}
		}
	}
	
	public PuzzleState() {
		super(null);
		String txt = "123456789";
		n = (int) Math.sqrt(txt.length());
		board = new byte[n+2][n+2];
		randomString(txt);
		depth = 0;
		rootMove = "";
		computeHeuristicGrade();
		//computeHeuristicManhattan();
	}
	
	@Override
	public double computeHeuristicGrade() {
		int misplacedPuzzles = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i+1][j+1] != ((j + 1) + i * n)) {
					misplacedPuzzles++;
				}
			}
		}
		setH(misplacedPuzzles);
		return misplacedPuzzles;
	}
	
	public double computeHeuristicManhattan() {
		double manhattan = 0;
		for (int i = 0; i < n ; i++ ) {
			for (int j = 0; j < n ; j++ ) {
				manhattan += Math.abs(i - ( (board[i+1][j+1] - 1) % n ) + Math.abs(j - Math.floor( (board[i+1][j+1] - 1) / n ) ) );
			}
		}
		manhattan = manhattan / 2;
		setManhattanHeuristic(manhattan);
		return manhattan;
	}
	
	@Override
	public String getHashCode() {
		return toString();
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public byte[][] getBoard() {
		return board;
	}
	public void setBoard(byte[][] board) {
		this.board = board;
	}
	public void setBoard(byte element, int i, int j) {
		this.board[i][j] = element;
	}
	public double getManhattanHeuristic() {
		return manhattanHeuristic;
	}
	public void setManhattanHeuristic(double manH) {
		manhattanHeuristic = manH;
	}
	
	@Override
	public String toString() {
		String out = "";
		for (int i = 1; i < n+1; i++) {
			for (int j = 1; j < n+1; j++) {
				out += board[i][j];
				if (j < n) {
					out += ",";
				}	
			}
			if(i < n) {
				out += "\n";
			}
		}
		return out;
	}
}