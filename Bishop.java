package pieces;

import java.awt.Point;



public class Bishop extends Piece
{
	public Bishop(Point a, boolean b)
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
		boolean isPieceWhite = (positions[this.pos.x][this.pos.y]==10) ;
		boolean isPieceBlack = (positions[this.pos.x][this.pos.y]==4) ;
		boolean isOutputWhite = (positions[OP.x][OP.y] > 6);
		boolean isOutputBlack = (positions[OP.x][OP.y] <= 6 && positions[OP.x][OP.y] > 0);
		boolean isOutputEmpty = (positions[OP.x][OP.y] == 0);
		//code to see if move is possible or not from this.x, this.y to OP.x,OP.y
		//returns 1 if move is possible and returns 0 if move is not possible
		

            if(Math.abs(OP.x-this.pos.x)==Math.abs(OP.y-this.pos.y))//checks for diagonal move of Bishop
            {
                int flag=0,i,j;
                
                if(OP.x>this.pos.x && OP.y>this.pos.y){
                    for(i=this.pos.x+1, j=this.pos.y+1 ; i<OP.x && j<OP.y;i++,j++){//checks if there is any peice in between start and final position of bishop
                        if(positions[i][j] != 0)//if any peice exists flag is made 1
                            flag=1;
                    }
                }
                else if(OP.x>this.pos.x && OP.y<this.pos.y){
                    for(i=this.pos.x+1,  j=this.pos.y-1;i<OP.x&&j>OP.y;i++,j--){
                        if(positions[i][j] != 0)
                            flag=1;
                    }
                }
                else if(OP.x<this.pos.x && OP.y>this.pos.y){
                    for( i=this.pos.x-1,  j=this.pos.y+1;i>OP.x&&j<OP.y;i--,j++){
                        if(positions[i][j] != 0)
                            flag=1;
                    }
                }
                else{
                    for( i=this.pos.x-1,  j=this.pos.y-1;i>OP.x&&j>OP.y;i--,j--){
                        if(positions[i][j] != 0)
                            flag=1;
                    }
                }
                if(flag==0)
                	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) ||( isPieceBlack && (isOutputWhite || isOutputEmpty)))
                		return 1;
                	else
                		return 0;

            }

		return 0;
	}
}