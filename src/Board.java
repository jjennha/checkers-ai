
public class Board {
	int[][] board;
	int sizeX = 8;
	int sizeY = 8;
	static final int 
		EMPTY = 0,
		N1 = 2,
		N2 = 3,
		K1 = 4,
		K2 = 5,
		P1 = 1,
		P2 = 2;
	
	public Board(int[][] state){
		this.board = state;
	}
	public int[][] getBoard(){
		return board;
	}
	public Board(){
		this.board = new int[8][8];
		boolean alt = true;
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[0].length;j++){
				if(alt){
					this.board[i][j] = EMPTY;
				}else{
					this.board[i][j] = EMPTY;
				}
				alt = !alt;
			}
			alt = !alt;
		}
		alt = true;
		for(int i=0;i<3;i++){
			for(int j=0;j<board[0].length;j++){
				if(alt){
					this.board[i][j] = N1;
				}
				alt = !alt;
			}
			alt = !alt;
		}
		alt = false;
		for(int i=board.length-1;i>=board.length-3;i--){
			for(int j=0;j<board[0].length;j++){
				if(alt){
					this.board[i][j] = N2;
				}
				alt = !alt;
			}
			alt = !alt;
		}
	}
	
	public void print(){
		System.out.println();
		System.out.print("  ");
		for(int i=0;i<8;i++){
			System.out.print(" "+i);
		}
		System.out.println();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				if(j==0){
					System.out.print(i+" ");
				}
				if(board[i][j]==N1){
					System.out.print("|x"+"");
				}else if(board[i][j]==K1){
					System.out.print("|X"+"");
				}else if(board[i][j]==N2){
					System.out.print("|"+"y"+"");
				}else if(board[i][j]==K2){
					System.out.print("|Y"+"");
				}else{
					System.out.print("|"+".");
				}
			}
			System.out.println("|");
		}
	}
}

