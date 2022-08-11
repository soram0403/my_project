package com.yedam.room;

import java.util.List;
import java.util.Scanner;

import com.yedam.catMember.CatMemberService;

public class RoomService {
	Scanner sc = new Scanner(System.in);

	// 빈방 조회
	public void emptyRoom() {
		List<Room> list = RoomDAO.getInstance().emptyRoom();

		for (Room room : list) {

			if (room.getRoomState() == 0) {

				System.out.println(" □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
				System.out.print(" □ 방 번호 : " + room.getRoomNumber());
				System.out.print(" | 예약 가능한 방입니다. □\n");
				System.out.println(" □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");

			} else if (room.getRoomState() == 1) {
				System.out.println(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.print(" ■ 방 번호 : " + room.getRoomNumber());
				System.out.print(" | 이미 예약된 방입니다. ■\n");
				System.out.println(" ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
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
			System.out.println(" 방 번호 입력>");
			int roomNo = Integer.parseInt(sc.nextLine());
			System.out.println();

			System.out.println(" 회원 ID를 입력하세요");
			System.out.println(" ID 입력>");
			String memberId = sc.nextLine();

			System.out.println(" 회원 이름를 입력하세요");
			System.out.println(" 입력>");
			String name = sc.nextLine();

			System.out.println(" 예약 기간을 입력하세요");
			System.out.println(" 기간 입력>");
			int dates = Integer.parseInt(sc.nextLine());

			System.out.println(" 고양이 이름을 입력하세요");
			System.out.println(" 입력>");
			String catName = sc.nextLine();

			room.setRoomNumber(roomNo);
			room.setMemberId(memberId);
			room.setMemberName(name);
			room.setRoomDate(dates);
			room.setCatName(catName);

			RoomDAO.getInstance().bookRoom(room);
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
				System.out.println(" ┏━━━━━━━━━━━━━━━━━━━━━┓");
				System.out.println("   1.예약하기 | 2.돌아가기 ");
				System.out.println(" ┗━━━━━━━━━━━━━━━━━━━━━┛");
				System.out.println(" 메뉴 선택>");
				int menuNo = Integer.parseInt(sc.nextLine());

				if (menuNo == 1) {
					room = new Room();

					System.out.println(" 예약할 방 번호를 선택하세요");
					System.out.println(" 방 번호 입력>");
					int roomN = Integer.parseInt(sc.nextLine());
					System.out.println();

					System.out.println(" 예약 기간을 입력하세요 (최소 1일, 최대 6개월)");
					System.out.println("                 1개월부터는 개월단위로 결제 가능");
					System.out.println(" 입력>");
					int dates = Integer.parseInt(sc.nextLine());
					System.out.println();

					room.setRoomState(room.getRoomNumber());
					room.setMemberId(CatMemberService.cmb.getMemberId());
					room.setCatName(CatMemberService.cmb.getCatName());
					room.setRoomDate(dates);
					room.setMemberName(CatMemberService.cmb.getMemberName());
					room.setRoomNumber(roomN);

					RoomDAO.getInstance().bookRoom(room);

				}
			} else if (room.getRoomState() == 1) {
				System.out.println();
				System.out.println(" ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
				System.out.println(" ＊＊ " + room.getRoomNumber() + "번 방은 이미 예약된 방입니다. ＊＊");
				System.out.println(" ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
				System.out.println();
			}
		}
	}
//	 ▶ 1일 : 50,000원                
//	 ▶ 3일 : 120,000원              
//	 ▶ 7일 : 300,000원              
//	 ▶ 1개월(30일) : 1,200,000원     
//	        ※※선불제 입니다.※※
//	    ※※1개월은 30일입니다.※※
	// 1개월 넘어서부터는 개월수로 결제 가능합니다.
//	    ※※최대 6개월까지 가능합니다.※※   

	// 예약시 일반 가격 출력
	public void priceInfo() {
		System.out.println("방 번호를 입력하세요");
		int roomNo = Integer.parseInt(sc.nextLine());
		List<Room> list = RoomDAO.getInstance().priceInfo(roomNo);

		for (Room room : list) {
			switch (room.getRoomDate()) {
			case 1: System.out.println("요금은 50,000원 입니다.");
				break;
			case 3: System.out.println("요금은 120,000원 입니다.");
				break;
			case 7: System.out.println("요금은 300,000원 입니다.");
				break;
			case 30: System.out.println("요금은 1,200,000원 입니다.");
				break;
			default: System.out.println("요금은 " + room.getRoomDate() * 50000 + "원 입니다.");

				break;
			}

		}
	}
	// 멤버쉽 결제시 가격 출력 (멤버쉽 : 20% 할인, 포인트 2000점씩 사용)
	public void salePriceInfo() {
		System.out.println("방 번호를 입력하세요");
		int roomNo = Integer.parseInt(sc.nextLine());
		List<Room> list = RoomDAO.getInstance().salePriceInfo(roomNo);
		
		for(Room room : list) {
			switch (room.getRoomDate()) {
			case 1: System.out.println("요금은 40,000원 입니다.");
				break;
			case 3: System.out.println("요금은 96,000원 입니다.");
				break;
			case 7: System.out.println("요금은 240,000원 입니다.");
				break;
			case 30: System.out.println("요금은 960,000원 입니다.");
				break;
			default: System.out.println("요금은 " + ((room.getRoomDate() * 50000)-(room.getRoomDate() * 50000 * 0.2)) + "원 입니다.");

				break;
			}
		}
	}

	// select room_number, member_name, cat_name, room_date from room where
	// member_id = ?
	// 예약 조회
	public void roomInfo() {

		List<Room> list = RoomDAO.getInstance().roomInfo(CatMemberService.cmb.getMemberId());

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("                              " + CatMemberService.cmb.getMemberName() + "님의 예약 정보 ");

		for (Room room : list) {
			System.out.println("");
			System.out.print("   ◐  방 번호는 " + room.getRoomNumber() + "번 이고,");
			System.out.print(" 회원 ID는 " + room.getMemberId());
			System.out.print(", 예약 기간은 " + room.getRoomDate() + "일");
			System.out.print(", 고양이 이름은 " + room.getCatName() + " 입니다.  ◐  ");
			System.out.println();
			System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
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
