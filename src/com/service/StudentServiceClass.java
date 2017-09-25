package com.service;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.dao.*;
import com.dto.*;

public class StudentServiceClass {

	public String studentmain(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

		String student_id = info.getM_id();

		StudentDAO dao = new StudentDAO();
		List<Subject> stugrade = dao.gradeList("", student_id);
		Subject studentinfo = dao.studentinfo(student_id);

		request.setAttribute("stugrade", stugrade);
		request.setAttribute("studentinfo", studentinfo);

		request.setAttribute("logininfo", info);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/student/student001.jsp";

	}

	public String student001(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

		String student_id = info.getM_id();

		String open_course_id = request.getParameter("open_course_id");
		String course_name = request.getParameter("course_name");
		String course_start_day = request.getParameter("course_start_day");
		String course_end_day = request.getParameter("course_end_day");
		String class_name = request.getParameter("class_name");

		StudentDAO dao = new StudentDAO();
		List<Subject> stusub = dao.subjectList("", student_id, open_course_id);
		Subject studentinfo = dao.studentinfo(student_id);

		request.setAttribute("stusub", stusub);

		request.setAttribute("open_course_id", open_course_id);
		request.setAttribute("course_name", course_name);
		request.setAttribute("course_start_day", course_start_day);
		request.setAttribute("course_end_day", course_end_day);
		request.setAttribute("class_name", class_name);

		request.setAttribute("studentinfo", studentinfo);
		request.setAttribute("logininfo", info);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/student/student002.jsp";
	}

	// 교재 출력

	public String studentbook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cover_img = "";
		String book_name = request.getParameter("book_name");

		Subject sb = new Subject();
		sb.setBook_name(book_name);

		StudentDAO dao = new StudentDAO();
		cover_img = dao.studentPicture(sb);

		request.setAttribute("cover_img", cover_img);

		return "/WEB-INF/view/student/studentimgname.jsp";
	}

	// 개인정보
	public String studentinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

		String student_id = info.getM_id();

		StudentDAO dao = new StudentDAO();
		Subject stuinfo = dao.studentList(student_id);

		Subject studentinfo = dao.studentinfo(student_id);

		request.setAttribute("stuinfo", stuinfo);
		request.setAttribute("logininfo", info);
		request.setAttribute("studentinfo", studentinfo);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/student/info.jsp";

	}

	// 학생 정보 변경
	public void studentinfomodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");
		String student_id = info.getM_id(); // 로그인 중인 계정의 fk (학생의 pk와 연결)
		String pws = info.getPw(); // 계정의 pw

		String student_phone = request.getParameter("student_phone"); // 수정하기 위해 전달한 휴대폰 번호
		String pw = request.getParameter("pw"); // 전달한 비번. 세션에있는 pw와 맞는 확인해야 한다.

		StudentDAO dao = new StudentDAO();
		Subject studentList = new Subject();
		String modifyphone = "";

		if (pws.equals(pw)) {
			dao.studentUpdate(student_phone, student_id);
			studentList = dao.studentList(student_id);
			modifyphone = studentList.getStudent_phone();

		} else {
			modifyphone = "false";
		}

		PrintWriter out = response.getWriter();
		out.write(modifyphone);
		out.flush();
		out.close();
	}

	// 학생 암호 변경창 호출을 위한 확인메소드
	public void studentpwmodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");
		String pws = info.getPw(); // 계정의 pw
		String pw = request.getParameter("pw"); // 전달한 비번. 세션에있는 pw와 맞는 확인해야 한다.
		String modifyphone = "";

		if (pws.equals(pw)) {

		} else {
			modifyphone = "false";
		}

		PrintWriter out = response.getWriter();
		out.write(modifyphone);
		out.flush();
		out.close();
	}

	// 학생 암호 변경창 호출을 위한 확인메소드
	public void studentpwmodify2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");
		String pws = info.getPw(); // 계정의 pw
		String student_id = info.getM_id();
		String id_ = info.getId_();
		int grade = info.getGrade();
		String currentpw = request.getParameter("currentpw");
		String newpw = request.getParameter("newpw");
		String newpwcheck = request.getParameter("newpwcheck");

		String modifyphone = "";
		LoginDAO dao = new LoginDAO();

		if (!pws.equals(currentpw)) {
			modifyphone = "false1";
			// modifyphone = "현재 암호가 틀렸습니다.";
		} else if (newpw.equals(currentpw) || newpwcheck.equals(currentpw)) {
			modifyphone = "false2";
			// modifyphone = "현재 암호와 같은 암호입니다.";
		} else if (!newpw.equals(newpwcheck)) {
			modifyphone = "false3";
			// modifyphone = "변경할 암호를 같게 입력해야합니다. 다시 확인해주세요.";
		} else {
			// modifyphone = "비밀번호를 변경했습니다.";
			// 변경하는 동시에 로그인중인 세션정보 업데이트를 한다.
			dao.teachpwmodify(newpw, student_id);
			LoginInfo m = new LoginInfo();
			m.setId_(id_);
			m.setGrade(grade);
			m.setM_id(student_id);
			m.setPw(newpw);
			session.setAttribute("logininfo", m);

		}

		PrintWriter out = response.getWriter();
		out.write(modifyphone);
		out.flush();
		out.close();
	}
}