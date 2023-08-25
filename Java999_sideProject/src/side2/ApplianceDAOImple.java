package side2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleDriver;

public class ApplianceDAOImple implements ApplianceDAO {


	private static final String TABLE_NAME = "APPLIANCE";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "side3";
	private static final String PW = "123";
	private static final String COL_ID = "AP_ID";
	private static final String COL_SQE = "SQE1.NEXTVAL";
	private static final String COL_NAME = "AP_NAME";
	private static final String COL_INFO = "AP_INFO";
	private static final String COL_MFR = "AP_MFR";
	private static final String COL_DELETED = "IS_DELETED";



	private static String appInsert = "INSERT INTO " + TABLE_NAME +
									  " VALUES ("
									  + COL_SQE + ",?,?,?,0)  RETURNING " + COL_SQE + " INTO ?";

	private static String appSelect = "SELECT * FROM " + TABLE_NAME + 
									  " WHERE " + COL_DELETED +" = 0";
	
	private static String appSerch = "SELECT * FROM " + TABLE_NAME 
								   + " WHERE " + COL_NAME + " LIKE ?"
								   + " OR " + COL_MFR + " LIKE ?" 
								   + " AND " + COL_DELETED + " = 0";

	private static String appInfo = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";

	
	private static String appSoftDelte = "UPDATE " + TABLE_NAME + " SET "
										 + COL_DELETED + " = 1 "
										 + "WHERE " + COL_ID + " = ?";

	private static String appUpdate = "UPDATE " + TABLE_NAME + " SET "
									  + COL_NAME + " = ?, "
									  + COL_MFR + " = ?, "
									  + "WHERE AP_ID = ?";
	//나중에 휴지통 기능 구현한다면 사용
	private static String appDelete = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";

	private static ApplianceDAOImple instance = null;

	private ApplianceDAOImple() {

	}

	public static ApplianceDAOImple getInstance() {
		if (instance == null) {
			instance = new ApplianceDAOImple();
		}
		return instance;
	}

	ArrayList<ApplianceDTO> list = new ArrayList<>();

	@Override
	public int appInsert(ApplianceDTO dto) {
		int result = -1;
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			 CallableStatement cstmt = conn.prepareCall(appInsert);
			System.out.println("실행 sql문 확인 : " + appInsert);
			
			String apName = dto.getApName().toUpperCase();
			String apMfr = dto.getApMfr().toUpperCase();
			System.out.println("apName 확인 " + apName);
			System.out.println("apMfr 확인 " + apMfr);
			System.out.println("ApInfo 확인 " + dto.getApInfo());
			cstmt.setString(1, apName);//전부 대문자로 바꿔서 넣었어야 했음
			cstmt.setString(2, apMfr);
			cstmt.setString(3, dto.getApInfo());
			
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.executeUpdate();
			
			result = cstmt.getInt(4);
			System.out.println(result);
			System.out.println("쿼리 실행 확인");
			cstmt.close();
			conn.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();

			
		}



		return result;
	}

	@Override
	public ArrayList<ApplianceDTO> select() {
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(appSelect);
			ResultSet rs = pstmt.executeQuery();
			System.out.println(appSelect);
			list = new ArrayList<>();
			while(rs.next()) {
				//ID,이름,가격,제조사,재고순
				ApplianceDTO dto = new ApplianceDTO();
				dto.setApID(rs.getInt(COL_ID));
				dto.setApName(rs.getString(COL_NAME));
				dto.setApMfr(rs.getString(COL_MFR));
				list.add(dto);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql문 에러 :" + appSelect);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("다른 무언가 에러");
		}

		return list;
	}//end select

	@Override
	public ArrayList<ApplianceDTO> serch(String apName) {

		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(appSerch);
			System.out.println("sql문 : " + appSerch);
			System.out.println("검색text : " + apName);
			pstmt.setString(1, apName);
			pstmt.setString(2, apName);
			ResultSet rs = pstmt.executeQuery();

			list = new ArrayList<>();
			while(rs.next()) {
				//ID,이름,가격,제조사,재고순
				ApplianceDTO dto = new ApplianceDTO();
				dto.setApID(rs.getInt(COL_ID));
				dto.setApName(rs.getString(COL_NAME));
				dto.setApMfr(rs.getString(COL_MFR));
				list.add(dto);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql문 에러 :" + appSelect);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("다른 무언가 에러");
		}

		return list;
	}//end serch



	@Override
	public int appUpdate(ApplianceDTO dto) {
		int result = -1;
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(appUpdate);
			//1.name 2.price 3.mfr 4.stock 5.id
			System.out.println("쿼리문 확인 : " + appUpdate);
			pstmt.setString(1, dto.getApName());
			pstmt.setString(2, dto.getApMfr());
			pstmt.setInt(3, dto.getApID());

			result = pstmt.executeUpdate();

			pstmt.close();
			conn.close();
			return result;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;
	}//and appUpdate

	@Override
	public int appDelete(String apId) {
		int result = -1;
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(appSoftDelte);
			System.out.println(appSoftDelte);
			pstmt.setString(1, apId);
			System.out.println("넘겨받은 문자열 확인 " + apId);
			result = pstmt.executeUpdate();


			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}//end appDelete

	@Override
	public ApplianceDTO appInfo(String apId) {
		ApplianceDTO dto = new ApplianceDTO();
		try {
			DriverManager.registerDriver(new OracleDriver());
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			PreparedStatement pstmt = conn.prepareStatement(appInfo);
			System.out.println("sql문 확인 : " + appInfo);
			System.out.println("매개변수 확인 : " +apId);
			pstmt.setString(1, apId);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {

				dto.setApID(rs.getInt(COL_ID));
				dto.setApName(rs.getString(COL_NAME));
				dto.setApMfr(rs.getString(COL_MFR));
			}

			rs.close();
			pstmt.close();
			conn.close();
			return dto;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql구문 에러");
		} 


		return null;
	}//end appInfo

}//end ApplianceDAOImple