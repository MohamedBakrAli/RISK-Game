package Agents;

import java.util.ArrayList;
import Board.Board;
import Board.vertex;

public class Aggressive implements Agent {

	@Override
	public Board play(Board currBoard, int p_id) {
		Board newBoard = currBoard;

		// add the bonus to the vertex with the most armies
		ArrayList<Integer> captured_partitions = newBoard.get_partions(p_id);
		int bonus=0;
		for(int i=0;i<captured_partitions.size();i++) {
			bonus+=newBoard.PartitionsList[captured_partitions.get(i)].get_value();
		}
		int ver=newBoard.get_vertex_with_most_armies(p_id);
		if(newBoard.player_list[p_id].getBonus()) {
			bonus+=2;
			newBoard.player_list[p_id].set_bounce(false);
		}
		//System.out.println("before:"+ver+" armies:"+newBoard.verticiesList[ver].get_NumberOfArmy());;
		newBoard.verticiesList[ver].set_NumberOfArmy(newBoard.verticiesList[ver].get_NumberOfArmy()+bonus);
		//System.out.println("after:" + newBoard.verticiesList[ver].get_NumberOfArmy());
		ArrayList<Integer> attackP_list, defenceP_list;
		
		if (p_id == 1) {
			attackP_list = newBoard.player_list[p_id].get_capturedVerticies();
			defenceP_list = newBoard.player_list[2].get_capturedVerticies();
		} else {
			attackP_list = newBoard.player_list[p_id].get_capturedVerticies();
			defenceP_list = newBoard.player_list[1].get_capturedVerticies();
		}
		
		vertex res[] = getVertices(attackP_list, defenceP_list, newBoard);
		if(res==null)
			return null;
		newBoard.attack(res[0], res[1]);
		System.out.println(res[1].get_id());
		newBoard.new_turns();
		return newBoard;
	}

	private vertex[] getVertices(ArrayList<Integer> attackList, ArrayList<Integer> defList, Board board) {
		vertex res[] = new vertex[2];
		int maxArmies = 0;
		vertex attackVertex = null;
		vertex defVertex = null;
		for (int i = 0; i < attackList.size(); i++) {
			vertex currAttack = board.verticiesList[attackList.get(i)];
			for (int j = 0; j < defList.size(); j++) {
				vertex currEnemy = board.verticiesList[defList.get(j)];
				if (board.check_attack(currAttack, currEnemy)) {
					if (currEnemy.get_NumberOfArmy() > maxArmies) {
						maxArmies = currEnemy.get_NumberOfArmy();
						attackVertex = currAttack;
						defVertex = currEnemy;
					}
				}
			}
		}
		if (maxArmies > 0) {
			res[0] = attackVertex;
			res[1] = defVertex;
			return res;
		}
		return null;
	}

}
