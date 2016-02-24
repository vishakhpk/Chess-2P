package pieces;

import java.awt.Point;

abstract public class Piece
{
	Piece(boolean a)
	{
			this.isAlive = a;

	}
	public boolean isAlive;
	public Point pos;
	public int move(Point oP, int[][] positions) {
		// TODO Auto-generated method stub
		return 0;
	}
}