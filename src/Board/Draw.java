package Board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import Agents.Agent;

public class Draw extends JComponent {

	private static final long serialVersionUID = 1L;
	int width;
	int height;
	private static final int SIZE = 600;
	private int a = SIZE / 2;
	private int b = a;
	private int r = 4 * SIZE / 5;

	private vertex[] nodes;
	private ArrayList<Point> edges;
	private Ellipse2D[] nodesAreas;
	private Board board;
	private ListenForMouse listener;
	public Draw(Board board) {
		width = 30;
		height = 30;
		this.board = board;
		this.setBounds(0, 30, 1100, 670);
		this.nodes = board.verticiesList;
		this.edges = board.edges;
		nodesAreas = new Ellipse2D[nodes.length];
		initialize();
		this.repaint();
		listener= new ListenForMouse(this);
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.setStroke(new BasicStroke(2.0f));
		Image img1 = Toolkit.getDefaultToolkit().getImage("Images/background.jpg");
		g.drawImage(img1, 0, 0, getWidth(), getHeight(), this);
		g2d.setColor(Color.black);
		FontMetrics f = g.getFontMetrics();
		a = getWidth() / 2;
		b = getHeight() / 2;
		int m = Math.min(a, b);
		r = 4 * m / 5;
		int r2 = Math.abs(m - r) / 2;
		// draw edges
		g2d.setColor(Color.RED);
		for (Point p : edges) {
			g.drawLine(nodes[(int) p.getX()].getX(), nodes[(int) p.getX()].getY(), nodes[(int) p.getY()].getX(),
					nodes[(int) p.getY()].getY());
		}
		// draw nodes
		int nodeHeight = Math.max(height, f.getHeight())+2;
		for (int i = 1; i < nodes.length; i++) {
			if (nodes[i].get_PlayerNum() == 1)
				g2d.setColor(Color.WHITE);
			else
				g2d.setColor(Color.BLUE);
			
			String str = "ID="+Integer.toString(nodes[i].get_id())
						+" ,P#="+Integer.toString(nodes[i].get_PartitionNum(i))
						+" ,A="+Integer.toString(nodes[i].get_NumberOfArmy());
			int nodeWidth = Math.max(width, f.stringWidth(str)+width/2)+2;
			g2d.fillOval(nodes[i].getX() - nodeWidth/2, nodes[i].getY() - nodeHeight/2, nodeWidth, nodeHeight);
			g2d.setColor(Color.black);
			g2d.drawOval(nodes[i].getX() - nodeWidth/2, nodes[i].getY() - nodeHeight/2, nodeWidth, nodeHeight);
			g2d.drawString(str, nodes[i].getX()-f.stringWidth(str)/2,
					 nodes[i].getY()+f.getHeight()/2);
		}
	}

	public void initialize() {
		a = getWidth() / 2;
		b = getHeight() / 2;
		int m = Math.min(a, b);
		r = 4 * m / 5;
		int r2 = Math.abs(m - r) / 2;
	//	System.out.println("a:" + a + "b:" + b + "r:" + r);
		for (int i = 1; i < nodes.length; i++) {
			double t = 2 * Math.PI * (i-1) / (nodes.length-1);
			int x = (int) Math.round(a + r * Math.cos(t));
			int y = (int) Math.round(b + r * Math.sin(t));
			nodes[i].setX(x);
			nodes[i].setY(y);
			nodesAreas[i] = new Ellipse2D.Double(x-r2,y-r2,2*r2,2*r2);
		}
	}
	
	public void setNodes(vertex []v)
	{
		for(int i = 1;i<nodes.length;i++)
		{
			nodes[i] = v[i];
		}
	}

	private class ListenForMouse {

		private Draw d;
		public ListenForMouse(Draw d) {
			this.d = d;
		}
	/*	public void listen() {
			d.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					Point p = new Point(e.getX(),e.getY());
					for(int i=1;i<nodesAreas.length;i++)
					{
						if(nodesAreas[i].contains(p)){
							System.out.println(i);
							if(nodes[i].get_PlayerNum() == 1)
							{
								v1 = nodes[i];
							}else if(nodes[i].get_PlayerNum() == 2){
								v2 = nodes[i];
							}
						}
					}
					if(v1!=null&&v2!=null)
					{
						if(board.check_attack(v1, v2)){
							board.attack(v1, v2);
							v1 = null;v2 = null;
							turn = 2;
							try {
								agent2.play(board, 2);
							} catch (CloneNotSupportedException e1) {
								e1.printStackTrace();
							}
							repaint();
						}
						else{
							System.out.println("can't attack! choose again");
							v1 = null;v2 = null;
						}
					}
				}
			});
		}*/
	}
}
