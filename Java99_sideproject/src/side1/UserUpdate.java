package side1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class UserUpdate {

	private JFrame frame;
	private JTextField textEmail;
	private JTextField textPhone;
	private JPasswordField textPw;
	private MemberDAO dao;
	private Session session;
	private JTextField textName;


	public UserUpdate() {
		initialize();
	}

	private void initialize() {
		session = Session.getInstance();

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 426);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//커밋테스
		JLabel lblNewLabel = new JLabel("비밀번호");
		lblNewLabel.setBounds(36, 54, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("이메일");
		lblNewLabel_1.setBounds(36, 159, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("연락처");
		lblNewLabel_2.setBounds(36, 212, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);

		textEmail = new JTextField();
		textEmail.setBounds(105, 156, 247, 21);
		frame.getContentPane().add(textEmail);
		textEmail.setColumns(10);

		textPhone = new JTextField();
		textPhone.setBounds(105, 209, 247, 21);
		frame.getContentPane().add(textPhone);
		textPhone.setColumns(10);

		textPw = new JPasswordField();
		textPw.setBounds(105, 51, 247, 21);
		frame.getContentPane().add(textPw);

		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
				frame.dispose();
			}
		});
		btnUpdate.setBounds(105, 254, 97, 23);
		frame.getContentPane().add(btnUpdate);

		JButton btnNewButton_1 = new JButton("취소");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			frame.dispose();
			UserInfo info = new UserInfo();
			info.show();
			}
		});
		btnNewButton_1.setBounds(255, 254, 97, 23);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton = new JButton("회원탈퇴");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = delete();
				if(result == -1) {
					System.out.println("삭제실패");
				}else {
					JOptionPane.showMessageDialog(null, "회원 탈퇴 완료되었습니다");
					session.setDto("비회원", "nonMember");
					frame.dispose();
				}



			}
		});
		btnNewButton.setBounds(308, 334, 97, 23);
		frame.getContentPane().add(btnNewButton);

		if(session.getGrade().equals("ADMIN")) {
			btnNewButton.setEnabled(false);
			btnNewButton.setVisible(false);
		}



		JLabel lblNewLabel_3 = new JLabel("이름");
		lblNewLabel_3.setBounds(36, 107, 57, 15);
		frame.getContentPane().add(lblNewLabel_3);

		textName = new JTextField();
		textName.setBounds(105, 104, 247, 21);
		frame.getContentPane().add(textName);
		textName.setColumns(10);
	}
	public void show() {
		frame.setVisible(true);
	}
	public void update() {
		//session에 이미 기존 모든 정보가 담겨있으니
		//빈칸인 경우는 기존정보를 넘기는 방식으로 세개를 넘기는것

		char[] pw = textPw.getPassword();
		String name = textName.getText();
		String email = textEmail.getText();
		String phone = textPhone.getText();

		dao = MemberDAOImple.getInstance();
		Signup sign = new Signup();


		if(sign.pwNull(pw)) {
			pw = session.getDto().getPw();
		}

		if(name.isBlank()){
			name = session.getDto().getName();
		}

		if(email.isBlank()) {
			email = session.getDto().getEmail();
		}	

		if(phone.isBlank()) {
			phone = session.getDto().getPhone();
		}
		MemberDTO dto = new MemberDTO(session.getDto().getMemberID(),pw,name,email,phone);

		dao.update(dto);

	}

	public int delete() {
		int result = -1;
		dao = MemberDAOImple.getInstance();
		System.out.println(session.getDto().getMemberID());
		String id = session.getDto().getMemberID();
		result = dao.delete(id);


		return result;
	}
}