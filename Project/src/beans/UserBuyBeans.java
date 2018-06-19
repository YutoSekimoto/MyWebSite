package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserBuyBeans {

	//フィールド
	private int id;
	private String itemName;
	private int userId;
	private String imageFile;
	private int price;
	private Date createDate;
	private String formatDate;

	//アクセサメソッド
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getImageFile() {
		return imageFile;
	}
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFormatDate() {
		return formatDate;
	}
	public void setFormatDate(Date formatDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 kk時mm分ss秒");
		String stringFormatDate = format.format(formatDate);
		this.formatDate = stringFormatDate;
	}

}
