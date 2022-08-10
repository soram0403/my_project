package com.yedam.room;

import java.util.ArrayList;
import java.util.List;

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
			String sql = "select room_number, room_state from room";
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
	
	public List<Room> checkRoom(String id) {
		List<Room> list = new ArrayList<>();
		Room room = null;

//		ROOM_NUMBER NOT NULL NUMBER       
//		ROOM_STATE           NUMBER       
//		MEMBER_ID            VARCHAR2(20) 
//		CAT_NAME             VARCHAR2(10) 
//		ROOM_DATE            VARCHAR2(20) 
//		MEMBER_NAME          VARCHAR2(20) 
		try { 
			conn();
			String sql = "select room_number, room_state, room_date, member_name from room where member_id=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				room = new Room();

				room.setRoomNumber(rs.getInt("room_number"));
				room.setRoomDate(rs.getString("room_date"));
				room.setMemberId(rs.getString("member_id"));
				room.setMemberName(rs.getString("member_name"));
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

	// 빈방 예약(빈방이면 예약완료/ 아니면 예약불가능 -> 다시선택)
	public List<Room> bookRoom(Room room) {
		List<Room> list = new ArrayList<>();

		int result = 0;
		try {
			conn();
			String sql = "update room set member_id = ?, cat_name = ?, member_name = ?, room_date = ?, room_state = 1 where room_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room.getMemberId());
			pstmt.setString(2, room.getCatName());
			pstmt.setString(3, room.getRoomDate());
			pstmt.setString(4, room.getMemberName());
			pstmt.setInt(5, room.getRoomNumber());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	// 예약 취소 (update room_state)
	public int cancelRoom(String memberId) {
		int result = 0;
		try {
			conn();
			String sql = "update room_state = 0 where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

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
