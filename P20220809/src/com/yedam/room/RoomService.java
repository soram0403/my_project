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

				System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");
				System.out.print("□ 방 번호 : " + room.getRoomNumber());
				System.out.print(" | 예약 가능한 방입니다. □\n");
				System.out.println("□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□");

			} else if (room.getRoomState() == 1) {
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.print("■ 방 번호 : " + room.getRoomNumber());
				System.out.print(" | 이미 예약된 방입니다. ■\n");
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
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
					
					System.out.println(" 예약 기간을 입력하세요 (최소 1일, 최대 3달)");
					System.out.println(" 입력>");
					String dates = sc.nextLine();
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
				System.out.println(" ＊＊ "+room.getRoomNumber() + "번 방은 이미 예약된 방입니다. ＊＊");
				System.out.println(" ＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
				System.out.println();
			}
		}
	}
	// select room_number, member_name, cat_name, room_date from room where member_id = ?
	// 예약 조회
	public void roomInfo() {

		List<Room> list = RoomDAO.getInstance().roomInfo(CatMemberService.cmb.getMemberId());

		System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println(CatMemberService.cmb.getMemberName() + "님의 예약 정보 ");

		for (Room room : list) {
			System.out.println("");
				System.out.print("   ◐  방 번호는 " + room.getRoomNumber() + "번 이고,");
				System.out.print(" 회원 ID는 " + room.getMemberId());
				System.out.print(", 예약 기간은 " + room.getRoomDate());
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
