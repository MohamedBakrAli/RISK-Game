package Board;

import java.util.ArrayList;

public class partition implements Cloneable{
    private int id;
    private int value;
    private ArrayList<Integer>verticies;
    public partition(int id) {
        // TODO Auto-generated constructor stub
        this.id=id;
        verticies=new ArrayList<Integer>();
    }
    public void set_value (int val) {
        this.value=val;
    }
    public int get_value () {
        return value;
    }
    public ArrayList<Integer> get_verticies() {
        return verticies;
    }
    public void add_vertex(int v) {
        verticies.add(v);
    }
    
    @Override
	public Object clone() throws CloneNotSupportedException {
    	partition p ;
		try {
			p = (partition) super.clone();
			p.verticies = new ArrayList<>();
			p.verticies.addAll(this.verticies);
			
		} catch (CloneNotSupportedException e) {
			return null;
		}
		
		return p;
	}
}
 