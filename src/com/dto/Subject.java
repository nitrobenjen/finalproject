package com.dto;

public class Subject {

	//과정, 과목, 성적 입출력 자료형
	private String subject_id, subject_name, open_sub_id, sub_start_day, sub_end_day, teacher_id, teacher_name, teacher_phone, student_id, student_name, student_phone, student_regdate, teacher_ssn, teacher_hiredate, id_, pw;
	// 과목id, 과목이름, 개설과목id, 과목 시작종료 날짜, 강사 기초정보, 수강생 id, 수강생 명, 수강생 휴대폰, 수강생 등록일, 강사 주민번호, 강사 등록일, id, pw
	private String open_course_id, course_id, course_name, course_start_day, course_end_day;//개설과정id, 과정id, 과정이름, 과정 시작, 종료날짜
	private String class_name, class_id; //강의실 id, 강의실 명
	private String book_name, book_id, publisher; // 책id, 책이름, 출판사
	private String test_date, test_munje; //시험날짜, 시험문제
	private String jungwon, course_count, student_count, subject_count; // 정원, 과정수, 학생수, 과목수
	private String b_silki, b_chulsuk, b_filki, g_silki, g_chulsuk, g_filki; // 출석, 실기, 필기 점수,배점
	private String finish_day; //중도 탈락
	private String pass_drop,total;
	private String cover_img,student_ssn;
	private int totalpage; //페이징 처리를 위한 변수. 총 레코드 수
	
	
	
	

	public String getStudent_ssn() {
		return student_ssn;
	}
	public void setStudent_ssn(String student_ssn) {
		this.student_ssn = student_ssn;
	}
	public String getCover_img() {
		return cover_img;
	}
	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}
	public String getId_() {
		return id_;
	}
	public void setId_(String id_) {
		this.id_ = id_;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPass_drop() {
		return pass_drop;
	}
	public void setPass_drop(String pass_drop) {
		this.pass_drop = pass_drop;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTeacher_hiredate() {
		return teacher_hiredate;
	}
	public void setTeacher_hiredate(String teacher_hiredate) {
		this.teacher_hiredate = teacher_hiredate;
	}
	public String getTeacher_ssn() {
		return teacher_ssn;
	}
	public void setTeacher_ssn(String teacher_ssn) {
		this.teacher_ssn = teacher_ssn;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public String getFinish_day() {
		return finish_day;
	}
	public void setFinish_day(String finish_day) {
		this.finish_day = finish_day;
	}
	public String getStudent_regdate() {
		return student_regdate;
	}
	public void setStudent_regdate(String student_regdate) {
		this.student_regdate = student_regdate;
	}
	public String getStudent_phone() {
		return student_phone;
	}
	public void setStudent_phone(String student_phone) {
		this.student_phone = student_phone;
	}
	public String getTeacher_phone() {
		return teacher_phone;
	}
	public void setTeacher_phone(String teacher_phone) {
		this.teacher_phone = teacher_phone;
	}

	public String getCourse_count() {
		return course_count;
	}
	public String getSubject_count() {
		return subject_count;
	}
	public void setSubject_count(String subject_count) {
		this.subject_count = subject_count;
	}
	public void setCourse_count(String course_count) {
		this.course_count = course_count;
	}
	public String getStudent_count() {
		return student_count;
	}
	public void setStudent_count(String student_count) {
		this.student_count = student_count;
	}
	private String completion;
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getCompletion() {
		return completion;
	}
	public void setCompletion(String completion) {
		this.completion = completion;
	}
	public String getStudent_name() {
		return student_name;
	}
	public String getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getJungwon() {
		return jungwon;
	}
	public void setJungwon(String jungwon) {
		this.jungwon = jungwon;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}


	public String getStudent_id() {
		return student_id;
	}
	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public String getOpen_course_id() {
		return open_course_id;
	}
	public void setOpen_course_id(String open_course_id) {
		this.open_course_id = open_course_id;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getOpen_sub_id() {
		return open_sub_id;
	}
	public void setOpen_sub_id(String open_sub_id) {
		this.open_sub_id = open_sub_id;
	}
	public String getSub_start_day() {
		return sub_start_day;
	}
	public void setSub_start_day(String sub_start_day) {
		this.sub_start_day = sub_start_day;
	}
	public String getSub_end_day() {
		return sub_end_day;
	}
	public void setSub_end_day(String sub_end_day) {
		this.sub_end_day = sub_end_day;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getCourse_start_day() {
		return course_start_day;
	}
	public void setCourse_start_day(String course_start_day) {
		this.course_start_day = course_start_day;
	}
	public String getCourse_end_day() {
		return course_end_day;
	}
	public void setCourse_end_day(String course_end_day) {
		this.course_end_day = course_end_day;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getTest_date() {
		return test_date;
	}
	public void setTest_date(String test_date) {
		this.test_date = test_date;
	}
	public String getTest_munje() {
		return test_munje;
	}
	public void setTest_munje(String test_munje) {
		this.test_munje = test_munje;
	}
	
	public String getB_silki() {
		return b_silki;
	}
	public void setB_silki(String b_silki) {
		this.b_silki = b_silki;
	}
	public String getB_chulsuk() {
		return b_chulsuk;
	}
	public void setB_chulsuk(String b_chulsuk) {
		this.b_chulsuk = b_chulsuk;
	}
	public String getB_filki() {
		return b_filki;
	}
	public void setB_filki(String b_filki) {
		this.b_filki = b_filki;
	}
	public String getG_silki() {
		return g_silki;
	}
	public void setG_silki(String g_silki) {
		this.g_silki = g_silki;
	}
	public String getG_chulsuk() {
		return g_chulsuk;
	}
	public void setG_chulsuk(String g_chulsuk) {
		this.g_chulsuk = g_chulsuk;
	}
	public String getG_filki() {
		return g_filki;
	}
	public void setG_filki(String g_filki) {
		this.g_filki = g_filki;
	}

	
	

}
