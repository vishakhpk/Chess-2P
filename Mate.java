package gui;

import java.awt.Point;

class Mate 
{
	Back a;
	int valid_moves;
	int col;
	Point[] pos = new Point[16];
	int[][] temp_matrix = new int[8][];
	Mate(int positions[][], int a)
	{
		this.a = new Back(positions);
		this.valid_moves=0;
		this.col=a;
		for(int i=0;i<8;i++)
		{
			this.temp_matrix[i]= new int[8];
		}
		// a=0 then i check if black king is in checkmate
		// a=1 then white king
		int k=0;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				this.temp_matrix[i][j]=positions[i][j];
				if(this.col==0)
				{
					if(positions[i][j]<=6&&positions[i][j]>0)
					{
						this.pos[k]= new Point(i,j);
						k++;
					}
				}
				else if(this.col==1)
				{
					if(positions[i][j]>=7&&positions[i][j]<=12)
					{
						this.pos[k]= new Point(i,j);
						k++;
					}
				}
				//now pos[] stores all the locations of all alive pieces of the same suit as that being checked
				//also temp_matrix stores all the current positions when checkmate is called
			}
	}

}
