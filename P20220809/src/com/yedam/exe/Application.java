package com.yedam.exe;

import java.util.Scanner;

import com.yedam.catMember.CatMemberService;

public class Application {
	Scanner sc = new Scanner(System.in);
	int menuNo = 0;
	CatMemberService cms = new CatMemberService();
	
	public Application() {
		run();
	}

	private void run() {
		System.out.println("1.로그인 | 2.종료");
		menuNo = Integer.parseInt(sc.nextLine());
		switch(menuNo) {
		case 1:
			cms.doLogin();
			if(CatMemberService.cmb != null) {
				new ManageMent();
			}
		case 2:
			System.out.println("end of prog");
			break;
		}
	}
}
