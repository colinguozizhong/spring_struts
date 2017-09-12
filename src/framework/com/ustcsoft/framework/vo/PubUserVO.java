package com.ustcsoft.framework.vo;

//外网用户
public class PubUserVO extends UserVO{

	private static final long serialVersionUID = -205074496010322966L;
	
	// 用户类型 01个人02企业
	private String userType;
	/**
	 * 身份证号码
	 */
	private String cardId;
	/**
	 * 企业名称
	 */
	private String qiYeMingCheng;
	/**
	 * 手机号码
	 */
	private String phoneNo;
	/**
	 * 邮编
	 */
	private String postCode;
	/**
	 * 行政区划名称
	 */
	private String area;
	/**
	 * 法定代表人
	 */
	private String legalPerson;
	
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getQiYeMingCheng() {
		return qiYeMingCheng;
	}

	public void setQiYeMingCheng(String qiYeMingCheng) {
		this.qiYeMingCheng = qiYeMingCheng;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
}
