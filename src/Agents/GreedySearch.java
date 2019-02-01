package Agents;

import java.util.ArrayList;

import Board.Board;
import Board.partition;
import Board.Player;
import Board.vertex;

public class GreedySearch implements Agent {

	@Override
	public Board play(Board currBoard, int p_id) throws CloneNotSupportedException {

		ArrayList<Integer> attackP_list, defenceP_list;
		int EnemyID = 0;
		if (p_id == 1) {
			attackP_list = currBoard.player_list[p_id].get_capturedVerticies();
			defenceP_list = currBoard.player_list[2].get_capturedVerticies();
			EnemyID = 2;
		} else {
			attackP_list = currBoard.player_list[p_id].get_capturedVerticies();
			defenceP_list = currBoard.player_list[1].get_capturedVerticies();
			EnemyID = 1;
		}
		int minArmies = Integer.MAX_VALUE;
		Board finalBord = null;
		for (int i = 0; i < attackP_list.size(); i++) {
			
			
			for (int j = 0; j < defenceP_list.size(); j++) {
				Board newBoard = (Board) currBoard.clone();
				vertex currAttacker = newBoard.verticiesList[attackP_list.get(i)];
				vertex currDefender = newBoard.verticiesList[defenceP_list.get(j)];
				if (newBoard.check_attack(currAttacker, currDefender)) {
					newBoard.attack(currAttacker, currDefender);
					int n = calcArmies(newBoard, EnemyID);
					if (n < minArmies) {
						minArmies = n;
						finalBord = newBoard;
					}
				}
			}
		}
		System.out.println("minimum armies:"+minArmies);
	//	finalBord.new_turns();
		return finalBord;
	}

	private int calcArmies(Board board, int id) {
		ArrayList<Integer> captured = board.player_list[id].get_capturedVerticies();
		int sum = 0;
		for (Integer v : captured) {
			sum += board.verticiesList[v].get_NumberOfArmy();
		}
		return sum;
	}
}
