package beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBeans {

	//フィールド
	private int id;
	private int itemId;
	private String name;
	private String comment;
	private Date createDate;
	private String formatDate;

	//アクセサメソッド
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
