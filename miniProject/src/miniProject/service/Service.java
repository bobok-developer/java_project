package miniProject.service;

import static miniProject.utils.CommonUtils.*;
import miniProject.vo.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class Service {
	private int sales;												// 매출
	private int purchase;											// 매입
	private Account loginUser = null;								// 현재 로그인된 계정의 정보를 담을 객체
	private Account[] seats = new Account[5];						// 좌석목록
	private ArrayList<Account> members = new ArrayList<Account>();	// 회원목록
	private ArrayList<Food> menuList = new ArrayList<Food>();		// 메뉴목록
	private ArrayList<Fee> feeList = new ArrayList<Fee>();			// 요금목록
	private ArrayList<Long> times = new ArrayList<>();				// 이용시간
	private ArrayList<Log> salesLog = new ArrayList<Log>();			// 판매기록
	private ArrayList<Log> orderLog = new ArrayList<Log>();			// 구매(발주)기록
	
	{
		try {
			ObjectInputStream ois = null;
			ois = inputObjectFile("memberList.ser");			
			members = (ArrayList<Account>) ois.readObject();
			ois.close();
			
			ois = inputObjectFile("menuList.ser");
			menuList = (ArrayList<Food>) ois.readObject();
			ois.close();
			
			ois = inputObjectFile("salesLog.ser");			
			salesLog = (ArrayList<Log>) ois.readObject();
			ois.close();
			
			ois = inputObjectFile("orderLog.ser");			
			orderLog = (ArrayList<Log>) ois.readObject();
			ois.close();
			
			ois = inputObjectFile("feeList.ser");			
			feeList = (ArrayList<Fee>) ois.readObject();
			ois.close();
			
			DataInputStream dis = new DataInputStream(new FileInputStream("transaction.ser"));	// 장부에 저장된 매출/매입액 불러오기
			sales = dis.readInt();	
			purchase = dis.readInt();
			dis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	// 회원가입
	/**
	 * @author 민우
	 */
	public void register() {
		while(true) {
			Account tmpUser = insertInfo();
			printMemberInfo(tmpUser);
			try {
				System.out.print("입력한 정보가 맞습니까? 1. 예 2. 아니오 3. 종료 > ");
				int choice = nextInt();
				
				switch(choice) {
				case 1:
					members.add(tmpUser);
					save("memberList.ser", members);
					return;
				case 2:
					decMemNum();
					break;
				case 3:
					decMemNum();
					return;
				default:
					System.out.println("잘못 입력했습니다.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Account findById(String id) {
		for(int i = 0; i < members.size(); i++) {
			if(id.equals(members.get(i).getId())) return members.get(i);
		}
		return null;
	}
	
	Account findByPhone(String phone) {
		for(int i = 0; i < members.size(); i++) {
			if(phone.equals(members.get(i).getPhone())) return members.get(i);
		}
		return null;
	}
	
	/**
	 * 회원가입 정보를 입력받아 해당 정보로 생성된 Account 객체를 반환
	 * @author 민우
	 * @return
	 * 		입력받은 정보로 생성된 Account 객체
	 */
	Account insertInfo() {
		System.out.println("=====================================================================================================");
		System.out.println("회원가입 정보를 입력합니다.");
		String id = insertId();
		String pw = insertPw();
		String phone = insertPhone();
		
		Account user = new Account(id, pw, phone);
		
		return user;
	}
		
	/**
	 * 아이디를 입력받아 검사 후 통과시 아이디 문자열을 반환
	 * @author 민우
	 * @return
	 * 		아이디 문자열
	 */
	String insertId() {
		while(true) {
			System.out.print("아이디 > ");
			String id = nextLine();

			if(checkId(id)) {
				return id;
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean checkId(String id) {
		if(!isOnlyChar(id)) {
			System.out.println("[오류] 아이디는 영문, 숫자가 포함된 5자 이상 13자 미만의 문자열이어야 합니다.");
			return false;
		}
		if(isIdExist(id)) {
			System.out.println("[오류] " + id + "은(는) 이미 존재하는 아이디입니다.");
			return false;
		}
		return true;
	}
	
	/**
	 * 비밀번호를 입력받아 검사 후 통과시 비밀번호 문자열을 반환
	 * @author 민우
	 * @return
	 * 		비밀번호 문자열
	 */
	String insertPw() {
		while(true) {
			System.out.print("비밀번호(5자이상) > ");
			String pw = nextLine();
			System.out.print("비밀번호확인 > ");
			String pwck = nextLine();
			
			if(checkPw(pw, pwck)) {
				return pw;
			}
		}
	}
	
	/**
	 * 
	 * @param pw
	 * @param pwck
	 * @return
	 */
	boolean checkPw(String pw, String pwck) {
		if(!isOnlyChar(pw)) {
			System.out.println("[오류] 비밀번호는 영문, 숫자가 포함된 5자 이상 12자 미만의 문자열이어야 합니다.");
			return false;
		}
		if(!isPwMatch(pw, pwck)) {
			System.out.println("[오류] 입력한 비밀번호와 일치하지 않습니다.");
			return false;
		}
		return true;
	}
	
	/**
	 * 전화번호를 입력받아 검사 후 통과시 전화번호 문자열을 반환
	 * @author 민우
	 * @return
	 * 		전화번호 문자열
	 */
	String insertPhone() {
		while(true) {
			System.out.print("전화번호(숫자로만입력) > ");
			String phone = nextLine();

			if(checkPhone(phone)) {
				return phone;
			}
		}
	}
	/**
	 * 
	 * @param phone
	 * @return
	 */
	boolean checkPhone(String phone) {
		if(!isOnlyNum(phone) || !isPhoneLengthEnough(phone)) {
			System.out.println("[오류] 전화번호는 10자리 또는 11자리의 숫자로만 입력해야 합니다.");
			return false;
		}
		return true;
	}
		
	/**
	 * 입력받은 문자열에 공백이 포함되어있는지 검사한 후 boolean값을 반환
	 * @param str
	 * 			검사할 문자열
	 * @return
	 * 			공백이 포함되었을 경우 false 반환, 포함되지 않았을 경우 true 반환
	 */	
	boolean isOnlyChar(String str) {
		Pattern regex = Pattern.compile("^[A-Za-z[0-9]]{5,12}$");
		Matcher idMatcher = regex.matcher(str);
		return idMatcher.matches();
	}

	/**
	 * 아이디가 회원목록에 이미 존재하는지 검사한 후 boolean값을 반환
	 * @author 민우
	 * @param id
	 * 			존재하는지 찾을 id 문자열
	 * @return
	 * 			이미 존재할경우 true 반환, 존재하지 않을경우 false 반환
	 */
	boolean isIdExist(String id) {
		Account tmp = findById(id);
		
		if(tmp != null) {
			return true;			
		}
		
		return false;
	}
					
	/**
	 * 입력한 비밀번호와 비밀번호확인 문자열이 동일한지 검사한 후 boolean값을 반환
	 * @author 민우
	 * @param pw
	 * 			최초에 입력한 비밀번호 문자열
	 * @param pwCheck
	 * 			비밀번호 확인 문자열
	 * @return
	 * 			일치하지 않을 경우 false , 일치할 경우 true 반환
	 */
	boolean isPwMatch(String pw, String pwCheck) {
		if(pw.equals(pwCheck)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 입력한 전화번호 문자열이 숫자로만 이루어져있는지 검사한 후 boolean값을 반환
	 * @param phone
	 * 			입력한 전화번호 문자열
	 * @return
	 * 			숫자 외의 문자가 포함되었을 경우 false 반환, 숫자만으로 이루어진 경우 true 반환
	 */
	boolean isOnlyNum(String phone) {
		for(int i = 0; i < phone.length(); i++) {
			if(phone.charAt(i) < '0' || phone.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @author 민우
	 * @param phone
	 * @return
	 */
	boolean isPhoneLengthEnough(String phone) {
		if(phone.length() < 10 || phone.length() > 11) {
			return false;
		}
		return true;
	}
			
	// 로그인
	/**
	 * 
	 * @author 보경
	 */
	public void login() {
		String id = "";
		String pw = "";
		while(true) {
			printLoginMenu();
			int input = nextInt();
			switch (input) {
			case 1: // 회원 로그인
				System.out.print("아이디 > ");
				id = nextLine();
				System.out.print("비밀번호 > ");
				pw = nextLine();
				if(findBy(id, pw) != null) {
					loginUser = findBy(id, pw);
					System.out.println("로그인성공");
					if(loginUser == members.get(0)) {
						adminMenu();
					}
					else {
						memberMenu();
					}
				}
				else if(findById(id) == null) {
					System.out.println("아이디 확인 후 다시 입력");
				}
				else {
					System.out.println("비밀번호 확인 후 다시 입력");
				}
				break;
				
			case 2: // 비회원 로그인
				registerNoMem();
				memberMenu();
				break;
			case 3: // 이전
				return;
			default:
				System.out.println("1~3값으로 다시 입력");
			}
		}
	}
	
	void registerNoMem() {
		String phone = insertPhone();
		loginUser = new Account(phone); // 비회원 생성자 호출
		System.out.println("임시회원번호: " + loginUser.getNum());
		System.out.println(loginUser);
	}
	
	/**
	 * 
	 * @param id
	 * 			사용자가 입력한 id 값
	 * @return 회원정보에 일치하는 값이 있으면 해당 계정정보를 반환
	 */
	Account findBy(String id, String pw) {
		if(findById(id) != null) {
			for(int i = 0 ; i < members.size(); i++) {
				if(id.equals(members.get(i).getId()) && pw.equals(members.get(i).getPw())) {
					return members.get(i);
				}
			}
		}
		return null;
	}
	
	void printLoginMenu() {
		System.out.println("============================================ 홈 > 로그인 ============================================");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │    1. 회 원      │  │    2. 비회원     │  │     3. 이 전     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	// 회원메뉴
	/**
	 * 일반회원 로그인시 탐색가능한 메뉴를 출력
	 * @author 민우
	 */
	public void memberMenu() {
		while(true) {
			try {
				printMemberMenu();
				int input = nextInt();
				
				switch(input) {
				case 1: // 좌석선택
					selectSeat();
					break;
				case 2:	// 요금결제
					pay();
					break;
				case 3:	// 음식구매
					purchase();
					break;
				case 4:	// 이용상태관리
					controlStat();
					break;
				case 5:	// 정보수정
					updateInfo();
					break;
				case 6:	// 로그아웃
					logout();
					break;
				default:
					System.out.println("다시 입력하세요.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");                                                                                           
			}
		}
	}
	
	void printMemberMenu() {
		System.out.println("===================================== 홈 > 로그인 > 회원메뉴 ========================================");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │   1. 좌석선택    │  │   2. 요금결제    │  │   3. 음식구매    │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │ 4. 이용상태관리  │  │   5. 정보수정    │  │   6. 로그아웃    │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	// 좌석선택
	/**
	 * @author 민우
	 */
	public void selectSeat() {		
		while(true) {
			for(int i = 0; i < seats.length; i++) {
				if(seats[i] == loginUser) {
					printEmptySeat();
					System.out.println(loginUser.getId() + "님은 이미 " + (i+1) + "번 좌석을 사용중입니다.");
					System.out.print("좌석을 이동하시겠습니까? 1. 예 2. 아니오 > ");
					int input = nextInt();
					if(input == 1) {
						seats[i] = null;
						loginUser.setSeatNum(-1);
						System.out.println("좌석을 다시 선택해주세요.");
					}
					return;
				}
			}
			printSelectSeatMenu(seats);
			int input = nextInt()-1;
			
			if(input >= 0 && input <= 4) {
				if(seats[input] == null) {
					seats[input] = loginUser;
					loginUser.setSeatNum(input);
					break;					
				} else {
					System.out.println("이미 이용중인 좌석입니다.");
				}				
			} else {
				System.out.println("다시 입력해주세요.");
			}
		}
	}
		
	/**
	 * 
	 * @param seats
	 */	void printSelectSeatMenu(Account[] seats) {
		System.out.println("======================================= 회원메뉴 > 좌석선택 =========================================");
		printEmptySeat();
		System.out.println("=====================================================================================================");
		System.out.print("이용할 좌석번호를 입력하세요. > ");
	}
	
	/**
	 * 현재 좌석현황 배열을 입력받아 비어있는 좌석을 출력
	 * @param seats
	 * 			현재 좌석현황 배열
	 */
	void printEmptySeat() {
		for(int i = 0; i < seats.length; i++) {
			if(seats[i] == null) {
				System.out.println((i+1)+ "번 좌석" + " : " + "◻︎ 이용가능");				
			} else {
				System.out.println((i+1) + "번 좌석" + " : " + "☒ 이용중");
			}
		}
	}
	
	// 요금결제
	
	// 요금결제
	/**
	 * @author 찬희
	 */
	public void pay() {
		while(true) {
			try {
				printPayMenu();
				int input = nextInt();
				switch(input) {
				case 1:	
					printFeeList();
					System.out.println("사용할 요금제 선택");
					input = nextInt();
					Fee fe = findBy(input);
					if(fe != null) {
						printFeeInfo(fe);
						System.out.println("요금을 결제하시겠습니까?");
						System.out.println("1.네 | 2.다시선택 | 3. 취소");
						input = nextInt();
						if(input == 1) {
							sales += fe.getPrice();
							int ff = fe.getTime();
							loginUser.setRemainTime(ff + loginUser.getRemainTime());
							System.out.println(sales + "원");
							System.out.println(loginUser.getRemainTime() + "분");
							addSalesLog(fe);
							save("transaction.ser", sales,purchase);
							save("memberList.ser", members);
							break;
						} else if(input == 2) {
							break;
						}
					}
				case 2:	// 다음 : 이용상태관리
					return;	
				default:
					System.out.println("다시 입력하세요");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	void printFeeList() {
        String[] menus = {"품번", "상품명", "판매가격"};
        String[] str = new String[3];
        int[] len = {10, 20, 10};

        System.out.println("==========================================< 요금목록 >===============================================");
        System.out.println(format(menus, len));
        for(int i = 0; i < feeList.size(); i++) {
        	if(loginUser.isMember() == feeList.get(i).isMember()) {
            str[0] = Integer.toString(feeList.get(i).getItemNum());
            str[1] = feeList.get(i).getName();
            str[2] = Integer.toString(feeList.get(i).getPrice());
            System.out.println(format(str, len));
        	}
        }
        System.out.println("=====================================================================================================");
    }
	
	Fee findBy(int num) {
        for(int i = 0; i < feeList.size(); i++) {
        	if(num==feeList.get(i).getItemNum() && loginUser.isMember() == feeList.get(i).isMember())  
        return feeList.get(i);
        }
        return null;
    }
	
	void printPayMenu() {
		System.out.println("=======================================  회원메뉴 > 요금결제  ========================================");
		System.out.println("                            ┌─────────┐  ┌─────────┐                            ");
		System.out.println("                            │   1. 시간선택    │  │     2. 이 전     │                            ");
		System.out.println("                            └─────────┘  └─────────┘                            ");
		System.out.println("======================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	/**
	 * 
	 * @param item
	 */
	void addSalesLog(Fee fee) {
		Date date = new Date(System.currentTimeMillis());
		Log log = new Log(loginUser, fee, date);
		salesLog.add(log);
		save("salesLog.ser", salesLog);
	}

	// 이용상태관리
	/**
	 * @author 보경
	 */
	public void controlStat() {
		while(true) {
			printControlStatMenu();
			int input = nextInt();
			
			switch(input) {
			case 1: // 이용시작
				startUse();
				break;
			case 2: // 일시정지
				pauseUse();
				break;					
			case 3: // 이용종료
				stopUse();
				break;
			case 4:
				return;
			}
		}
	}
	
	void startUse() {
		if(loginUser.getRemainTime() <= 0) {	// 이용시간이 0 미만일 경우
			System.out.println("요금 결제 후 이용가능.");
		} else if(!loginUser.isStatus()) {		// 이용중이 아닐 경우
			loginUser.setStatus(true);
			times.add(System.currentTimeMillis());
			System.out.println("이용시작됨.");		
		} else {								// 이미 이용중일 경우
			System.out.println("이미 이용중입니다.");
		}
	}
	
	void pauseUse() {
		if(loginUser.isStatus()) {		// 이용중인 상태에서 일시정지 하는 경우
			times.add(System.currentTimeMillis());
			calUsingTime();
			loginUser.setStatus(false);
			System.out.println("일시정지됨.");
		} else {						// 일시정지 상태에서 일시정지를 다시 하는 경우
			System.out.println("이용중인 상태가 아닙니다.");
		}
	}
	
	void stopUse() {
		if(loginUser.isStatus()) {	// 이용중인 상태일 때
			times.add(System.currentTimeMillis());
		}
		
		calRemainTime();

		
		if(loginUser.getRemainTime() < 0) { // 남은 이용시간이 음수 >> 초과시간 추가결제
			payAdditionalFee();
		}
		
		loginUser.setStatus(false);
		times = new ArrayList<Long>();

		logout();
	}
	
	void calRemainTime() {
		long totalUsingTime = 0;
		calUsingTime();
		
		for(int i = 1; i < times.size() ; i+=2) {
			totalUsingTime += (times.get(i) - times.get(i-1)) / 1000;
		}
		
		System.out.println("총 이용시간 : " + printTime(totalUsingTime));
		System.out.println(loginUser.getId() + "님의 남은 이용시간 : " + printTime(loginUser.getRemainTime()));
	}
	
	void calUsingTime() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		long usingTime = 0;
		
		for(int i = 1; i < times.size() ; i+=2) {
			System.out.println("시작시간 : " + timeFormat.format(new Date(times.get(i-1))));
			System.out.println("정지시간 : " + timeFormat.format(new Date(times.get(i))));
			usingTime = (times.get(i) - times.get(i-1)) / 1000;
			System.out.println("지금까지 이용시간: " + printTime(usingTime));
		}
		
		if(loginUser.isStatus()) {	// 이용중에서 바로 이용종료 할 경우
			loginUser.setRemainTime((int) (loginUser.getRemainTime() - usingTime));			
		}
	}
	
	boolean isPauseStat() {
		if(!(loginUser.isStatus()) && times != null) {
			return true;
		}
		return false;
	}
	
	String printTime(long totalUsingTime) {
		String str = "";
		int hour = 0;
		
		if(totalUsingTime < 0) {
			totalUsingTime = -totalUsingTime;
			str += "-";
		}
		
		if((int)totalUsingTime > 60) {
			hour = (int) (totalUsingTime / 60);
			totalUsingTime %= 60;
		}
		str += hour + "시간" + (int)totalUsingTime + "분";
		return str;
	}
	
	/** @author 보경
	 * 이용시간 초과시 추가요금 결제화면 출력
	 * 결제한 시간이 초과시간보다 작으면 계속 결제화면 출력.
	 */
	void payAdditionalFee() {
//		int overTimeInUse = (int)(Math.abs(loginUser.getRemainTime())); 							// 남은 이용시간이 음수면(초과시간) 절대값으로 변환
		System.out.println("추가요금을 결제해 주세요.");
//		System.out.println("overTimeInUse" + overTimeInUse); 							// test
//		printFeeList(overTimeInUse);
		while(loginUser.getRemainTime() < 0) {
			printFeeList();
			System.out.print("요금제 선택: ");
			int input = nextInt();
			Fee fe = findBy(input);
			
			System.out.println("요금을 결제하시겠습니까?");
			System.out.println("1.네 | 2.아니오");
			input = nextInt();
			if(input == 1) {
				sales += fe.getPrice();
				int ff = fe.getTime();
				loginUser.setRemainTime(loginUser.getRemainTime() + ff);
//				System.out.println(sales + "원");
				System.out.println(loginUser.getId() + "님의 남은 이용시간 : " + printTime(loginUser.getRemainTime()));
				addSalesLog(fe);
				save("transaction.ser", sales, purchase);
				save("memberList.ser", members);
			}
		}
		return;
	}
	
	void printControlStatMenu() {
		System.out.println("====================================  회원메뉴 > 이용상태관리  ======================================");
		System.out.println("                             현재 상태 : " + (loginUser.isStatus() ? "이용중" : "이용중이 아님") + " | 남은 시간 : " + loginUser.getRemainTime() + "분");
		System.out.println("    ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐   ");
		System.out.println("    │   1. 이용시작    │  │   2. 일시정지    │  │   3. 이용종료    │  │    4. 이 전      │   ");
		System.out.println("    └─────────┘  └─────────┘  └─────────┘  └─────────┘   ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
		
	// 회원정보수정(회원메뉴)
	/**
	 * 
	 * @author 민우
	 */
	void updateInfo() {
		if(!loginUser.isMember()) {
			System.out.println("비회원은 정보를 수정할 수 없습니다.");
			return;
		}
		printMemberInfo(loginUser);
		System.out.println("============================== 회원메뉴 > 정보수정 =================================");
		System.out.println("회원정보는 비밀번호, 전화번호만 수정가능합니다.");
		String pw = updatePw();
		String phone = updatePhone();
		
		loginUser.setPw(pw);
		loginUser.setPhone(phone);			
	}
	
	/**
	 * @author 민우
	 * @return
	 */
	String updatePw() {
		while(true) {
			System.out.print("비밀번호(기존값:" +  loginUser.getPw() + ") > ");
			String pw = nextLine();
			System.out.print("비밀번호확인(기존값:" +  loginUser.getPw() + ") > ");
			String pwck = nextLine();
			
			if(checkPw(pw, pwck)) {
				return pw;
			}
		}
	}
	
	/**
	 * 
	 * @author 민우
	 * @return
	 */
	String updatePhone() {
		while(true) {
			System.out.print("전화번호(기존값:" +  loginUser.getPhone() + ") > ");
			String phone = nextLine();

			if(checkPhone(phone)) {
				return phone;
			}
		}
	}
	
	/**
	 * 
	 * @author 민우
	 */
	void logout() {
		if(loginUser.isStatus()) {
			System.out.println("로그아웃 전 이용을 종료해주세요.");
			return;
		}
		int idx = findSeatByUser(loginUser);
		if(idx != -1) {
			seats[idx] = null;
		}
		loginUser.setSeatNum(-1);
		loginUser = null;
		save("memberList.ser", members);
		System.out.println("이용을 종료합니다. 이용해주셔서 감사합니다.");
		System.exit(0);
	}
	
	/**
	 * 
	 * @author 민우
	 * @param user
	 * @return
	 */
	int findSeatByUser(Account user) {
		for(int i = 0; i < seats.length; i++) {
			if(seats[i] == user) return i;
		}
		return -1;
	}

	
	/**
	 * 
	 * @author 소연
	 */
	public void purchase() {
		boolean run = true;
		while (run) {
			try {
				printPurchaseMenu();
				int input = nextInt();
				
				int[] arr = {0, 24, 36, 50, 67, 97};
				
				if(input > 0 && input < 6) {
					purchaseMenu(arr[input-1], arr[input]);
				} else if(input == 6) {
					return;
				} else {
					System.out.println("다시 입력하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	/**
	 * 
	 * @author 소연
	 * @param start
	 * @param end
	 */
	void purchaseMenu(int start, int end) {
		// 출력포맷 
		String[] menus = {"상품번호", "상품명", "가격", "재고"};
		String[] str = new String[4];
		int[] len = {20, 40, 20, 20};
		
		// 변수
		Food food = new Food();
		int itemAmount = 0;
		
		System.out.println("==============================================<상품목록>=============================================");
		System.out.println(format(menus, len));
		for (int i = start; i < end; i++) { // menuList 출력
			str[0] = String.valueOf(menuList.get(i).getItemNum());
			str[1] = menuList.get(i).getName();
			str[2] = String.valueOf(menuList.get(i).getPrice());
			str[3] = String.valueOf(menuList.get(i).getStock());
			System.out.println(format(str, len));
		}
	
		System.out.println("=====================================================================================================");
		System.out.println("주문하실 메뉴의 번호를 입력해주세요 >");
		
		int itemNum = nextInt();
			
		if(itemNum < start || itemNum > end) {
			System.out.println("현재 카테고리에 존재하지 않는 주문번호입니다.");
			return;
		} else {						
			food = findItemByNum(start, end, itemNum);
		}
		
		System.out.println(food.getName() + " (재고 : " + food.getStock() + "개)");
		
		// 3. 선택한 메뉴 갯수와 결제여부 묻기					
		// 4. 결제시 금액(price)만큼 총매출(sales)에 추가
		// 5. 결제시 구매한 갯수만큼 Food객체의 재고(stock)값 감소시키기
		System.out.println("몇개 구입하시겠습니까?");
		
		itemAmount = nextInt();
		
		if(itemAmount > food.getStock()) {
			System.out.println("재고량을 초과해서 구매할 수 없습니다.");
			return;
		} else if(itemAmount < 1) {
			System.out.println("1개 이상의 값을 입력해 주세요.");
			return;
		} else {
			System.out.println(food.getName() + " " + itemAmount + "개를 구매하셨습니다.");
			sales += (food.getPrice()*itemAmount);
			food.setStock(food.getStock() - itemAmount);
			save("transaction.ser", sales, purchase);
			save("menuList.ser", menuList);
			addSalesLog(food, itemAmount);
		}	
	}
	
	/**
	 * 
	 * @author 소연
	 * 
	 */
	void printPurchaseMenu() {
		System.out.println("========================================  회원메뉴 > 구 매  =========================================");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │    1. 식사류     │  │    2. 사이드     │  │     3. 스 낵     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │     4. 음 료     │  │    5. 차/커피    │  │     6. 이 전     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	/**
	 * 
	 * @param item
	 * @param num
	 */
	void addSalesLog(Food food, int num) {
		Date date = new Date(System.currentTimeMillis());
		Log log = new Log(loginUser, food, num, date);
		salesLog.add(log);
		save("salesLog.ser", salesLog);
	}
	
	
	// 관리자메뉴
	/**
	 * 관리자계정(admin)으로 로그인했을 경우 각 관리자 기능을 호출하는 메뉴를 출력
	 * @author 민우
	 */
	public void adminMenu() {		
		while(true) {
			try {
				printAdminMenu();
				int input = nextInt();
				
				switch(input) {
				case 1:	// 회원관리
					manageMember();
					break;
				case 2:	// 재고관리
					manageStock();
					break;
				case 3:	// 매출관리
					manageSales();
					break;
				case 4:	// 종료
					return;
				default:
					System.out.println("다시 입력하세요.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	/**
	 * 
	 * @author 민우
	 */
	void printAdminMenu() {
		System.out.println("===================================== 홈 > 로그인 > 관리자메뉴 ======================================");
		System.out.println("    ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐   ");
		System.out.println("    │   1. 회원관리    │  │   2. 재고관리    │  │   3. 매출관리    │  │   4. 로그아웃    │   ");
		System.out.println("    └─────────┘  └─────────┘  └─────────┘  └─────────┘   ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	// 회원관리
	
	/**
	 * @author 민우
	 */
	public void manageMember() {
		while(true) {
			try {
				printMngMemMenu();
				int input = nextInt();
				
				switch(input) {
				case 1: // 회원정보수정
					updateMem();
					break;
				case 2: // 회원삭제
					deleteMem();
					break;
				case 3: // 이전(관리자메뉴출력화면)
					return;
				default:
					System.out.println("다시 입력하세요.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	void printMngMemMenu() {
		System.out.println("======================================== 관리자메뉴 > 회원관리 ======================================");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │ 1. 회원정보수정  │  │   2. 회원삭제    │  │     3. 이 전     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	// 회원관리 > 회원정보수정
	/**
	 * 
	 * @author 민우
	 */
	void updateMem() {
		printMemberList(members);
		while(true) {
			try {
				System.out.print("수정할 회원번호를 입력하세요. > ");
				int input = nextInt();
				if(input != 1) {
					int idx = findByNum(input);
					if(idx == -1) {
						System.out.println("찾을 수 없습니다.");
					} else {
						printMemberInfo(members.get(idx));
						System.out.print("찾는 회원이 맞습니까? 1. 예 2. 아니요 3. 취소 > ");
						int check = nextInt();
						
						if(check == 1) {
							updateInfo(idx);
							System.out.println("수정이 완료되었습니다.");
							break;
						} else if(check == 3) {
							return;
						}		
					}
				} else {
					System.out.println("관리자계정은 수정할 수 없습니다.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
		return;
	}
	
	/**
	 * 
	 * @author 민우
	 * @param num
	 * @return
	 */
	int findByNum(int num) {
		for(int i = 0; i < members.size(); i++) {
			if(num == members.get(i).getNum()) return i;
		}
		return -1;
	}	
	
	/**
	 * 
	 * @author 민우
	 * @param idx
	 */
	void updateInfo(int idx) {
		System.out.println("================================ 관리자메뉴 > 회원관리 > 회원정보수정 ===============================");
		System.out.println("회원정보는 비밀번호, 전화번호, 남은시간만 수정가능합니다.");
		String pw = updatePw(idx);
		String phone = updatePhone(idx);
		int total = updateRemainTime(idx);
		
		members.get(idx).setPw(pw);
		members.get(idx).setPhone(phone);
		members.get(idx).setRemainTime(total);
	}
	
	/**
	 * 
	 * @author 민우
	 * @param idx
	 * @return
	 */
	String updatePw(int idx) {
		while(true) {
			System.out.print("비밀번호(기존값:" +  members.get(idx).getPw() + ") > ");
			String pw = nextLine();
			System.out.print("비밀번호확인(기존값:" +  members.get(idx).getPw() + ") > ");
			String pwck = nextLine();
			
			if(checkPw(pw, pwck)) {
				return pw;
			}
		}
	}
	
	/**
	 * 
	 * @author 민우
	 * @param idx
	 * @return
	 */
	String updatePhone(int idx) {
		while(true) {
			System.out.print("전화번호(기존값:" +  members.get(idx).getPhone() + ") > ");
			String phone = nextLine();

			if(checkPhone(phone)) {
				return phone;
			}
		}
	}
	
	/**
	 * 
	 * @author 민우
	 * @param idx
	 * @return
	 */
	int updateRemainTime(int idx) {
		while(true) {
			int remainTime = members.get(idx).getRemainTime();
			System.out.println("남은시간을 늘리려면 양수로, 줄이려면 음수로 입력하세요.");
			System.out.print("남은시간(기존값:" + remainTime + ", 단위:분) > ");
			int addTime = nextInt();
			int total = remainTime + addTime;

			if(checkRemainTime(remainTime, total)) {
				return total;
			} 
		}
	}
	
	/**
	 * 
	 * @author 민우
	 * @param remainTime
	 * @param total
	 * @return
	 */
	boolean checkRemainTime(int remainTime, int total) {
		if(!isRemainTimeOnlyNum(remainTime)) {
			System.out.println("[오류] 남은시간은 양수 또는 음수의 정수만 입력해야 합니다.");
			return false;
		}
		if(isTimeUnderZero(total)) {
			System.out.println("[오류] 남은시간은 0보다 작을 수 없습니다.");
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @author 민우
	 * @param remainTime
	 * @return
	 */
	boolean isRemainTimeOnlyNum(int remainTime) {
		if(remainTime < 0) {
			remainTime = -remainTime;
		}
		String str = Integer.toString(remainTime);		
		for(int i = 0; i < str.length(); i++) {
			if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @author 민우
	 * @param total
	 * @return
	 */
	boolean isTimeUnderZero(int total) {
		if(total < 0) return true;
		return false;
	}
	
	// 회원관리 > 회원삭제
	/**
	 * 회원 목록에서 회원 객체의 삭제를 수행
	 * @author 민우
	 * @param list
	 * 			회원 목록
	 */
	void deleteMem() {
		while(true) {
			try {
				System.out.println("================================ 관리자메뉴 > 회원관리 > 회원삭제 ===================================");
				int idx = insertUserNum();
				System.out.print("찾는 회원이 맞습니까? 1. 예 2. 아니오 3. 취소 > ");
				int input = nextInt();
				
				switch(input) {
				case 1:
					checkDeleteMem(idx);
					break;
				case 2:
					break;
				case 3:
					return;
				default:
					System.out.println("다시 입력하세요.");
				}				
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}		
		}
	}
	
	int insertUserNum() {
		while(true) {
			printMemberList(members);
			System.out.print("삭제할 회원번호를 입력하세요. > ");
			int input = nextInt();
			if(input == 1) {
				System.out.println("관리자계정은 삭제할 수 없습니다.");
			} else {
				int idx = findByNum(input);
				
				if(idx != -1) {
					printMemberInfo(members.get(idx));
					return idx;
				}				
				System.out.println("찾을 수 없습니다.");
			}
		}
	}
		
	/**
	 * 회원 객체 삭제 전, 다시 한 번 삭제 여부를 물음
	 * @param idx
	 * 			회원목록 내 삭제할 회원 객체의 인덱스
	 * @param list
	 * 			회원 목록
	 */
	void checkDeleteMem(int idx) {
		while(true) {
			System.out.print("정말로 삭제하시겠습니까?(삭제시 복구할 수 없습니다) 1. 예 2. 아니오 > ");
			int input = nextInt();
			switch(input) {
			case 1:
				members.remove(idx);
				save("memberList.ser", members);
				System.out.println("삭제가 완료되었습니다.");
				return;
			case 2:
				return;
			default:
				System.out.println("다시 입력하세요.");
			}
		}
	}
	
	/**
	 * @author 소연
	 */
	public void manageStock() {
		int[] arr = {0, 24, 36, 50, 67, 97};
		
		while(true) {
			try {
				printManageStockMenu();
				int input = nextInt();
			
				if(input > 0 && input < 6) {
					Food seletedItem = selectItem(arr[input-1], arr[input]);
					insertOrderInfo(seletedItem);
				} else if(input == 6) {
					return;
				} else {
					System.out.println("다시 입력하세요.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	void printManageStockMenu() {
		System.out.println("=====================================  관리자메뉴 > 재고관리  =======================================");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │    1. 식사류     │  │    2. 사이드     │  │     3. 스 낵     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("                ┌─────────┐  ┌─────────┐  ┌─────────┐               ");
		System.out.println("                │     4. 음 료     │  │    5. 차/커피    │  │     6. 이 전     │               ");
		System.out.println("                └─────────┘  └─────────┘  └─────────┘               ");
		System.out.println("=====================================================================================================");
		System.out.print("메뉴를 선택하세요. > ");
	}
	
	Food selectItem(int start, int end) {
		while(true) {
			try {
				printFoodList(start, end, menuList);
				System.out.print("추가 주문할 물품의 품번을 입력하세요. > ");
				int num = nextInt();
				Food food = findItemByNum(start, end, num);
				
				if(food != null) {
					return food;
				} else {
					System.out.println("찾을 수 없습니다.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	Food findItemByNum(int start, int end, int itemNum) {
		for(int i = start; i < end; i++) {
			if(menuList.get(i).getItemNum() == itemNum) return menuList.get(i);
		}
		return null;
	}
	
	void insertOrderInfo(Food food) {
		while(true) {
			try {
				System.out.print("주문할 물품의 갯수를 입력하세요. > ");
				int num = nextInt();
				
				if(num >= 0) {
					purchase += addStock(food, num);
					save("transaction.ser", sales, purchase);
					addOrderLog(food, num);
					printOrderLog(orderLog);
					return;
				} else {
					System.out.println("0이상의 정수로 입력하세요.");
				}
			} catch(NumberFormatException e) {
				System.out.println("숫자로 입력하세요.");
			}
		}
	}
	
	int addStock(Food food, int num) {
		System.out.println(food.getStock());
		food.setStock(food.getStock() + num);
		System.out.println(food.getStock());
		save("menuList.ser", menuList);
		int purchase = food.getPurchasePrice() * num;
		
		return purchase;
	}
	
	void addOrderLog(Food food, int num) {
		Date date = new Date(System.currentTimeMillis());
		Log log = new Log(food, num, date);
		orderLog.add(log);
		save("orderLog.ser", orderLog);
	}
	
	
	/**
	 * @author 찬희
	 */
	public void manageSales() {
		printSalesLog(salesLog);
		printOrderLog(orderLog);
		System.out.println("매출 : " + printNumPerThou(sales));
		System.out.println("매입 : " + printNumPerThou(purchase));
		System.out.println("영업이익 : " + printNumPerThou(getTotal()));
	}

	public ArrayList<Account> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Account> members) {
		this.members = members;
	}
	
	/**
	 * 매출액(sales)과 매입액(purchase)의 차인 영업이익(total)을 반환하는 getter
	 * @return
	 * 		매출액(sales)과 매입액(purchase)의 차인 영업이익(total)
	 */
	public int getTotal() {
		return sales - purchase;
	}
}
