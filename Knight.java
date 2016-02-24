package pieces;

import java.awt.Point;

public class Knight extends Piece
{
	public Knight(Point a, boolean b)
	{
		super(b);
		this.pos = a;
	}
	public void setpos(int i, int j)
	{
		this.pos.x=i;
		this.pos.y=j;
	}

	public int move(Point OP, int positions[][])
	{
		boolean isPieceWhite = (positions[this.pos.x][this.pos.y]==9) ;
		boolean isPieceBlack = (positions[this.pos.x][this.pos.y]==3) ;
		boolean isOutputWhite = (positions[OP.x][OP.y] > 6);
		boolean isOutputBlack = (positions[OP.x][OP.y] <= 6 && positions[OP.x][OP.y] > 0);
		boolean isOutputEmpty = (positions[OP.x][OP.y] == 0);
		//code to see if move is possible or not from this.x, this.y to OP.x,OP.y
		//returns 1 if move possible else returns 0
		//checks for all possible basic knight move
            if(Math.abs(OP.x-this.pos.x)==2 && Math.abs(OP.y-this.pos.y)==1)
            {
              	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) || (isPieceBlack && (isOutputWhite || isOutputEmpty)))
               		return 1;
               	else
               		return 0;
            }
            else if(Math.abs(OP.y-this.pos.y)==2 && Math.abs(OP.x-this.pos.x)==1)
            {
               	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) ||( isPieceBlack && (isOutputWhite || isOutputEmpty)))
               		return 1;
               	else
               		return 0;
            }
            return 0;
	}
}