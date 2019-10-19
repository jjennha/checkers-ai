import java.util.*;

class Pair {
	int x;
	int y;
	int type;
	Pair skips;
	public Pair(int x, int y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
	}
	public Pair(){}
}
class Move { //Potential Moves
	Pair from;
	Pair chosen;
	List<Pair> to;
	
	int numMoves;
	public Move(Pair f){
		from = f;
		to = new LinkedList<>();
	}
	public void addMove(Pair p){
		numMoves++;
		to.add(p);
	}
	public void setChosen(Pair c){
		chosen = c;
	}
}

class ClassAgent extends Player{
	static int nType = Board.N1;
	static int kType = Board.K1;
	static int pType = Board.P1;
	public ClassAgent(){
		super(nType,kType,pType);
	}
	
	public void play(Board b){
		nextMove(b);
	}
	public Move nextMove(Board current){
		return chooseMove(findValidMoves(current));
	}
	public Move chooseMove(List<Move> moves){
		System.out.println(moves.size());
		for(Move m: moves){
			if(m.to.size()>0){
				m.chosen = m.to.get(0);
				return m;
			}
		}
		return null;
	}
	
	public int evaluate(Board board){
		return 0;
	}
}

public class Game {
	ClassAgent ai;
	Player player;
	Board board;
	int current;
	Scanner in;
	public Game(Player p){
		this.ai = new ClassAgent();
		this.player = p;
		this.current = Board.P2;
		this.board = new Board();
		in = new Scanner(System.in);
	}
	public void play(){
		board.print();
		Move nextMove = null;
		if(current == Board.P1){
			List<Move> validMoves = ai.findValidMoves(board);
			ai.printValidMoves(validMoves);
			nextMove = ai.chooseMove(validMoves);
		}else{
			List<Move> validMoves = player.findValidMoves(board);
			player.printValidMoves(validMoves);
			System.out.print("\nEnter: ");
			int num;
			num = in.nextInt();
			nextMove = player.chooseMove(validMoves, num);
		}
		if(nextMove!=null){
			current = updateBoard(nextMove,current);
		}
		
	}
	public int updateBoard(Move move, int current){
		int nextPlayer = current;
		if(current==Board.P2){
			nextPlayer = Board.P1;
		}else{
			nextPlayer = Board.P2;
		}
		int[][] bArr = board.getBoard();
		if(move.chosen.skips!=null){
			Pair skip = move.chosen.skips;
			bArr[skip.x][skip.y] = Board.EMPTY;
			nextPlayer = current;
		}
		
		Pair from = move.from;
		Pair to = move.chosen;
		
		if(current==Board.P1&&to.x==7){
			from.type = Board.K1;
		}
		if(current==Board.P2&&to.x==0){
			from.type = Board.K2;
		}
		bArr[from.x][from.y] = to.type;
		bArr[to.x][to.y] = from.type;
		return nextPlayer;
	}
	public int getWinner(){
		int[][] bArr = board.getBoard();
		boolean contains1 = false;
		boolean contains2 = false;
		for(int i=0;i<board.sizeX;i++){
			for(int j=0;j<board.sizeY;j++){
				if(bArr[i][j]==Board.N1||bArr[i][j]==Board.K1){
					contains1 = true;
				}
				if(bArr[i][j]==Board.N2||bArr[i][j]==Board.K2){
					contains2 = true;
				}
				if(contains1&&contains2){
					return 0; // no one wins
				}
			}
		}
		if(ai.findValidMoves(board).isEmpty()){
			return 2; // player wins
		}
		if(contains1){
			return 1; // ai wins
		}
		return 2;
	}
	public static void main(String[] args) {
		Player user = new Player();
		Game game = new Game(user);
		
		
		while(game.getWinner()==0){
			game.play();
		}
	}
}
/*
 * 
 * 	public List<Pair> findValidMovesAsPairs(Board b){
		List<Pair> result = new LinkedList<>();
		List<Move> moves = findValidMoves(b);
		for(Move m: moves){
			for(Pair p: m.to){
				result.add(p);
			}
		}
		return result;
	}
	public List<Move> findValidMoves(Board b){
		List<Move> moves = new LinkedList<>();
		List<Pair> current = getCurrentPos(b);
		int[][] board = b.getBoard();
		for(Pair p: current){
			int type = p.type;
			Move movesForP = new Move(p);
			if(type!=Board.N1 || type!=Board.K1){
				continue;
			}
			if(isValid(p.x+1,p.y+1,b)){ // go down right
				movesForP.addMove(new Pair(p.x+1,p.y+1,type));
			}
			if(isValid(p.x-1,p.y+1,b)){ // go down left
				movesForP.addMove(new Pair(p.x-1,p.y+1,type));
			}
			if(isValid(p.x+2,p.y+2,b)&&(board[p.x+1][p.y+1]==nType||board[p.x+1][p.y+1]==kType)){ // go down right skip
				movesForP.addMove(new Pair(p.x+2,p.y+2,type));
			}
			if(isValid(p.x-2,p.y+2,b)&&(board[p.x-1][p.y+1]==nType||board[p.x-1][p.y+1]==kType)){ // go down left skip
				movesForP.addMove(new Pair(p.x-2,p.y+2,type));
			}
			if(type==Board.K1){
				if(isValid(p.x+1,p.y-1,b)){ // go up right
					movesForP.addMove(new Pair(p.x+1,p.y-1,type));
				}
				if(isValid(p.x-1,p.y-1,b)){ // go up left
					movesForP.addMove(new Pair(p.x-1,p.y-1,type));
				}
				if(isValid(p.x+2,p.y-2,b)&&(board[p.x+1][p.y-1]==nType||board[p.x+1][p.y-1]==kType)){ // go up right skip
					movesForP.addMove(new Pair(p.x+2,p.y-2,type));
				}
				if(isValid(p.x-2,p.y-2,b)&&(board[p.x-1][p.y-1]==nType||board[p.x-1][p.y-1]==kType)){ // go up left skip
					movesForP.addMove(new Pair(p.x-2,p.y-2,type));
				}
			}
			moves.add(movesForP);
		}
		return moves;
	}
	private List<Pair> getCurrentPos(Board c){
		int[][] board = c.getBoard();
		List<Pair> current = new LinkedList<>();
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board.length;j++){
				if(board[i][j]==Board.N1 || board[i][j]==Board.K1){
					current.add(new Pair(i,j,board[i][j]));
				}
			}
		}
		return current;
	}
	private boolean isValid(int x, int y,Board b){
		if(x<0||y<0||x>=b.sizeX||y>=b.sizeY||b.getBoard()[x][y]!=Board.EMPTY){
			return false;
		}
		return true;
	}
 * 
 * 
 * */
