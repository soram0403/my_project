package com.yedam.room;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import com.yedam.catMember.CatMember;
import com.yedam.catMember.CatMemberDAO;
import com.yedam.catMember.CatMemberService;

public class RoomService {
	Scanner sc = new Scanner(System.in);
	CatMemberService cms = new CatMemberService();

	// 빈방 조회
	public void emptyRoom() {
		List<Room> list = RoomDAO.getInstance().emptyRoom();

		for (Room room : list) {

			if (room.getRoomState() == 0) {
				System.out.println();
				System.out.println(" □□□□□□□□□□□□□□□□□□□□□□");
				System.out.print(" □  방 번호 : " + room.getRoomNumber());
				System.out.print(" | 예약 가능한 방입니다.  □ \n");
				System.out.println(" □□□□□□□□□□□□□□□□□□□□□□");
				System.out.println();

			} else if (room.getRoomState() == 1) {
				System.out.println(" ■■■■■■■■■■■■■■■■■■■■■■");
				System.out.print(" ■  방 번호 : " + room.getRoomNumber());
				System.out.print(" | 이미 예약된 방입니다.  ■ \n");
				System.out.println(" ■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println();
			}

		}
	}
	// admin이 예약하기

	public void adminBookRoom() {
		Room room = new Room();
		if (room.getRoomState() == 1) {
			System.out.println("이미 예약된 방입니다.");

		} else if (room.getRoomState() == 0) {
			System.out.println(" 예약할 방 번호를 입력하세요");
			System.out.print(" 방 번호 입력>");
			int roomNo = Integer.parseInt(sc.nextLine());
			System.out.println();

			System.out.println(" 회원 ID를 입력하세요");
			System.out.print(" ID 입력>");
			String memberId = sc.nextLine();
			System.out.println();

			System.out.println(" 회원 이름를 입력하세요");
			System.out.print(" 입력>");
			String name = sc.nextLine();
			System.out.println();

			System.out.println(" 예약 기간을 입력하세요");
			System.out.print(" 기간 입력>");
			int dates = Integer.parseInt(sc.nextLine());
			System.out.println();

			System.out.println(" 고양이 이름을 입력하세요");
			System.out.print(" 입력>");
			String catName = sc.nextLine();

			room.setRoomNumber(roomNo);
			room.setMemberId(memberId);
			room.setMemberName(name);
			room.setRoomDate(dates);
			room.setCatName(catName);

			System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━");
			System.out.println("  1.일반결제 | 2.멤버쉽결제 ");
			System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━");
			System.out.print("메뉴 선택>");
			int menu = Integer.parseInt(sc.nextLine());

			if (menu == 1) {
				// 일반 결제
				RoomDAO.getInstance().bookRoom(room);
				System.out.println();
				priceInfo();
			} else if (menu == 2) {
				// 포인트 결제

				if (CatMemberService.cmb.getPoints() >= 2000) {
					RoomDAO.getInstance().bookRoom(room);
					salePriceInfo();
					System.out.println(" 회원 ID를 입력하세요");
					System.out.print(" ID 입력>");
					String id = sc.nextLine();
					CatMemberDAO.getInstance().minusPoints(id);
				} else if (CatMemberService.cmb.getPoints() < 2000) {
					System.out.println(" 회원의 포인트가 부족합니다.");
					System.out.println(" ※ 포인트를 충전하세요 ※");
				}

			}
		}
	}

	// 2-2) 빈방 예약(빈방이면 예약완료/ 아니면 예약불가능 -> 다시선택)
	public void bookRoom() {

		System.out.println(" 조회할 방 번호를 입력하세요.");
		System.out.print(" 방 번호 입력>");
		int roomNo = Integer.parseInt(sc.nextLine());
		List<Room> list = RoomDAO.getInstance().checkRoom(roomNo);

		for (Room room : list) {
			if (room.getRoomState() == 0) {
				System.out.println(room.getRoomNumber() + "번 방은 예약 가능합니다.");
				System.out.println();
				System.out.println(" ┏━━━━━━━━━━━━━━━━━━━━━━━━━┓");
				System.out.println("   1.예약하기 | 2.돌아가기 ");
				System.out.println(" ┗━━━━━━━━━━━━━━━━━━━━━━━━━┛");
				System.out.println(" 메뉴 선택>");
				int menuNo = Integer.parseInt(sc.nextLine());

				if (menuNo == 1) {
					room = new Room();

					System.out.println(" 예약할 방 번호를 선택하세요");
					System.out.println(" 방 번호 입력>");
					int roomN = Integer.parseInt(sc.nextLine());
					System.out.println();

					System.out.println(" 예약 기간을 입력하세요 (최소 1일, 최대 6개월)");
					System.out.println(" ※ 1개월부터는 개월단위로 결제 가능 ※ ");
					System.out.println(" 입력>");
					int dates = Integer.parseInt(sc.nextLine());
					System.out.println();

					room.setRoomState(room.getRoomNumber());
					room.setMemberId(CatMemberService.cmb.getMemberId());
					room.setCatName(CatMemberService.cmb.getCatName());
					room.setRoomDate(dates);
					room.setMemberName(CatMemberService.cmb.getMemberName());
					room.setRoomNumber(roomN);

					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━");
					System.out.println("  1.일반결제 | 2.멤버쉽결제 | ");
					System.out.println(" ━━━━━━━━━━━━━━━━━━━━━━━━━━");

					System.out.print("메뉴 선택>");

					int menu = Integer.parseInt(sc.nextLine());

					if (menu == 1) {
						// 일반 결제
						RoomDAO.getInstance().bookRoom(room);
						System.out.println();
						priceInfo();
					} else if (menu == 2) {
						// 포인트 결제

						List<CatMember> list2 = CatMemberDAO.getInstance()
								.pointInfo(CatMemberService.cmb.getMemberId());
						for (CatMember cm : list2) {
							if (cm.getPoints() >= 2000) {
								RoomDAO.getInstance().bookRoom(room);
								salePriceInfo();
								CatMemberDAO.getInstance().minusPoints(CatMemberService.cmb.getMemberId());

							} else {
								System.out.println(" ( ´•̥×•̥` ) 포인트가 부족합니다. ( ´•̥×•̥` )");
								System.out.println("       ※ 카운터에 문의하세요 ※");
							}
						}
					}

					else if (room.getRoomState() == 1) {
						System.out.println();
						System.out.println(" ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
						System.out.println(" ＊＊ " + room.getRoomNumber() + "번 방은 이미 예약된 방입니다. ＊＊");
						System.out.println(" ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
						System.out.println();

					}
				}
			}
		}
	}

	// 예약시 일반 가격 출력
	public void priceInfo() {

		System.out.println("예약한 방 번호를 입력하세요");
		int roomNo = Integer.parseInt(sc.nextLine());
		List<Room> list = RoomDAO.getInstance().priceInfo(roomNo);

		for (Room room : list) {

			DecimalFormat df = new DecimalFormat("###,###");

			switch (room.getRoomDate()) {
			case 1:
				System.out.println(" ▶ 일반 요금은 50,000원 입니다. ◀");
				break;
			case 3:
				System.out.println(" ▶ 일반 요금은 120,000원 입니다. ◀");
				break;
			case 7:
				System.out.println(" ▶ 일반 요금은 300,000원 입니다. ◀");
				break;
			case 30:
				System.out.println(" ▶ 일반 요금은 1,200,000원 입니다. ◀");
				break;
			default:
				System.out.println(" ▶ 일반 요금은 " + df.format(room.getRoomDate() * 50000) + "원 입니다. ◀");

				break;
			}

		}
	}

	// 멤버쉽 결제시 가격 출력 (멤버쉽 : 20% 할인, 포인트 2000점씩 사용)
	public void salePriceInfo() {

		System.out.println("예약한 방 번호를 입력하세요");
		int roomNo = Integer.parseInt(sc.nextLine());
		List<Room> list = RoomDAO.getInstance().salePriceInfo(roomNo);

		for (Room room : list) {

			DecimalFormat df = new DecimalFormat("###,###");

			switch (room.getRoomDate()) {
			case 1:
				System.out.println(" ▶ 멤버십 요금은 40,000원 입니다. ◀");
				break;
			case 3:
				System.out.println(" ▶ 멤버십 요금은 96,000원 입니다. ◀");
				break;
			case 7:
				System.out.println(" ▶ 멤버십 요금은 240,000원 입니다. ◀");
				break;
			case 30:
				System.out.println(" ▶ 멤버십 요금은 960,000원 입니다. ◀");
				break;
			default:
				System.out.println(" ▶ 멤버십 요금은 "
						+ df.format((room.getRoomDate() * 50000) - (room.getRoomDate() * 50000 * 0.2)) + "원 입니다. ◀");

				break;
			}
		}
	}

	// select room_number, member_name, cat_name, room_date from room where
	// member_id = ?
	// 예약 조회
	public void roomInfo() {

		List<Room> list = RoomDAO.getInstance().roomInfo(CatMemberService.cmb.getMemberId());

		if (list.size() == 0) {
			System.out.println(" ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
			System.out.println(" ┃     예약한 정보가 존재하지 않습니다. 예약을 해 주세요.    ┃");
			System.out.println(" ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
		} else {

			for (Room room : list) {
				if (room.getRoomState() == 1) {
					System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
					System.out.println(
							"                              " + CatMemberService.cmb.getMemberName() + "님의 예약 정보 ");
					System.out.println("");
					System.out.print("   ◐  방 번호는 " + room.getRoomNumber() + "번 이고,");
					System.out.print(" 회원 ID는 " + room.getMemberId());
					System.out.print(", 예약 기간은 " + room.getRoomDate() + "일");
					System.out.print(", 고양이 이름은 " + room.getCatName() + " 입니다.  ◐  ");
					System.out.println();
					System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
				}
			}
		}
	}

	// 2-3) 예약 취소
	public void cancelRoom() {
		System.out.println(" 취소할 방 번호를 입력하세요");
		System.out.println(" 방 번호 입력>");
		int roomNo = Integer.parseInt(sc.nextLine());
		System.out.println();

		RoomDAO.getInstance().cancelRoom(roomNo);
	}

}
