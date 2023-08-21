package side1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Signup {

	private JFrame frame;
	private JTextField textId;
	private JTextField textName;
	private JTextField textEmail;
	private JTextField textPhone;
	private JPasswordField textPw;
	private MemberDTO dto;
	private MemberDAO dao;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public Signup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 441, 464);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("회원가입");
		lblNewLabel.setBounds(201, 20, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setBounds(43, 75, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("비밀번호");
		lblNewLabel_2.setBounds(43, 136, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("이름");
		lblNewLabel_3.setBounds(43, 194, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("이메일");
		lblNewLabel_4.setBounds(43, 254, 57, 15);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("연락처");
		lblNewLabel_5.setBounds(43, 315, 57, 15);
		frame.getContentPane().add(lblNewLabel_5);

		JButton btnNewButton = new JButton("등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sign();
			}
		});
		btnNewButton.setBounds(167, 373, 97, 23);
		frame.getContentPane().add(btnNewButton);

		textId = new JTextField();
		textId.setBounds(122, 72, 198, 21);
		frame.getContentPane().add(textId);
		textId.setColumns(10);

		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(122, 191, 198, 21);
		frame.getContentPane().add(textName);

		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(122, 251, 198, 21);
		frame.getContentPane().add(textEmail);

		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(122, 312, 198, 21);
		frame.getContentPane().add(textPhone);

		textPw = new JPasswordField();
		textPw.setBounds(122, 133, 198, 21);
		frame.getContentPane().add(textPw);
	}


	public void show() {
		frame.setVisible(true);
	}

	public void sign() {
		String id = textId.getText();
		char[] pw = textPw.getPassword();
		String name = textName.getText();
		String phone = textEmail.getText();
		String email = textPhone.getText();


		dao = MemberDAOImple.getInstance();

		dto = new MemberDTO(id,pw,name,phone,email);
		int result = dao.insert(dto);
		if(id.isBlank()|| pwNull(pw) ||name.isBlank()||phone.isBlank()||email.isBlank()) {
			System.out.println("값이 모두 채워지지 않았음");

			JOptionPane.showMessageDialog(null, "빈칸이 없이 모두 입력해 주세요");
		}else if(result == -1) {
			JOptionPane.showMessageDialog(null, "아이디가 중복입니다");
		}else {
			JOptionPane.showMessageDialog(null, "회원가입 성공");
			frame.dispose();

			System.out.println(result);

		}



	}//end sign

	public boolean pwNull(char[] ch) {

		if(ch == null || ch.length == 0) {
			return true;
		}

		 for (char c : ch) {
		        if (!Character.isWhitespace(c)) {//공백문자 찾기
		            return false;  
		        }
		    }
		return true;
	}
}