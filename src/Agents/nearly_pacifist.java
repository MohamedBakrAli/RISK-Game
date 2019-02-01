package Agents;

import java.io.File;
import java.util.ArrayList;

import Board.Board;
import Board.Player;

public class nearly_pacifist implements Agent {

	@Override
	public Board play(Board currBoard, int p_id) {
		Board newBoard=currBoard;
		
		// add the bounce 
		ArrayList<Integer>captured_partitioned=newBoard.get_partions(p_id); 
		int bounes=0;
		for(int i=0;i<captured_partitioned.size();i++) {
			bounes+=newBoard.PartitionsList[captured_partitioned.get(i)].get_value();
		}
		int bounced_ver=newBoard.get_vertex_with_least_armies(p_id);
		if(newBoard.player_list[p_id].getBonus()) {
			bounes+=2;
			newBoard.player_list[p_id].set_bounce(false);
		}
		newBoard.verticiesList[bounced_ver].set_NumberOfArmy(newBoard.verticiesList[bounced_ver].get_NumberOfArmy()+bounes);
		
		// make the attack
		int loss=Integer.MAX_VALUE,v1=0,v2=0;
		ArrayList<Integer>attackP_list,defenceP_list;
		if(p_id==1) {
			attackP_list=newBoard.player_list[p_id].get_capturedVerticies();
			defenceP_list=newBoard.player_list[2].get_capturedVerticies();
		}
		else {
			attackP_list=newBoard.player_list[p_id].get_capturedVerticies();
			defenceP_list=newBoard.player_list[1].get_capturedVerticies();
		}
		for(int i=0;i<attackP_list.size();i++) {
			for(int j=0;j<defenceP_list.size();j++) {
				if(newBoard.check_attack(newBoard.verticiesList[ attackP_list.get(i)],newBoard.verticiesList[  defenceP_list.get(j)]) ) {
					if( newBoard.verticiesList[  defenceP_list.get(j)].get_NumberOfArmy() <loss) {
						loss=newBoard.verticiesList[  defenceP_list.get(j)].get_NumberOfArmy();
						v1=attackP_list.get(i);
						v2=defenceP_list.get(j);
						//System.out.println(v1+" "+v2+" "+loss);
					}
				}
			}
		}
		//System.out.println(v1+" "+v2);
		newBoard.attack(newBoard.verticiesList[v1], newBoard.verticiesList[v2]);
		newBoard.new_turns();
		return newBoard;
		
	}

}
