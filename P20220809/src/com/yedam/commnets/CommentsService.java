package com.yedam.commnets;

import java.util.List;
import java.util.Scanner;

import com.yedam.catMember.CatMemberService;

public class CommentsService {

	Scanner sc = new Scanner(System.in);

	// 후기 작성
	public void insertComments() {

		Comments ct = new Comments();
		
		System.out.println();
		System.out.println(" 후기를 입력하세요");
		System.out.print(" 입력>");
		String comment = sc.nextLine();
		System.out.println();

		ct.setMemberId(CatMemberService.cmb.getMemberId());
		ct.setComments(comment);

		CommentsDAO.getInstance().insertComments(ct);
	}

	// 후기 수정
	public void updateComments() {
		Comments ct = new Comments();
		
		System.out.println();
		System.out.println(" 수정한 후기를 입력하세요");
		System.out.print(" 입력>");
		String comment = sc.nextLine();
		System.out.println();

		ct.setMemberId(CatMemberService.cmb.getMemberId());
		ct.setComments(comment);

		CommentsDAO.getInstance().updateComments(ct);
	}

	// 후기 삭제
	public void deleteComments() {
		System.out.println("☹ 정말로 삭제하실 건가요? ☹");
		System.out.println(" 1.예 | 2.아니요");
		System.out.println(" 선택>");
		int No = Integer.parseInt(sc.nextLine());

		if (No == 1) {
			List<Comments> list = CommentsDAO.getInstance().deleteComments(CatMemberService.cmb.getMemberId());
		}
	}

	// 후기 조회
	public void getCommentInfo() {
		List<Comments> list = CommentsDAO.getInstance().getCommentsInfo();

		for (Comments comments : list) {
			System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			System.out.print(" 회원 ID : " + comments.getMemberId());
			System.out.print(", 후기 : " + comments.getComments()+ "\n");
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		}

	}

}
