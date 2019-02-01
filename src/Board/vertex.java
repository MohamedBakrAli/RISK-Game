package Board;

import java.util.ArrayList;

public class vertex implements Cloneable {
	private int ID;
	private int NumberOfArmy;// input
	private int PlayerNum; // input
	private int PartitionNum;
	private int x;
	private int y;
	private ArrayList<Integer> neighbours;

	public vertex(int ID) {
		this.ID = ID;
		neighbours = new ArrayList<Integer>();
	}

	public void set_NumberOfArmy(int n) {
		NumberOfArmy = n;
	}

	public int get_NumberOfArmy() {
		return NumberOfArmy;
	}

	public void set_PlayerNum(int n) {
		PlayerNum = n;
	}

	public int get_PlayerNum() {
		return PlayerNum;
	}

	public void set_PartitionNum(int n) {
		PartitionNum = n;
	}

	public int get_PartitionNum(int n) {
		return PartitionNum;
	}

	public ArrayList<Integer> get_neighbours() {
		return neighbours;
	}

	public void add_neighbours(int v) {
		neighbours.add(v);
	}

	public int get_id() {
		return ID;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		vertex v ;
		try {
			v = (vertex) super.clone();
			v.neighbours = new ArrayList<>();
			v.neighbours.addAll(this.neighbours);
			
		} catch (CloneNotSupportedException e) {
			return null;
		}
		
		return v;
	}
}