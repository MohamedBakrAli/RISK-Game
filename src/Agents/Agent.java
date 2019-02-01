package Agents;

import Board.Board;

public interface Agent {
	public Board play(Board currBoard,int p_id) throws CloneNotSupportedException;
}
