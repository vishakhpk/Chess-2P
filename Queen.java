package pieces;

import java.awt.Point;



public class Queen extends Piece
{
	public Queen(Point a, boolean b)
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
		//returns 1 if move is possible else returns zero
		boolean isPieceWhite = (positions[this.pos.x][this.pos.y]==11) ;
		boolean isPieceBlack = (positions[this.pos.x][this.pos.y]==5) ;
		boolean isOutputWhite = (positions[OP.x][OP.y] > 6);
		boolean isOutputBlack = (positions[OP.x][OP.y] <= 6 && positions[OP.x][OP.y] > 0);
		boolean isOutputEmpty = (positions[OP.x][OP.y] == 0);
		// checks for diagonal moves
            if(Math.abs(OP.x-this.pos.x)==Math.abs(OP.y-this.pos.y))
            {
                     int flag=0,i,j;
                if(OP.x>this.pos.x && OP.y>this.pos.y){//checks if there is any peice between initial and final position
                    for(i=this.pos.x+1, j=this.pos.y+1;i<OP.x && j<OP.y;i++,j++){
                        if(positions[i][j] != 0)//if there is a peice then flag is made 1
                            flag=1;
                    }
                }
                else if(OP.x>this.pos.x && OP.y<this.pos.y){
                    for( i=this.pos.x+1, j=this.pos.y-1;i<OP.x && j>OP.y;i++,j--){
                        if(positions[i][j] != 0)
                            flag=1;
                    }
                }
                else if(OP.x<this.pos.x && OP.y>this.pos.y){
                    for( i=this.pos.x-1, j=this.pos.y+1;i>OP.x&&j<OP.y;i--,j++){
                        if(positions[i][j] != 0)
                            flag=1;
                    }
                }
                else{
                    for( i=this.pos.x-1, j=this.pos.y-1;i>OP.x&&j>OP.y;i--,j--){
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
          //checks for different cases of  horizontal and vertical moves of a rook
            if(this.pos.x==OP.x &&this.pos.y!=OP.y)
            {
                int flag = 0;
                if(OP.y > this.pos.y){
                    for(int i=this.pos.y+1;i<OP.y;i++){//looks for any peice between initial and final position 
                        if(positions[OP.x][i] != 0)
                            flag = 1;
                    }
                }
                else{
                    for(int i=this.pos.y-1;i>OP.y;i--){
                        if(positions[OP.x][i] != 0)
                            flag = 1;
                    }
                }
                if(flag == 0)
                	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) ||( isPieceBlack && (isOutputWhite || isOutputEmpty)))
                		return 1;
                	else
                		return 0;



            }
            if(this.pos.y==OP.y && this.pos.x!=OP.x)
            {
                int flag = 0;
                if(OP.x > this.pos.x){
                    for(int i=this.pos.x+1;i<OP.x;i++){
                        if(positions[i][OP.y] != 0)
                            flag = 1;
                    }
                }
                else{
                    for(int i=this.pos.x-1;i>OP.x;i--){
                        if(positions[i][OP.y] != 0)
                            flag = 1;
                    }
                }
                if(flag == 0)
                	if((isPieceWhite && (isOutputBlack || isOutputEmpty)) ||( isPieceBlack && (isOutputWhite || isOutputEmpty)))
                		return 1;
                	else
                		return 0;

            }

		return 0;
	}
}
