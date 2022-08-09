package com.yedam.catMember;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;

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
			String sql = "select m.member_id member_id, c.cat_name cat_name, m.member_name member_name, m.dates dates from catmember m join cat c on m.member_id = c.member_id";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				catmember.setMemberId(rs.getString("member_id"));
				catmember.setMemberName(rs.getString("member_name"));
				catmember.setCatName(rs.getString("cat_name"));
				catmember.setDates(rs.getString("dates"));
				
				list.add(catmember);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return list;
	}
	
	// 회원(1명) 상세 조회 // id, 이름, 고양이이름, 고양이성격, 특이사항, 방 번호
	public List<CatMember> getDetailCatMember(String memberId) {
		List<CatMember> list = new ArrayList<>();
		CatMember catmember = null;
		
		try {
			conn();
			String sql = "select * from catmember";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				catmember.setMemberId(rs.getString("member_id"));
				catmember.setMemberName(rs.getString("member_name"));
				catmember.setCatName(rs.getString("cat_name"));
				catmember.setDates(rs.getString("dates"));
				
				list.add(catmember);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return list;
	}
	
	// 회원 등록 (id,pw,name,tel,addr)
	public int registerMember(CatMember catMember) {
		int result = 0;
		try {
			conn();
			String sql = "insert into catmember (member_id,member_pw,member_name,member_tel,member_addr) values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, catMember.getMemberId());
			pstmt.setString(2, catMember.getMemberPw());
			pstmt.setString(3, catMember.getMemberName());
			pstmt.setString(4, catMember.getMemberTel());
			pstmt.setString(5, catMember.getMemberAddr());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return result;
	}
	// 회원 수정
	// number == 1 연락처 수정 | 2 주소 수정 | 3 상태 수정
	public int updateMember(CatMember catmember, int number) {
		int result = 0;
		try {
			conn();
			
			String sql = "select member_id from catmember";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(number == 1) {
				String sql2 = "update member_tel = ? from catmember where member_id =?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, rs.getString("member_tel"));
				pstmt.setString(2, rs.getString("member_id"));
				
				result = pstmt.executeUpdate();
			} else if (number == 2) {
				String sql3 = "update member_addr = ? from catmember where member_id =?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setString(1, rs.getString("member_addr"));
				pstmt.setString(2, rs.getString("member_id"));

				result = pstmt.executeUpdate();
						
			} else if (number == 3) {
				String sql4 = "update states = ? from catmember where member_id =?";
				pstmt = conn.prepareStatement(sql4);
				pstmt.setString(1, rs.getString("states"));
				pstmt.setString(2, rs.getString("member_id"));

				result = pstmt.executeUpdate();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

}
