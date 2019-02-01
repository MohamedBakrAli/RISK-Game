package Agents;

import java.util.ArrayList;

import Board.Board;

public class completely_passive implements Agent {

	@Override
	public Board play(Board currBoard, int p_id) {
		Board newBoard=currBoard;
		
		// add the bounce 
		ArrayList<Integer>captured_partitioned=newBoard.get_partions(p_id); 
		int bonus=0;
		for(int i=0;i<captured_partitioned.size();i++) {
			bonus+=newBoard.PartitionsList[captured_partitioned.get(i)].get_value();
		}
		int ver=newBoard.get_vertex_with_least_armies(p_id);
		if(newBoard.player_list[p_id].getBonus()) {
			bonus+=2;
			newBoard.player_list[p_id].set_bounce(false);
		}
		newBoard.verticiesList[ver].set_NumberOfArmy(bonus+newBoard.verticiesList[ver].get_NumberOfArmy());
		newBoard.new_turns();
		return newBoard;
	}

}
