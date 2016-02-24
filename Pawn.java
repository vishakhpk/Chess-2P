package pieces;

import java.awt.Point;

public class Pawn extends Piece
{
	public Pawn(Point a, boolean b)
	{
		super(b);
		this.pos = a;
	}
	public void setpos(int i, int j)
	{
		this.pos.x=i;
		this.pos.y=j;
	}
	/*
	 * Checks if a move is valid and also check that there is no piece blocking its path
	 * For a straight move the output must be empty
	 * And for a diagonal move the output positions must contain a piece of other kind
	 */
	public int move(Point OP, int positions[][])
	{
		boolean isPieceWhite = (positions[this.pos.x][this.pos.y]==7);
		boolean isPieceBlack = (positions[this.pos.x][this.pos.y]==1);
		boolean isOutputWhite = (positions[OP.x][OP.y] > 6);
		boolean isOutputBlack = (positions[OP.x][OP.y] <= 6 && positions[OP.x][OP.y] > 0);
		boolean isOutputEmpty = (positions[OP.x][OP.y] == 0);
		int flag=0,differentiator=-1;
		
		if(positions[this.pos.x][this.pos.y]==1)
			differentiator = 1;
		
		if((this.pos.x == 1 || this.pos.x == 6) && (OP.x-this.pos.x == 2*differentiator && OP.y == this.pos.y)){// checks if pawn not moved and is making a double forward move
			if(positions[this.pos.x+(differentiator)][this.pos.y] == 0){ 										// if the cell in front is empty
				if(isOutputEmpty){																				//output is not occupied
					flag = 1;																					//move possible; flag=1;
				}
			}
		}
		else if(OP.x - this.pos.x == (differentiator)){															//is a forward move of one unit
			if(OP.y == this.pos.y){																				//straight
				if(isOutputEmpty){																				//for a 1 unit move the output must be unoccupied
					flag = 1;
				}
			}
			else if(OP.y-this.pos.y==1 || OP.y-this.pos.y==-1){													//diagonal move
				if((isPieceBlack && isOutputWhite) || (isPieceWhite && isOutputBlack)){							//making sure that the diagonal position is occupied by white
					flag = 1;
				}
			}
		}	
		return flag;
	}
}