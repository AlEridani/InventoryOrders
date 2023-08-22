package side1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class SideMain {

	private JFrame frame;
	private JTable inven;
	private JButton btnMyInfo;
	static Session session;
	static String grade;

	private JButton btnAdmin;
	private JLabel lblId;
	private JButton btnLogout;
	private JButton btnSignup;
	private JButton btnNewButton;
	private ArrayList<ApplianceDTO> appList;
	private DefaultTableModel tableModel;
	private String clickedID;
	private boolean userInfoToken = false;
	private boolean loginToken = false;

	

	
	private JButton btnAppInsert;
	private JTextField textSerch;

	

	public static void main(String[] args) {

		session = session.getInstance();
		session.setDto("비회원", "none");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SideMain window = new SideMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SideMain() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1196, 602);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnNewButton = new JButton("로그인");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginSession();

			}
		});
		btnNewButton.setBounds(960, 38, 97, 23);
		frame.getContentPane().add(btnNewButton);

		btnSignup = new JButton("회원가입");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Signup sign = new Signup();
				
				sign.show();
				
			}
		});
		btnSignup.setBounds(1075, 38, 97, 23);
		frame.getContentPane().add(btnSignup);

		appTableOutput();
		inven = new JTable(tableModel);
		inven.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = inven.getSelectedRow();
				int idColumnIndex = inven.getColumnModel().getColumnIndex("제품 ID");
				clickedID = inven.getModel().getValueAt(selectedRow, idColumnIndex).toString();
				System.out.println("클릭인덱스: " + clickedID);

				int count = e.getClickCount();// 더블클릭시
				if (count == 2 && isLoggedIn()) {
					product();
				}

			}
		});
		inven.setBackground(Color.LIGHT_GRAY);
		inven.setBounds(1, 50, 1022, 393);
		frame.getContentPane().add(inven);

		JScrollPane scrollPane = new JScrollPane(inven);
		scrollPane.setBounds(50, 85, 1076, 318);
		frame.getContentPane().add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		btnMyInfo = new JButton("내 정보");
		btnMyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					UserInfo userInfo = new UserInfo();
					userInfo.addFrameCloseListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							userInfoToken = false;
						}
					});
					
					if(!userInfoToken) {
					userInfo.show();
					userInfoToken = true;
					}
					
			}
		});
		btnMyInfo.setBounds(1075, 38, 97, 23);
		frame.getContentPane().add(btnMyInfo);
		////여기 해야됨
		btnAdmin = new JButton("관리자 페이지");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberList memberList = new MemberList();
				memberList.addFrameCloseListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						MemberDAO dao = MemberDAOImple.getInstance();
						session.setDto(dao.currentUserInfo(session.getDto().getMemberID()));
						refreshUI();
					}
				});
				memberList.show();
			}
		});
		btnAdmin.setBounds(688, 38, 113, 23);
		frame.getContentPane().add(btnAdmin);

		lblId = new JLabel("아이디 들어가는 자리");
		lblId.setHorizontalAlignment(JLabel.RIGHT);
		lblId.setBounds(919, 13, 168, 15);
		frame.getContentPane().add(lblId);

		btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.setDto("비회원", "none");
				session.getDto().setPw(null);
				session.getDto().setName(null);
				session.getDto().setEmail(null);
				session.getDto().setPhone(null);
				refreshUI();
				JOptionPane.showMessageDialog(null, "로그아웃 완료");
			}
		});
		btnLogout.setBounds(959, 38, 97, 23);
		frame.getContentPane().add(btnLogout);

		btnAppInsert = new JButton("물품관리");
		btnAppInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppManage appManage = new AppManage();
				appManage.addFrameCloseListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						appTableRefresh();
					}
				});
				appManage.show();

			}
		});
		btnAppInsert.setBounds(71, 447, 113, 60);
		frame.getContentPane().add(btnAppInsert);

		textSerch = new JTextField();
		textSerch.setBounds(298, 39, 162, 21);
		frame.getContentPane().add(textSerch);
		textSerch.setColumns(10);

		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				appSerch();

			}
		});
		btnNewButton_1.setBounds(506, 38, 97, 23);
		frame.getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("전자제품");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				appTableRefresh();
			}
		});
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 23));
		lblNewLabel.setBounds(50, 13, 199, 62);
		frame.getContentPane().add(lblNewLabel);
		btnAdmin.setVisible(false);

		refreshUI();

	}

	/**
	 * 로그인 상태를 확인하는 메서드 세션의 등급을 확인해서 멤버 등급이 비회원인 none이면 false 아니면 true
	 * 
	 */
	public boolean isLoggedIn() {

		if (session.getGrade().equals("none")) {
			return false;
		} else {
			return true;
		}

	}



	/**
	 * ui새로고침 멤버 등급별로 쓸수 있는것을 나눠둠
	 */
	public void refreshUI() {
		System.out.println("Refreshing UI");
		if (session.getGrade().equals("none")) {// 비회원
			btnMyInfo.setVisible(false);
			btnAdmin.setVisible(false);
			btnSignup.setVisible(true);
			btnNewButton.setVisible(true);
			btnAppInsert.setVisible(false);
			lblId.setVisible(false);
			btnLogout.setVisible(false);
		} else if (session.getGrade().equals("USER")) {// 회원
			lblId.setText(session.getDto().getMemberID() + "님");
			lblId.setVisible(true);
			btnMyInfo.setVisible(true);
			btnSignup.setVisible(false);
			btnLogout.setVisible(true);
			btnAdmin.setVisible(false);
			btnNewButton.setVisible(false);
			btnAppInsert.setVisible(false);
		} else if (session.getGrade().equals("ADMIN")) {// 관리자
			btnAdmin.setVisible(true);
			lblId.setText("관리자    " + session.getDto().getMemberID() + "님");
			lblId.setVisible(true);
			btnMyInfo.setVisible(true);
			btnSignup.setVisible(false);
			btnLogout.setVisible(true);
			btnNewButton.setVisible(false);
			btnAppInsert.setVisible(true);
		}
		System.out.println(session.getGrade());
		frame.revalidate();
		frame.repaint();
	}

	public void appTableOutput() {
		ApplianceDAO dao = ApplianceDAOImple.getInstance();
		appList = dao.select(); // 데이터 조회
		table();

	}

	/**
	 * 로그인창이 닫히면 메인클래스의 테이블을 새로고침하고 로그인 정보를 가져옴
	 */
	public void loginSession() {
		if (!loginToken) {
			Login login = new Login();
			login.addFrameCloseListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					session = Session.getInstance();
					refreshUI();
					loginToken = false;
				}
			});
			login.show();
			loginToken = true;
		}
	}

	public void product() {
		session = Session.getInstance();
		ApplianceDAO dao = ApplianceDAOImple.getInstance();
		ApplianceDTO dto = dao.appInfo(clickedID);
		if (dto.getApStock() > 0) {
			Products product = new Products(session, dto);
			product.addFrameCloseListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					session = Session.getInstance();
					appTableRefresh();

				}
			});
			product.show();
		} else {
			JOptionPane.showMessageDialog(null, "재고가 없습니다");
		}

	}

	public void appTableRefresh() {
		DefaultTableModel model = (DefaultTableModel) inven.getModel();
		model.setNumRows(0);
		appTableOutput();
		inven.setModel(tableModel);

	}// end refresh

	public void appSerch() {
		ApplianceDAO dao = ApplianceDAOImple.getInstance();
		appList = dao.serch("%" + textSerch.getText() + "%");
		table();

		DefaultTableModel model = (DefaultTableModel) inven.getModel();
		model.setNumRows(0);
		inven.setModel(tableModel);
	}
	
	
	public void table() {
		
		int size = appList.size();
		String[] header = { "제품 ID", "제품명", "가격", "제조사", "재고" };
		Object[][] data = new Object[size][header.length];
		for (int i = 0; i < size; i++) {
			data[i][0] = appList.get(i).getApID();
			System.out.println(appList.get(i).getApID());
			data[i][1] = appList.get(i).getApName();
			data[i][2] = appList.get(i).getApPrice();
			data[i][3] = appList.get(i).getApMfr();
			data[i][4] = appList.get(i).getApStock();

		}
		tableModel = new DefaultTableModel(data, header) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
	}
}// end main