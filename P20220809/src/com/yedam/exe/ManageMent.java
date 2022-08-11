package com.yedam.exe;

import java.util.Scanner;

import com.yedam.cat.CatService;
import com.yedam.catMember.CatMember;
import com.yedam.catMember.CatMemberDAO;
import com.yedam.catMember.CatMemberService;
import com.yedam.commnets.CommentsService;
import com.yedam.room.RoomService;

public class ManageMent {
	Scanner sc = new Scanner(System.in);
	CatMemberService cms = new CatMemberService();
	RoomService rs = new RoomService();
	CatService cs = new CatService();
	CommentsService CommentS = new CommentsService();

	int menuNo = 0;

	public ManageMent() {
		employeeJob();
	}

	private void employeeJob() {
		while (true) {
			menuInfo();
			if (CatMemberService.cmb.getRole().equals("0")) {
				// 직원
				if (menuNo == 1) {
					// 회원 등록
					cms.registerMember();
				} else if (menuNo == 2) {
					// 조회
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.println(" 1.회원 조회 | 2.회원 상세 조회 | 3.후기 조회");
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.print("메뉴 선택>");

					int menu = Integer.parseInt(sc.nextLine());

					if (menu == 1) {
						// 전체 간단 조회 (주인id, 주인이름, 고양이이름, 가입일)
						cms.getCatMember();
					} else if (menu == 2) {
						// 회원 1명 상세 조회 (id, 이름, 고양이이름, 고양이성격, 특이사항, 방 번호)
						cms.getDetailCatMember();
					} else if (menu == 3) {
						// 후기 조회
						CommentS.getCommentInfo();
					}
				} else if (menuNo == 3) {
					// 예약하기
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.println("  1.예약 하기 | 2. 예약 취소");
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.print("메뉴 선택>");

					int menu = Integer.parseInt(sc.nextLine());

					if (menu == 1) {
						// 예약 조회
						rs.emptyRoom();
						rs.adminBookRoom();
						System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
						System.out.println("  1.일반결제 | 2.멤버쉽결제");
						System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
						System.out.print("메뉴 선택>");
						menu = Integer.parseInt(sc.nextLine());
						if(menu == 1) {
							// 일반 결제
							System.out.println();
							rs.priceInfo();
						}else if(menu == 2) {
							// 포인트 결제
							System.out.println();
							rs.salePriceInfo();
						}
						
					}else if(menu == 2) {
						rs.cancelRoom();
					}

				} else if (menuNo == 4) {
					// 포인트 충전
					CatMember catmember = new CatMember();

					System.out.println();
					System.out.println(" 회원 아이디를 입력하세요");
					System.out.print(" ID 입력>");
					String id = sc.nextLine();
					System.out.println();

					System.out.println(" 충전할 포인트를 입력하세요");
					System.out.print(" 입력>");
					int points = Integer.parseInt(sc.nextLine());
					System.out.println();

					catmember.setMemberId(id);
					catmember.setPoints(points);

					int result = CatMemberDAO.getInstance().updatepoints(catmember);

					if (result == 1) {
						System.out.println(" ＊포인트 충전 완료＊");
					} else if (result == 2) {
						System.out.println(" ※※포인트 충전 실패※※");
					}

				} else if (menuNo == 5) {

					// 회원 수정 (회원수정 / 고양이 상태 수정)
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.println("  1.회원 정보 수정 | 2.고양이 정보 수정 ");
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.print("메뉴 선택>");
					int menuN = Integer.parseInt(sc.nextLine());

					if (menuN == 1) {
						cms.updateMember();
					} else if (menuN == 2) {
						cs.updateCat();
					}

				} else if (menuNo == 6) {
					// 회원 삭제
					cms.deleteMember();
				} else if (menuNo == 7) {
					cms.logout();
				} else if (menuNo == 8) {
					// 프로그램 종료
					System.out.println();
					System.out.println("〷 프로그램 종료 〷");
					System.out.println();
					break;
				}
				// end of 직원
			} else if (CatMemberService.cmb.getRole().equals("1")) {
				if (menuNo == 1) {
					// 요금표 조회
					System.out.println(" ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
					System.out.println(" ✦ 1. 일반 가격 | 2. 멤버십 가격 ");
					System.out.println(" ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
					System.out.print("메뉴 선택>");
					int menu = Integer.parseInt(sc.nextLine());
					if(menu == 1) {
						cms.showPrice();
					}else if(menu == 2) {
						cms.showSalePrice();
					}
					
				} else if (menuNo == 2) {
					// 예약 (1:예약 2:예약취소)
					System.out.println(" ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
					System.out.println(" ✦ 1.예약 하기 | 2.예약 조회 | 3.예약 취소 ✦");
					System.out.println(" ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
					System.out.print("메뉴 선택>");
					int menu = Integer.parseInt(sc.nextLine());
					if (menu == 1) {
						rs.bookRoom();
						rs.priceInfo();
					} else if (menu == 2) {
						rs.roomInfo();
					} else if (menu == 3) {
						rs.cancelRoom();
					}

				} else if (menuNo == 3) {
					System.out.println(" ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
					System.out.println(" ✦ 1.후기 등록 | 2.후기 수정 | 3.후기 삭제 ✦");
					System.out.println(" ✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦✦");
					System.out.print("메뉴 선택>");
					int menu = Integer.parseInt(sc.nextLine());

					if (menu == 1) {
						CommentS.insertComments();
					} else if (menu == 2) {
						CommentS.updateComments();
					} else if (menu == 3) {
						CommentS.deleteComments();
					}
					// 후기
				} else if (menuNo == 4) {
					cms.logout();
				} else if (menuNo == 5) {
					// 종료
					System.out.println();
					System.out.println("〷 프로그램 종료 〷");
					System.out.println();
					break;
				}
			}

		}

	}

	private void menuInfo() {
		// role : (0 : 직원, 1: 회원)
		if (CatMemberService.cmb.getRole().equals("0")) {
			// 직원
			System.out.println(
					" ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
			System.out.println("   1.회원 등록 | 2.조회하기 | 3.예약하기 | 4.포인트 충전 | 5.회원 수정 | 6.회원 삭제 | 7.로그아웃 | 8.종료 ");
			System.out.println(
					" ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		} else if (CatMemberService.cmb.getRole().equals("1")) {
			// 회원
			System.out.println(" ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
			System.out.println(" ┃ 1.요금표 조회 | 2.예약 | 3.후기 | 4.로그아웃 | 5.종료 ┃");
			System.out.println(" ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		}
		System.out.print("메뉴 선택> ");
		menuNo = Integer.parseInt(sc.nextLine());
	}
}
