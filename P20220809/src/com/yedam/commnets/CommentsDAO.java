package com.yedam.commnets;

import java.util.ArrayList;
import java.util.List;

import com.yedam.common.DAO;

public class CommentsDAO extends DAO{
	private static CommentsDAO commentsDao = null;
	
	private CommentsDAO() {}
	
	public static CommentsDAO getInstance() {
		return commentsDao == null ? commentsDao = new CommentsDAO() : commentsDao;
	}
//	MEMBER_ID    VARCHAR2(20)   
//	COMMENTS     VARCHAR2(3000) 
	// 후기 등록
	public int insertComments(Comments com) {
		int result = 0;
		try {
			conn();
			String sql = "insert into comments values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, com.getMemberId());
			pstmt.setString(2, com.getComments());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return result;
	}
	
	// 후기 수정
	public int updateComments(Comments com) {
		int result = 0;
		
		try {
			conn();
			String sql = "update comments set comments = ? where member_id = ?";
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1,com.getComments());
			pstmt.setString(2, com.getMemberId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		
		return result;
	}
	
	// 후기 삭제
	public int deleteComments(Comments com) {
		int result = 0;
		
		try {
			conn();
			String sql = "delete comments where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, com.getComments());
			
			result = pstmt.executeUpdate();
			
			if(result==1) {
				System.out.println("후기 삭제 완료");
			} else {
				System.out.println("후기 삭제 실패");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return result;
	}
	
	// 후기 조회 member_id , comments
		public List<Comments> getCommentsInfo() {
			List<Comments> list = new ArrayList<>();
			Comments comments = null;
			try {
				conn();
				String sql = "select * from comments";
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					comments = new Comments();
					comments.setMemberId(rs.getString("member_id"));
					comments.setComments(rs.getString("comments"));
					
					list.add(comments);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				disconnect();
			}
			return list;
		}
}
