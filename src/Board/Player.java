package Board;

import java.util.ArrayList;

public class Player implements Cloneable {
	private boolean bounce;
	private ArrayList<Integer> capturedVerticies;
	private Integer player_id;

	public Player(ArrayList<Integer> c, Integer id) {
		this.capturedVerticies = c;
		this.player_id = id;
	}

	public void add_capturedVerticie(int capturedVerticie) {
		this.capturedVerticies.add(capturedVerticie);
	}

	public void remove_capturedVerticie(int capturedVerticie) {
		this.capturedVerticies.remove((Object) capturedVerticie);
	}

	public ArrayList<Integer> get_capturedVerticies() {
		return this.capturedVerticies;
	}

	public void set_bounce(boolean b) {
		bounce = b;
	}

	public boolean getBonus() {
		return bounce;
	}

	public int get_id() {
		return this.player_id;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Player p;
		try {
			p = (Player) super.clone();
			p.capturedVerticies = new ArrayList<>();
			p.capturedVerticies.addAll(this.capturedVerticies);
			
		} catch (CloneNotSupportedException e) {
			return null;
		}
		
		return p;
	}
}
