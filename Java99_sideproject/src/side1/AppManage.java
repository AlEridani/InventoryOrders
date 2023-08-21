package side1;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class AppManage {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel tableModel;
	private ArrayList<ApplianceDTO> list;
	private ApplianceDAO dao;
	private ApplianceDTO dto;
	private JTextField textSerch;
	private String clickedID;
	private static AppManage instance = null;

	public static AppManage getInstance() {
		if (instance == null) {
			instance = new AppManage();
		}
		return instance;
	}


	public AppManage() {
		initialize();
	}

	private void initialize() {
		dao = ApplianceDAOImple.getInstance();
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 600);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnAppInsert = new JButton("상품등록");
	     btnAppInsert.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                AppInsert insert = new AppInsert();
	                insert.addFrameCloseListener(new WindowAdapter() {
	                    @Override
	                    public void windowClosed(WindowEvent e) {
	                        tableRefresh(); 
	                    }
	                });
	                insert.show();
	            }
	        });
		btnAppInsert.setBounds(68, 467, 97, 52);
		frame.getContentPane().add(btnAppInsert);


		tableOutput();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 int selectedRow = table.getSelectedRow();
				 int idColumnIndex = table.getColumnModel().getColumnIndex("제품 ID");
				 clickedID = table.getModel().getValueAt(selectedRow, idColumnIndex).toString();
				 System.out.println("클릭인덱스: " + clickedID);

				 int count = e.getClickCount();//더블클릭시
				 if(count == 2) {
					 appUpdate();
				 }
			}
		});
		table.setBounds(46, 42, 711, 384);
		frame.getContentPane().add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(49, 43, 708, 383);
		frame.getContentPane().add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		textSerch = new JTextField();
		textSerch.setBounds(431, 483, 194, 21);
		frame.getContentPane().add(textSerch);
		textSerch.setColumns(10);

		JButton btnNewButton_1 = new JButton("상품검색");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appSerchByName();
			}
		});
		btnNewButton_1.setBounds(660, 467, 97, 52);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2_1 = new JButton("상품삭제");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appDelete();
				tableRefresh();
			}
		});
		btnNewButton_2_1.setBounds(212, 467, 97, 52);
		frame.getContentPane().add(btnNewButton_2_1);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void appSerchByName() {
		String serchApName = "%" + textSerch.getText() + "%";
		list = dao.serch(serchApName);

		String[] header = { "제품 ID", "제품명", "가격", "제조사", "재고" };
		Object[][] data = new Object[list.size()][header.length];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i).getApID();
			data[i][1] = list.get(i).getApName();
			data[i][2] = list.get(i).getApPrice();
			data[i][3] = list.get(i).getApMfr();
			data[i][4] = list.get(i).getApStock();

		}

		// JTable 모델 업데이트
		DefaultTableModel model = new DefaultTableModel(data, header) {
			@Override
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};

		table.setModel(model);
	}

	public void appDelete() {
		if(!(clickedID == null)) {
			int result =dao.appDelete(clickedID);
			if(result == -1) {
				JOptionPane.showMessageDialog(null, "삭제 실패");
			}else {
				JOptionPane.showMessageDialog(null, "삭제 성공");
			}
		}else {
			System.out.println("아무것도 선택안됨");
		}
	}


	public void appUpdate() {
		dto = dao.appInfo(clickedID);

		AppInsert appInsert = new AppInsert(dto);
			appInsert.addFrameCloseListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	            	tableRefresh();
	            }
	        });
		appInsert.show();
	}//end appUpdate


	public void tableOutput() {
		list = dao.select(); // 데이터 조회

		// ID,이름,가격,제조사,재고순
		String[] header = { "제품 ID", "제품명", "가격", "제조사", "재고" };
		Object[][] data = new Object[list.size()][header.length];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i).getApID();
			data[i][1] = list.get(i).getApName();
			data[i][2] = list.get(i).getApPrice();
			data[i][3] = list.get(i).getApMfr();
			data[i][4] = list.get(i).getApStock();

		}
		tableModel = new DefaultTableModel(data,header) {
			@Override
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		table = new JTable(tableModel);
	}

	public void tableRefresh() {
		list = dao.select();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);

		String[] header = { "제품 ID", "제품명", "가격", "제조사", "재고" };
		Object[][] data = new Object[list.size()][header.length];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i).getApID();
			data[i][1] = list.get(i).getApName();
			data[i][2] = list.get(i).getApPrice();
			data[i][3] = list.get(i).getApMfr();
			data[i][4] = list.get(i).getApStock();

		}
		tableModel = new DefaultTableModel(data,header) {
			@Override
			public boolean isCellEditable (int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);
	}//end tableRefresh()
	
	  public void addFrameCloseListener(WindowListener listener) {
	        frame.addWindowListener(listener);
	    }//end addFrameCloseListener

}//end AppManage