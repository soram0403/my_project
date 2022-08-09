package com.yedam.cat;

import java.util.Scanner;

public class CatService {
	
	public static Cat cat = null;
	Scanner sc = new Scanner(System.in);
	
	// 등록
	public void registerCat() {
		Cat cat = new Cat();
		
		System.out.println("회원 아이디를 입력하세요");
		System.out.println("입력>");
		String id = sc.nextLine();
		
		System.out.println("고양이 이름을 입력하세요");
		System.out.println("입력>");
		String catName = sc.nextLine();
		
		System.out.println("고양이 성격을 입력하세요");
		System.out.println("입력>");
		String catCharacter = sc.nextLine();
		
		cat.setMemberId(id);
		cat.setCatName(catName);
		cat.setCatCharacter(catCharacter);
		
		int result = CatDAO.getInstance().registerCat(cat);
		
		if(result == 1) {
			System.out.println("등록이 완료되었습니다.");
		} else {
			System.out.println("등록이 실패했습니다.");
		}
		
	}
	
	// 수정
	public void updateCat() {
		
	}
	
	// 삭제
}
