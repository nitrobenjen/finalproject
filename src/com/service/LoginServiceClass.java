package com.service;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.dao.LoginDAO;
import com.dto.LoginInfo;

public class LoginServiceClass {

	// "/test.do" 요청주소와 매핑되는 메소드
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/login.jsp";

	}
	
	
	public String loginstart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		String url = "";
		
		String id_ = request.getParameter("id_");
		String pw = request.getParameter("pw");	
		LoginInfo pk = new LoginInfo();
		LoginDAO dao = new LoginDAO();
		pk = dao.login(id_, pw);	
		HttpSession session = null;
	
		
		if(pk.getGrade() == 0) {
			url = "/WEB-INF/view/redirect.jsp?url=loginfail.do";
		}
		
		if(pk.getId_() != null && pk.getGrade() == 0) {
			//관리자 페이지로 이동		
			url = "/WEB-INF/view/redirect.jsp?url=adminmain.do";
			session = request.getSession(true);
			session.setAttribute("logininfo", pk);
		}else if(pk.getGrade() == 1) {
			//강사 페이지로 이동
			url = "/WEB-INF/view/redirect.jsp?url=teachschedulelist.do";
			session = request.getSession(true);
			session.setAttribute("logininfo", pk);
		}else if(pk.getGrade() == 2) {
			//수강생 페이지로 이동
			url = "/WEB-INF/view/redirect.jsp?url=studentmain.do";
			session = request.getSession(true);
			session.setAttribute("logininfo", pk);
			
		}
		// 뷰 페이지 주소 지정 -> 포워딩
		return url;

	}
	
	
	public String loginfail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/loginfail.jsp";

	}
	
	
	
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.invalidate();
	
		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/redirect.jsp?url=login.do";

	}
	
}