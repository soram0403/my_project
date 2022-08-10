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
				
				System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				System.out.print("☆ 방 번호 : " + room.getRoomNumber()); 
				System.out.print(" | 예약 가능한 방입니다. ☆\n");
				System.out.println("☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆☆");
				
			} else if (room.getRoomState() == 1) {
				System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
				System.out.print("★ 방 번호 : " + room.getRoomNumber());
				System.out.print(" | 이미 예약된 방입니다. ★\n");
				System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
			}
			
		}
	}

	// 2-2) 빈방 예약(빈방이면 예약완료/ 아니면 예약불가능 -> 다시선택)
	public void bookRoom() {
		System.out.println("조회할 방 번호를 입력하세요.");
		System.out.println("입력>");
		int roomNo = Integer.parseInt(sc.nextLine());
		List<Room> list = RoomDAO.getInstance().checkRoom(CatMemberService.cmb.getMemberId());
			
		for(Room room : list) {
			if(room.getRoomState() == 0) {
				System.out.println(room.getRoomNumber()+"번 방은 예약 가능합니다.");
				System.out.println("1.예약하기 | 2.돌아가기");
				int menuNo = Integer.parseInt(sc.nextLine());
				
				if(menuNo == 1) {
					System.out.println("예약할 방 번호를 선택하세요");
					int roomN = Integer.parseInt(sc.nextLine());
					
					System.out.println("예약 기간을 입력하세요 (최소 1일, 최대 3달)");
					System.out.println("입력>");
					String dates = sc.nextLine();
					
					System.out.println("아이디를 입력하세요");
					System.out.println("입력>");
					String id = sc.nextLine();
					
					System.out.println("이름을 입력하세요");
					System.out.println("입력>");
					String name = sc.nextLine();
					
					System.out.println("고양이 이름을 입력하세요");
					System.out.println("입력>");
					String catName = sc.nextLine();
					
					room.setRoomNumber(roomN);
					room.setRoomDate(dates);
					room.setMemberId(id);
					room.setCatName(catName);
					room.setMemberName(name);
					
					list = RoomDAO.getInstance().bookRoom(room);
				} 
			}else if(room.getRoomState() == 1) {
				System.out.println(room.getRoomNumber()+"번 방은 이미 예약된 방입니다.");
			}
		}
	}
	
	// 예약 조회
	public void roomInfo() {
		System.out.println("ID를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();
		List<Room> list = RoomDAO.getInstance().checkRoom(id);
		System.out.println(CatMemberService.cmb.getMemberName() + "님의 예약 정보");
//		1) room_number 	 number not null,
//		2) room_state 	 number default '0', -- (0:빈방, 1:예약완료)
//		3) room_date	 char default '1' 	   -- 대여기간
//		4) member_id	 varchar2(20)
//		5) member_name	 varchar2(20)
//		5) cat_name 	 varchar2(10)
		for(Room room : list) {
			// member_id, member_name, room_number, room_date
			System.out.print("방 번호 : "+room.getRoomNumber());
			System.out.print(" | 예약 기간 : " + room.getRoomDate());
			System.out.print(" | 회원 ID : " + room.getMemberId());
			System.out.print(" | 회원 이름 : " +room.getMemberName());
			System.out.println();
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
