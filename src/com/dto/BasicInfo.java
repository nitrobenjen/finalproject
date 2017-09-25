package com.dto;

public class BasicInfo {

	private String id, name, phone, regDate, publisher, cover_img;
	private int ssn, jungwon, sub_count, open_sub_count, count;// count -> 강사에서 강의가능과목수, 수강생에서 수강횟수
	private String finish_day;
	
	public String getId() {
		return id;
	}
	public int getSub_count() {
		return sub_count;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getFinish_day() {
		return finish_day;
	}
	public void setFinish_day(String finish_day) {
		this.finish_day = finish_day;
	}
	public void setSub_count(int sub_count) {
		this.sub_count = sub_count;
	}
	public int getOpen_sub_count() {
		return open_sub_count;
	}
	public void setOpen_sub_count(int open_sub_count) {
		this.open_sub_count = open_sub_count;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getCover_img() {
		return cover_img;
	}
	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public int getJungwon() {
		return jungwon;
	}
	public void setJungwon(int jungwon) {
		this.jungwon = jungwon;
	}
}
