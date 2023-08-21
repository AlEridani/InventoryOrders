package side1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class AppInsert {

	private JFrame frame;
	private JTextField textApId;
	private JTextField textApName;
	private JTextField textApPrice;
	private JTextField textApMfr;
	private JTextField textStock;
	
	


	public AppInsert() {

		initialize();
	}

	public AppInsert(ApplianceDTO dto) {
		initialize2(dto);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 499, 468);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("상품번호");
		lblNewLabel.setBounds(51, 48, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("상품이름");
		lblNewLabel_1.setBounds(51, 112, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("상품가격");
		lblNewLabel_2.setBounds(51, 161, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("제조사");
		lblNewLabel_3.setBounds(51, 212, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("재고");
		lblNewLabel_4.setBounds(51, 258, 57, 15);
		frame.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("상품 등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApInsert();

			}
		});
		btnNewButton.setBounds(257, 381, 97, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("취소");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(374, 381, 97, 23);
		frame.getContentPane().add(btnNewButton_1);

		textApId = new JTextField();
		textApId.setBounds(120, 45, 250, 21);
		frame.getContentPane().add(textApId);
		textApId.setColumns(10);

		textApName = new JTextField();
		textApName.setColumns(10);
		textApName.setBounds(120, 109, 250, 21);
		frame.getContentPane().add(textApName);

		textApPrice = new JTextField();
		textApPrice.setColumns(10);
		textApPrice.setBounds(120, 158, 250, 21);
		frame.getContentPane().add(textApPrice);

		textApMfr = new JTextField();
		textApMfr.setColumns(10);
		textApMfr.setBounds(120, 209, 250, 21);
		frame.getContentPane().add(textApMfr);

		textStock = new JTextField();
		textStock.setColumns(10);
		textStock.setBounds(120, 255, 82, 21);
		frame.getContentPane().add(textStock);
	}

	private void initialize2(ApplianceDTO dto) {
		frame = new JFrame();
		frame.setBounds(100, 100, 499, 468);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("상품번호");
		lblNewLabel.setBounds(51, 48, 57, 15);
		frame.getContentPane().add(lblNewLabel);


		JLabel lblNewLabel_1 = new JLabel("상품이름");
		lblNewLabel_1.setBounds(51, 112, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("상품가격");
		lblNewLabel_2.setBounds(51, 161, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("제조사");
		lblNewLabel_3.setBounds(51, 212, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("재고");
		lblNewLabel_4.setBounds(51, 258, 57, 15);
		frame.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("상품 수정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appUpdate();

			}
		});
		btnNewButton.setBounds(257, 381, 97, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("취소");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(374, 381, 97, 23);
		frame.getContentPane().add(btnNewButton_1);

		textApId = new JTextField(dto.getApID());
		textApId.setBounds(120, 45, 250, 21);
		frame.getContentPane().add(textApId);
		textApId.setColumns(10);
		textApId.setEnabled(false);

		textApName = new JTextField(dto.getApName());
		textApName.setColumns(10);
		textApName.setBounds(120, 109, 250, 21);
		frame.getContentPane().add(textApName);

		textApPrice = new JTextField(String.valueOf(dto.getApPrice()));
		textApPrice.setColumns(10);
		textApPrice.setBounds(120, 158, 250, 21);
		frame.getContentPane().add(textApPrice);

		textApMfr = new JTextField(dto.getApMfr());
		textApMfr.setColumns(10);
		textApMfr.setBounds(120, 209, 250, 21);
		frame.getContentPane().add(textApMfr);

		textStock = new JTextField(String.valueOf(dto.getApStock()));
		textStock.setColumns(10);
		textStock.setBounds(120, 255, 82, 21);
		frame.getContentPane().add(textStock);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void ApInsert() {
		int result;
		String apID = textApId.getText();
		String apName = textApName.getText();
		String apPrice = textApPrice.getText();
		String apMfr = textApMfr.getText();
		String apStock = textStock.getText();

		int price = stringToInteger(apPrice);
		int stock = stringToInteger(apStock);

		if (price == -1 || stock == -1) {
			System.out.println("가격 재고 잘못입력");
			JOptionPane.showMessageDialog(null, "재고입력이 잘못되었습니다");
		} else {
			ApplianceDTO dto = new ApplianceDTO(apID, apName, price, apMfr, stock);
			ApplianceDAO dao = ApplianceDAOImple.getInstance();
			result = dao.appInsert(dto);
			if (result == -1) {
				JOptionPane.showMessageDialog(null, "등록실패");

			} else {
				JOptionPane.showMessageDialog(null, "등록성공");
				frame.dispose();
			}
		}

	}//end ApInsert

	public int stringToInteger(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isDigit(ch)) {
				int convertInt = Integer.parseInt(str);
				if (convertInt >= 0) {
					return convertInt;
				}
			}

		}
		return -1;

	}//end StringToInteger


	public void appUpdate() {
		int result;
		String apId = textApId.getText();
		String apName = textApName.getText();
		String apPrice = textApPrice.getText();
		String apMfr = textApMfr.getText();
		String apStock = textStock.getText();

		int price = stringToInteger(apPrice);
		int stock = stringToInteger(apStock);


		if (price == -1 || stock == -1) {
			System.out.println("가격 재고 잘못입력");
			JOptionPane.showMessageDialog(null, "재고입력이 잘못되었습니다");
		} else {
			ApplianceDTO dto = new ApplianceDTO(apId, apName, price, apMfr, stock);
			ApplianceDAO dao = ApplianceDAOImple.getInstance();
			result = dao.appUpdate(dto);
			if (result == -1) {
				JOptionPane.showMessageDialog(null, "수정 실패");
			} else {
				JOptionPane.showMessageDialog(null, "수정 성공");

				frame.dispose();
			}
		}

		ApplianceDTO dto = new ApplianceDTO(apId, apName, price, apMfr, stock);
		ApplianceDAO dao = ApplianceDAOImple.getInstance();
		dao.appUpdate(dto);



	}//end appUpdate

	  public void addFrameCloseListener(WindowListener listener) {
	        frame.addWindowListener(listener);
	    }//end addFrameCloseListener

}