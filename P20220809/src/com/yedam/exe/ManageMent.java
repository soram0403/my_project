package com.yedam.exe;

import java.util.Scanner;

import com.yedam.cat.CatService;
import com.yedam.catMember.CatMemberService;
import com.yedam.room.RoomService;

public class ManageMent {
	Scanner sc = new Scanner(System.in);
	CatMemberService cms = new CatMemberService();
	RoomService rs = new RoomService();
	CatService cs = new CatService();
	
	int menuNo = 0;
	
	public ManageMent() {
		employeeJob();
	}

	private void employeeJob() {
		while(true) {
			menuInfo();
			if(CatMemberService.cmb.getRole().equals("0")) {
				//직원
				if(menuNo == 1) {
					// 회원 등록
					cms.registerMember();
				} else if (menuNo == 2) {
					// 회원 조회
					
				} else if (menuNo == 3) {
					// 회원 수정
				} else if (menuNo == 4) {
					// 회원 삭제
				} else if (menuNo == 5) {
					// 프로그램 종료
					System.out.println("프로그램 종료");
					break;
				}
			} // end of 직원
			else if(CatMemberService.cmb.getRole().equals("1")) {
				if(menuNo == 1) {
					//요금표 조회
				} else if (menuNo == 2) {
					// 예약
				} else if (menuNo == 3) {
					// 후기
				} else if (menuNo == 4) {
					// 종료
					System.out.println("프로그램 종료");
					break;
				}
			}
			
		}
	}
	
	private void menuInfo() {
		// role : (0 : 직원, 1: 회원)
		if(CatMemberService.cmb.getRole().equals("0")) {
			// 직원
			System.out.println("1.회원 등록 | 2.회원 조회 | 3. 회원 수정 | 4. 회원 삭제 | 6. 로그아웃 | 7.종료");
		}else if(CatMemberService.cmb.getRole().equals("1")) {
			// 회원
			System.out.println("1.요금표 조회 | 2.예약 | 3.후기 | 4.로그아웃 | 5.종료");
		}
		System.out.println("입력>");
		menuNo = Integer.parseInt(sc.nextLine());
	}
}
