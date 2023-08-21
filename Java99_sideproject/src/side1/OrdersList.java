package side1;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSplitPane;

public class OrdersList {

	private JFrame frame;
	private int y;


	public OrdersList() {
		initialize();
	}


	private void initialize() {
	
		frame = new JFrame();
		frame.setBounds(100, 100, 378, 582);//위치,위치,가로,세로
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("더보기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(22, 437, 289, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(20, 30, 359, 371);
		frame.getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(2, 30, 359, 371);
		frame.getContentPane().add(scrollPane);
	
		
		JLabel lblNewLabel = new JLabel("주문 내역");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel.setBounds(12, 10, 161, 18);
		frame.getContentPane().add(lblNewLabel);
	}
	
	public void show() {
		frame.setVisible(true);
	}
}
