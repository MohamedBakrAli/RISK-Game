package Board;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Agents.Agent;

public class MainFrame {
	JFrame frame;
	JButton next;
	private vertex bonus_ver;
	private vertex v1 = null;
	private vertex v2 = null;
	private int turn = 1;
	private Agent agent1;
	private Agent agent2;
	private Board curBoard;
	private Draw draw;
	private JLabel label;

	public MainFrame(Board b, Agent agent1, Agent agent2) {
		this.agent1 = agent1;
		this.agent2 = agent2;
		curBoard = b;
		frame = new JFrame();
		frame.setBounds(0, 0, 1100, 700);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		next = new JButton("play");
		next.setBounds(frame.getWidth()/2-50, 0, 100, 30);
		setButton(next);
		frame.add(next);
		draw = new Draw(b);
		frame.getContentPane().add(draw);

		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void setButton(JButton btn) {

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (turn == 1) {
					turn = 2;
					// player 1 is a human
					if (agent1 == null) {
						createDialog();

					} else {
						try {
							Board temp = agent1.play(curBoard, 1);
							if (temp != null) {
								curBoard = temp;
								draw.setNodes(curBoard.verticiesList);
								draw.repaint();
							}

						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						finished(2);
					}
				} else {
					turn = 1;
					try {
						Board temp = agent2.play(curBoard, 2);
						if (temp != null) {
							curBoard = temp;
							draw.setNodes(curBoard.verticiesList);
							draw.repaint();
						}
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finished(1);
				}

			}
		});
	}

	private void createDialog() {
		JDialog d = new JDialog(frame, "player 1");
		d.setSize(300, 200);
		d.setLocationRelativeTo(null);
		d.setLayout(null);
		JLabel l1 = new JLabel("Bonus vertex");
		l1.setBounds(10, 10, 100, 30);
		JLabel l2 = new JLabel("Attack vertex");
		l2.setBounds(10, 50, 100, 30);
		JLabel l3 = new JLabel("Attacked vertex");
		l3.setBounds(10, 90, 100, 30);

		JTextField b_ield = new JTextField();
		b_ield.setBounds(110, 10, 80, 30);
		JTextField v1_field = new JTextField();
		v1_field.setBounds(110, 50, 80, 30);
		JTextField v2_field = new JTextField();
		v2_field.setBounds(110, 90, 80, 30);

		d.add(l1);
		d.add(b_ield);
		d.add(l2);
		d.add(v1_field);
		d.add(l3);
		d.add(v2_field);
		JButton play = new JButton("ok");
		play.setBounds(220, 130, 60, 30);
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int b = Integer.valueOf(b_ield.getText());
				int ver1 = Integer.valueOf(v1_field.getText());
				int ver2 = Integer.valueOf(v2_field.getText());
				bonus_ver = curBoard.verticiesList[b];
				v1 = curBoard.verticiesList[ver1];
				v2 = curBoard.verticiesList[ver2];
				// System.out.println("bonus:"+bonus_ver.get_id()+"
				// v1"+v1.get_id()+" v2:"+v2.get_id());
				ArrayList<Integer> captured_partitions = curBoard.get_partions(1);
				System.out.println("player 2:"+curBoard.player_list[1].get_capturedVerticies());
				int bonus = 0;
				for (int i = 0; i < captured_partitions.size(); i++) {
					bonus += curBoard.PartitionsList[captured_partitions.get(i)].get_value();
				}
				System.out.println("par:"+bonus);
				if (curBoard.player_list[1].getBonus()) {
					bonus += 2;
					curBoard.player_list[1].set_bounce(false);
				}
				bonus_ver.set_NumberOfArmy(bonus_ver.get_NumberOfArmy() + bonus);
				System.out.println("Armis@"+bonus_ver.get_NumberOfArmy());
				if (curBoard.check_attack(v1, v2)) {
					curBoard.attack(v1, v2);
					draw.repaint();
				}else{
					turn = 1;
					JDialog d2 = new JDialog(frame, "player 1");
					d2.setSize(300, 200);
					d2.setLocationRelativeTo(null);
					d2.setLayout(null);
					JLabel error = new JLabel("can't attack");
					error.setBounds(20,20,100,50);
					d2.add(error);
					d2.setVisible(true);
				}
				d.setVisible(false);
				finished(2);

			}
		});
		d.add(play);
		d.setVisible(true);
	}

	private boolean finished(int E_id) {
		int p_id = E_id == 2 ? 1 : 2;
		if (curBoard.player_list[E_id].get_capturedVerticies().isEmpty()) {
			Dialog d2 = new Dialog(frame);
			d2.setLocationRelativeTo(null);
			d2.setSize(200, 200);
			d2.setLayout(null);
			JLabel win = new JLabel("Player:" + p_id + " won");
			JButton btn = new JButton("OK");
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					d2.setVisible(false);
				}
			});
			win.setBounds(50, 50, 100, 30);
			btn.setBounds(70, 100, 60, 30);
			d2.add(win);
			d2.add(btn);
			d2.setVisible(true);
			return true;
		}
		return false;
	}
}
