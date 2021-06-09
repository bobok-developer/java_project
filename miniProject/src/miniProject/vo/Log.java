package miniProject.vo;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable{
	private static final long serialVersionUID = 1268482161012943474L;
	private Account user;		// 구입한 사용자
	private Merchandise item;	// 구입/판매한 상품(요금 또는 음식)
	private int itemNum;		// 구입/판매한 물건의 수량
	private int total;			// 구입/판매 총금액
	private Date date;			// 구입/판매일
	
	public Log() {}
	
	// 회원 구매 음식용 생성자
	public Log(Account user, Merchandise item, int itemNum, Date date) {
		super();
		this.user = user;
		this.item = item;
		this.itemNum = itemNum;
		this.total = ((Food) item).getPrice() * itemNum;
		this.date = date;
	}
	
	// 관리자 발주 음식용 생성자
	public Log(Merchandise item, int itemNum, Date date) {
		super();
		this.user = null;
		this.item = item;
		this.itemNum = itemNum;
		this.total = ((Food) item).getPurchasePrice() * itemNum;
		this.date = date;
	}
	
	// 요금용 생성자
	public Log(Account user, Merchandise item, Date date) {
		super();
		this.user = user;
		this.item = item;
		this.total = ((Fee) item).getPrice();
		this.date = date;
	}

	public Account getUser() {
		return user;
	}
	public void setUser(Account user) {
		this.user = user;
	}
	public Merchandise getItem() {
		return item;
	}
	public void setItem(Food item) {
		this.item = item;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
