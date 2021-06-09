package miniProject.vo;

import static miniProject.utils.CommonUtils.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * 직렬화시 포함된 필드
 * 	memNum(static), num, id, pw, phone, remainTime, status, member
 */
public class Account implements Serializable {
	private static final long serialVersionUID = -236989942385663711L;
	private static int memNum;		// 가입횟수(회원번호 발급에 참조, 자동증가)
	private int num;				// 회원번호(memNum에 의해 발급)
	private String id;				// 아이디
	private String pw;				// 비밀번호
	private String phone;			// 전화번호
	private int remainTime;			// 남은시간
	private int seatNum;			// 좌석번호
	private boolean status;			// 이용상태
	private boolean member;			// 회원/비회원 여부
	
	static {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream("memNum.ser"));
			memNum = dis.readInt();	
			dis.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	{
		memNum++;
		save("memNum.ser", memNum);
	}
	
	/**
	 * 로그인 및 임시 객체 생성시의 회원가입횟수가 늘어나지 않도록하는 기본 생성자
	 */
	public Account() {
		memNum--;
	}
	
	/**
	 * 비회원 가입시의 생성자
	 * @param phone
	 * 			입력받은 전화번호 문자열
	 */
	public Account(String phone) {
		this.num = memNum;
		this.id = "guest";
		this.pw = "0000";
		this.phone = phone;
		this.seatNum = -1;
		this.remainTime = 0;
		this.status = false;
		this.member = false;
	}
	
	/**
	 * 회원 가입시의 생성자
	 * @param id
	 * 			계정 아이디
	 * @param pw
	 * 			계정 비밀번호
	 * @param phone
	 * 			전화번호
	 */
	public Account(String id, String pw, String phone) {
		this.num = memNum;
		this.id = id;
		this.pw = pw;
		this.phone = phone;
		this.seatNum = -1;
		this.remainTime = 0;
		this.status = false;
		this.member = true;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public static int getMemNum() {
		return memNum;
	}

	public static void setMemNum(int memNum) {
		Account.memNum = memNum;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}
	
	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	@Override
	public String toString() {
		return "계정정보 [회원번호=" + num + ", 아이디=" + id + ", 전화번호=" + phone + ", 남은시간=" + remainTime + ", 상태="
				+ status + ", 회원/비회원=" + (member ? "회원]" : "비회원]");
	}

}
