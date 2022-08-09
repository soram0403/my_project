package com.yedam.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {

//	ROOM_NUMBER NOT NULL NUMBER       
//	ROOM_STATE           NUMBER       
//	MEMBER_ID            VARCHAR2(20) 
//	CAT_NAME             VARCHAR2(10) 
//	ROOM_DATE   		 VARCHAR2(20) 
//	MEMBER_NAME          VARCHAR2(20)
	
	private int roomNumber;
	private int roomState;
	private String memberId;
	private String memberName;
	private String catName;
	private String roomDate;
	
}
