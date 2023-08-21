package side1;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

public class UserInfo {

	private JFrame frame;
	static Session session;
	private SideMain mainFrame;
	private JLabel lblOrderDate;
	private JLabel lblAppName;
	private JLabel lblPrice;
	private JLabel lblQuantity;
	private JLabel lblOrderNumber;



	public UserInfo() {
		initialize();
	}

	
	private void initialize() {
		session = Session.getInstance();
		
		
		
	
		frame = new JFrame();
		frame.setBounds(100, 100, 362, 652);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lableName = new JLabel(session.getDto().getName());
		lableName.setBounds(105, 33, 195, 93);
		frame.getContentPane().add(lableName);

		JLabel labelphone = new JLabel(session.getDto().getPhone());
		labelphone.setBounds(105, 183, 171, 50);
		frame.getContentPane().add(labelphone);

		JLabel labelEmail = new JLabel(session.getDto().getEmail());
		labelEmail.setBounds(105, 121, 171, 36);
		frame.getContentPane().add(labelEmail);

		JButton btnNewButton = new JButton("개인정보 변경");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserUpdate update = new UserUpdate();
				update.show();
				frame.dispose();

			}
		});
		btnNewButton.setBounds(193, 281, 124, 23);
		frame.getContentPane().add(btnNewButton);



		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setBounds(12, 72, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("이메일");
		lblNewLabel_1.setBounds(12, 132, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("연락처");
		lblNewLabel_2.setBounds(12, 201, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("권한");
		lblNewLabel_3.setBounds(12, 245, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel(session.getGrade());
		lblNewLabel_4.setBounds(105, 245, 105, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(SystemColor.desktop);
		separator.setBounds(12, 376, 322, 15);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel_5 = new JLabel("최근 주문 내역");
		lblNewLabel_5.setBounds(22, 401, 147, 23);
		frame.getContentPane().add(lblNewLabel_5);

		
				
		lblOrderDate = new JLabel("주문날짜 들어가는곳");
		lblOrderDate.setBounds(22, 461, 136, 23);
		frame.getContentPane().add(lblOrderDate);
		
		lblAppName = new JLabel("제조사 + 물건명 + 물건코드");
		lblAppName.setBounds(22, 494, 278, 31);
		frame.getContentPane().add(lblAppName);
		
		lblPrice = new JLabel("주문금액(총합)");
		lblPrice.setBounds(83, 535, 147, 15);
		frame.getContentPane().add(lblPrice);
		
		lblQuantity = new JLabel("주문갯수");
		lblQuantity.setBounds(22, 535, 57, 15);
		frame.getContentPane().add(lblQuantity);
		
		lblOrderNumber = new JLabel("주문번호");
		lblOrderNumber.setBounds(22, 436, 85, 15);
		frame.getContentPane().add(lblOrderNumber);
		
		JButton btnNewButton_1 = new JButton("주문내역 전체보기 >");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrdersList orderlist = new OrdersList();
				orderlist.show();
			}
		});
		btnNewButton_1.setBounds(0, 590, 346, 23);
		frame.getContentPane().add(btnNewButton_1);
		
	


	}


	public void show() {
		showLabelRecord(session);
		frame.setVisible(true);
	}
	public void showLabelRecord(Session session) {
		//로직문제 지금 sql문을 새로가져오는게 안되는것같음
		
		PurchaseDAO dao = PurchaseDAOImple.getInstance();
		ArrayList<PurchaseDTO> list = dao.purchaseRecord(session.getDto().getMemberID());
		if(!list.isEmpty()) {
			int size = list.size();
			PurchaseDTO dto = list.get(size-1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(dto.getOrderDate());
			lblOrderDate.setText(date);
			lblAppName.setText(dto.getApMfr() + " " + dto.getApName() + " " +dto.getApID());
			lblPrice.setText(String.valueOf(dto.getOrderPrice()) + "원");
			lblQuantity.setText(String.valueOf(dto.getOrderQunatity()));
			
		}else {
			lblOrderDate.setVisible(false);
			lblAppName.setVisible(false);
			lblPrice.setVisible(false);
			lblQuantity.setVisible(false);
			lblOrderNumber.setVisible(false);
		}
	}//end showLabelRecord
	
	
}