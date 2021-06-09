package miniProject.vo;

import java.io.Serializable;

/**
 *	직렬화시 포함된 필드
 *	name, price, num
 */
public abstract class Merchandise implements Serializable {
	private static final long serialVersionUID = -4280881976853207054L;
	private String name;	// 상품명
	private int num;		// 상품번호
	private int price;		// 판매가격
	
	public Merchandise() {}
	
	public Merchandise(String name, int num, int price) {
		this.name = name;
		this.num = num;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getItemNum() {
		return num;
	}

	public void setItemNum(int num) {
		this.num = num;
	}	
	
}