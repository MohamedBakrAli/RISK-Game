package Agents;

import Board.Board;

public class CopyMaker implements Cloneable{
	Board b = new Board();
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		CopyMaker c = (CopyMaker)super.clone();
		c.b = new Board();
		return c;
	}
}
