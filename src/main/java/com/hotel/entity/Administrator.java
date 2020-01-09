package com.hotel.entity;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Administrator implements Serializable {
	private static final long serialVersionUID = 1L;
	private String AdmId;
	private String aName;
	private String aPassword;
	private String aSex;
	private String limit;
	
	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrator(String AdmId, String aName, String aPassword, String aSex, String limit) {
		super();
		this.AdmId = AdmId;
		this.aName = aName;
		this.aPassword = aPassword;
		this.aSex = aSex;
		this.limit = limit;
	}

	public String getAdmId() {
		return AdmId;
	}

	public void setAdmId(String AdmId) {
		this.AdmId = AdmId;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaPassword() {
		return aPassword;
	}

	public void setaPassword(String aPassword) {
		this.aPassword = aPassword;
	}

	public String getaSex() {
		return aSex;
	}

	public void setaSex(String aSex) {
		this.aSex = aSex;
	}
	
	public String getlimit() {
		return limit;
	}

	public void setlimit(String limit) {
		this.limit = limit;
	}

//	@Override
//	public String toString() {
//		return "Administrators [AdmId=" + AdmId + ", aName=" + aName + ", aPassword=" + aPassword + ", aSex=" + aSex + ", limit="+limit+"]";
//	}
	
	@Override
	public String toString() {
		return "{\"AdmId\":\"" + AdmId + "\", \"aName\":\"" + aName + "\", \"aPassword\":\"" +
				aPassword + "\", \"aSex\":\"" + aSex + "\", \"limit\":\""+limit+"\"}";
	}

}
