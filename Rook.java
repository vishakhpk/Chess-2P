package pieces;

import java.awt.Point;

public class Rook extends Piece
{
	public Rook(Point a, boolean b)
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
		//code to see if move is possible or not from this.x, this.y to OP.x,OP.y
		//returns 1 if move is possible else returns 0
		boolean isPieceWhite = (positions[this.pos.x][this.pos.y]==8) ;
		boolean isPieceBlack = (positions[this.pos.x][this.pos.y]==2) ;
		boolean isOutputWhite = (positions[OP.x][OP.y] > 6);
		boolean isOutputBlack = (positions[OP.x][OP.y] <= 6 && positions[OP.x][OP.y] > 0);
		boolean isOutputEmpty = (positions[OP.x][OP.y] == 0);
            if(this.pos.x==OP.x &&this.pos.y!=OP.y)//checks for horizontal moves
            {
                int flag = 0,i;
                if(OP.y > this.pos.y){
                    for(i=this.pos.y+1;i<OP.y;i++){//checks if any peice is between initial and final position
                        if(positions[OP.x][i] != 0)
                            flag = 1;
                    }
                }
                else{
                    for( i=this.pos.y-1;i>OP.y;i--){
                        if(positions[OP.x][i] != 0)
                            flag = 1;
                    }
                }
                if(flag == 0)
                	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) || isPieceBlack && (isOutputWhite || isOutputEmpty))
                		return 1;
                	else
                		return 0;
            }
            else if(this.pos.y==OP.y && this.pos.x!=OP.x)//checks for vertical moves
            {
                int flag = 0,i;
                if(OP.x > this.pos.x){
                    for(i=this.pos.x+1;i<OP.x;i++){
                        if(positions[i][OP.y] != 0)
                            flag = 1;
                    }
                }
                else{
                    for( i=this.pos.x-1;i>OP.x;i--){
                        if(positions[i][OP.y] != 0)
                            flag = 1;
                    }
                }
                if(flag == 0)
                	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) || isPieceBlack && (isOutputWhite || isOutputEmpty))
                		return 1;
                	else
                		return 0;
            }
		return 0;
	}
}