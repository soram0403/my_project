package com.yedam.catMember;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;
import com.yedam.room.Room;

public class CatMemberDAO extends DAO {
	private static CatMemberDAO cmd = null;

	private CatMemberDAO() {
	}

	public static CatMemberDAO getInstance() {
		return cmd == null ? cmd = new CatMemberDAO() : cmd;
	}

//	2) 회원 조회 -> 2-1) 전체 간단 조회(주인이름, 고양이이름, 기간) | 2-2) 상세 조회(내용 전체 조회) | 2-3 사용자 후기 조회
//
//	3) 회원 등록 -> 3-1) 회원(집사) 등록(회원id,pw,이름,연락처,주소) | 3-2) 고양이 등록(회원id,고양이이름, 성격, hotel_date)
//
//	4) 회원 수정 -> 4-1) 회원(집사) 수정 -> 1.연락처 수정 | 2.주소 수정 | 3.상태 수정
//		        4-2) 고양이 수정 -> 1.states 수정 | 2.special_note(특이사항) 수정
//
//	5) 회원 삭제 -> onwer_id 삭제

	// 로그인
	public CatMember loginInfo(String memberId) {
		CatMember catMember = null;
		try {
			conn();
			String sql = "select * from catmember where member_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				catMember = new CatMember();
				catMember.setMemberId(rs.getString("member_id"));
				catMember.setMemberPw(rs.getString("member_pw"));
				catMember.setCatName(rs.getString("cat_name"));
				catMember.setMemberName(rs.getString("member_name"));
				catMember.setRole(rs.getString("role"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return catMember;
	}

	// 회원 전체 (간단) 조회 //(주인id, 주인이름, 고양이이름, 가입일) -- join
	public List<CatMember> getCatMember() {
		List<CatMember> list = new ArrayList<>();
		CatMember catmember = null;

		try {
			conn();
			String sql = "select m.member_id member_id, c.cat_name cat_name, m.member_name member_name,\r\n"
					+ "to_char(m.dates, 'YYYY\"년\" MM\"월\" DD\"일\"') dates, m.points points from catmember m join cat c on m.member_id = c.member_id\r\n"
					+ "where m.role = 1" + "order by m.member_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				catmember = new CatMember();

				catmember.setMemberId(rs.getString("member_id"));
				catmember.setMemberName(rs.getString("member_name"));
				catmember.setCatName(rs.getString("cat_name"));
				catmember.setDates(rs.getString("dates"));
				catmember.setPoints(rs.getInt("points"));

				list.add(catmember);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 회원(1명) 상세 조회 // id, 이름, 번호, 고양이이름, 고양이성격, 특이사항, 주소
	public List<CatMember> getDetailCatMember(String id) {
		List<CatMember> list = new ArrayList<>();
		CatMember catmember = null;

		try {
			conn();
			String sql = "select m.member_id member_id, m.member_name member_name, m.member_addr member_addr , m.cat_name cat_name,\r\n"
					+ "m.member_tel member_tel, c.cat_character cat_character, c.special_note special_note\r\n"
					+ "from catmember m, cat c\r\n"
					+ "where c.member_id = m.member_id and m.role = 1 and m.member_id= ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				catmember = new CatMember();

				catmember.setMemberId(rs.getString("member_id"));
				catmember.setMemberName(rs.getString("member_name"));
				catmember.setMemberAddr(rs.getString("member_addr"));
				catmember.setCatName(rs.getString("cat_name"));
				catmember.setMemberTel(rs.getString("member_tel"));
				catmember.setCatCharacter(rs.getString("cat_character"));
				catmember.setSpecialNote(rs.getString("special_note"));

				list.add(catmember);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 회원 등록 (id,pw,name,tel,addr)
	public int registerMember(CatMember catMember) {
		int result = 0;
		try {
			conn();
			String sql = "insert into catmember (member_id,member_pw,member_name,member_tel,member_addr,cat_name) values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, catMember.getMemberId());
			pstmt.setString(2, catMember.getMemberPw());
			pstmt.setString(3, catMember.getMemberName());
			pstmt.setString(4, catMember.getMemberTel());
			pstmt.setString(5, catMember.getMemberAddr());
			pstmt.setString(6, catMember.getCatName());

			result = pstmt.executeUpdate();
			if (result == 1) {
				String sql2 = "insert into cat (member_id, cat_name, cat_character) values(?,?,?)";
				pstmt = conn.prepareStatement(sql2);

				pstmt.setString(1, catMember.getMemberId());
				pstmt.setString(2, catMember.getCatName());
				pstmt.setString(3, catMember.getCatCharacter());
				result = pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}

	// 회원 수정
	// 연락처 수정
	public int updateTel(CatMember catmember) {
		int result = 0;
		try {
			conn();
			String sql = "update catmember set member_tel = ? where member_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, catmember.getMemberTel());
			pstmt.setString(2, catmember.getMemberId());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 주소 수정
	public int updateAddr(CatMember catmember) {
		int result = 0;
		try {
			conn();
			String sql = "update catmember set member_addr = ? where member_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, catmember.getMemberAddr());
			pstmt.setString(2, catmember.getMemberId());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 포인트 수정
	public int updatepoints(CatMember catmember) {
		int result = 0;
		try {
			conn();
			String sql = "update catmember set points = points + ? where member_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, catmember.getPoints());
			pstmt.setString(2, catmember.getMemberId());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 회원 삭제
	public int deleteMember(String memberId) {
		int result = 0;
		try {
			conn();
			String sql = "delete catmember where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			result = pstmt.executeUpdate();

			if (result == 1) {
				String sql2 = "delete cat where member_id = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, memberId);

				result = pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 포인트 확인
	public List<CatMember> pointInfo(String memberId) {
		List<CatMember> list = new ArrayList<>();
		CatMember cm = null;
		try {
			conn();
			String sql = "select points, member_id, member_name from catmember where member_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cm = new CatMember();
				
				cm.setPoints(rs.getInt("points"));
				cm.setMemberId(rs.getString("member_id"));
				cm.setMemberName(rs.getString("member_name"));

				list.add(cm);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 예약후 포인트 차감
	public int minusPoints(String memberId) {
		int result = 0;

		try {
			conn();
			String sql = "UPDATE catmember SET points = points - 2000 where member_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			result = pstmt.executeUpdate();
			
			if (result == 1) {
				System.out.println("포인트 차감 완료.");
			} else if (result == 0) {
				System.out.println("포인트 차감 실패.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;

	}

}
