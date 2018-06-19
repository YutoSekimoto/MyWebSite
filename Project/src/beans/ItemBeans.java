package beans;

public class ItemBeans {

	//フィールド
	private int id;
	private String name;
	private String detail;
	private int price;
	private String file;
	private int number;
	private int userId;

	//空のコンストラクタ
	public ItemBeans() {}

	//全ての値をセットするコンストラクタ
	public ItemBeans(String name , String detail , int price , String file) {

		this.name = name;
		this.detail = detail;
		this.price = price;
		this.file = file;

	}

	//アクセサメソッド
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}



}
