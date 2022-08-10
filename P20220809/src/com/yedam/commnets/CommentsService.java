package com.yedam.commnets;

import java.util.List;
import java.util.Scanner;

public class CommentsService {

	Scanner sc = new Scanner(System.in);

	// 후기 작성
	public void insertComments() {
		Comments ct = new Comments();
		
		System.out.println("아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();
		
		System.out.println("후기를 입력하세요");
		System.out.println("입력>");
		String comment = sc.nextLine();
		
		ct.setMemberId(id);
		ct.setComments(comment);
		
		int result = CommentsDAO.getInstance().insertComments(ct);
		if(result == 1) {
			System.out.println("후기 등록 성공");
		} else {
			System.out.println("후기 등록 실패");
		}
	}

	// 후기 수정
	public void updateComments() {
		Comments ct = new Comments();
		System.out.println("아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();
		
		System.out.println("수정한 후기를 입력하세요");
		System.out.println("입력>");
		String comment = sc.nextLine();
		
		ct.setMemberId(id);
		ct.setComments(comment);
		
		int result = CommentsDAO.getInstance().updateComments(ct);
		if(result == 1) {
			System.out.println("후기 수정 성공");
		} else {
			System.out.println("후기 수정 실패");
		}
	}

	// 후기 삭제
	public void deleteComments() {
		Comments ct = new Comments();
		System.out.println("아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();
		
		ct.setMemberId(id);
		
		int result = CommentsDAO.getInstance().deleteComments(ct);
		if(result == 1) {
			System.out.println("후기 삭제 성공");
		} else {
			System.out.println("후기 삭제 실패");
		}
	}
	
	// 후기 조회
	public void getCommentInfo() {
		List<Comments> list = CommentsDAO.getInstance().getCommentsInfo();
		
		for(Comments comments : list) {
			System.out.println("회원 ID : " + comments.getMemberId());
			System.out.println("후기 : " + comments.getComments());
		}
		
	}
	
}
