package side1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleDriver;

public class PurchaseDAOImple implements PurchaseDAO {

	private static final String TABLE_NAME = "PURCHASE";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "side2";
	private static final String PW = "side22";

	private static final String COL_ORDER_NUMBER = "ORDER_NUMBER";
	private static final String COL_ORDER_NUMBER_SQE = "SEQ1.NEXTVAL";
	private static final String COL_SESSION_ID = "MEMBER_ID";
	private static final String COL_APID = "AP_ID";
	private static final String COL_AP_NAME = "AP_NAME";
	private static final String COL_AP_MFR = "AP_MFR";
	private static final String COL_ORDER_QUANTITY = "ORDER_QUANTITY";
	private static final String COL_ORDER_PRICE = "ORDER_PRICE";
	private static final String COL_DATE = "ORDER_DATE";

	private static final String purchaseSql = "INSERT INTO " + TABLE_NAME + " (" + COL_ORDER_NUMBER + ", "
			+ COL_SESSION_ID + ", " + COL_APID + ", " + COL_ORDER_QUANTITY + ", " + COL_ORDER_PRICE + ")" + " VALUES ("
			+ COL_ORDER_NUMBER_SQE + ", ?, ?, ?, ?)";

	// 나중에 수정하기
	private static final String purchaseSelect = "SELECT p." + COL_DATE + ", a." + COL_APID + ", a." + COL_AP_NAME
			+ ", a." + COL_AP_MFR + ", p." + COL_ORDER_QUANTITY + ", p." + COL_ORDER_PRICE + ", p." + COL_ORDER_NUMBER
			+ " FROM APPLIANCE a join " + TABLE_NAME + " p" + " ON a." + COL_APID + " = p." + COL_APID + " WHERE p."
			+ COL_SESSION_ID + " = ?";

	private static PurchaseDAOImple instance = null;

	public static PurchaseDAOImple getInstance() {
		if (instance == null) {
			instance = new PurchaseDAOImple();
		}
		return instance;
	}

	private PurchaseDAOImple() {

	}

	@Override
	public int purchase(PurchaseDTO dto) {
		int result = -1;
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(purchaseSql);
			// 세션id,제품id,주문수량,총합가격
			pstmt.setString(1, dto.getMemberID());
			System.out.println(dto.getMemberID());
			pstmt.setString(2, dto.getApID());
			System.out.println(dto.getApID());
			pstmt.setInt(3, dto.getOrderQunatity());
			System.out.println(dto.getOrderQunatity());
			pstmt.setLong(4, dto.getOrderPrice());
			System.out.println(dto.getOrderPrice());
			System.out.println("sql문 확인 : " + purchaseSql);
			result = pstmt.executeUpdate();
			System.out.println("구매버튼 실행 sql이후");
			pstmt.close();
			conn.close();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ArrayList<PurchaseDTO> purchaseRecord(String id) {
		ArrayList<PurchaseDTO> list = new ArrayList<>();

		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(purchaseSelect);
			System.out.println("sql문 확인 : " + purchaseSelect);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				PurchaseDTO dto = new PurchaseDTO();
				dto.setOrderNumber(rs.getInt(COL_ORDER_NUMBER));
				System.out.println("주문번호 : " + rs.getInt(COL_ORDER_NUMBER) + " 제품 이름 : " + rs.getString(COL_AP_NAME));
				dto.setOrderDate(rs.getDate(COL_DATE));
				dto.setApID(rs.getString(COL_APID));
				dto.setApName(rs.getString(COL_AP_NAME));
				dto.setApMfr(rs.getString(COL_AP_MFR));
				dto.setOrderQunatity(rs.getInt(COL_ORDER_QUANTITY));
				dto.setOrderPrice(rs.getInt(COL_ORDER_PRICE));
				dto.setMemberID(id);
				list.add(dto);

			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
		}

		return list;
	}

}