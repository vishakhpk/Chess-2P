package gui;

import java.awt.Point;
import java.util.ArrayList;
import pieces.*;
import javax.swing.JOptionPane;

class Back
{
	ArrayList<Piece[]> allPieces = new ArrayList<Piece[]>();
	//instances of pieces
   	public Pawn[] b_pawn = new Pawn[8];
   	public Pawn[] w_pawn = new Pawn[8];

   	public Rook[] b_rook = new Rook[2];
   	public Rook[] w_rook = new Rook[2];

   	public Knight[] b_knight = new Knight[2];
   	public Knight[] w_knight = new Knight[2];

   	public Bishop[] b_bishop = new Bishop[2];
   	public Bishop[] w_bishop = new Bishop[2];

   	public Queen[] b_queen = new Queen[1];
   	public Queen[] w_queen = new Queen[1];

   	public King[] b_king = new King[1];
   	public King[] w_king = new King[1];

   	static int alternating_counter=0;
   	private Piece tempPieceHeld = null;
	private int tempPieceInt = 0;

   	Back(int positions[][])
	{
		int i,j,cbpawn=0,cwpawn=0,cbrook=0,cwrook=0,cbknight=0,cwknight=0,cbbishop=0,cwbishop=0,cwqueen=0,cbqueen=0;
		//Instantiates all the pieces on the board based on data received from positions[][]
		//Also sets the isAlive to false or true
		//all c* variables in this function are counter variables to instantiate the pieces into appropriate piece arrays
		for(i=0;i<8;i++)
		{
			for(j=0;j<8;j++)
			{
				switch(positions[i][j])
				{
				case 12: 
					w_king[0] = new King(new Point(i,j),true);
					break;
				case 11:
					w_queen[cwqueen++] = new Queen(new Point(i,j),true);
					break;
				case 6:
					b_king[0] = new King(new Point(i, j),true);
					break;
				case 5:
					b_queen[cbqueen++] = new Queen(new Point(i,j),true);
					break;
				case 1:
					b_pawn[cbpawn++] = new Pawn(new Point(i,j),true);
					break;
				case 7:
					w_pawn[cwpawn++] = new Pawn(new Point(i,j),true);
					break;
				case 2:
					b_rook[cbrook++] = new Rook(new Point(i,j),true);
					break;
				case 8:
					w_rook[cwrook++] = new Rook(new Point(i,j),true);
					break;
				case 3:
					b_knight[cbknight++] = new Knight(new Point(i,j),true);
					break;
				case 9:
					w_knight[cwknight++] = new Knight(new Point(i,j),true);
					break;
				case 4:
					b_bishop[cbbishop++] = new Bishop(new Point(i,j),true);
					break;
				case 10:
					w_bishop[cwbishop++] = new Bishop(new Point(i,j),true);
					break;
				}
			}
		}
		
				while(cbpawn<8){
					b_pawn[cbpawn++] = new Pawn(new Point(10,10),false);
				}
				while(cwpawn<8){
					w_pawn[cwpawn++] = new Pawn(new Point(10,10),false);
				}
				while(cbrook!=2){
					b_rook[cbrook++] = new Rook(new Point(10,10),false);
				}
				while(cwrook!=2){
					w_rook[cwrook++] = new Rook(new Point(10,10),false);
				}
				while(cbknight!=2){
					b_knight[cbknight++] = new Knight(new Point(10,10),false);
				}
				while(cwknight!=2){
					w_knight[cwknight++] = new Knight(new Point(10,10),false);
				}
				while(cbbishop!=2){
					b_bishop[cbbishop++] = new Bishop(new Point(10,10),false);
				}
				while(cwbishop!=2){
					w_bishop[cwbishop++] = new Bishop(new Point(10,10),false);
				}
				if(cbqueen!=1){
					b_queen[0] = new Queen(new Point(10,10),false);
				}
				if(cwqueen!=1){
					w_queen[0] = new Queen(new Point(10,10),false);
				}
				
				allPieces.add(null);
				allPieces.add(b_pawn);
				allPieces.add(b_rook);
				allPieces.add(b_knight);
				allPieces.add(b_bishop);
				allPieces.add(b_queen);
				allPieces.add(b_king);
				allPieces.add(w_pawn);
				allPieces.add(w_rook);
				allPieces.add(w_knight);
				allPieces.add(w_bishop);
				allPieces.add(w_queen);
				allPieces.add(w_king);
				//all pieces are instantiated
				//note allPieces(0) is set to null because positions[i][j]=0 is an empty slot in our chessboard
			}

	int inspect_move(int positions[][], Point IP, Point OP)
	{
		int res=0;
		//System.out.println("In inspect move");
		//Here we cycle through allPieces and call the appropriate move function based on position of IP
		for(Piece piece:allPieces.get(positions[IP.x][IP.y])){
			if(piece.pos.equals(IP)){
				res=piece.move(OP,positions);
			}
		}
		return res;
	}

