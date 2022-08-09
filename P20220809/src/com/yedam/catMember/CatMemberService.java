package com.yedam.catMember;

import java.util.Scanner;

public class CatMemberService {

	public static CatMember cmb = null;
	Scanner sc = new Scanner(System.in);

	// 로그인
	public void doLogin() {
		CatMember catmember = new CatMember();
		System.out.println("아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();

		System.out.println("비밀번호를 입력하세요");
		System.out.println("입력>");
		String pw = sc.nextLine();

		catmember = CatMemberDAO.getInstance().loginInfo(id);

		if (catmember.getMemberPw().equals(pw)) {
			cmb = catmember;
		} else {
			System.out.println("잘못된 비밀번호입니다.");
		}
	}

	// 로그아웃
	public void logout() {
		cmb = null;
		if (cmb != null) {
			doLogin();
		} else {
			cmb = null;
			System.out.println("로그아웃되었습니다.");
		}

	}
//	2) 회원 조회 (employee로 로그인시 조회 가능)
//	2-1) 전체 간단 조회(주인이름, 고양이이름, 기간)
//	2-2) 상세 조회(내용 전체 조회)

	// 회원등록
	public void registerMember() {
		CatMember cm = new CatMember();

		System.out.println("회원 아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();

		System.out.println("회원 비밀번호를 입력하세요");
		System.out.println("입력>");
		String pw = sc.nextLine();

		System.out.println("이름를 입력하세요");
		System.out.println("입력>");
		String name = sc.nextLine();

		System.out.println("전화번호를 입력하세요");
		System.out.println("입력>");
		String tel = sc.nextLine();

		System.out.println("주소를 입력하세요");
		System.out.println("입력>");
		String addr = sc.nextLine();

		cm.setMemberId(id);
		cm.setMemberPw(pw);
		cm.setMemberName(name);
		cm.setMemberTel(tel);
		cm.setMemberAddr(addr);

		int result = CatMemberDAO.getInstance().registerMember(cm);
		if (result == 1) {
			System.out.println("등록이 완료되었습니다.");
		} else {
			System.out.println("등록이 실패했습니다.");
		}
	}

	// 회원 수정
	public void updateMember() {
		CatMember catmember = new CatMember();
		
		System.out.println("1 연락처 수정 | 2 주소 수정 ");
		System.out.println("입력>");
		int number = Integer.parseInt(sc.nextLine());
		System.out.println("회원 아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();
		if(number == 1) {
			System.out.println("수정할 연락처를 입력하세요");
			System.out.println("입력>");
			
		}else if(number == 2) {
			System.out.println("수정할 주소를 입력하세요");
			System.out.println("입력>");
		}
		
		String tel = sc.nextLine();
		String addr = sc.nextLine();

		catmember.setMemberId(id);
		catmember.setMemberTel(tel);
		catmember.setMemberAddr(addr);
		
		int result = CatMemberDAO.getInstance().updateMember(catmember, number);
		
		if(result == 1) {
			System.out.println("수정 완료");
		} else {
			System.out.println("수정 실패");
		}

		
	}

	// 회원 삭제
	public void deleteMember() {
		System.out.println("삭제할 회원의 아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();

		int result = CatMemberDAO.getInstance().deleteMember(id);

		if (result == 1) {
			System.out.println("삭제가 완료되었습니다.");
		} else {
			System.out.println("삭제가 실패했습니다.");
		}
	}
}
