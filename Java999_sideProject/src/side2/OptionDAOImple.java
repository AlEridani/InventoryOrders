package side2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleDriver;

public class OptionDAOImple implements OptionDAO {
	
	private static OptionDAOImple instance = null;
	
	public OptionDAOImple() {}
	
	public static OptionDAOImple getInstance() {
		if (instance == null) {
			instance = new OptionDAOImple();
		}
		return instance;
	}
	
	
	
	private static final String TABLE_NAME = "APP_OPTION";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "side3";
	private static final String PW = "123";
	
	private static final String COL_ID = "OPTION_ID";
	private static final String COL_NAME = "OPTION_NAME";
	private static final String COL_PRICE = "PRICE";
	private static final String COL_STOCK = "STOCK";
	private static final String COL_AP_ID = "AP_ID";
	
	private static final String OPTION_INSERT = "INSERT INTO " + TABLE_NAME +
												" VALUES (?,?,?,?,?)";
	
	
	@Override
	public int insert(OptionDTO dto) {
		int result = -1;
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(OPTION_INSERT);
			//품목번호,제품이름,가격,재고,ap_id순
			pstmt.setString(1, dto.getOptionId());
			pstmt.setString(2, dto.getApName());
			pstmt.setInt(3, dto.getPrice());
			pstmt.setInt(4, dto.getStock());
			pstmt.setInt(5, dto.getApId());
			
			result = pstmt.executeUpdate();
			System.out.println("insert 쿼리문 이후");
			
			conn.close();
			pstmt.close();
			
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<OptionDTO> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OptionDTO output(String OptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(String optionId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
