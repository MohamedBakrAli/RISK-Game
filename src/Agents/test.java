package Agents;

import java.io.File;
import java.util.ArrayList;

import Board.Board;

public class test {

	public static void main(String[] args) throws CloneNotSupportedException {
		Board b=new Board();
		File f=new File("C:\\Users\\Mostafa_Ahmed\\Downloads\\RiskProject\\src\\Board\\data.txt");
		b.readGraph(f);
		b.verticiesList[1].set_NumberOfArmy(10);b.verticiesList[1].set_PlayerNum(1);
		b.verticiesList[2].set_NumberOfArmy(6);b.verticiesList[2].set_PlayerNum(2);
		b.verticiesList[3].set_NumberOfArmy(4);b.verticiesList[3].set_PlayerNum(1);
		b.verticiesList[4].set_NumberOfArmy(2);b.verticiesList[4].set_PlayerNum(2);
		
		ArrayList<Integer>a=new ArrayList<>();
		a.add(1);a.add(3);
		b.set_players(a, 1);

		a=new ArrayList<>();
		a.add(2);a.add(4);
		b.set_players(a, 2);
		
		Board b2 = (Board)b.clone();
		System.out.println(b.verticiesList[1].get_NumberOfArmy());
		b2.verticiesList[1].set_NumberOfArmy(20);
		
		System.out.println(b2.verticiesList[1].get_NumberOfArmy());
		System.out.println(b.verticiesList[1].get_NumberOfArmy());
		
		b.player_list[1].set_bounce(true);
		b2.player_list[1].set_bounce(false);
		System.out.println(b.player_list[1].getBonus());
		
		GreedySearch g = new GreedySearch();
		Board x = g.play(b, 1);
		
		System.out.println("vertices list armies");
		for(int i=1;i<=4;i++)
			System.out.println(x.verticiesList[i].get_NumberOfArmy());
		
		
	}

}
