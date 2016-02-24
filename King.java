package pieces;

import java.awt.Point;


public class King extends Piece{
	public King(Point a, boolean b){
		super(b);
		this.pos = a;
	}
	public void setpos(int i, int j){
		this.pos.x=i;
		this.pos.y=j;
	}
	/*
	 * Checks if the move made by the piece is valid
	 * It checks that the output is empty or has a piece of other kind
	 */
	public int move(Point OP, int positions[][]){
	
		int flag=0;
		
		boolean isWhiteKing = (positions[this.pos.x][this.pos.y]==12) ;
		boolean isBlackKing = (positions[this.pos.x][this.pos.y]==6) ;
		boolean isOutputWhite = (positions[OP.x][OP.y] > 6);
		boolean isOutputBlack = (positions[OP.x][OP.y] <= 6 && positions[OP.x][OP.y] > 0);
		boolean isOutputEmpty = (positions[OP.x][OP.y] == 0);
		
		if(Math.abs(OP.x-this.pos.x) <= 1 && Math.abs(OP.y-this.pos.y) <= 1){
			if(isWhiteKing && (isOutputBlack || isOutputEmpty)){
				flag = 1;
			}
			else if(isBlackKing && (isOutputWhite || isOutputEmpty)){
				flag = 1;
			}
		}
		return flag;
	}
}