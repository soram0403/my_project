package com.yedam.room;

import java.util.List;
import java.util.Scanner;

public class RoomService {
	Scanner sc = new Scanner(System.in);

	// 2-1) 빈방 여부 확인
	public void emptyRoom() {
		List<Room> list = RoomDAO.getInstance().emptyRoom();

		for (Room room : list) {
			int result = room.getRoomNumber();

			if (result == 0) {
				System.out.println("예약 가능합니다.");
			} else if (result == 1) {
				System.out.println("이미 예약된 방입니다.");
			}
			System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			System.out.println("방 번호 : " + room.getRoomNumber());
			System.out.println("방 상태 : " + result);
			System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		}
	}

	// 2-2) 빈방 예약(빈방이면 예약완료/ 아니면 예약불가능 -> 다시선택)
	public void bookRoom() {
		List<Room> list = RoomDAO.getInstance().emptyRoom();

		for (Room room : list) {
			int result = room.getRoomNumber();

			if (result == 0) {
				System.out.println("방 번호를 선택하세요");
				System.out.println("입력>");
				String roomName = sc.nextLine();
				
				System.out.println("아이디를 입력하세요");
				System.out.println("입력>");
				String id = sc.nextLine();
				
				System.out.println("이름을 입력하세요");
				System.out.println("입력>");
				String name = sc.nextLine();
				
				System.out.println("고양이 이름을 입력하세요");
				System.out.println("입력>");
				String catName = sc.nextLine();
				
				room.setRoomNumber(result);
				room.setMemberId(id);
				room.setCatName(catName);
				room.setMemberName(name);
				
				list = RoomDAO.getInstance().bookRoom(room);
			} else if (result == 1) {
				System.out.println("이미 예약된 방입니다.");
			}
		}
	}
	// 2-3) 예약 취소
	public void cancelRoom() {
		System.out.println("아이디를 입력하세요");
		System.out.println("입력>");
		String memberId = sc.nextLine();
		
		RoomDAO.getInstance().cancelRoom(memberId);
	}
}
