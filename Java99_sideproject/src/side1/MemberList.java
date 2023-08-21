package side1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class MemberList {

	private JFrame frame;
	private JTextField textSerch;
	private JTable table;
	private DefaultTableModel tableModel;
	private MemberDAO dao;
	private ArrayList<MemberDTO> list;
	private String clickedID;

	public MemberList() {
		initialize();

	}

	private void initialize() {
		dao = MemberDAOImple.getInstance();
		frame = new JFrame();
		frame.setBounds(100, 100, 598, 554);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("회원 아이디 검색");
		lblNewLabel.setBounds(196, 36, 187, 15);
		frame.getContentPane().add(lblNewLabel);

		textSerch = new JTextField();
		textSerch.setBounds(315, 33, 116, 21);
		frame.getContentPane().add(textSerch);
		textSerch.setColumns(10);



		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 int selectedRow = table.getSelectedRow();
				 int idColumnIndex = table.getColumnModel().getColumnIndex("ID");
				 clickedID = table.getModel().getValueAt(selectedRow, idColumnIndex).toString();
				 System.out.println("클릭인덱스: " + clickedID);
			}
		});
		frame.getContentPane().add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 83, 436, 406);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scrollPane);
		frame.setVisible(true);

		JButton btnUserDelete = new JButton("멤버삭제");
		btnUserDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userDelete();
				reloadTable();
			}
		});
		btnUserDelete.setBounds(460, 228, 110, 51);
		frame.getContentPane().add(btnUserDelete);

		JButton btnChangeAdmin = new JButton("관리자 등록");
		btnChangeAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clickedID != null) {
				memderGradeChange();
				reloadTable();
				}else {
					System.out.println("클릭안됨");
				}
			}

		});
		btnChangeAdmin.setBounds(460, 338, 110, 51);
		frame.getContentPane().add(btnChangeAdmin);

		JButton btnUserDelete_1 = new JButton("멤버검색");
		btnUserDelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberSerch();
			}
		});
		btnUserDelete_1.setBounds(460, 18, 110, 51);
		frame.getContentPane().add(btnUserDelete_1);

		JButton btnUserDelete_2 = new JButton("전체멤버");
		btnUserDelete_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memberSelect();

			}
		});
		btnUserDelete_2.setBounds(460, 118, 110, 51);
		frame.getContentPane().add(btnUserDelete_2);

		JButton btnChangeUser = new JButton("관리자 삭제");
		btnChangeUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memderAdminToUser();
				reloadTable();
			}
		});
		btnChangeUser.setBounds(460, 438, 110, 51);
		frame.getContentPane().add(btnChangeUser);

	}




	public void memberSelect() {


		list = dao.select();

		int size = dao.select().size();
		String[] header = { "ID", "비밀번호", "이름", "EMAIL", "PHONE", "회원등급" };
		Object[][] data = new Object[size][header.length];
		for (int i = 0; i < size; i++) {
			data[i][0] = list.get(i).getMemberID();
			data[i][1] = list.get(i).getPw();// 암호 그대로 표시 안되게 뭉개기
			data[i][2] = list.get(i).getName();
			data[i][3] = list.get(i).getEmail();
			data[i][4] = list.get(i).getPhone();
			data[i][5] = list.get(i).getMemberGrade();

		}
		DefaultTableModel model = new DefaultTableModel(data, header){
			@Override
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		table.setModel(model);

	}


	public void show() {
		frame.setVisible(true);
	}

	public void memberSerch() {

		String serchId = "%" + textSerch.getText() + "%";
		list = dao.serch(serchId);
		int size = list.size();
		String[] header = { "ID", "비밀번호", "이름", "EMAIL", "PHONE", "회원등급" };
		Object[][] data = new Object[size][header.length];
		for (int i = 0; i < size; i++) {
			data[i][0] = list.get(i).getMemberID();
			data[i][1] = list.get(i).getPw();// 암호 그대로 표시 안되게 뭉개기
			data[i][2] = list.get(i).getName();
			data[i][3] = list.get(i).getEmail();
			data[i][4] = list.get(i).getPhone();
			data[i][5] = list.get(i).getMemberGrade();

		}

		DefaultTableModel model = new DefaultTableModel(data, header){
			@Override
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		table.setModel(model);
	}
	private void reloadTable() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setRowCount(0);
		memberSelect();


	}

	public void memderGradeChange() {
		MemberDTO dto = dao.session(clickedID);//id를 검색해서 dto에 넣는다
		dao.memberGrade(dto);//dto의 멤버등급을 변경한다
	}

	public void memderAdminToUser() {
		MemberDTO dto = dao.session(clickedID);//id를 검색해서 dto에 넣는다
		dao.memberChangeAdminToUser(dto);//dto의 멤버등급을 변경한다
	}

	public void userDelete() {
		MemberDTO dto = dao.session(clickedID);
		if(!dto.getMemberGrade().equals("ADMIN")) {
		dao.delete(dto.getMemberID());
		}else {
			JOptionPane.showMessageDialog(null, "관리자 계정은 삭제할 수 없습니다");
		}
	}

}