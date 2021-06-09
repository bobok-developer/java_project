package miniProject.vo;

import static miniProject.utils.CommonUtils.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Data {
	/**
	 * 최초 한 번만 실행(데이터 초기화 목적 이외 실행 금지)
	 */
	public static void main(String[] args) {
		ArrayList<Account> members = new ArrayList<Account>();
		ArrayList<Food> menuList = new ArrayList<Food>();
		ArrayList<Fee> feeList = new ArrayList<Fee>();
		ArrayList<Log> salesLog = new ArrayList<Log>();			// 판매기록
		ArrayList<Log> orderLog = new ArrayList<Log>();			// 구매(발주)기록
		int sales = 0;
		int purchase = 0;
		
		// 기본 계정
		members.add(new Account("admin", "admin", ""));					// 관리자계정
		members.add(new Account("bobo1", "bobo1", "01011112222"));		// 일반회원 1
		members.add(new Account("cks12", "cks12", "01022223333"));		// 일반회원 2
		members.add(new Account("soyo123", "soyo123", "01025253434"));	// 일반회원 3
		members.add(new Account("minu98", "minu98", "01099998888"));	// 일반회원 4
		save("memberList.ser", members);
		
		// 식사 메뉴(상품번호 1~24)
		menuList.add(new Food("식사", 1, "기본라면", 3500));
		menuList.add(new Food("식사", 2, "계란라면", 3800));
		menuList.add(new Food("식사", 3, "치즈라면", 4000));
		menuList.add(new Food("식사", 4, "불닭볶음면", 3500));
		menuList.add(new Food("식사", 5, "진짬뽕", 4000));
		menuList.add(new Food("식사", 6, "짜파게티", 3800));
		menuList.add(new Food("식사", 7, "우동", 4000));
		menuList.add(new Food("식사", 8, "새우튀김우동", 4800));
		menuList.add(new Food("식사", 9, "잔치국수", 3500));
		menuList.add(new Food("식사", 10, "냉모밀", 4000));
		menuList.add(new Food("식사", 11, "치킨마요", 4000));
		menuList.add(new Food("식사", 12, "참치마요", 4000));
		menuList.add(new Food("식사", 13, "고기만두(4개)", 2800));
		menuList.add(new Food("식사", 14, "김치만두(4개)", 2800));
		menuList.add(new Food("식사", 15, "군만두", 3000));
		menuList.add(new Food("식사", 16, "떡볶이", 3500));
		menuList.add(new Food("식사", 17, "라볶이", 4200));
		menuList.add(new Food("식사", 18, "제육덮밥", 5000));
		menuList.add(new Food("식사", 19, "김치볶음밥", 4500));
		menuList.add(new Food("식사", 20, "돈까스", 5500));
		menuList.add(new Food("식사", 21, "치킨버거", 3500));
		menuList.add(new Food("식사", 22, "불고기버거", 3500));
		menuList.add(new Food("식사", 23, "치즈추가", 500));
		menuList.add(new Food("식사", 24, "공기밥", 1000));
		
		// 사이드 메뉴(상품번호 25~36)
		menuList.add(new Food("사이드", 25, "감자튀김", 3000));
		menuList.add(new Food("사이드", 26, "새우튀김(3개)", 3500));
		menuList.add(new Food("사이드", 27, "고구마맛탕", 3000));
		menuList.add(new Food("사이드", 28, "핫도그", 2000));
		menuList.add(new Food("사이드", 29, "소떡소떡", 3500));
		menuList.add(new Food("사이드", 30, "피카츄돈까스", 1200));
		menuList.add(new Food("사이드", 31, "콜팝치킨", 3500));
		menuList.add(new Food("사이드", 32, "타코야끼(8개)", 3500));
		menuList.add(new Food("사이드", 33, "치즈스틱(4개)", 3000));
		menuList.add(new Food("사이드", 34, "수제소세지(2개)", 3000));
		menuList.add(new Food("사이드", 35, "호떡(2개)", 2000));
		menuList.add(new Food("사이드", 36, "붕어빵(3개)", 2000));
		
		// 과자 메뉴(상품번호 37~50)
		menuList.add(new Food("과자", 37, "포카칩", 1500));
		menuList.add(new Food("과자", 38, "나초", 1500));
		menuList.add(new Food("과자", 39, "홈런볼", 1500));
		menuList.add(new Food("과자", 40, "콘칩", 1500));
		menuList.add(new Food("과자", 41, "자갈치", 1500));
		menuList.add(new Food("과자", 42, "오징어땅콩", 2000));
		menuList.add(new Food("과자", 43, "츄러스", 1500));
		menuList.add(new Food("과자", 44, "양파링", 1500));
		menuList.add(new Food("과자", 45, "숏다리", 1500));
		menuList.add(new Food("과자", 46, "누네띠네", 1200));
		menuList.add(new Food("과자", 47, "칸초", 1200));
		menuList.add(new Food("과자", 48, "빼빼로", 1200));
		menuList.add(new Food("과자", 49, "아몬드빼빼로", 1500));
		menuList.add(new Food("과자", 50, "누드빼빼로", 1500));
		
		// 음료수 메뉴(상품번호 51~67)
		menuList.add(new Food("음료", 51, "마운틴듀", 1500));
		menuList.add(new Food("음료", 52, "밀키스", 1500));
		menuList.add(new Food("음료", 53, "사이다", 1500));
		menuList.add(new Food("음료", 54, "콜라", 1500));
		menuList.add(new Food("음료", 55, "웰치스", 1500));
		menuList.add(new Food("음료", 56, "환타", 1500));
		menuList.add(new Food("음료", 57, "몬스터에너지", 2000));
		menuList.add(new Food("음료", 58, "깔라만시에이드", 3000));
		menuList.add(new Food("음료", 59, "레드자몽에이드", 3000));
		menuList.add(new Food("음료", 60, "청포도에이드", 3000));
		menuList.add(new Food("음료", 61, "레몬에이드", 2000));
		menuList.add(new Food("음료", 62, "레몬콕", 2000));
		menuList.add(new Food("음료", 63, "체리콕", 2000));
		menuList.add(new Food("음료", 64, "체리에이드", 2000));
		menuList.add(new Food("음료", 65, "블루베리에이드", 2000));
		menuList.add(new Food("음료", 66, "블루레몬에이드", 2000));
		menuList.add(new Food("음료", 67, "망고에이드", 2000));
		
		// 커피/차 메뉴(상품번호 68~97)
		menuList.add(new Food("커피/차", 68, "레몬유자차", 2000));
		menuList.add(new Food("커피/차", 69, "자몽차", 2000));
		menuList.add(new Food("커피/차", 70, "캐모마일", 2000));
		menuList.add(new Food("커피/차", 71, "얼그레이", 2000));
		menuList.add(new Food("커피/차", 72, "페퍼민트", 2000));
		menuList.add(new Food("커피/차", 73, "자스민차", 2000));
		menuList.add(new Food("커피/차", 74, "녹차", 2000));
		menuList.add(new Food("커피/차", 75, "히비스커스차", 2000));
		menuList.add(new Food("커피/차", 76, "복숭아 아이스티", 1500));
		menuList.add(new Food("커피/차", 77, "믹스베리 아이스티", 2000));
		menuList.add(new Food("커피/차", 78, "아이스커피", 2000));
		menuList.add(new Food("커피/차", 79, "아메리카노 HOT", 2000));
		menuList.add(new Food("커피/차", 80, "카페라떼 HOT", 2500));
		menuList.add(new Food("커피/차", 81, "카페모카 HOT", 2500));
		menuList.add(new Food("커피/차", 82, "바닐라라떼 HOT", 2500));
		menuList.add(new Food("커피/차", 83, "카라멜마끼아또 HOT", 2500));
		menuList.add(new Food("커피/차", 84, "흑당카페라떼 HOT", 2500));
		menuList.add(new Food("커피/차", 85, "핫초코", 2500));
		menuList.add(new Food("커피/차", 86, "아메리카노 ICE", 2200));
		menuList.add(new Food("커피/차", 87, "카페라떼 ICE", 2700));
		menuList.add(new Food("커피/차", 88, "카페모카 ICE", 2700));
		menuList.add(new Food("커피/차", 89, "바닐라라떼 ICE", 2700));
		menuList.add(new Food("커피/차", 90, "녹차스무디", 3500));
		menuList.add(new Food("커피/차", 91, "딸기스무디", 3500));
		menuList.add(new Food("커피/차", 92, "요거트스무디", 3500));
		menuList.add(new Food("커피/차", 93, "망고스무디", 3500));
		menuList.add(new Food("커피/차", 94, "바나나스무디", 3500));
		menuList.add(new Food("커피/차", 95, "블루베리스무디", 3500));
		menuList.add(new Food("커피/차", 96, "아이스딸기라떼", 3000));
		menuList.add(new Food("커피/차", 97, "아이스녹차라떼", 3000));
		save("menuList.ser", menuList);
		
		// 비회원요금목록
		feeList.add(new Fee("1시간", 1, 1200, 60, false));
		feeList.add(new Fee("2시간", 2, 2400, 120, false));
		feeList.add(new Fee("3시간", 3, 3500, 180, false));
		feeList.add(new Fee("4시간", 4, 4500, 240, false));
		feeList.add(new Fee("5시간", 5, 5500, 300, false));
		feeList.add(new Fee("10시간", 6, 10000, 600, false));
		feeList.add(new Fee("20시간", 7, 20000, 1200, false));
		
		// 회원요금목록
		feeList.add(new Fee("1시간", 1, 900, 60, true));
		feeList.add(new Fee("2시간", 2, 1800, 120, true));
		feeList.add(new Fee("3시간", 3, 3000, 180, true));
		feeList.add(new Fee("4시간", 4, 3500, 240, true));
		feeList.add(new Fee("5시간", 5, 4500, 300, true));
		feeList.add(new Fee("10시간", 6, 8000, 600, true));
		feeList.add(new Fee("20시간", 7, 16000, 1200, true));
		save("feeList.ser", feeList);
		
		// 매출매입장부
		save("transaction.ser", sales, purchase);
		
		// 판매내역
		save("salesLog.ser", salesLog);
		
		// 구매내역
		save("orderLog.ser", orderLog);
	}
}
