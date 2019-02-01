package Board;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Agents.Agent;

import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JButton;

public class StartFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame window = new StartFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String options[] = {"Human","completely passive","aggressive","nearly pacifist","greedy agent","A* agent","real-time A*"};
		String options2[] = {"completely passive","aggressive","nearly pacifist","greedy agent","A* agent","real-time A*"};
		frame = new JFrame();
		frame.setBounds(0, 0, 1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblPlayer = new JLabel("Player 1:");
		lblPlayer.setForeground(new Color(0, 0, 0));
		lblPlayer.setBackground(new Color(255, 255, 255));
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblPlayer.setBounds(120, 122, 220, 20);
		frame.getContentPane().add(lblPlayer);
		
		JLabel lblPlayer_1 = new JLabel("Player 2:");
		lblPlayer_1.setForeground(new Color(0, 0, 0));
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblPlayer_1.setBounds(765, 122, 220, 50);
		frame.getContentPane().add(lblPlayer_1);
		
		JComboBox<String> comboBox = new JComboBox<String>(options);
		comboBox.setBackground(new Color(230, 230, 250));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(120, 180, 220, 50);
		frame.getContentPane().add(comboBox);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>(options2);
		comboBox_1.setBackground(new Color(230, 230, 250));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox_1.setSelectedIndex(0);
		comboBox_1.setBounds(765, 180, 220, 50);
		frame.getContentPane().add(comboBox_1);
	
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		btnStart.setBounds(876, 589, 130, 46);
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Board b = new Board();
				Agent agent_1;
				Agent agent_2;
				File f1 = new File("Files/graph.txt");
				b.readGraph(f1);
				File f2 = new File("Files/player.txt");
				b.setPlayers(f2);
				String agent1 = (String) comboBox.getSelectedItem();
				String agent2 = (String) comboBox_1.getSelectedItem();
				AgentMaker maker = new AgentMaker();
				//create agents to play against each other 
				if(agent1.equals("Human"))
					agent_1 = null;
				else
					agent_1 = maker.create(agent1);
				agent_2 = maker.create(agent2);
				//System.out.println("agent 1:"+agent1+" agent 2:"+agent2);
				MainFrame main_frame = new MainFrame(b,agent_1,agent_2);
			}
		});
		frame.getContentPane().add(btnStart);	
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("Images/risk1.jpg"));
		label.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		frame.getContentPane().add(label);
		frame.setResizable(false);
		
	}
}
