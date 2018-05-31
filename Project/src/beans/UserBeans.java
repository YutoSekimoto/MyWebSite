package beans;

public class UserBeans {

	//フィールド
	private int id;
	private String loginId;
	private String password;
	private String name;
	private String email;
	private String address;
	private String birthDate;
	private String createDate;
	private String updateDate;

	//空のコンストラクタ
	public UserBeans() {}

	//idとupdateDate以外の値をセットするコンストラクタ(ユーザー登録）
	public UserBeans(String loginid , String password , String name , String email , String birthdate , String createdate) {

		this.loginId = loginid;
		this.password = password;
		this.name = name;
		this.birthDate = birthdate;
		this.createDate = createdate;

	}
	//全ての値をセットするコンストラクタ(ユーザーログイン)
	public UserBeans(int id , String loginid , String password , String name , String email , String address , String birthdate , String createdate , String updatedate) {

		this.id = id;
		this.loginId = loginid;
		this.password = password;
		this.name = name;
		this.email = email;
		this.address = address;
		this.birthDate = birthdate;
		this.createDate = createdate;
		this.updateDate = updatedate;

	}

	//アクセサメソッド
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}




}
