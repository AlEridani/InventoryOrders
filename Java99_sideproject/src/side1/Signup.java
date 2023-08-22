package side1;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Signup{

	private JFrame frame;
	private JFrame frame2;
	private JTextField textId;
	private JTextField textName;
	private JTextField textEmail;
	private JTextField textPhone;
	private JPasswordField textPw;
	private MemberDTO dto;
	private MemberDAO dao;
	private JRadioButton rbtnSignAccep;
	private JRadioButton rbtnSignDecl;
	private JRadioButton rbtnAccep;
	private JRadioButton rbtnDecl;
	private JButton btnNewButton_1;


	public Signup() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 927, 768);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("로그인 약관");
		textArea.setBounds(33, 65, 866, 232);
		frame.getContentPane().add(textArea);
		
		rbtnSignAccep = new JRadioButton("동의");
		rbtnSignAccep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbtnChecked();
			}
		});
		rbtnSignAccep.setBounds(672, 307, 77, 23);
		frame.getContentPane().add(rbtnSignAccep);
		
		rbtnSignDecl = new JRadioButton("거절");
		rbtnSignDecl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbtnChecked();
			}
		});
		rbtnSignDecl.setBounds(753, 303, 150, 31);
		rbtnSignDecl.setSelected(true);
		frame.getContentPane().add(rbtnSignDecl);
		
		ButtonGroup signGroup = new ButtonGroup();
		signGroup.add(rbtnSignAccep);
		signGroup.add(rbtnSignDecl);
		
		JLabel lblNewLabel_6 = new JLabel("회원가입");
		lblNewLabel_6.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_6.setBounds(387, 10, 199, 67);
		frame.getContentPane().add(lblNewLabel_6);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("개인정보 동의");
		textArea_1.setBounds(33, 357, 866, 232);
		frame.getContentPane().add(textArea_1);
		
		rbtnAccep = new JRadioButton("동의");
		rbtnAccep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbtnChecked();
			}
		});
		rbtnAccep.setBounds(672, 591, 61, 23);
		frame.getContentPane().add(rbtnAccep);
		
		rbtnDecl = new JRadioButton("거절");
		rbtnDecl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbtnChecked();
			}
		});
		rbtnDecl.setBounds(763, 591, 121, 23);
		rbtnDecl.setSelected(true);
		frame.getContentPane().add(rbtnDecl);
		
		ButtonGroup rbtnGroup = new ButtonGroup();
		rbtnGroup.add(rbtnAccep);
		rbtnGroup.add(rbtnDecl);
		
		btnNewButton_1 = new JButton("회원가입");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				signupFrame();
				
			}
		});
		btnNewButton_1.setBounds(672, 659, 181, 48);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.setEnabled(false);
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
			frame2.dispose();

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

	  public void addFrameCloseListener(WindowListener listener) {
	        frame.addWindowListener(listener);
	    }//end addFrameCloseListener
	  
	  
	  public void signupFrame() {
		  	frame2 = new JFrame();
			frame2.setBounds(100, 100, 441, 464);
			frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame2.getContentPane().setLayout(null);
			frame2.setVisible(true);

			JLabel lblNewLabel = new JLabel("회원가입");
			lblNewLabel.setBounds(201, 20, 57, 15);
			frame2.getContentPane().add(lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel("아이디");
			lblNewLabel_1.setBounds(43, 75, 57, 15);
			frame2.getContentPane().add(lblNewLabel_1);

			JLabel lblNewLabel_2 = new JLabel("비밀번호");
			lblNewLabel_2.setBounds(43, 136, 57, 15);
			frame2.getContentPane().add(lblNewLabel_2);

			JLabel lblNewLabel_3 = new JLabel("이름");
			lblNewLabel_3.setBounds(43, 194, 57, 15);
			frame2.getContentPane().add(lblNewLabel_3);

			JLabel lblNewLabel_4 = new JLabel("이메일");
			lblNewLabel_4.setBounds(43, 254, 57, 15);
			frame2.getContentPane().add(lblNewLabel_4);

			JLabel lblNewLabel_5 = new JLabel("연락처");
			lblNewLabel_5.setBounds(43, 315, 57, 15);
			frame2.getContentPane().add(lblNewLabel_5);

			JButton btnNewButton = new JButton("등록");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sign();
					
				}
			});
			btnNewButton.setBounds(167, 373, 97, 23);
			frame2.getContentPane().add(btnNewButton);

			textId = new JTextField();
			textId.setBounds(122, 72, 198, 21);
			frame2.getContentPane().add(textId);
			textId.setColumns(10);

			textName = new JTextField();
			textName.setColumns(10);
			textName.setBounds(122, 191, 198, 21);
			frame2.getContentPane().add(textName);

			textEmail = new JTextField();
			textEmail.setColumns(10);
			textEmail.setBounds(122, 251, 198, 21);
			frame2.getContentPane().add(textEmail);

			textPhone = new JTextField();
			textPhone.setColumns(10);
			textPhone.setBounds(122, 312, 198, 21);
			frame2.getContentPane().add(textPhone);

			textPw = new JPasswordField();
			textPw.setBounds(122, 133, 198, 21);
			frame2.getContentPane().add(textPw);
			
	
	  }
	  
	  
	  public void rbtnChecked() {
			if(!rbtnSignAccep.isSelected() || !rbtnAccep.isSelected()) {
				btnNewButton_1.setEnabled(false);
			}else {
				btnNewButton_1.setEnabled(true);
			}
	  }
}