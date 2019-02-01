package Board;

import java.util.ArrayList;

import Board.Board;
import Board.partition;
import Board.Player;
import Board.vertex;

import java.util.*;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Board implements Cloneable {
	public vertex[] verticiesList;
	public partition[] PartitionsList;
	public Player[] player_list;
	ArrayList<Point> edges;
	private int turns = 0;

	private int VNum;
	private int PNum;
	
	public Board() {
		edges = new ArrayList<>();
	}
	public int get_turns(){
		return this.turns;
	}
	public void new_turns(){
		this.turns ++;
	}
	public void setPlayers(File file)
	{
		
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
		Scanner sc = new Scanner(br);
		String line = sc.nextLine();
		String[] split = line.split(" ");
		int p1 = Integer.parseInt(split[1]);
		System.out.println("p1:"+p1);
		ArrayList<Integer>a=new ArrayList<>();
		player_list[1] = new Player(a, 1);
		for(int i=1;i<=p1;i++)
		{
			line = sc.nextLine();
			split = line.split(" ");
			a.add(Integer.parseInt(split[0]));
			verticiesList[Integer.parseInt(split[0])].set_PlayerNum(1);
			verticiesList[Integer.parseInt(split[0])].set_NumberOfArmy(Integer.parseInt(split[1]));
		}
		System.out.println("A1:"+a);
		player_list[1] = new Player(a, 1);
		//set player2
		line = sc.nextLine();
		split = line.split(" ");
		a = new ArrayList<>();
		int p2 = Integer.parseInt(split[1]);
		System.out.println("p2:"+p2);
		for(int i=1;i<=p2;i++)
		{
			line = sc.nextLine();
			split = line.split(" ");
			a.add(Integer.parseInt(split[0]));
			verticiesList[Integer.parseInt(split[0])].set_PlayerNum(2);
			verticiesList[Integer.parseInt(split[0])].set_NumberOfArmy(Integer.parseInt(split[1]));
		}
		System.out.println("A2:"+a);
		player_list[2] = new Player(a, 2);
		/*verticiesList[1].set_NumberOfArmy(10);verticiesList[1].set_PlayerNum(1);
		verticiesList[2].set_NumberOfArmy(6);verticiesList[2].set_PlayerNum(2);
		verticiesList[3].set_NumberOfArmy(2);verticiesList[3].set_PlayerNum(1);
		verticiesList[4].set_NumberOfArmy(5);verticiesList[4].set_PlayerNum(2);*/
		
		
	}

	// set_player
	public void set_players(ArrayList<Integer> cap, int id) {
		player_list[id] = new Player(cap, id);
	}
	
	public void readGraph(File file) {
		int V, E, P;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
		Scanner sc = new Scanner(br);
		String line = sc.nextLine();
		String[] split = line.split(" ");
		V = Integer.parseInt(split[1]);
		VNum = V;
		line = sc.nextLine();
		split = line.split(" ");
		E = Integer.parseInt(split[1]);
		player_list = new Player[3];
		verticiesList = new vertex[V + 1];
		for (int i = 1; i <= V; i++)
			verticiesList[i] = new vertex(i);
		for (int i = 0; i < E; i++) {
			line = sc.nextLine();
			split = line.split("[(;) ]");
			int n1 = Integer.parseInt(split[1]);
			int n2 = Integer.parseInt(split[2]);
			edges.add(new Point(n1, n2));
			// System.out.println(split[1]+" "+split[2]);
			verticiesList[n1].add_neighbours(n2);
			verticiesList[n2].add_neighbours(n1);
		}
		line = sc.nextLine();
		split = line.split(" ");
		P = Integer.parseInt(split[1]);
		PNum = P;
		PartitionsList = new partition[P + 1];
		for (int i = 1; i <= P; i++)
			PartitionsList[i] = new partition(i);
		for (int i = 1; i <= P; i++) {
			line = sc.nextLine();
			split = line.split(" ");
			PartitionsList[i].set_value(Integer.parseInt(split[0]));
			for (int j = 1; j < split.length; j++) {
				PartitionsList[i].add_vertex(Integer.parseInt(split[j]));
				verticiesList[Integer.parseInt(split[j])].set_PartitionNum(i);
			}
		}
	}

	// return list of captured partitions that p_id hold
	public ArrayList<Integer> get_partions(int p_id) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<Integer> pla_cap = new ArrayList<Integer>();

		pla_cap = player_list[p_id].get_capturedVerticies();
		//System.out.println("p_id"+p_id+"  "+pla_cap);
		for (int i = 1; i < PartitionsList.length; i++) {
	//System.out.println();
			if (pla_cap.containsAll(PartitionsList[i].get_verticies())) {
				//System.out.println("captured:"+i);
				res.add(i);
			}
		}
		return res;
	}

	// return true if v1 can attack v2 o.w return false
	public boolean check_attack(vertex v1, vertex v2) {
		if (v1.get_PlayerNum() == v2.get_PlayerNum())
			return false;
		if (!v1.get_neighbours().contains(v2.get_id()))
			return false;
		if (v1.get_NumberOfArmy() - v2.get_NumberOfArmy() >= 2)
			return true;
		return false;
	}

	// v1 will attack v2
	public void attack(vertex v1, vertex v2) {
		if (!check_attack(v1, v2))
			return;
		player_list[v2.get_PlayerNum()].remove_capturedVerticie(v2.get_id());
		v2.set_PlayerNum(v1.get_PlayerNum());
		player_list[v2.get_PlayerNum()].add_capturedVerticie(v2.get_id());
		v1.set_NumberOfArmy(v1.get_NumberOfArmy() - v2.get_NumberOfArmy() - 1);
		v2.set_NumberOfArmy(1);
		player_list[v1.get_PlayerNum()].set_bounce(true);
	}

	public int get_vertex_with_least_armies(int p_id) {
		ArrayList<Integer> temp = player_list[p_id].get_capturedVerticies();
		Collections.sort(temp);
		if (temp.size() == 0)
			return -1;
		int army = verticiesList[temp.get(0)].get_NumberOfArmy(), num = temp.get(0);
		for (int i = 1; i < temp.size(); i++) {
			if (verticiesList[temp.get(i)].get_NumberOfArmy() < army) {
				army = verticiesList[temp.get(i)].get_NumberOfArmy();
				num = temp.get(i);
			}
		}
		return num;
	}

	public int get_vertex_with_most_armies(int p_id) {
		ArrayList<Integer> temp = player_list[p_id].get_capturedVerticies();
		Collections.sort(temp);
		if (temp.size() == 0)
			return -1;
		int army = verticiesList[temp.get(0)].get_NumberOfArmy();
		int num = temp.get(0);
		for (int i = 1; i < temp.size(); i++) {
			if (verticiesList[temp.get(i)].get_NumberOfArmy() > army) {
				army = verticiesList[temp.get(i)].get_NumberOfArmy();
				num = temp.get(i);
			}
		}
		return num;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Board b;
		try {
			b = (Board) super.clone();
			b.verticiesList = new vertex[VNum + 1];
			b.PartitionsList = new partition[PNum + 1];
			b.player_list = new Player[3];

			for (int i = 1; i < this.verticiesList.length; i++) {
				b.verticiesList[i] = (vertex) this.verticiesList[i].clone();
			}
			for (int i = 1; i < this.PartitionsList.length; i++) {
				b.PartitionsList[i] = (partition) this.PartitionsList[i].clone();
			}
			for (int i = 1; i < this.player_list.length; i++) {
				b.player_list[i] = (Player) this.player_list[i].clone();
			}
		} catch (CloneNotSupportedException e) {
			return null;
		}

		return b;
	}

}
