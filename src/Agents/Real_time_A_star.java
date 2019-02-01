package Agents;

import java.util.ArrayList;

import Board.Board;
import Board.vertex;

public class Real_time_A_star implements Agent{
	int depth = 50;
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
				if (depth == 0)
					return finalBord;
				Board newBoard = (Board) currBoard.clone();
				vertex currAttacker = newBoard.verticiesList[attackP_list.get(i)];
				vertex currDefender = newBoard.verticiesList[defenceP_list.get(j)];
				if (newBoard.check_attack(currAttacker, currDefender)) {
					newBoard.attack(currAttacker, currDefender);
					/*
					 * g = f + h
					 * f is the number of turns till now.
					 * h is the number of enemy arimes.
					 */
					int g  =  newBoard.get_turns() + calcArmies(newBoard, EnemyID);
					if (g < minArmies) {
						minArmies = g;
						finalBord = newBoard;
					}
				}
				depth --;
			}
		}
		System.out.println("minimum armies:"+minArmies);
		finalBord.new_turns();
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
