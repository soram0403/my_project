package com.yedam.room;

import java.util.ArrayList;
import java.util.List;

import com.yedam.catMember.CatMemberService;
import com.yedam.common.DAO;

public class RoomDAO extends DAO {
	private static RoomDAO roomdao = null;

	private RoomDAO() {
	}

	public static RoomDAO getInstance() {
		return roomdao == null ? roomdao = new RoomDAO() : roomdao;
	}

//	ROOM_NUMBER NOT NULL NUMBER       
//	ROOM_STATE           NUMBER       
//	MEMBER_ID            VARCHAR2(20) 
//	CAT_NAME             VARCHAR2(10) 
//	ROOM_DATE            VARCHAR2(20) 
//	MEMBER_NAME          VARCHAR2(20) 

	// 빈방여부 조회
	public List<Room> emptyRoom() {
		List<Room> list = new ArrayList<>();
		Room room = null;
		try {
			conn();
			String sql = "select room_number, room_state from room order by room_number";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				room = new Room();
				room.setRoomNumber(rs.getInt("room_number"));
				room.setRoomState(rs.getInt("room_state"));

				list.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 빈방여부 확인
	public List<Room> checkRoom(int RoomNo) {
		List<Room> list = new ArrayList<>();
		Room room = null;
		try {
			conn();
			String sql = "select room_state from room where room_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, RoomNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				room = new Room();

				room.setRoomState(rs.getInt("room_state"));
				room.setRoomNumber(RoomNo);

				list.add(room);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 빈방 예약(빈방이면 예약완료/ 아니면 예약불가능 -> 다시선택)
	public int bookRoom(Room room) {
		int result = 0;

		try {
			conn();
			String sql = "update room set room_state=1, member_id=?, cat_name=?, room_date=?, member_name =? where room_number = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room.getMemberId());
			pstmt.setString(2, room.getCatName());
			pstmt.setInt(3, room.getRoomDate());
			pstmt.setString(4, room.getMemberName());
			pstmt.setInt(5, room.getRoomNumber());

			result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("예약 완료 되었습니다.");
			} else {
				System.out.println("예약 실패입니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 예약할 시 가격 출력 (일반결제)
	public List<Room> priceInfo(int roomNo) {
		List<Room> list = new ArrayList<>();
		Room room = null;
		try {
			conn();
			String sql = "select room_date from room where room_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				room = new Room();

				room.setRoomDate(rs.getInt("room_date"));
				room.setRoomNumber(roomNo);
				list.add(room);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

//	 예약할 시 가격 출력 (멤버쉽 : 20% 할인, 포인트 2000점씩 사용)
	public List<Room> salePriceInfo(int roomNo) {
		List<Room> list = new ArrayList<>();
		Room room = null;
		try {
			conn();
			String sql = "select room_date from room where room_number = ?"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String sql2 = "update catmember set points = points-2000 where member_id= ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, room.getMemberId());
				
				int result = pstmt.executeUpdate();
				
				if(result == 1) {
					System.out.println("결제 완료.");
				} else {
					System.out.println("결제 실패.");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 예약 조회
	public List<Room> roomInfo(String memberId) {
		List<Room> list = new ArrayList<>();
		Room room = null;
		try {
			conn();
			String sql = "select room_number, member_name, cat_name, room_date from room where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				room = new Room();

				room.setRoomNumber(rs.getInt("room_number"));
				room.setMemberName(rs.getString("member_name"));
				room.setCatName(rs.getString("cat_name"));
				room.setRoomDate(rs.getInt("room_date"));
				room.setMemberId(memberId);

				list.add(room);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 예약 취소 (update room_state)
	public int cancelRoom(int roomNo) {
		int result = 0;
		try {
			conn();
			String sql = "update room set room_state = 0, member_id='' , cat_name='',room_date=0, member_name='' where room_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNo);

			result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("예약 취소 완료.");
			} else {
				System.out.println("예약 취소 실패.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}
}
