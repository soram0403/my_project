package com.yedam.cat;

import com.yedam.common.DAO;

public class CatDAO extends DAO {
	private static CatDAO catDAO = null;

	private CatDAO() {
	}

	public static CatDAO getInstance() {
		return catDAO == null ? catDAO = new CatDAO() : catDAO;
	}

	// 등록
	public int registerCat(Cat cat) {
		int result = 0;
		try {
			conn();
			String sql = "insert into cat (member_id, cat_name, cat_character) values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cat.getMemberId());
			pstmt.setString(2, cat.getCatName());
			pstmt.setString(3, cat.getCatCharacter());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 수정
	public int updateCat(Cat cat) {
		int result = 0;
		try {
			conn();
			String sql = "update cat set special_note = ? ,states = ? where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cat.getSpecialNote());
			pstmt.setString(2, cat.getStates());
			pstmt.setString(3, cat.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}
	
	// 삭제
	public int deleteCat(String memberId) {
		int result = 0;
		try {
			conn();
			String sql = "delete cat where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}
}
