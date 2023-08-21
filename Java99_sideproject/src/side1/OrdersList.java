package side1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class OrdersList {

	private JFrame frame;
	private int x = 20;
	private int y = 30;
	private JPanel panel;

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
				Orders();
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

	public void Orders() {
		
		int x = 10;
		int y = 10;
		int width = 200;
		int height = 30;
		int gap = 5; 
		
		
		Session session = Session.getInstance();
		PurchaseDAO dao = PurchaseDAOImple.getInstance();
		ArrayList<PurchaseDTO> list = dao.purchaseRecord(session.getDto().getMemberID());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		int size = list.size();
		JLabel[] lblName = new JLabel[size];
		JLabel[] lblOrderDate = new JLabel[size];
		JLabel[] lblPrice = new JLabel[size];
		JLabel[] lblQuantity = new JLabel[size];
		JLabel[] lblOrderNumber = new JLabel[size];
		for (int i = 0; i < size; i++) {
			
			lblOrderNumber[i] = new JLabel(String.valueOf(list.get(i).getOrderNumber()));
			panel.add(lblOrderNumber[i]);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(list.get(i).getOrderDate());
			lblOrderDate[i] = new JLabel(date);
			panel.add(lblOrderDate[i]);
			
			lblName[i] = new JLabel(list.get(i).getApMfr() + " " +list.get(i).getApName() );
			panel.add(lblName[i]);
			
			lblPrice[i] = new JLabel(String.valueOf(list.get(i).getOrderPrice()));
			panel.add(lblPrice[i]);
			lblQuantity[i] = new JLabel(String.valueOf(list.get(i).getOrderQunatity()));
			panel.add(lblQuantity[i]);
			

			lblName[i].setVisible(true);
			
			

			  // 다음 라벨의 y 좌표를 업데이트
			lblOrderDate[i].setBounds(x, y, width, height);
			y += (height + gap);
			lblName[i].setBounds(x, y, width, height);
			y += (height + gap);  // 다음 라벨의 y 좌표를 업데이트

			lblPrice[i].setBounds(x, y, width, height);
			y += (height + gap);  // 다음 라벨의 y 좌표를 업데이트

			lblQuantity[i].setBounds(x, y, width, height);
			y += (height + gap);  // 다음 라벨의 y 좌표를 업데이트

			lblOrderNumber[i].setBounds(x, y, width, height);

		
		}
		 panel.revalidate();
		 panel.repaint();
	}

}// end OrderList