	public int isKingSafe(int positions[][], int col)
	{
		int flag=1;
		//Based on col, we check if any piece of one colour can attack the other king in this move itself
		//if col=0, we check if any black piece can attack the white king
		if(col==0)
		{
			for(int i=1; i<=6;i++){
				  for(Piece piece : allPieces.get(i)){
					  if(piece.isAlive){
						  if(piece.move(w_king[0].pos, positions)==1){
							  flag=0;
						  }
					  }
				  }
			 }
		}

		//if col=1 we check if any white piece can attack the black king
		else if(col==1)
		{
			for(int i=7; i<=12;i++){
				for(Piece piece : allPieces.get(i)){
					if(piece.isAlive){
						if(piece.move(b_king[0].pos, positions)==1){
							flag=0;
						}
					}
				}
			}
		}
		return flag;
	}
	
	/*
	 * Makes a temporary move of a piece facilitating us to check for check, check-mate and if own king is safe
	 * It makes changes to the pices.pos and piece.isAlive accordingly
	 */
	void temporaryMove(int positions[][],Point IP,Point OP){
		if(positions[OP.x][OP.y] == 0){
			for(Piece piece : allPieces.get(positions[IP.x][IP.y])){
				if(piece.pos.equals(IP)){
					piece.pos = OP;
				}
			}
			positions[OP.x][OP.y] = positions[IP.x][IP.y];
			positions[IP.x][IP.y] = 0;
			tempPieceInt = 0;
		}
		
		else{
			for(Piece piece : allPieces.get(positions[OP.x][OP.y])){
				if(piece.pos.equals(OP)){
					tempPieceHeld = piece;
					piece.isAlive = false;
					tempPieceInt = positions[OP.x][OP.y];
					break;
				}
			}
			for(Piece piece : allPieces.get(positions[IP.x][IP.y]))
				if(piece.pos.equals(IP))
					piece.pos = OP;
			positions[OP.x][OP.y] = positions[IP.x][IP.y];
			positions[IP.x][IP.y] = 0;
		}
	}
	
	/*
	 * Reverts the earlier temporary move
	 * Accesses the temporary piece held and makes changes
	 */
	void temporaryMoveRevert(int positions[][],Point IP,Point OP){
		for(Piece piece : allPieces.get(positions[OP.x][OP.y]))
			if(piece.pos.equals(OP))
				piece.pos = IP;
		positions[IP.x][IP.y] = positions[OP.x][OP.y];
		positions[OP.x][OP.y] = tempPieceInt;
		if(tempPieceHeld != null){
			tempPieceHeld.isAlive = true;
		}
		tempPieceHeld = null;
		tempPieceInt = 0;
		
	}
	
	/*
	 * This method checks if the players are playing in alternating
	 * It also checks that the white players start first
	 * It also allows you to make a move only if your king is safe
	 * If your king is in check it ensures that you make a logical move to subvert the check
	 */
	int isAlernating(int positions[][],Point IP,Point OP, int res){
		if(alternating_counter%2 == 0){									//White move if alternating_counter is even
			if(positions[IP.x][IP.y]>6 && positions[IP.x][IP.y]<=12){
				if(res == 1){
					temporaryMove(positions,IP,OP);
					if(isKingSafe(positions,0) == 1){					//To check if white king is safe
						alternating_counter+=1;
						System.out.println("Black players move next");
					}
					else{
						System.out.println("White in check");
						//message_box("WHITE IN CHECK","ERROR");
						res = 0;
					}
					temporaryMoveRevert(positions,IP,OP);
				}
			}
			else{
				System.out.println("It is white players move");
				res = 0;
			}
		}
		else{														//Black move made
			if(positions[IP.x][IP.y]>0 && positions[IP.x][IP.y]<=6){
				if(res == 1){
					temporaryMove(positions,IP,OP);
					if(isKingSafe(positions,1) == 1){
						alternating_counter+=1;
						System.out.println("White player move next");
					}
					else{
						System.out.println("Black in check");
	//					message_box("BLACK IN CHECK","ERROR");
						res = 0;
					}
					temporaryMoveRevert(positions,IP,OP);
					}
			}
			else{
				System.out.println("It is black players move!");
				res = 0;
			}
		}
		return res;
	}
	
