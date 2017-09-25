package com.service;

import java.io.*;
import java.time.LocalDate;

import javax.servlet.*;
import javax.servlet.http.*;
import com.dao.*;
import com.dto.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.*;

public class AdminServiceClass {

	// -----------------------------------------경훈부분-------------------------------
	// "/test.do" 요청주소와 매핑되는 메소드
	public String admin201(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<BasicInfo> result = new ArrayList<BasicInfo>();
		List<BasicInfo> temp = new ArrayList<BasicInfo>();
		AdminDAO dao = new AdminDAO();

		result = dao.techerlist();
		temp = dao.addsubList();

		request.setAttribute("result", result);
		request.setAttribute("temp", temp);

		return "/WEB-INF/view/admin/admin201.jsp";

	}

	public String admin_teacher_subjectList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdminDAO dao = new AdminDAO();

		String teacher_id = request.getParameter("teacher_id");

		List<Subject> subList = new ArrayList<Subject>();

		subList = dao.sublist(teacher_id);

		request.setAttribute("subList", subList);

		return "/WEB-INF/view/admin/admin201-sub.jsp";

	}

	public String admin_teacherinsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String ssn = request.getParameter("ssn");
		String[] subject_id = request.getParameterValues("subject_id");

		if (!(name == null && phone == null && ssn == null)) {

			AdminDAO dao = new AdminDAO();

			dao.sub_add1(name, phone, ssn, subject_id);

		}

		System.out.println(Arrays.toString(subject_id));

		return "/WEB-INF/view/redirect.jsp?url=admin201.do";

	}

	public String admin_teacherupdatelist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String teacher_id = request.getParameter("teacher_id");

		List<BasicInfo> temp = new ArrayList<BasicInfo>();
		AdminDAO dao = new AdminDAO();
		temp = dao.addsubList();

		request.setAttribute("temp", temp);
		List<Subject> subList = new ArrayList<Subject>();
		subList = dao.sublist(teacher_id);
		request.setAttribute("subList", subList);

		return "/WEB-INF/view/admin/admin201-sub2.jsp";
		// return "/WEB-INF/view/redirect.jsp?url=admin201.do";

	}

	public String admin_teacherupdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String teacher_id = request.getParameter("teacher_id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String ssn = request.getParameter("ssn");
		String[] subject_id = request.getParameterValues("subject_id");
		System.out.println(teacher_id);
		System.out.println(name);
		System.out.println(phone);
		System.out.println(ssn);

		if (!(name == null && phone == null && ssn == null)) {
			AdminDAO dao = new AdminDAO();
			dao.teacherupdate(teacher_id, name, phone, ssn, subject_id);
		}

		// return "/WEB-INF/view/admin/admin201-sub2.jsp";
		return "/WEB-INF/view/redirect.jsp?url=admin201.do";

	}

	public String admin_teacherdelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String teacher_id = request.getParameter("teacher_id");

		System.out.println(teacher_id);
		AdminDAO dao = new AdminDAO();
		dao.teacherdelete(teacher_id);

		return "/WEB-INF/view/redirect.jsp?url=admin201.do";

	}

	// -----------------------------------------경훈부분-------------------------------

	// -----------------------------------------명욱이형부분-------------------------------
	// 관리자 과정목록 출력 3_1
	public String adminOpenCourseList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminDAO dao = new AdminDAO();
		List<Subject> openCourseList = dao.adminOpenCourseList();
		request.setAttribute("openCourseList", openCourseList);
		return "/WEB-INF/view/admin/admin301.jsp";
	}

	// 관리자 개설과정 삭제 3_1
	public String admin_3_1_OpenCourseDel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String open_course_id = request.getParameter("open_course_id");

		AdminDAO dao = new AdminDAO();
		dao.adminOpenCourseDelete(open_course_id);

		return "/WEB-INF/view/redirect.jsp?url=adminOpenCourseList.do";
	}

	// 관리자 개설과정 수정 3_1
	public String admin_3_1_OpenCourseUp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String open_course_id = request.getParameter("open_course_id");
		String class_id = request.getParameter("class_id");
		String course_start_day = request.getParameter("ocustartdate");
		String course_end_day = request.getParameter("ocuenddate");

		Subject openCourse = new Subject();
		openCourse.setOpen_course_id(open_course_id);
		openCourse.setClass_id(class_id);
		openCourse.setCourse_start_day(course_start_day);
		openCourse.setCourse_end_day(course_end_day);

		AdminDAO dao = new AdminDAO();
		dao.adminOpenCourseUpdate(openCourse);

		return "/WEB-INF/view/redirect.jsp?url=adminOpenCourseList.do";
	}

	// 관리자 과목목록 출력 3_2
	public String adminOpenSubList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String open_course_id = request.getParameter("open_course_id");

		AdminDAO dao = new AdminDAO();
		// 개설과정 단일 정보
		Subject openCourse = dao.adminOpenCourseInfo(open_course_id);

		// 개설과목 정보 목록
		List<Subject> openSubList = dao.adminOpenSubList(open_course_id);

		request.setAttribute("openCourse", openCourse);
		request.setAttribute("openSubList", openSubList);

		return "/WEB-INF/view/admin/admin302.jsp";
	}

	// 관리자 개설과목 삭제 3_2
	public String admin_3_2_OpenSubDel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String open_course_id = request.getParameter("open_course_id");
		String open_sub_id = request.getParameter("open_sub_id");

		AdminDAO dao = new AdminDAO();
		dao.adminOpenSubDelete(open_sub_id);

		return "/WEB-INF/view/redirect.jsp?url=adminOpenSubList.do&open_course_id=" + open_course_id;
	}

	// 관리자 개설과목 수정 3_2
	public String admin_3_2_OpenSubUp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String open_course_id = request.getParameter("open_course_id");
		String open_sub_id = request.getParameter("open_sub_id");
		String book_id = request.getParameter("book_id");
		String teacher_id = request.getParameter("teacher_id");
		String sub_start_day = request.getParameter("oubstartdate");
		String sub_end_day = request.getParameter("oubenddate");
		Subject openSub = new Subject();
		openSub.setOpen_sub_id(open_sub_id);
		openSub.setBook_id(book_id);
		openSub.setTeacher_id(teacher_id);
		openSub.setSub_start_day(sub_start_day);
		openSub.setSub_end_day(sub_end_day);

		AdminDAO dao = new AdminDAO();
		dao.adminOpenSubUpdate(openSub);

		return "/WEB-INF/view/redirect.jsp?url=adminOpenSubList.do&open_course_id=" + open_course_id;
	}

	// 관리자 수강생 목록 출력 3_3
	public String admin3_3_StudentList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		String open_course_id = request.getParameter("open_course_id");
		if (key == null || "".equals(key)) {
			key = "all";
			value = "";
		}

		String curPage_st = request.getParameter("curPage");
		int curPage_int = 0;
		if (curPage_st == null || "".equals(curPage_st)) {
			curPage_int = 1;
		} else {
			curPage_int = Integer.parseInt(curPage_st);
		}

		AdminDAO dao = new AdminDAO();
		int totalRow = dao.getStudentTotalRow(open_course_id, key, value);
		// int totalRow = 3;
		int totalPage = (int) Math.ceil(totalRow * 1.0 / 10);
		int endRow = curPage_int * 10;
		int startRow = endRow - 9;

		List<BasicInfo> studentList = dao.adminStudentList(open_course_id, key, value, startRow, endRow);
		request.setAttribute("studentList", studentList);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("curPage", curPage_int);
		request.setAttribute("key", key);
		request.setAttribute("value", value);
		request.setAttribute("open_course_id", open_course_id);
		int dateCompare = dao.adminOpenCourseInfo(open_course_id).getCourse_end_day()
				.compareToIgnoreCase(LocalDate.now().toString());
		String now = LocalDate.now().toString();
		System.out.println(dateCompare);
		request.setAttribute("dateCompare", dateCompare);
		return "/WEB-INF/view/admin/admin303.jsp";
	}

	// 과정 및 강의실 목록 JSON으로 변환 및 응답 처리
	public void admin3_1_CourseAndClassListToJSON(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		AdminDAO dao = new AdminDAO();
		List<BasicInfo> courseList = dao.adminCourseList();
		List<BasicInfo> classList = dao.adminClassList();

		// Gson 객체 생성
		Gson gson = new Gson();
		// JsonObject 객체 생성
		JsonObject js = new JsonObject();
		// JsonObject 객체에 jsonElement(jsonArray) 추가
		// add("key",jsonElement(jsonArray))
		js.add("courseList", gson.toJsonTree(courseList));
		js.add("classList", gson.toJsonTree(classList));

		// 한글 설정
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		// json 객체를 스트링 타입으로 전송
		out.write(js.toString());
		out.close();
	}

	// 개설과정 등록 메소드
	public String admin3_1_OpenCourseAdd(HttpServletRequest request, HttpServletResponse response) {
		String course_id = request.getParameter("course_id");
		String class_id = request.getParameter("class_id");
		String course_start_day = request.getParameter("ocustartdate");
		String course_end_day = request.getParameter("ocuenddate");

		Subject openCourse = new Subject();
		openCourse.setCourse_id(course_id);
		openCourse.setClass_id(class_id);
		openCourse.setCourse_start_day(course_start_day);
		openCourse.setCourse_end_day(course_end_day);

		AdminDAO dao = new AdminDAO();
		dao.adminOpenCourseInsert(openCourse);
		return "/WEB-INF/view/redirect.jsp?url=adminOpenCourseList.do";
	}

	// 개설과목 등록 메소드
	public String admin3_3_OpenSubAdd(HttpServletRequest request, HttpServletResponse response) {
		String open_course_id = request.getParameter("open_course_id");

		String subject_id = request.getParameter("subject_id");
		String book_id = request.getParameter("book_id");
		String teacher_id = request.getParameter("teacher_id");
		String sub_start_day = request.getParameter("oubstartdate");
		String sub_end_day = request.getParameter("oubenddate");

		Subject openSub = new Subject();
		openSub.setOpen_course_id(open_course_id);
		openSub.setBook_id(book_id);

		openSub.setSubject_id(subject_id);
		openSub.setTeacher_id(teacher_id);
		openSub.setSub_start_day(sub_start_day);
		openSub.setSub_end_day(sub_end_day);

		AdminDAO dao = new AdminDAO();
		dao.adminOpenSubInsert(openSub);
		return "/WEB-INF/view/redirect.jsp?url=adminOpenSubList.do&open_course_id=" + open_course_id;

	}

	// 과목 및 교재 목록 JSON으로 변환 및 응답 처리
	public void admin3_2_SubjectAndBookListToJSON(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String open_course_id = request.getParameter("open_course_id");
		AdminDAO dao = new AdminDAO();
		List<BasicInfo> subjectList = dao.adminSubjectList(open_course_id);
		List<BasicInfo> bookList = dao.adminBookList();

		// Gson 객체 생성
		Gson gson = new Gson();
		// JsonObject 객체 생성
		JsonObject js = new JsonObject();
		// JsonObject 객체에 jsonElement(jsonArray) 추가
		// add("key",jsonElement(jsonArray))
		js.add("subjectList", gson.toJsonTree(subjectList));
		js.add("bookList", gson.toJsonTree(bookList));

		// 한글 설정
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		// json 객체를 스트링 타입으로 전송
		out.write(js.toString());
		out.close();

	}

	// 강사 목록 JSON으로 변환 및 응답 처리
	public void admin3_2_TeacherListToJSON(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String subject_id = request.getParameter("subject_id");
		AdminDAO dao = new AdminDAO();
		List<BasicInfo> teacherList = dao.adminTeacherList(subject_id);
		request.setAttribute("teacherList", teacherList);
		// Gson 객체 생성
		Gson gson = new Gson();
		// JsonObject 객체 생성
		JsonObject js = new JsonObject();
		// JsonObject 객체에 jsonElement(jsonArray) 추가
		// add("key",jsonElement(jsonArray))
		js.add("teacherList", gson.toJsonTree(teacherList));

		// 한글 설정
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		// json 객체를 스트링 타입으로 전송
		out.write(js.toString());
		out.close();
	}
	// -----------------------------------------명욱이형부분-------------------------------
	//--------------------------------------------김정은------------------------------------
	// 과정
		public String adminmain(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			AdminDAO dao = new AdminDAO();
			List<Subject> infocourse = dao.basicInfoListFromCourse(1, "");
			request.setAttribute("infocourse", infocourse);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin101.jsp";

		}

		// 과목
		public String admin102(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			AdminDAO dao = new AdminDAO();
			List<Subject> infosubject = dao.basicInfoListFromSubject(1, "");
			request.setAttribute("infosubject", infosubject);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin102.jsp";

		}

		// 교재
		public String admin103(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			
			
			
			AdminDAO dao = new AdminDAO();
			List<Subject> infobook = dao.basicInfoListFromBook(1, "");
			
			request.setAttribute("infobook", infobook);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin103.jsp";

		}

		// 강의실
		public String admin104(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			AdminDAO dao = new AdminDAO();
			List<Subject> infoclass = dao.basicInfoListFromClass_(1, "");
			request.setAttribute("infoclass", infoclass);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin104.jsp";

		}

		// 과정 등록
		public String admincourseinsert(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String course_name = request.getParameter("course_name");

			if (!(course_name == null || course_name.equals(""))) {

				Subject sb = new Subject();
				sb.setCourse_name(course_name);

				AdminDAO dao = new AdminDAO();
				dao.basicInfoAddIntoCourse(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=adminmain.do&code=000";
		}

		// 과목 등록
		public String adminsubjectinsert(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String subject_name = request.getParameter("subject_name");

			if (!(subject_name == null || subject_name.equals(""))) {

				Subject sb = new Subject();
				sb.setSubject_name(subject_name);

				AdminDAO dao = new AdminDAO();
				dao.basicInfoAddIntoSubject(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=admin102.do&code=000";
		}

		// 교재 등록
		public String adminbookinsert(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String book_name = request.getParameter("book_name");
			String publisher = request.getParameter("publisher");
			String cover_img = request.getParameter("cover_img");

			if (!(book_name == null || book_name.equals(""))) {

				Subject sb = new Subject();
				sb.setBook_name(book_name);
				sb.setPublisher(publisher);
				sb.setCover_img(cover_img);

				AdminDAO dao = new AdminDAO();
				dao.basicInfoAddIntoBook(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=admin103.do&code=000";
		}

		// 강의실 등록
		public String adminclassinsert(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String class_name = request.getParameter("class_name");
			String jungwon = request.getParameter("jungwon");

			if (!(class_name == null || class_name.equals(""))) {

				Subject sb = new Subject();
				sb.setClass_name(class_name);
				sb.setJungwon(jungwon);

				AdminDAO dao = new AdminDAO();
				dao.basicInfoAddIntoClass_(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=admin104.do&code=000";
		}

		// 과정 수정
		public String admincourseupdate(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			
			String course_id = request.getParameter("course_id");
			String course_name = request.getParameter("course_name");
			

			if (!(course_id == null || course_id.equals(""))) {

				Subject sb = new Subject();
				sb.setCourse_id(course_id);
				sb.setCourse_name(course_name);

				AdminDAO dao = new AdminDAO();

				dao.basicInfoUpdateCourse(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=adminmain.do&code=000";
		}

		// 과목 수정
		public String adminsubjectupdate(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			
			String subject_id = request.getParameter("subject_id");
			String subject_name = request.getParameter("subject_name");
			
			

			if (!(subject_id == null || subject_id.equals(""))) {

				Subject sb = new Subject();
				sb.setSubject_id(subject_id);
				sb.setSubject_name(subject_name);

				AdminDAO dao = new AdminDAO();

				dao.basicInfoUpdateSubject(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=admin102.do&code=000";
		}

		// 교재 수정
		public String adminbookupdate(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			String book_id = request.getParameter("book_id");
			String book_name = request.getParameter("book_name");
			String publisher = request.getParameter("publisher");
			String cover_img = request.getParameter("cover_img");
			
			System.out.println(book_id);
			System.out.println(book_name);
			System.out.println(publisher);
			System.out.println(cover_img);
			
			

			if (!(book_id == null || book_id.equals(""))) {

				Subject sb = new Subject();
				sb.setBook_id(book_id);
				sb.setBook_name(book_name);
				sb.setPublisher(publisher);
				sb.setCover_img(cover_img);

				AdminDAO dao = new AdminDAO();

				dao.basicInfoUpdateBook(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=admin103.do&code=000";
		}

		// 강의실 수정
		public String adminclassupdate(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			String class_id = request.getParameter("class_id");
			String class_name = request.getParameter("class_name");
			String jungwon = request.getParameter("jungwon");
			
			System.out.println(class_id);
			System.out.println(class_name);
			System.out.println(jungwon);
			
			

			if (!(class_id == null || class_id.equals(""))) {

				Subject sb = new Subject();
				sb.setClass_id(class_id);
				sb.setClass_name(class_name);
				sb.setJungwon(jungwon);

				AdminDAO dao = new AdminDAO();

				dao.basicInfoUpdateClass_(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return "/WEB-INF/view/redirect.jsp?url=admin104.do&code=000";
		}

		// 과정 삭제
		public String admincoursedelete(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String code = "201";
			String course_id = request.getParameter("course_id");

			System.out.println(course_id);

			if (!(course_id == null || course_id.equals(""))) {

				Subject sb = new Subject();
				sb.setCourse_id(course_id);

				AdminDAO dao = new AdminDAO();
				code = dao.basicInfoDeleteCourse(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=adminmain.do&code=%s", code);
		}

		// 과목 삭제
		public String adminsubjectdelete(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String code = "201";
			String subject_id = request.getParameter("subject_id");

			if (!(subject_id == null || subject_id.equals(""))) {

				Subject sb = new Subject();
				sb.setSubject_id(subject_id);

				AdminDAO dao = new AdminDAO();
				code = dao.basicInfoDeleteSubject(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=admin102.do&code=%s", code);
		}

		// 교재 삭제
		public String adminbookdelete(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			String code = "201";
			String book_id = request.getParameter("book_id");

			if (!(book_id == null || book_id.equals(""))) {

				Subject sb = new Subject();
				sb.setBook_id(book_id);
				AdminDAO dao = new AdminDAO();
				code = dao.basicInfoDeleteBook(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=admin103.do&code=%s", code);
		}

		// 강의실 삭제
		public String adminclassdelete(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			String code = "201";

			String class_id = request.getParameter("class_id");

			if (!(class_id == null || class_id.equals(""))) {

				Subject sb = new Subject();
				sb.setClass_id(class_id);

				AdminDAO dao = new AdminDAO();
				code = dao.basicInfoDeleteClass_(sb);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=admin104.do&code=%s", code);
		}
		
		//교재 출력
		
		public String adminbook(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		
			String cover_img = "";
			String book_id = request.getParameter("book_id");
	 
			Subject sb = new Subject();
			sb.setBook_id(book_id);

			AdminDAO dao = new AdminDAO();
			cover_img = dao.basicInfoPicture(sb);
			
			
			
			request.setAttribute("cover_img",cover_img );
			
		 
		
			return "/WEB-INF/view/admin/adminimgname.jsp";
		}
//------------------------이지혜------------------------------------
		public String admin401(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");

			String count = "0";
			String totalcount = "0";

			// 글쓰기 성공, 실패 메시지 반환
			String code = request.getParameter("code");
			if (code == null) {
				code = "000";
			}

			// 글삭제 성공, 실패 메시지 반환
			String dcode = request.getParameter("dcode");
			if (dcode == null) {
				dcode = "000";
			}

			// 검색 기능 추가
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			if (key == null) {
				// 최초 실행시 key, value가 null인 상태이다.
				// 전체 출력용 key 값 지정
				key = "all";
				value = "";
			}

			AdminDAO dao = new AdminDAO();
			List<BasicInfo> studentlist = null;

			// 전체 출력 + 검색 출력
			studentlist = dao.studentlist(key, value);

			// 검색(or 전체) 결과 개수
			count = String.valueOf(studentlist.size());

			// 전체 개수 (검색 결과와 관계 없다)
			totalcount = String.valueOf(dao.studentlistCount());

			request.setAttribute("totalcount", totalcount);
			request.setAttribute("count", count);
			request.setAttribute("studentlist", studentlist);
			request.setAttribute("key", key);
			request.setAttribute("value", value);
			request.setAttribute("code", code);
			request.setAttribute("dcode", dcode);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin401.jsp";

		}

		// "/admin401insert.do" 요청주소와 매핑되는 메소드
		public String admin401insert(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드
			request.setCharacterEncoding("UTF-8");
			String student_name = request.getParameter("student_name");
			String student_ssn = request.getParameter("student_ssn");
			String student_phone = request.getParameter("student_phone");
			String student_regdate = request.getParameter("student_regdate");

			String code = "101"; // 등록 실패

			if (!(student_name == null || student_name.equals(""))) {

				BasicInfo g = new BasicInfo();
				g.setName(student_name);
				g.setSsn(Integer.parseInt(student_ssn));
				g.setPhone(student_phone);
				g.setRegDate(student_regdate);

				AdminDAO dao = new AdminDAO();
				code = dao.studentAdd(g);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=admin401.do&code=%s", code);
		}

		// "/admin401del.do" 요청주소와 매핑되는 메소드
		public String admin401del(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드
			request.setCharacterEncoding("UTF-8");

			String student_id = request.getParameter("student_id");

			System.out.println("student_id");

			String code = "201"; // 삭제 실패

			System.out.println("ok");

			if (!(student_id == null || student_id.equals(""))) {

				System.out.println("ok2");

				BasicInfo g = new BasicInfo();
				g.setId(student_id);

				AdminDAO dao = new AdminDAO();
				code = dao.studentDelete(g);

			}

			System.out.println("ok3");

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=admin401.do&code=%s", code);
		}

		// "/admin401del.do" 요청주소와 매핑되는 메소드
		public String admin401up(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드
			request.setCharacterEncoding("UTF-8");

			String student_id = request.getParameter("student_id");
			String student_name = request.getParameter("student_name");
			String student_ssn = request.getParameter("student_ssn");
			String student_phone = request.getParameter("student_phone");

			String code = "301"; // 삭제 실패

			if (!(student_id == null || student_id.equals(""))) {

				BasicInfo g = new BasicInfo();
				g.setId(student_id);
				g.setName(student_name);
				g.setSsn(Integer.parseInt(student_ssn));
				g.setPhone(student_phone);

				AdminDAO dao = new AdminDAO();
				code = dao.studentUpdate(g);

			}

			// 뷰 페이지 주소 지정 -> 리다이렉트
			return String.format("/WEB-INF/view/redirect.jsp?url=admin401.do&code=%s", code);
		}

		// "/admin402.do" 요청주소와 매핑되는 메소드
		public String admin402(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");

			String student_id = request.getParameter("student_id");
			String student_name = request.getParameter("student_name");
			String student_phone = request.getParameter("student_phone");

			AdminDAO dao = new AdminDAO();
			List<Subject> studentoculist = null;

			// 전체 출력 + 검색 출력
			studentoculist = dao.studentoculist("", student_id);

			request.setAttribute("student_id", student_id);
			request.setAttribute("student_name", student_name);
			request.setAttribute("student_phone", student_phone);
			request.setAttribute("studentoculist", studentoculist);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin402.jsp";

		}

		// "/admin501.do" 요청주소와 매핑되는 메소드
		public String admin501(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");

			String totalcount = "0";

			// 검색 기능 추가
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			if (key == null) {
				// 최초 실행시 key, value가 null인 상태이다.
				// 전체 출력용 key 값 지정
				key = "all";
				value = "";
			}

			AdminDAO dao = new AdminDAO();
			List<Subject> admingradeoculist = null;

			// 전체 출력 + 검색 출력
			admingradeoculist = dao.admingradeoculist(key, value);

			// 전체 개수 (검색 결과와 관계 없다)
			totalcount = String.valueOf(dao.admingradeoculistCount());

			request.setAttribute("admingradeoculist", admingradeoculist);
			request.setAttribute("totalcount", totalcount);
			request.setAttribute("key", key);
			request.setAttribute("value", value);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin501.jsp";

		}

		// "/admin502.do" 요청주소와 매핑되는 메소드
		public String admin502(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");

			String totalcount = "0";

			// 검색 기능 추가
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			if (key == null) {
				// 최초 실행시 key, value가 null인 상태이다.
				// 전체 출력용 key 값 지정
				key = "all";
				value = "";
			}

			AdminDAO dao = new AdminDAO();
			List<BasicInfo> studentlist = null;

			// 전체 출력 + 검색 출력
			studentlist = dao.studentlist(key, value);

			// 전체 개수 (검색 결과와 관계 없다)
			totalcount = String.valueOf(dao.studentlistCount());

			request.setAttribute("totalcount", totalcount);
			request.setAttribute("studentlist", studentlist);
			request.setAttribute("key", key);
			request.setAttribute("value", value);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin502.jsp";

		}

		// "/admin501_sub.do" 요청주소와 매핑되는 메소드
		// 성적관리 > 과정별 > 과목별
		public String admin501_sub(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");
			
			String open_course_id = request.getParameter("open_course_id");
			String course_name = request.getParameter("course_name");
			String course_start_day = request.getParameter("course_start_day");
			String course_end_day = request.getParameter("course_end_day");		

			AdminDAO dao = new AdminDAO();
			List<Subject> admingradeoublist = null;

			// 전체 출력 + 검색 출력
			admingradeoublist = dao.admingradeoublist("", open_course_id);

			request.setAttribute("gradeoublist", admingradeoublist);
			request.setAttribute("open_course_id", open_course_id);
			request.setAttribute("course_name", course_name);
			request.setAttribute("course_start_day", course_start_day);
			request.setAttribute("course_end_day", course_end_day);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin501_sub.jsp";

		}
		
		public String admin502_sub(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");
			
			String student_id = request.getParameter("student_id");
			String student_name = request.getParameter("student_name");
			String student_phone = request.getParameter("student_phone");
			int student_ssn = Integer.parseInt(request.getParameter("student_ssn"));
			
			AdminDAO dao = new AdminDAO();
			List<Subject> studentoculist = null;

			// 전체 출력 + 검색 출력
			studentoculist = dao.studentoculist("", student_id);

			request.setAttribute("studentoculist", studentoculist);
			request.setAttribute("student_id", student_id);
			request.setAttribute("student_name", student_name);
			request.setAttribute("student_phone", student_phone);
			request.setAttribute("student_ssn", student_ssn);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin502_sub.jsp";

		}
		
		//수강생별 수강내역 과목확인
		public String admin502_sub_oubgrade(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// 액션 코드 작성
			request.setCharacterEncoding("UTF-8");
			
			String open_course_id = request.getParameter("open_course_id");
			String student_id = request.getParameter("student_id");
			
			AdminDAO dao = new AdminDAO();
			List<Subject> studentOubGradelist = null;

			// 전체 출력 + 검색 출력
			studentOubGradelist = dao.studentOubGradelist("", open_course_id, student_id);

			request.setAttribute("studentOubGradelist", studentOubGradelist);
			request.setAttribute("student_id", student_id);
			request.setAttribute("open_course_id", open_course_id);

			// 뷰 페이지 주소 지정 -> 포워딩
			return "/WEB-INF/view/admin/admin502_Ajax.jsp";

		}	
}