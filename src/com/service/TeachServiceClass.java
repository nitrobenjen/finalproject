package com.service;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.dao.LoginDAO;
import com.dao.TeachDAO;
import com.dto.LoginInfo;
import com.dto.Subject;
import com.google.gson.Gson;
import com.util.MyFileRename;

public class TeachServiceClass {
	//--------------------------------------------------재홍------------------------------------------

	////////////////// 강의 예정////////////////////////////////////////////

	// 강사 강의스케줄 > 강의 예정 목록
	public String teachschedulelist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

		String teacher_id = info.getM_id();
		TeachDAO dao = new TeachDAO();
		List<Subject> teachlist = new ArrayList<Subject>();
		Subject teachinfo = new Subject();
		teachlist = dao.teachsubschedule(teacher_id);
		teachinfo = dao.teachinfo(teacher_id);

		request.setAttribute("teachinfo", teachinfo);
		request.setAttribute("teachlist", teachlist);
		request.setAttribute("logininfo", info);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/teacher/teacher101.jsp";

	}

	// 강사 강의스케줄 > 강의 예정 목록 > 검색
	public void teachschedulesearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		TeachDAO dao = new TeachDAO();
		List<Subject> teachlist = new ArrayList<Subject>();

		if ("subject_name".equals(key)) {
			teachlist = dao.teachsubschedulesubname("TCH001", value);
		} else if ("course_name".equals(key)) {
			teachlist = dao.teachsubschedulecoursename("TCH001", value);
		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(teachlist));
		out.flush();
		out.close();
	}

	// 강사 강의스케줄 > 강의 예정 > 수강생 명단 클릭할 시 처리할 메소드
	public void teachschedulestulist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		String open_course_id = request.getParameter("open_course_id");

		// 액션 코드 작성
		TeachDAO dao = new TeachDAO();
		List<Subject> teacherstulist = new ArrayList<Subject>();
		teacherstulist = dao.teachstulist(open_course_id);

		/////////// 페이징 처리
		int total = teacherstulist.size(); // 페이징 처리를 위한 총 레코드 수
		int max = 10; // 한페이지에 표시할 페이지 수
		int totalpage = 0;// 총 페이지 수
		int pagegroup = 10; // 한번에 표시될 페이지 수
		if (total % max == 0) {
			totalpage = total / max;
		} else {
			totalpage = total / max + 1;
		}
		String currentpage2 = request.getParameter("currentpage"); // 현재 페이지
		if (currentpage2 == null) {
			currentpage2 = "1";
		}

		int currentpage = Integer.parseInt(currentpage2);
		int a = currentpage * max - max; // 임시변수
		List<Subject> teachepage = new ArrayList<Subject>(); // 선택된 페이지만 보내기 위한 임시 객체 생성

		for (int i = a; i < max * currentpage; i++) {
			if (i >= total)
				break;
			Subject pa = new Subject();
			pa.setStudent_id(teacherstulist.get(i).getStudent_id());
			pa.setStudent_name(teacherstulist.get(i).getStudent_name());
			pa.setStudent_phone(teacherstulist.get(i).getStudent_phone());
			pa.setStudent_regdate(teacherstulist.get(i).getStudent_regdate());
			pa.setFinish_day(teacherstulist.get(i).getFinish_day());
			pa.setTotalpage(totalpage);
			teachepage.add(pa);

		}

		////////////////
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(teachepage));
		out.flush();
		out.close();

	}

	////////////////// 강의 중////////////////////////////////////////////

	// 강사 강의스케줄 > 강의 중 목록
	public String teachinsub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		HttpSession session = request.getSession();
		LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

		String teacher_id = info.getM_id();
		TeachDAO dao = new TeachDAO();
		List<Subject> teachlist = new ArrayList<Subject>();
		teachlist = dao.teachinsub(teacher_id);

		Subject teachinfo = new Subject();

		teachinfo = dao.teachinfo(teacher_id);

		request.setAttribute("teachinfo", teachinfo);
		request.setAttribute("teachlist", teachlist);
		request.setAttribute("logininfo", info);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/teacher/teacher102.jsp";

	}

	// 강사 강의스케줄 > 강의 중 목록 > 검색
	public void teachinsubsearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		TeachDAO dao = new TeachDAO();
		List<Subject> teachlist = new ArrayList<Subject>();

		if ("subject_name".equals(key)) {
			teachlist = dao.teachinsubname("TCH001", value);
		} else if ("course_name".equals(key)) {
			teachlist = dao.teachinsubcoursename("TCH001", value);
		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(teachlist));
		out.flush();
		out.close();
	}

	////////////////// 강의 종료////////////////////////////////////////////

	// 강사 강의스케줄 > 강의 종료 목록
	public String teachendsub(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		HttpSession session=request.getSession();
		LoginInfo info=(LoginInfo)session.getAttribute("logininfo");

		String teacher_id = info.getM_id();
		TeachDAO dao = new TeachDAO();
		List<Subject> teachlist = new ArrayList<Subject>();
		teachlist = dao.teachendsub(teacher_id);
		Subject teachinfo = new Subject();

		teachinfo = dao.teachinfo(teacher_id);

		request.setAttribute("teachlist", teachlist);
		request.setAttribute("teachinfo", teachinfo);
		request.setAttribute("logininfo", info);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/teacher/teacher103.jsp";

	}

	// 강사 강의스케줄 > 강의 종료 목록 > 검색
	public void teachendsubsearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		String key = request.getParameter("key");
		String value = request.getParameter("value");

		TeachDAO dao = new TeachDAO();
		List<Subject> teachlist = new ArrayList<Subject>();

		if ("subject_name".equals(key)) {
			teachlist = dao.teachendsubname("TCH001", value);
		} else if ("course_name".equals(key)) {
			teachlist = dao.teachendcoursename("TCH001", value);
		}

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.write(gson.toJson(teachlist));
		out.flush();
		out.close();
	}
	
	
	
	
	////////////////////강사 개인정보//////////////////
	
	public String teachinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 액션 코드 작성
		HttpSession session=request.getSession();
		LoginInfo info=(LoginInfo)session.getAttribute("logininfo");

		List<Subject> teachsubinfo = new ArrayList<Subject>();
		String teacher_id = info.getM_id();
		TeachDAO dao = new TeachDAO();
		Subject teachinfo = new Subject();

		teachinfo = dao.teachinfo(teacher_id);
		teachsubinfo = dao.teachsubinfo(teacher_id);
	
		request.setAttribute("teachsubinfo", teachsubinfo);
		request.setAttribute("teachinfo", teachinfo);
		request.setAttribute("logininfo", info);

		// 뷰 페이지 주소 지정 -> 포워딩
		return "/WEB-INF/view/teacher/info.jsp";

	}
	
	
	
	
	// 강사 정보 변경
	public void teachinfomodify(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException, SQLException {

		
			// 액션 코드 작성
			response.setCharacterEncoding("UTF-8");
			HttpSession session=request.getSession();
			LoginInfo info=(LoginInfo)session.getAttribute("logininfo");
			String teacher_id = info.getM_id(); //로그인 중인 계정의 fk (강사의 pk와 연결)
			String pws = info.getPw(); //계정의 pw
			
			
			String teacher_phone = request.getParameter("teacher_phone"); //수정하기 위해 전달한 휴대폰 번호
			String pw = request.getParameter("pw"); //전달한 비번. 세션에있는 pw와 맞는 확인해야 한다.
			
			TeachDAO dao = new TeachDAO();
			Subject teachinfo = new Subject();
			String modifyphone="";
			
			if(pws.equals(pw)) {
				dao.teachphonemodify(teacher_phone, teacher_id);
				teachinfo = dao.teachinfo(teacher_id);
				modifyphone = teachinfo.getTeacher_phone();
				
				
			}else {
				modifyphone = "false";
			}
			
			PrintWriter out = response.getWriter();
			out.write(modifyphone);
			out.flush();
			out.close();
		}
	
	
	//강사 암호 변경창 호출을 위한 확인메소드
	public void teachpwmodify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

	
		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		LoginInfo info=(LoginInfo)session.getAttribute("logininfo");
		String pws = info.getPw(); //계정의 pw		
		String pw = request.getParameter("pw"); //전달한 비번. 세션에있는 pw와 맞는 확인해야 한다.
		String modifyphone="";
		
		if(pws.equals(pw)) {
			
		}else {
			modifyphone = "false";		
		}
		
		PrintWriter out = response.getWriter();
		out.write(modifyphone);
		out.flush();
		out.close();
	}
	
	
	
	//강사 암호 변경창 호출을 위한 확인메소드
	public void teachpwmodify2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

	
		// 액션 코드 작성
		response.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		LoginInfo info=(LoginInfo)session.getAttribute("logininfo");
		String pws = info.getPw(); //계정의 pw
		String teacher_id = info.getM_id();
		String id_ = info.getId_();
		int grade = info.getGrade();		
		String currentpw = request.getParameter("currentpw");
		String newpw = request.getParameter("newpw");
		String newpwcheck = request.getParameter("newpwcheck");
		
		String modifyphone="";
		LoginDAO dao = new LoginDAO();
		
		if(!pws.equals(currentpw)) {
			modifyphone = "false1";
			//modifyphone = "현재 암호가 틀렸습니다.";
		}else if(newpw.equals(currentpw) || newpwcheck.equals(currentpw)) {
			modifyphone = "false2";
			//modifyphone = "현재 암호와 같은 암호입니다.";
		}else if(!newpw.equals(newpwcheck)) {
			modifyphone = "false3";
			//modifyphone = "변경할 암호를 같게 입력해야합니다. 다시 확인해주세요.";
		}else {			
			//modifyphone = "비밀번호를 변경했습니다.";
			//변경하는 동시에 로그인중인 세션정보 업데이트를 한다.
			dao.teachpwmodify(newpw, teacher_id);
			LoginInfo m = new LoginInfo();
			m.setId_(id_);
			m.setGrade(grade);
			m.setM_id(teacher_id);
			m.setPw(newpw);
			session.setAttribute("logininfo", m);
			
		}
		
		PrintWriter out = response.getWriter();
		out.write(modifyphone);
		out.flush();
		out.close();
	}
	//--------------------------------------------------재홍------------------------------------------
	//--------------------------------------------------예리------------------------------------------
	// 배점 관리 페이지
		public String teacher201(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			HttpSession session = request.getSession();
			LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

			String teacher_id = info.getM_id();
			
			TeachDAO dao = new TeachDAO();
			List<Subject> bajumlist = dao.bajum_list(teacher_id, "", "");
			
			Subject teachinfo = new Subject();
			teachinfo = dao.teachinfo(teacher_id);

			request.setAttribute("teachinfo", teachinfo);
			request.setAttribute("bajumlist", bajumlist);

			return "/WEB-INF/view/teacher/teacher201.jsp";
		}

		// 배점관리 - 배점 점수 예외처리
		public String teachBajumChk(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String b_chulsuk = request.getParameter("b_chulsuk");
			String b_filki = request.getParameter("b_filki");
			String b_silki = request.getParameter("b_silki");

			if (b_chulsuk.equals("")) {
				b_chulsuk = "0";
			}
			if (b_filki.equals("")) {
				b_filki = "0";
			}
			if (b_silki.equals("")) {
				b_silki = "0";
			}

			String code = "101";

			if (Integer.parseInt(b_chulsuk) < 0 || Integer.parseInt(b_chulsuk) > 100) {
				code = "101";
			} else if (Integer.parseInt(b_filki) < 0 || Integer.parseInt(b_filki) > 100) {
				code = "101";
			} else if (Integer.parseInt(b_silki) < 0 || Integer.parseInt(b_silki) > 100) {
				code = "101";
			} else if (Integer.parseInt(b_chulsuk) + Integer.parseInt(b_filki) + Integer.parseInt(b_silki) == 100) {
				code = "100";
			}

			return String.format("/WEB-INF/view/teacher/teacher202.jsp?code=%s", code);
		}

		public String teachbajumAdd(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
		

			String code = "201";

			String savePath = request.getServletContext().getRealPath("file");
			System.out.println(savePath);

			Subject info = new Subject();
			// 파일 업로드 정보 전체(caption, filename 등)를 가져온다. -> Collection
			Collection<Part> parts = request.getParts(); // 전체 Part목록을 가져온다.
			// 개별 part
			for (Part part : parts) {

				// 파일 업로드 정보 중에서 파라미터 이름을 가져온다. : name속성의 값.
				String name = part.getName();
				System.out.println("name : " + name);

				// 파일 업로드 정보 중에서 콘텐츠 타입을 가져온다.
				String contentType = part.getContentType();

				// 파일 업로드 정보가 파일인지 일반 텍스트인지 구분하는 조건 지정
				if (part.getHeader("Content-Disposition").contains("filename=")) {

					// 파일인 경우만 물리적인 사이즈 제공, 물리적 크기를 가져온다.. byte 단위
					long size = part.getSize();
					System.out.println("size : " + size);

					// 파일 업로드 정보 중에서 파일인 경우 파일이름을 가져온다.
					String fileName = part.getSubmittedFileName();
					System.out.println("fileName : " + fileName);

					String newFileName = MyFileRename.addName(savePath, fileName);
					part.write(savePath + "//" + newFileName);
					info.setTest_munje(newFileName);

				} else {

					// 파일 업로드 정보 중에서 일반텍스트인 경우 텍스트를 가져온다.
					String open_sub_id = request.getParameter("open_sub_id");
					String b_chulsuk = request.getParameter("b_chulsuk");
					String b_filki = request.getParameter("b_filki");
					String b_silki = request.getParameter("b_silki");
					String test_date = request.getParameter("testdate");
					System.out.println("open_sub_id : " + open_sub_id);

					info.setOpen_sub_id(open_sub_id);
					info.setB_chulsuk(b_chulsuk);
					info.setB_filki(b_filki);
					info.setB_silki(b_silki);
					info.setTest_date(test_date);

				}

				System.out.println("---------------------------------");
			}

			TeachDAO dao = new TeachDAO();
			code = dao.bajuminsert(info);

			return String.format("/WEB-INF/view/redirect.jsp?url=teacher201.do&code=%s", code);
		}

		// 배점관리 - 배점 수정
		public String teachbajumUp(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String code = "301";

			String savePath = request.getServletContext().getRealPath("file");
			System.out.println(savePath);

			Subject info = new Subject();
			// 파일 업로드 정보 전체(caption, filename 등)를 가져온다. -> Collection
			Collection<Part> parts = request.getParts(); // 전체 Part목록을 가져온다.
			// 개별 part
			for (Part part : parts) {

				// 파일 업로드 정보 중에서 파라미터 이름을 가져온다. : name속성의 값.
				String name = part.getName();
				System.out.println("name : " + name);

				// 파일 업로드 정보 중에서 콘텐츠 타입을 가져온다.
				String contentType = part.getContentType();
				long size = 0;

				// 파일 업로드 정보가 파일인지 일반 텍스트인지 구분하는 조건 지정
				if (part.getHeader("Content-Disposition").contains("filename=")) {

					// 파일인 경우만 물리적인 사이즈 제공, 물리적 크기를 가져온다.. byte 단위
					size = part.getSize();
					System.out.println("size : " + size);
					if (size > 0) {

						// 파일 업로드 정보 중에서 파일인 경우 파일이름을 가져온다.
						String fileName = part.getSubmittedFileName();
						System.out.println("fileName : " + fileName);

						// 파일 업로드 정보 중에서 파일인 경우 파일을 지정된 폴더(picture)에 복사한다.
						// 주의) 동일한 이름을 가진 파일이 업로드 되는 경우 기존 파일에 대해서 덮어쓰기가 된다.
						// -> 이름 변경 정책 필요
						// -> (안전성이 보장되지 않은) 원본 이름을 없애고, (안전성이 보장되는) 새로운 이름 부여 -> 랜덤한 이름 생성하되, 중복되지 않도록
						// 생성한다. 예를 들어, 날짜/시간+난수 을 기반으로 파일 이름 생성.
						String newFileName = MyFileRename.addName(savePath, fileName);
						part.write(savePath + "//" + newFileName);
						info.setTest_munje(newFileName);
					}

				} else {

					// 파일 업로드 정보 중에서 일반텍스트인 경우 텍스트를 가져온다.
					String open_sub_id = request.getParameter("open_sub_id");
					String b_chulsuk = request.getParameter("b_chulsuk");
					String b_filki = request.getParameter("b_filki");
					String b_silki = request.getParameter("b_silki");
					String test_date = request.getParameter("testdate");
					if (size == 0 || size < 0) {
						String test_munje = request.getParameter("test_munjae");
						info.setTest_munje(test_munje);
						System.out.println("test_munje : " + test_munje);
					}
					System.out.println("open_sub_id : " + open_sub_id);

					info.setOpen_sub_id(open_sub_id);
					info.setB_chulsuk(b_chulsuk);
					info.setB_filki(b_filki);
					info.setB_silki(b_silki);
					info.setTest_date(test_date);

				}

				System.out.println("---------------------------------");
			}

			TeachDAO dao = new TeachDAO();
			code = dao.bajumupdate(info);

			return String.format("/WEB-INF/view/redirect.jsp?url=teacher201.do&code=%s", code);
		}

		// 성적 관리
		public String teacher301(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			HttpSession session = request.getSession();
			LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

			String teacher_id = info.getM_id();
			
			TeachDAO dao = new TeachDAO();
			Subject teachinfo = new Subject();
			teachinfo = dao.teachinfo(teacher_id);

			request.setAttribute("teachinfo", teachinfo);
			List<Subject> gradelist = dao.bajum_list(teacher_id, "", "");
			// System.out.println(scheduleDue.toString());
			// System.out.println("test");
			request.setAttribute("gradelist", gradelist);

			return "/WEB-INF/view/teacher/teacher301.jsp";
		}

		// 성적 관리 - 과목 선택 수강생 명단
		public String teacher302(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			HttpSession session = request.getSession();
			LoginInfo info = (LoginInfo) session.getAttribute("logininfo");

			String teacher_id = info.getM_id();
			
			TeachDAO dao = new TeachDAO();
			
			Subject teachinfo = new Subject();
			teachinfo = dao.teachinfo(teacher_id);

			request.setAttribute("teachinfo", teachinfo);
			
			String open_sub_id = request.getParameter("open_sub_id");
			List<Subject> bajuminfo = dao.bajum_list(teacher_id, open_sub_id, "select");
			request.setAttribute("bajuminfo", bajuminfo);

			List<Subject> studentlist = dao.studentList(open_sub_id);
			request.setAttribute("studentList", studentlist);

			return "/WEB-INF/view/teacher/teacher302.jsp";
		}

		// 성적 관리 - 성적 등록
		public String teachgradeAdd(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String open_sub_id = request.getParameter("open_sub_id");
			String student_id = request.getParameter("student_id");
			String g_chulsuk = request.getParameter("g_chulsuk");
			String g_filki = request.getParameter("g_filki");
			String g_silki = request.getParameter("g_silki");
			System.out.println(g_chulsuk);
			System.out.println(g_filki);
			System.out.println(g_silki);

			Subject s = new Subject();
			s.setOpen_sub_id(open_sub_id);
			s.setStudent_id(student_id);
			s.setG_chulsuk(g_chulsuk);
			s.setG_filki(g_filki);
			s.setG_silki(g_silki);

			TeachDAO dao = new TeachDAO();
			int gradeinfo = dao.gradeinsert(s);

			return String.format("teacher302.do?open_sub_id=%s", open_sub_id);
			// return "/WEB-INF/view/redirect.jsp?url=teach1_1.do";
		}

		// 성적 관리 - 성적 수정
		public String teachgradeUp(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String open_sub_id = request.getParameter("open_sub_id");
			String student_id = request.getParameter("student_id");
			String g_chulsuk = request.getParameter("g_chulsuk");
			String g_filki = request.getParameter("g_filki");
			String g_silki = request.getParameter("g_silki");
			System.out.println(g_chulsuk);
			System.out.println(g_filki);
			System.out.println(g_silki);

			Subject s = new Subject();
			s.setOpen_sub_id(open_sub_id);
			s.setStudent_id(student_id);
			s.setG_chulsuk(g_chulsuk);
			s.setG_filki(g_filki);
			s.setG_silki(g_silki);

			TeachDAO dao = new TeachDAO();
			String gradeinfo = dao.gradeupdate(s);

			return String.format("teacher302.do?open_sub_id=%s", open_sub_id);
			// return "/WEB-INF/view/redirect.jsp?url=teach1_1.do";
		}

}