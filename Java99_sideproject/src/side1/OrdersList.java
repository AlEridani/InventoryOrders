package side1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

public class OrdersList {

	private JFrame frame;
	private int x = 20;
	private int y = 30;
	private JPanel panel;
	private int count = 0;

	public OrdersList() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 378, 582);// 위치,위치,가로,세로
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("더보기");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count++;
				Orders(count);
			}
		});
		btnNewButton.setBounds(22, 437, 289, 23);
		frame.getContentPane().add(btnNewButton);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);

		int y = 30;
		panel.setBounds(x, y, 359, 371);
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

	public void Orders(int itemsPerPage) {
		
		
	
		
		Session session = Session.getInstance();
		PurchaseDAO dao = PurchaseDAOImple.getInstance();
		ArrayList<PurchaseDTO> list = dao.purchaseRecord(session.getDto().getMemberID());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		int x = 10;
		int y = 10;
		int width = 200;
		int height = 30;
		int gap = 5; 
		int separatorSpacing = 10;
		int size = list.size();
		
		int page = 3;
		int end = size  - (page-1) * itemsPerPage;
		int start = end - itemsPerPage;
		
		if(start < 0) {
			start = 0;
		}
		
		JLabel[] lblName = new JLabel[size];
		JLabel[] lblOrderDate = new JLabel[size];
	
		JLabel[] lblQuantity = new JLabel[size];
		JLabel[] lblOrderNumber = new JLabel[size];
		for (int i = end; i >= start; i--) {
			
			lblOrderNumber[i] = new JLabel("주문번호 : " + String.valueOf(list.get(i).getOrderNumber()));
			panel.add(lblOrderNumber[i]);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(list.get(i).getOrderDate());
			lblOrderDate[i] = new JLabel(date);
			panel.add(lblOrderDate[i]);
			
			lblName[i] = new JLabel(list.get(i).getApMfr() + " " +list.get(i).getApName());
			panel.add(lblName[i]);
			
			lblQuantity[i] = new JLabel(String.valueOf(list.get(i).getOrderQunatity()) + "개 " + list.get(i).getOrderPrice() + " 원");
			panel.add(lblQuantity[i]);
			
			
		
			
		    panel.add(Box.createVerticalStrut(separatorSpacing));
			JSeparator separator = new JSeparator();
	        separator.setBounds(x, y, width, 2); // separator의 높이를 2로 설정
	        panel.add(separator);
	        y += (2 + gap + separatorSpacing); 

	        panel.add(Box.createVerticalStrut(separatorSpacing));
		}//end for
		
		
		 panel.revalidate();
		 panel.repaint();
	}

}// end OrderList
