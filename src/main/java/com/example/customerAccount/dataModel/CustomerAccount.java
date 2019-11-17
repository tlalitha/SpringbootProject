package com.example.customerAccount.dataModel;

import java.sql.Date;

public class CustomerAccount {
	
private int cusId;
private int accId;
private String fName;
private String sName;
private Date  dob;
private long accNum;
public int getCusId() {
	return cusId;
}
public void setCusId(int cusId) {
	this.cusId = cusId;
}
public int getAccId() {
	return accId;
}
public void setAccId(int accId) {
	this.accId = accId;
}
public String getfName() {
	return fName;
}
public void setfName(String fName) {
	this.fName = fName;
}
public String getsName() {
	return sName;
}
public void setsName(String sName) {
	this.sName = sName;
}
public Date getDob() {
	return dob;
}
public void setDob(Date dob) {
	this.dob = dob;
}
public long getAccNum() {
	return accNum;
}
public void setAccNum(long accNum) {
	this.accNum = accNum;
}


}