	public void printPos(int positions[][]){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				System.out.print(positions[i][j]+" ");
			}
			System.out.println("\n");
		}
	}
	
	public boolean isValid(int positions[][],Point IP,Point OP)
	{
		//main backend function, analyses the move based on given IP and OP and current positions[][] and tells if the move is legal
		int res=inspect_move(positions,IP,OP);
		res = isAlernating(positions,IP,OP,res);
		//calls isAlternating to see that teams make alternate mooves only
		if (res == 1)
		{
			// if res=1 the move is legal
			// so we check if this puts the other king in check
			this.isCheck(positions, IP, OP);
			this.temporaryMove(positions, IP, OP);
			if(alternating_counter % 2==1)
				this.isCheckMate(positions,0);
			if (alternating_counter % 2 ==0)
				this.isCheckMate(positions, 1);
			// this checks if the game has reached checkmate condition and if the game is over
			this.temporaryMoveRevert(positions, IP, OP);
			return true;
		}
		
		else
		{
			message_box("INVALID","ERROR");
			return false;
		}
	}
	
	public void isCheck(int positions[][], Point IP, Point OP)
	{
		//this function analyses based on alternating counter if the king is in check
		//this.printPos(positions);
		this.temporaryMove(positions, IP, OP);
		if(Back.alternating_counter % 2 == 1)
		{
			//here a white move was made which may put the black king in check
			//System.out.println("in 1");
			if(this.isKingSafe(positions, 1)==0)
				message_box("BLACK KING IN CHECK","NOTE");
		}
		else if(Back.alternating_counter % 2 == 0)
		{	
			//here a black move was made which may put the white king in check
			//System.out.println("in 2");
			if(this.isKingSafe(positions, 0)==0)
				message_box("WHITE KING IN CHECK","NOTE");
		}
		this.temporaryMoveRevert(positions, IP, OP);
		//this.printPos(positions);
	}

	public static void message_box(String message, String title)
	{
		//function to print a message box with two string arguements, message and header
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void isCheckMate(int positions[][], int col)
	{
		// in the checkmate condition, there are absolutely no legal moves
		// this means that regardless of wherever IP and OP are, the move is invalid because the king is not safe and cannot be saved by any move
		int valid_moves=0;
		int i,j,k=0;
		int flag=0;
		int [][] temp_matrix = new int [8][];
		Point[] ownpos = new Point[16];
		for(i=0;i<8;i++)
		{
			temp_matrix[i]= new int[8];
		}
		// col=0 then i check if black king is in checkmate
		// col=1 then white king
		for(i=0;i<8;i++)
			for(j=0;j<8;j++)
			{
				temp_matrix[i][j]=positions[i][j];
				if(col==0)
				{
					if(positions[i][j]<=6&&positions[i][j]>0)
					{
						ownpos[k]= new Point(i,j);
						k++;
					}
				}
				else if(col==1)
				{
					if(positions[i][j]>=7&&positions[i][j]<=12)
					{
						ownpos[k]= new Point(i,j);
						k++;
					}
				}
				//now pos[] stores all the locations of all alive pieces of the same suit as that being checked
				//also temp_matrix stores all the current positions when checkmate is called
			}

		//System.out.println("LENGTH " + k);
		int limit =k;
		
		//now we have to cycle through all pos[] and see if there is any possible move from pos[i] to any of the 64 buttons
		for(i=0;i<limit;i++)
		{
			//System.out.println(i + "iteration of i loop");
			Point temp_IP= ownpos[i];
			//System.out.println("POS X :" + ownpos[i].x + " Y :" + ownpos[i].y);
			//System.out.println("TEMP_IP :" + temp_IP.x + " Y :" + temp_IP.y);
			///System.out.println("TEMP_MATRIX[IP] :" + temp_matrix[temp_IP.x][temp_IP.y]);
			for(j=0;j<8;j++)
			{
				for(k=0;k<8;k++)
				{
					// we store each [j][k] as temp_OP and see if there is a valid move from pos[i] to temp_OP
					Point temp_OP = new Point(j,k);
					for(Piece piece: allPieces.get(temp_matrix[temp_IP.x][temp_IP.y]))
						if(piece.pos.equals(temp_IP)&&piece.isAlive)
							flag=piece.move(temp_OP,temp_matrix);
/*					
	*/				//System.out.println("FLAG IS " + flag);
					
					//if flag is 1 it means some move is possible
					//now we have to check if that move sabotages it's own king
					if(flag==1)
					{
						//this.printPos(temp_matrix);
						this.temporaryMove(temp_matrix, temp_IP, temp_OP);
						//this.printPos(temp_matrix);

						if(Back.alternating_counter % 2 == 0)
						{
							if(this.isKingSafe(temp_matrix, 0)==1)
							{
								valid_moves=1;
							}
						}
						else if(Back.alternating_counter % 2 == 1)
						{	
							if(this.isKingSafe(temp_matrix, 1)==1)
							{
								valid_moves=1;
							}
						}
						this.temporaryMoveRevert(temp_matrix, temp_IP, temp_OP);
					
					//this code block checks if the move that made flag=1 jeopardizes your own king
					// if it does not i.e. if after the move is made, the king is safe then we increment valid_moves
					}//big if (flag == 1)
					if(valid_moves!=0){
						break;}
				
				}//k loop
					if(valid_moves!=0)
						break;
				}//j loop
			if(valid_moves!=0)
			{ 
				/*System.out.println("BROKE AT " + i + " " + j + " " + k + " OWNPOS[i] " 
								   + ownpos[i] + " " + temp_matrix[ownpos[i].x][ownpos[i].y] + " OP "+ abcd.x
								   + " " + abcd.y);*/
				break;
			}
		}//i loop
	//System.out.println("VALID MOVES: " + valid_moves);
	// the idea is that the only condition where there are ABSOLUTELY no possible moves is in the mate condition
	// so we break out of the loop as soon as we find a single valid move
	// if no such move was found then it is a mate and endgame
	if(valid_moves!=1){
		System.out.println("CHECKMATE");
		message_box("CHECKMATE","ENDGAME");}
	}
}
