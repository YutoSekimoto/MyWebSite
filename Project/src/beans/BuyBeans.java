package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyBeans {

	//フィールド
	private int id;
	private int useId;
	private int price;
	private int deliveryId;
	private Date createDate;
	private String formatDate;
	private String deliveryMethod;
	private int deliveryPrice;

	//アクセサメソッド
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUseId() {
		return useId;
	}
	public void setUseId(int useId) {
		this.useId = useId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
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
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public int getDeliveryPrice() {
		return deliveryPrice;
	}
	public void setDeliveryPrice(int deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
}
