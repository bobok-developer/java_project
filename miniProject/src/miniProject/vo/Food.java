package miniProject.vo;

/**
 * 직렬화시 포함된 필드
 * kind, stock, purchasePrice
 */
public class Food extends Merchandise {
	private static final long serialVersionUID = -6863341935051929057L;
	private String kind;		// 음식종류
	private int purchasePrice;	// 발주시 구입가
	private int stock;			// 재고수
	
	public Food() {
		super();
		this.stock = 0;
	}
	
	/**
	 * 음식
	 * @param kind
	 * @param num
	 * @param name
	 * @param price
	 */
	public Food(String kind, int num, String name, int price) {
		super(name, num, price);
		this.kind = kind;
		this.purchasePrice = (int) (price * 0.6);
		this.stock = 2;
	}
	
	public Food(String kind, int itemNum, String name, int price, int stock) {
		super(name, itemNum, price);
		this.kind = kind;
		this.purchasePrice = (int) (price * 0.6);
		this.stock = stock;
	}
	
	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getKind() {
		return kind;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Food [name=" + this.getName() + ", price=" + this.getPrice() + ", kind=" + kind + ", stock=" + stock + "]";
	}

}
