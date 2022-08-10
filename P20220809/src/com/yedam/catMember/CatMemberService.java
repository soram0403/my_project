package com.yedam.catMember;

import java.util.List;
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
			System.out.println("로그인 완료");
		} else {
			System.out.println("잘못된 비밀번호입니다.");
			System.out.println("========================");
			doLogin();
		}
	}

	// 로그아웃
	public void logout() {
		cmb = null;
		System.out.println("로그아웃되었습니다.");
		try {
			while(true) {
			System.out.println("1.메뉴 선택 | 2.프로그램 종료");
			int menu = Integer.parseInt(sc.nextLine());
			if(menu == 1) {
				doLogin();
				break;
			} else if( menu == 2) {
				
				System.out.println("프로그램 종료");
				break;
			}
		
			}
		}catch(Exception e) {
			cmb = null;
		}
	}

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

		System.out.println("고양이 이름을 입력하세요");
		System.out.println("입력>");
		String catName = sc.nextLine();
		
		System.out.println("고양이 성격을 입력하세요");
		System.out.println("입력>");
		String character = sc.nextLine();
		
		cm.setMemberId(id);
		cm.setMemberPw(pw);
		cm.setMemberName(name);
		cm.setMemberTel(tel);
		cm.setCatName(catName);
		cm.setMemberAddr(addr);
		cm.setCatCharacter(character);

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

		System.out.println("1.연락처 수정 | 2.주소 수정 ");
		System.out.println("입력>");
		int number = Integer.parseInt(sc.nextLine());

		if (number == 1) {
			System.out.println("회원 아이디를 입력하세요");
			System.out.println("입력>");
			String id = sc.nextLine();

			System.out.println("수정할 연락처를 입력하세요");
			System.out.println("입력>");
			String tel = sc.nextLine();

			catmember.setMemberId(id);
			catmember.setMemberTel(tel);

			int result = CatMemberDAO.getInstance().updateTel(catmember);

			if (result == 1) {
				System.out.println("연락처 수정 완료");
			} else {
				System.out.println("연락처 수정 실패");
			}

		} else if (number == 2) {
			System.out.println("회원 아이디를 입력하세요");
			System.out.println("입력>");
			String id = sc.nextLine();

			System.out.println("수정할 주소를 입력하세요");
			System.out.println("입력>");
			String addr = sc.nextLine();
			catmember.setMemberId(id);
			catmember.setMemberAddr(addr);

			int result = CatMemberDAO.getInstance().updateAddr(catmember);

			if (result == 1) {
				System.out.println("주소 수정 완료");
			} else {
				System.out.println("주소 수정 실패");
			}

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

	// 2) 회원 조회 (employee로 로그인시 조회 가능)
	// 2-1) 전체 간단 조회(주인id, 주인이름, 고양이이름, 가입일)
	public void getCatMember() {
		List<CatMember> list = CatMemberDAO.getInstance().getCatMember();

		for (CatMember catmember : list) {
			// (주인id, 주인이름, 고양이이름, 가입일)
			System.out.println("===========================================================");
			System.out.print("ID : " + catmember.getMemberId());
			System.out.print(" | 회원 이름 : " + catmember.getMemberName());
			System.out.print(" | 고양이 이름 : " + catmember.getCatName());
			System.out.print(" | 가입일 : " + catmember.getDates());
			System.out.println();
			System.out.println("===========================================================");

		}
	}

	// 2-2) 상세 조회(id, 이름, 고양이이름, 고양이성격, 특이사항, 방 번호)
	public void getDetailCatMember() {

		System.out.println("조회할 회원의 ID를 입력하세요");
		String id = sc.nextLine();
		List<CatMember> list = CatMemberDAO.getInstance().getDetailCatMember(id);

		for (CatMember catmember : list) {
			if (id.equals(catmember.getMemberId())) {

				// (id, 고양이이름, 고양이성격, 특이사항, 방 번호)
				
				System.out.println(
						"========================================== 회원 ID : "+catmember.getMemberId() + "님의 정보 ==========================================");
				System.out.print("회원 이름 : " + catmember.getMemberName());
				System.out.print(" | 회원 번호 :" + catmember.getMemberTel());
				System.out.print(" | 고양이 이름 : " + catmember.getCatName());
				System.out.print(" | 고양이 성격 : " + catmember.getCatCharacter());
				System.out.print(" | 특이사항 : " + catmember.getSpecialNote());
				System.out.println();
				System.out.println(
						"=======================================================================================================");
			} else {
				System.out.println("존재하지 않는 회원 ID입니다.");
			}
		}

	}

	// 요금표 조회
	public void showPrice() {
		System.out.println("============== 요금표 ==============");
		System.out.println("   ＊1일 : 50,000원");
		System.out.println("   ＊3일 : 120,000원");
		System.out.println("   ＊7일 : 300,000원");
		System.out.println("   ＊1달이상(장기) : 1,200,000원");
		System.out.println("          ※※선불제 입니다.※※");
		System.out.println("      ※※최대 3개월까지 가능합니다.※※");
		System.out.println("==================================");
	}

}
