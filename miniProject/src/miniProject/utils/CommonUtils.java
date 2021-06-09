package miniProject.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import miniProject.vo.Account;
import miniProject.vo.Fee;
import miniProject.vo.Food;
import miniProject.vo.Log;

public class CommonUtils {
	
	private static Scanner scanner = new Scanner(System.in);

	public static String nextLine() {
		return scanner.nextLine();
	}
	
	public static int nextInt() {
		return Integer.parseInt(scanner.nextLine());
	}

	public static void printLogo() {
		System.out.println("                  __  ____  ____  ______    _   __   ____  __________  ___    _   ________ ");
		System.out.println("                 / / / / / / /  |/  /   |  / | / /  / __ \\/ ____/ __ )/   |  / | / / ____/");
		System.out.println("                / /_/ / / / / /|_/ / /| | /  |/ /  / /_/ / /   / __  / /| | /  |/ / / __   ");
		System.out.println("               / __  / /_/ / /  / / ___ |/ /|  /  / ____/ /___/ /_/ / ___ |/ /|  / /_/ /   ");
		System.out.println("              /_/ /_/\\____/_/  /_/_/  |_/_/ |_/  /_/    \\____/_____/_/  |_/_/ |_/\\____/ ");
		System.out.println("                                                                                           ");
		System.out.println("=====================================================================================================");
		System.out.println("                                휴먼피시방에 오신 것을 환영합니다.                                   ");
	}
	
	public static void printWelcom(String id) {
		System.out.println("=====================================================================================================");
		System.out.printf("                                    %10s 님 환영합니다.\n", id);
	}
	
