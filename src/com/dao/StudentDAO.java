package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dto.Subject;

public class StudentDAO {

	// 학생 개인 정보(변경할때)
	public Subject studentList(String value) {
		Subject m = new Subject();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.connect();
			String sql = "SELECT student_id, student_name, student_ssn, student_phone, (SELECT count(*) FROM course_list WHERE course_list.student_id=student.student_id) AS count_course  FROM student WHERE student_id=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				
				m.setStudent_id(rs.getString("student_id"));
				m.setStudent_name(rs.getString("student_name"));
				m.setStudent_ssn(rs.getString("student_ssn"));
				m.setStudent_phone(rs.getString("student_phone"));
				m.setCourse_count(rs.getString("count_course"));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			
			
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return m;

	}
	
	
	// 학생 개인정보 수정

		public int studentUpdate(String student_phone, String student_id) throws SQLException {
			int result =0;

			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBConnection.connect();
				conn.setAutoCommit(false);

				String sql = "UPDATE student SET student_phone=? WHERE student_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, student_phone);
				pstmt.setString(2, student_id);
				pstmt.executeUpdate();			
				conn.commit();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				conn.rollback();
			} finally {

				try {
					
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;

		}
	
	
	
	//학생정보
	public Subject studentinfo(String student_id) {
		Subject result = new Subject();
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.connect();
			String sql = "SELECT student_id, student_name, student_ssn, student_phone FROM student WHERE student_id = ?";
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, student_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				result.setStudent_id(rs.getString("student_id"));
				result.setStudent_name(rs.getString("student_name"));
				result.setStudent_ssn(rs.getString("student_ssn"));
				result.setStudent_phone(rs.getString("student_phone"));
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;

	}
	
	
	

	// 학생의 성적조회
	public List<Subject> gradeList(String key, String value) {
		List<Subject> result = new ArrayList<Subject>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT student_name, o1.open_course_id, o1.course_id, course_name, o1.class_id, class_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day, 'YYYY-MM-DD') AS course_end_day\r\n" + 
					",(SELECT COUNT(*) \r\n" + 
					"FROM open_sub\r\n" + 
					"WHERE open_course_id = o1.open_course_id) AS count_\r\n" + 
					"FROM open_course o1, course o2, class_ o3, course_list o4, student o5\r\n" + 
					"WHERE o1.course_id=o2.course_id\r\n" + 
					"AND o1.class_id = o3.class_id\r\n" + 
					"AND o1.open_course_id = o4.open_course_id\r\n" + 
					"AND o4.student_id = o5.student_id\r\n" + 
					"AND o4.student_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject grade = new Subject();
				grade.setOpen_course_id(rs.getString("open_course_id"));
				grade.setCourse_name(rs.getString("course_name"));
				grade.setCourse_start_day(rs.getString("course_start_day"));
				grade.setCourse_end_day(rs.getString("course_end_day"));
				grade.setClass_name(rs.getString("CLASS_NAME"));
				grade.setSubject_count(rs.getString("count_"));
				result.add(grade);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

	// 학생이 수강하는 개설과정
	public List<Subject> courseList(String key, String value) {
		List<Subject> result = new ArrayList<Subject>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			// 과정번호|과정명|과정기간|강의실|[수료여부]를 불러오기위한 쿼리문
			String sql = "SELECT student_id, open_course_id, course_name, course_start_day, "
					+ " course_end_day, Completion FROM open_course_conpletion_view"
					+ " WHERE student_id = upper(?) ORDER BY OPEN_COURSE_ID";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value); // student_id
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject course = new Subject();
				course.setOpen_course_id(rs.getString("open_course_id"));
				course.setCourse_name(rs.getString("course_name"));
				course.setCourse_start_day(rs.getString("course_start_day"));
				course.setCourse_end_day(rs.getString("course_end_day"));
				course.setClass_name(rs.getString("CLASS_NAME"));
				course.setCompletion(rs.getString("Completion"));
				result.add(course);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

	/*
	 * // 학생이 수강하는 개설과정의 과목 점수 public List<Subject> subjectList(String key, String
	 * value1, String value2) { List<Subject> result = new ArrayList<Subject>();
	 * Connection conn = null; PreparedStatement pstmt = null; try { conn =
	 * DBConnection.connect(); // 과목번호|과목명|과목기간|교재명|강사명|출결배점|필기배점|실기배점|출결점수|필기점수|
	 * //실기점수|시험날짜|시험문제 출력을 위한 쿼리 String sql =
	 * "select open_course_id, course_name, open_sub_id, " +
	 * " subject_name, course_start_day, course_end_day, student_id, student_name, "
	 * + " sub_start_day, sub_end_day, book_name, teacher_name, " +
	 * " chulsuk_total_grade, filki_total_grade," +
	 * " silki_total_grade,chulsuk_grade,filki_grade,silki_grade,test_date,test_munje from grade01_view"
	 * + " where student_id=UPPER(?) and open_course_id=UPPER(?)" +
	 * " order by OPEN_sub_ID"; pstmt = conn.prepareStatement(sql);
	 * pstmt.setString(1, value1); // sudent_id pstmt.setString(2, value2); //
	 * open_course_id ResultSet rs = pstmt.executeQuery(); while (rs.next()) {
	 * Subject openSub = new Subject();
	 * openSub.setOpen_sub_id(rs.getString("OPEN_SUB_ID"));
	 * openSub.setSubject_name(rs.getString("subject_name"));
	 * openSub.setSub_start_day(rs.getString("sub_start_day"));
	 * openSub.setSub_end_day(rs.getString("sub_end_day"));
	 * openSub.setBook_name(rs.getString("book_name"));
	 * openSub.setTeacher_name(rs.getString("teacher_name"));
	 * openSub.setB_chulsuk(rs.getString("CHULSUK_TOTAL_GRADE"));
	 * openSub.setB_filki(rs.getString("FILKI_TOTAL_GRADe"));
	 * openSub.setB_silki(rs.getString("SILKI_TOTAL_GRADE"));
	 * openSub.setG_chulsuk(rs.getString("CHULSUK_GRADE"));
	 * openSub.setG_filki(rs.getString("FILKI_GRADE"));
	 * openSub.setG_silki(rs.getString("SILKI_GRADE"));
	 * openSub.setTest_date(rs.getString("test_date"));
	 * openSub.setTest_munje(rs.getString("TEST_MUNJE")); result.add(openSub); }
	 * rs.close(); } catch (ClassNotFoundException | SQLException e) { //
	 * e.printStackTrace(); System.out.println(e.getMessage()); } finally { try { if
	 * (pstmt != null) pstmt.close(); } catch (SQLException se) {
	 * se.printStackTrace(); } try { DBConnection.close(); } catch (SQLException se)
	 * { se.printStackTrace(); } } return result; }
	 */

	// 학생이 수강하는 개설과정의 과목 점수
	public List<Subject> subjectList(String key, String value1, String value2) {
		List<Subject> result = new ArrayList<Subject>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			// 과목번호|과목명|과목기간|교재명|강사명|출결배점|필기배점|실기배점|출결점수|필기점수|
			// 실기점수|시험날짜|시험문제 출력을 위한 쿼리
			String sql = "SELECT o3.open_sub_id, subject_name,TO_CHAR(sub_start_day, 'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day ,o1.open_course_id, student_name\r\n" + 
					", o8.BOOK_NAME, cover_img, o9.TEACHER_NAME, o6.CHULSUK_GRADE, o7.CHULSUK_TOTAL_GRADE, o6.FILKI_GRADE, o7.FILKI_TOTAL_GRADE, o6.SILKI_GRADE, o7.SILKI_TOTAL_GRADE\r\n" + 
					",(o6.CHULSUK_GRADE + o6.FILKI_GRADE + o6.SILKI_GRADE ) AS total , (o7.CHULSUK_TOTAL_GRADE + o7.FILKI_TOTAL_GRADE +  o7.SILKI_TOTAL_GRADE)AS total_grade\r\n" + 
					",TO_CHAR(o7.TEST_DATE, 'YYYY-MM-DD') AS test_date, o7.TEST_MUNJE\r\n" + 
					"FROM open_course o1\r\n" + 
					"LEFT OUTER JOIN open_sub o3 ON o3.OPEN_COURSE_ID = o1.OPEN_COURSE_ID\r\n" + 
					"LEFT OUTER JOIN subject o2 ON o2.SUBJECT_ID = o3.SUBJECT_ID\r\n" + 
					"LEFT OUTER JOIN course_list o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n" + 
					"LEFT OUTER JOIN student o5 ON o4.STUDENT_ID = o5.STUDENT_ID\r\n" + 
					"LEFT OUTER JOIN grade o6 ON o5.STUDENT_ID = o6.STUDENT_ID AND o6.OPEN_SUB_ID = o3.OPEN_SUB_ID\r\n" + 
					"LEFT OUTER JOIN baejum o7 ON o3.OPEN_SUB_ID = o7.OPEN_SUB_ID\r\n" + 
					"LEFT OUTER JOIN book o8 ON o3.BOOK_ID = o8.BOOK_ID\r\n" + 
					"LEFT OUTER JOIN teacher o9 ON o3.TEACHER_ID = o9.TEACHER_ID\r\n" + 
					"WHERE o4.STUDENT_ID = ?\r\n" + 
					"AND o1.open_course_id = ?\r\n" + 
					"ORDER BY o3.OPEN_course_ID";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value1); // sudent_id
			pstmt.setString(2, value2); // open_course_id
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Subject openSub = new Subject();
				openSub.setOpen_sub_id(rs.getString("OPEN_SUB_ID"));
				openSub.setSubject_name(rs.getString("subject_name"));
				openSub.setSub_start_day(rs.getString("sub_start_day"));
				openSub.setSub_end_day(rs.getString("sub_end_day"));
				openSub.setBook_name(rs.getString("book_name"));
				openSub.setTeacher_name(rs.getString("teacher_name"));
				openSub.setB_chulsuk(rs.getString("CHULSUK_TOTAL_GRADE"));
				openSub.setB_filki(rs.getString("FILKI_TOTAL_GRADe"));
				openSub.setB_silki(rs.getString("SILKI_TOTAL_GRADE"));
				openSub.setG_chulsuk(rs.getString("CHULSUK_GRADE"));
				openSub.setG_filki(rs.getString("FILKI_GRADE"));
				openSub.setG_silki(rs.getString("SILKI_GRADE"));
				openSub.setTest_date(rs.getString("test_date"));
				openSub.setTest_munje(rs.getString("TEST_MUNJE"));
				openSub.setTotal(rs.getString("total"));
				result.add(openSub);
			}
			rs.close();
		} catch (ClassNotFoundException | SQLException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}
	
	
	public String studentPicture(Subject basicInfo) {
		String cover_img = "";

	
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();
			String sql = "SELECT cover_img FROM book WHERE book_name = ?";
		

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, basicInfo.getBook_name());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				cover_img = rs.getString("cover_img");
				}
			rs.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} // end try

		return cover_img;
	}
}
