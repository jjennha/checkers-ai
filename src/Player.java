import java.util.LinkedList;
import java.util.List;

public class Player {
	int nType = Board.N2;
	int kType = Board.K2;
	int pType = Board.P2;
	int score;
	public Player(){
		
	}
	public Player(int n, int k, int p){
		nType = n;
		kType = k;
		pType = p;
		score = 0;
	}
	
	public List<Pair> findValidMovesAsPairs(Board b){
		List<Pair> result = new LinkedList<>();
		List<Move> moves = findValidMoves(b);
		for(Move m: moves){
			for(Pair p: m.to){
				result.add(p);
			}
		}
		return result;
	}
	public List<Pair> findValidMovesAsPairs(List<Move> moves){
		List<Pair> result = new LinkedList<>();
		for(Move m: moves){
			for(Pair p: m.to){
				result.add(p);
			}
		}
		return result;
	}
	public Move chooseMove(List<Move> moves, int index){
		int i = 0;
		for(Move m: moves){
			for(Pair p: m.to){
				if(i==index){
					m.chosen = p;
					return m;
				}
				i++;
			}
		}
		return null;
	}
	//Move generator
	public void printValidMoves(List<Move> moves){
		System.out.println();
		System.out.println(" Option        Move");
		System.out.println("----------------------------");
		int index = 0;
		for(Move m: moves){
			Pair from = m.from;
			for(Pair p: m.to){
				System.out.println("  ("+index+")    ["+from.x+", "+from.y+"] to ["+p.x+", "+p.y+"]");
				index++;
			}
		}
	}
	public List<Move> startFromTop(Board b){
		List<Move> moves = new LinkedList<>();
		List<Pair> current = getCurrentPos(b);
		int[][] board = b.getBoard();
		
		for(Pair p: current){
			int type = p.type;
			Move movesForP = new Move(p);
			if(type!=nType && type!=kType){
				continue;
			}
			if(isValid(p.x+1,p.y+1,b)){ // go down right
				movesForP.addMove(new Pair(p.x+1,p.y+1,board[p.x+1][p.y+1]));
			}
			if(isValid(p.x+1,p.y-1,b)){ // go down left
				movesForP.addMove(new Pair(p.x+1,p.y-1,board[p.x+1][p.y-1]));
			}
			if(isValid(p.x+2,p.y+2,b)&&(board[p.x+1][p.y+1]!=type && board[p.x+1][p.y+1]!=type+2 && board[p.x+1][p.y+1]!=type-2 && board[p.x+1][p.y+1]!=Board.EMPTY)){ // go down right skip
				Pair next = new Pair(p.x+2,p.y+2,board[p.x+2][p.y+2]);
				next.skips = new Pair(p.x+1,p.y+1,board[p.x+1][p.y+1]);
				movesForP.addMove(next);
				movesForP.hasSkip = true;
			}
			if(isValid(p.x+2,p.y-2,b)&&(board[p.x+1][p.y-1]!=type && board[p.x+1][p.y-1]!=type+2 && board[p.x+1][p.y-1]!=type-2 && board[p.x+1][p.y-1]!=Board.EMPTY)){ // go down left skip
				Pair next = new Pair(p.x+2,p.y-2,board[p.x+2][p.y-2]);
				next.skips = new Pair(p.x+1,p.y-1,board[p.x+1][p.y-1]);
				movesForP.addMove(next);
				movesForP.hasSkip = true;
			}
			if(type==kType){
				if(isValid(p.x-1,p.y+1,b)){ // go up right
					movesForP.addMove(new Pair(p.x-1,p.y+1,board[p.x-1][p.y+1]));
				}
				if(isValid(p.x-1,p.y-1,b)){ // go up left
					movesForP.addMove(new Pair(p.x-1,p.y-1,board[p.x-1][p.y-1]));
				}
				if(isValid(p.x-2,p.y+2,b)&&(board[p.x-1][p.y+1]!=type && board[p.x-1][p.y+1]!=type+2 && board[p.x-1][p.y+1]!=type-2 && board[p.x-1][p.y+1]!=Board.EMPTY)){ // go up right skip
					Pair next = new Pair(p.x-2,p.y+2,board[p.x-2][p.y+2]);
					next.skips = new Pair(p.x-1,p.y+1,board[p.x-1][p.y+1]);
					movesForP.addMove(next);
					movesForP.hasSkip = true;
				}
				if(isValid(p.x-2,p.y-2,b)&&(board[p.x-1][p.y-1]!=type && board[p.x-1][p.y-1]!=type+2 && board[p.x-1][p.y-1]!=type-2 && board[p.x-1][p.y-1]!=Board.EMPTY)){ // go up left skip
					Pair next = new Pair(p.x-2,p.y-2,board[p.x-2][p.y-2]);
					next.skips = new Pair(p.x-1,p.y-1,board[p.x-1][p.y-1]);
					movesForP.addMove(next);
					movesForP.hasSkip = true;
				}
			}
			moves.add(movesForP);
		}
		return moves;
	}
	public List<Move> startFromBottom(Board b){
		List<Move> moves = new LinkedList<>();
		List<Pair> current = getCurrentPos(b);

		int[][] board = b.getBoard();
		for(Pair p: current){
			int type = p.type;
			Move movesForP = new Move(p);
			if(type!=nType && type!=kType){
				continue;
			}
			if(isValid(p.x-1,p.y+1,b)){ // go up right
				movesForP.addMove(new Pair(p.x-1,p.y+1,board[p.x-1][p.y+1]));
			}
			if(isValid(p.x-1,p.y-1,b)){ // go up left
				movesForP.addMove(new Pair(p.x-1,p.y-1,board[p.x-1][p.y-1]));
			}
			if(isValid(p.x-2,p.y+2,b)&&(board[p.x-1][p.y+1]!=type && board[p.x-1][p.y+1]!=type+2 && board[p.x-1][p.y+1]!=type-2 && board[p.x-1][p.y+1]!=Board.EMPTY)){ // go up right skip
				Pair next = new Pair(p.x-2,p.y+2,board[p.x-2][p.y+2]);
				next.skips = new Pair(p.x-1,p.y+1,board[p.x-1][p.y+1]);
				movesForP.addMove(next);
				movesForP.hasSkip = true;
			}
			if(isValid(p.x-2,p.y-2,b)&&(board[p.x-1][p.y-1]!=type && board[p.x-1][p.y-1]!=type+2 && board[p.x-1][p.y-1]!=type-2 && board[p.x-1][p.y-1]!=Board.EMPTY)){ // go up left skip
				Pair next = new Pair(p.x-2,p.y-2,board[p.x-2][p.y-2]);
				next.skips = new Pair(p.x-1,p.y-1,board[p.x-1][p.y-1]);
				movesForP.addMove(next);
				movesForP.hasSkip = true;
			}
			if(type==kType){
				if(isValid(p.x+1,p.y+1,b)){ // go down right
					movesForP.addMove(new Pair(p.x+1,p.y+1,board[p.x+1][p.y+1]));
				}
				if(isValid(p.x+1,p.y-1,b)){ // go down left
					movesForP.addMove(new Pair(p.x+1,p.y-1,board[p.x+1][p.y-1]));
				}
				if(isValid(p.x+2,p.y+2,b)&&(board[p.x+1][p.y+1]!=type && board[p.x+1][p.y+1]!=type+2 && board[p.x+1][p.y+1]!=type-2 && board[p.x+1][p.y+1]!=Board.EMPTY)){ // go down right skip
					Pair next = new Pair(p.x+2,p.y+2,board[p.x+2][p.y+2]);
					next.skips = new Pair(p.x+1,p.y+1,board[p.x+1][p.y+1]);
					movesForP.addMove(next);
					movesForP.hasSkip = true;
				}
				if(isValid(p.x+2,p.y-2,b)&&(board[p.x+1][p.y-1]!=type && board[p.x+1][p.y-1]!=type+2 && board[p.x+1][p.y-1]!=type-2 && board[p.x+1][p.y-1]!=Board.EMPTY)){ // go down left skip
					Pair next = new Pair(p.x+2,p.y-2,board[p.x+2][p.y-2]);
					next.skips = new Pair(p.x+1,p.y-1,board[p.x+1][p.y-1]);
					movesForP.addMove(next);
					movesForP.hasSkip = true;
				}
			}
			moves.add(movesForP);
		}
		return moves;
	}
	public List<Move> findValidMoves(Board b){
		List<Move> moves = new LinkedList<>();
		List<Move> containSkips = new LinkedList<>();
		if(pType == Board.P1){
			moves = startFromTop(b);
		}else{
			moves = startFromBottom(b);
		}
		for(Move m: moves){
			if(m.hasSkip){
				List<Pair> copy = new LinkedList<>();
				for(Pair sk: m.to){
					if(sk.skips!=null){
						copy.add(sk);
					}
				}
				m.to = copy;
				containSkips.add(m);
			}
		}
		if(containSkips.size()>0){
			return containSkips;
		}else{
			return moves;
		}
	}
	private List<Pair> getCurrentPos(Board c){
		int[][] board = c.getBoard();
		List<Pair> current = new LinkedList<>();
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board.length;j++){
				if(pType==Board.P1&&(board[i][j]==Board.N1 || board[i][j]==Board.K1)){
					current.add(new Pair(i,j,board[i][j]));
				}else if(pType==Board.P2&&(board[i][j]==Board.N2 || board[i][j]==Board.K2)){
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
}