	public static void printInitialMenu() {
		System.out.println("================================================ 홈 =================================================");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │   1. 회원가입    │  │    2. 로그인     │  │     3. 종 료     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
		
	/**
	 * 파일을 읽어올 ObjectInputStream을 생성
	 * @param fileName
	 * 			읽어올 파일명
	 * @return
	 * 			읽어올 파일명으로 생성된 ObjectInputStream 객체
	 */
	public static ObjectInputStream inputObjectFile(String fileName) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ois;
	}
	/**
	 * ArrayList배열을 파일로 저장
	 * @param fileName
	 * 			저장(생성)할 파일의 이름
	 * @param list
	 * 			파일에 저장할 ArrayList 배열
	 */
	public static void save(String fileName, ArrayList<?> list) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));			
			oos.writeObject(list);
			oos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 회원가입 횟수(memNum)를 파일로 저장
	 * @param fileName
	 * 				저장할 파일명(memNum.ser)
	 * @param num
	 * 				회원가입 횟수가 담긴 변수(memNum)
	 */
	public static void save(String fileName, int num) {
		try {
			DataOutputStream fos = new DataOutputStream(new FileOutputStream(fileName));
			fos.writeInt(num);
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void save(String fileName, int sales, int purchase) {
		try {
			DataOutputStream fos = new DataOutputStream(new FileOutputStream(fileName));
			fos.writeInt(sales);
			fos.writeInt(purchase);
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
				
	/**
	 * 글자 하나당 2byte로 계산할지 1byte로 계산할지를 알려주는 메서드, 파라미터가 한글일 경우 2byte를 그렇지 않을 경우는
	 * 1byte로 반환한다.
	 * 
	 * @param c
	 *            입력할 글자
	 * @return 반환될 숫자
	 */
	private static int getCharCount(char c) {
		int cnt = 0;
		if (c >= '가' && c <= '힣') {
			cnt++;
		}
		cnt++;
		return cnt;
	}

	/**
	 * 문자열을 입력받아 바이트 갯수를 리턴해주는 메서드, 한글이 포함된경우 2를 그렇지 않은 경우는 1을 가산한다.
	 * @param str
	 *            세야할 글자
	 * @return int 타입의 리턴
	 */
	public static int getStringByteCount(String str) {
		char[] words = str.toCharArray();
		int cnt = 0;
		for (char c : words) {
			cnt += getCharCount(c);
		}
		return cnt;
	}

	/**
	 * 콘솔에 출력할 문자열의 길이가 부적절하게 길 경우 지정된 크기만큼을 자른다.
	 * @param strs
	 *            잘라야할 원문
	 * @param lastIdx
	 *            잘라야할 길이
	 * @return 잘라서 완성된 문자열을 반환
	 */
	public static String subContent(String str, int len) {
		StringBuilder ret = new StringBuilder();
		char[] ori = str.toCharArray();
		int cnt = 0;
		for (char c : ori) {
			if (len / 2 * 2 - 3 < cnt) {
				ret = new StringBuilder(ret.substring(0, ret.length() - 2) + "..");
				break;
			}
			cnt += getCharCount(c);
			ret.append(c);
		}
		return ret.toString();
	}

	/**
	 * 출력시 필요한 문자열 배열과 각 배열의 한계길이값을 지정한 정수 배열을 받아와 하나의 스트링을 반환
	 * @param strs
	 *            출력할 텍스트 배열
	 * @param len
	 *            출력될 텍스트 포맷의 한계 길이
	 * @return 조합후 완성된 문자열을 반환한다.
	 */
	public static String format(String[] strs, int[] len) {
		String ret = "";

		for (int i = 0; i < strs.length; i++) {
			int cnt = len[i];
			strs[i] = subContent(strs[i], cnt);
			cnt = cnt - getStringByteCount(strs[i]) + strs[i].length();
			ret += "%-" + cnt + "s";
		}
		return String.format(ret, (Object[]) strs);
	}
	
	/**
	 * 회원목록에 저장된 모든 회원객체의 정보를 출력
	 * @param list
	 * 			모든 회원객체의 정보를 출력할 회원목록
	 */
	public static void printMemberList(ArrayList<Account> list) {
		String[] menus = {"회원번호", "아이디", "비밀번호", "전화번호", "남은시간", "이용상태", "회원여부"};
		String[] str = new String[7];
		int[] len = {10, 20, 20, 20, 10, 10, 10};
		
		System.out.println("==========================================< 회원목록 >===============================================");
		System.out.println(format(menus, len));
		for(int i = 0; i < list.size(); i++) {
			str[0] = Integer.toString(list.get(i).getNum());
			str[1] = list.get(i).getId();
			str[2] = list.get(i).getPw();
			str[3] = list.get(i).getPhone();
			str[4] = list.get(i).getRemainTime() + "분";
			str[5] = (list.get(i).isStatus()) ? "이용중" : "";
			str[6] = (list.get(i).isMember()) ? "회원" : "비회원";				
			System.out.println(format(str, len));
		}
		System.out.println("=====================================================================================================");
	}
	
	/**
	 * 회원목록에서 특정 회원객체의 정보를 출력
	 * @param user
	 * 			회원목록에서 찾을 회원객체
	 */
	public static void printMemberInfo(Account user) {
		String[] menus = {"회원번호", "아이디", "비밀번호", "전화번호", "남은시간", "이용상태", "회원여부"};
		String[] str = new String[7];
		int[] len = {10, 20, 20, 20, 10, 10, 10};
		
		System.out.println("==========================================< 회원정보 >===============================================");
		System.out.println(format(menus, len));
		str[0] = Integer.toString(user.getNum());
		str[1] = user.getId();
		str[2] = printPw(user.getPw());
		str[3] = user.getPhone();
		str[4] = user.getRemainTime() + "분";
		str[5] = (user.isStatus()) ? "이용중" : "";
		str[6] = (user.isMember()) ? "회원" : "비회원";		
		System.out.println(format(str, len));
		System.out.println("=====================================================================================================");
	}
	
	/**
	 * 회원목록에서 특정 인덱스에 존재하는 회원객체의 정보를 출력
	 * @param idx
	 * 			회원객체를 찾는데 참조할 인덱스
	 * @param list
	 * 			회원객체를 검색할 회원 목록
	 */
	public static void printMemberInfo(int idx, ArrayList<Account> list) {
		String[] menus = {"회원번호", "아이디", "비밀번호", "전화번호", "남은시간", "이용상태", "회원여부"};
		String[] str = new String[7];
		int[] len = {10, 20, 20, 20, 10, 10, 10};
		
		System.out.println("==========================================< 회원정보 >===============================================");
		System.out.println(format(menus, len));
		str[0] = Integer.toString(list.get(idx).getNum());
		str[1] = list.get(idx).getId();
		str[2] = list.get(idx).getPw();
		str[3] = list.get(idx).getPhone();
		str[4] = list.get(idx).getRemainTime() + "분";
		str[5] = (list.get(idx).isStatus()) ? "이용중" : "";
		str[6] = (list.get(idx).isMember()) ? "회원" : "비회원";			
		System.out.println(format(str, len));
		System.out.println("=====================================================================================================");
	}
	
	public static void printFoodInfo(int idx, ArrayList<Food> list) {
		String[] menus = {"종류", "품번", "상품명", "판매가격", "구입가격", "재고"};
		String[] str = new String[6];
		int[] len = {15, 10, 20, 10, 10, 10};
		
		System.out.println("==========================================< 재고현황 >===============================================");
		System.out.println(format(menus, len));
		str[0] = list.get(idx).getKind();
		str[1] = Integer.toString(list.get(idx).getItemNum());
		str[2] = list.get(idx).getName();
		str[3] = Integer.toString(list.get(idx).getPrice());
		str[4] = Integer.toString(list.get(idx).getPurchasePrice());
		str[5] = Integer.toString(list.get(idx).getStock());
		System.out.println(format(str, len));
		System.out.println("=====================================================================================================");
	}
	
	public static void printFoodInfo(Food food) {
		String[] menus = {"종류", "품번", "상품명", "판매가격", "구입가격", "재고"};
		String[] str = new String[6];
		int[] len = {15, 10, 20, 10, 10, 10};
		
		System.out.println("==========================================< 재고현황 >===============================================");
		System.out.println(format(menus, len));
		str[0] = food.getKind();
		str[1] = Integer.toString(food.getItemNum());
		str[2] = food.getName();
		str[3] = Integer.toString(food.getPrice());
		str[4] = Integer.toString(food.getPurchasePrice());
		str[5] = Integer.toString(food.getStock());
		System.out.println(format(str, len));
		System.out.println("=====================================================================================================");
	}
		
	public static void printFeeInfo(Fee fee) {
        String[] menus = {"품번", "상품명", "판매가격"};
        String[] str = new String[3];
        int[] len = {10, 20, 10};
		
        System.out.println("==========================================< 요금목록 >===============================================");
        System.out.println(format(menus, len));
        str[0] = Integer.toString(fee.getItemNum());
        str[1] = fee.getName();
        str[2] = Integer.toString(fee.getPrice());
        System.out.println(format(str, len));
        System.out.println("=====================================================================================================");
	}
	
	public static void printFoodList(int start, int end, ArrayList<Food> list) {
		String[] menus = {"종류", "품번", "상품명", "판매가격", "구입가격", "재고"};
		String[] str = new String[6];
		int[] len = {15, 10, 20, 10, 10, 10};

		System.out.println("==========================================< 재고현황 >===============================================");
		System.out.println(format(menus, len));
		for(int i = start; i < end; i++) {
			str[0] = list.get(i).getKind();
			str[1] = Integer.toString(list.get(i).getItemNum());
			str[2] = list.get(i).getName();
			str[3] = Integer.toString(list.get(i).getPrice());
			str[4] = Integer.toString(list.get(i).getPurchasePrice());
			str[5] = Integer.toString(list.get(i).getStock());
			System.out.println(format(str, len));
		}
		System.out.println("=====================================================================================================");
	}
	
	public static void printFoodList(ArrayList<Food> list) {
		String[] menus = {"종류", "품번", "상품명", "판매가격", "구입가격", "재고"};
		String[] str = new String[6];
		int[] len = {15, 10, 20, 10, 10, 10};

		System.out.println("==========================================< 재고현황 >===============================================");
		System.out.println(format(menus, len));
		for(int i = 0; i < list.size(); i++) {
			str[0] = list.get(i).getKind();
			str[1] = Integer.toString(list.get(i).getItemNum());
			str[2] = list.get(i).getName();
			str[3] = Integer.toString(list.get(i).getPrice());
			str[4] = Integer.toString(list.get(i).getPurchasePrice());
			str[5] = Integer.toString(list.get(i).getStock());
			System.out.println(format(str, len));
		}
		System.out.println("=====================================================================================================");
	}
	
	public static void printOrderLog(ArrayList<Log> list) {
		String[] menus = {"종류", "상품명", "개수", "구입가격", "총액", "구입날짜"};
		String[] str = new String[6];
		int[] len = {10, 25, 10, 10, 20, 30};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		int total = 0;

		System.out.println("==========================================< 구매내역 >===============================================");
		System.out.println(format(menus, len));
		for(int i = 0; i < list.size(); i++) {
			Food food = (Food) list.get(i).getItem();
			str[0] = food.getKind();
			str[1] = food.getName();
			str[2] = Integer.toString(list.get(i).getItemNum());
			str[3] = printNumPerThou(food.getPurchasePrice());
			str[4] = printNumPerThou(list.get(i).getTotal());
			str[5] = sdf.format(list.get(i).getDate());
			total += list.get(i).getTotal();
			System.out.println(format(str, len));
		}
		System.out.println("총계 : " + printNumPerThou(total) + "원");
		System.out.println("=====================================================================================================");
	}
	
	public static void printSalesLog(ArrayList<Log> list) {
		String[] menus = {"종류", "상품명", "구매자", "개수", "판매가격", "총액", "판매날짜"};
		String[] str = new String[7];
		int[] len = {10, 20, 15, 10, 10, 15, 25};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		int total = 0;

		System.out.println("==========================================< 판매내역 >===============================================");
		System.out.println(format(menus, len));
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getItem() instanceof Food) {
				Food food = (Food) list.get(i).getItem();
				str[0] = food.getKind();
				str[1] = food.getName();
				str[2] = list.get(i).getUser().getId();
				str[3] = Integer.toString(list.get(i).getItemNum());
				str[4] = printNumPerThou(food.getPrice());
				str[5] = printNumPerThou(list.get(i).getTotal());
				str[6] = sdf.format(list.get(i).getDate());
				total += list.get(i).getTotal();
			} else if(list.get(i).getItem() instanceof Fee) {
				Fee fee = (Fee) list.get(i).getItem();
				str[0] = "요금결제";
				str[1] = fee.getName();
				str[2] = list.get(i).getUser().getId();
				str[3] = "1";
				str[4] = printNumPerThou(fee.getPrice());
				str[5] = printNumPerThou(list.get(i).getTotal());
				str[6] = sdf.format(list.get(i).getDate());
				total += list.get(i).getTotal();
			}
			System.out.println(format(str, len));
		}
		System.out.println("총계 : " + printNumPerThou(total) + "원");
		System.out.println("=====================================================================================================");
	}
	
	
	/**
	 * 패스워드 문자열을 입력받아 첫글자와 마지막 글자를 제외하고 *(별표)로 출력
	 * @param pw
	 * 			출력할 패스워드 문자열
	 */
	public static String printPw(String pw) {
		String pwTmp = "";
		pwTmp += pw.charAt(0);
		for (int i = 1; i < pw.length()-1; i++) {
			pwTmp += '*';
		}
		pwTmp += pw.charAt(pw.length()-1);
		return pwTmp;
	}
		
	public static int findByItemNum(int start, int end, int itemNum, ArrayList<Food> list) {
		for(int i = start; i < end; i++) {
			if(list.get(i).getItemNum() == itemNum) return i;
		}
		return -1;
	}
		
	/**
	 * 회원가입 취소시 자동증가되는 회원가입 횟수를 감소시켜 저장
	 */
	public static void decMemNum() {
		Account.setMemNum(Account.getMemNum()-1);
		save("memNum.ser", Account.getMemNum());
	}
		
	/**
	 * 숫자를 입력받아 천단위로 ','를 삽입하여 출력
	 * @param num
	 * 			입력받는 정수
	 */
	public static String printNumPerThou(long num) {
		String str = String.format("%,d", num);
		return str;
	}
}
