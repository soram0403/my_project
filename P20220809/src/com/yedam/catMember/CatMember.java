package com.yedam.catMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatMember {
//	MEMBER_ID   NOT NULL VARCHAR2(20)  
//	MEMBER_PW   NOT NULL VARCHAR2(20)  
//	MEMBER_NAME NOT NULL VARCHAR2(20)  
//	MEMBER_TEL  NOT NULL VARCHAR2(15)  
//	MEMBER_ADDR          VARCHAR2(100) 
//	ROLE                 CHAR(1)       
//	DATES                DATE
	// room_number		 number
//	CAT_CHARACTER NOT NULL VARCHAR2(30)  
//	SPECIAL_NOTE           VARCHAR2(100) 
	
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberTel;
	private String memberAddr;
	private String catName;
	private String role;
	private String dates;
	private int roomNumber;
	private String catCharacter;
	private String specialNote;
}
