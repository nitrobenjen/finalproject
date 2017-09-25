package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dto.*;

public class TeachDAO {
//------------------------재홍--------------------------------------------------------------------
	// 강사 개인정보 가져오기 ()

	public Subject teachinfo(String value) {
		Subject m = new Subject();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS teacher_hiredate FROM teacher WHERE teacher_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				m.setTeacher_id(rs.getString("teacher_id"));
				m.setTeacher_name(rs.getString("teacher_name"));
				m.setTeacher_ssn(rs.getString("teacher_ssn"));
				m.setTeacher_phone(rs.getString("teacher_phone"));
				m.setTeacher_hiredate(rs.getString("teacher_hiredate"));
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

	// 강사 개인정보 수정(휴대폰)

	public int teachphonemodify(String teacher_phone, String teacher_id) throws SQLException {
		int result =0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();
			conn.setAutoCommit(false);

			String sql = "UPDATE teacher SET teacher_phone=? WHERE teacher_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher_phone);
			pstmt.setString(2, teacher_id);
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

	// 특정강사의 강의가능한 과목 목록

	public List<Subject> teachsubinfo(String value) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o2.subject_id, subject_name\r\n" + "FROM teacher o1\r\n"
					+ "LEFT OUTER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID\r\n"
					+ "LEFT OUTER JOIN subject o3 ON o2.SUBJECT_ID = o3.SUBJECT_ID\r\n" + "WHERE o1.teacher_id = ?\r\n"
					+ "ORDER BY o1.teacher_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Subject m = new Subject();
				m.setSubject_id(rs.getString("subject_id"));
				m.setSubject_name(rs.getString("subject_name"));
				result.add(m);
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

		return result;

	}

	///////////////////////// 강의 예정/////////////////////////////////////////

	// 특정 강사의 강의스케쥴 > 강의 예정 출력
	// 과목ID 과목명 과목기간 과정명 과정기간 강의실 교재명 수강생인원 수강생명단(수) 세부 명단은 별도의 액션으로 처리한다.
	public List<Subject> teachsubschedule(String value) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID WHERE o7.TEACHER_ID = ? "
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') > TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 과목 검색
	public List<Subject> teachsubschedulesubname(String teahcer_id, String subject_name) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n"
					+ "WHERE o7.TEACHER_ID = ? AND INSTR(subject_name, ?)>0\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') > TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teahcer_id);
			pstmt.setString(2, subject_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 과정검색
	public List<Subject> teachsubschedulecoursename(String teahcer_id, String subject_name) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n"
					+ "WHERE o7.TEACHER_ID = ? AND INSTR(course_name, ?)>0\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') > TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teahcer_id);
			pstmt.setString(2, subject_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 특정 과정에 속한 수강생 명단
	public List<Subject> teachstulist(String value) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o1.student_id, student_name, student_phone, student_regdate, DECODE(finish_day, null, '수료예정', CONCAT('중도탈락-',finish_day)) AS finish_day\r\n"
					+ "FROM student o1\r\n" + "INNER JOIN course_list o2 ON o1.STUDENT_ID = o2.STUDENT_ID\r\n"
					+ "INNER JOIN open_course o4 ON o2.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN fail_check o3 ON o2.STUDENT_ID = o3.STUDENT_ID AND o2.OPEN_COURSE_ID = o3.OPEN_COURSE_ID\r\n"
					+ "WHERE o4.open_course_id = ? " + "ORDER BY o1.student_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setStudent_id(rs.getString("student_id"));
				m.setStudent_name(rs.getString("student_name"));
				m.setStudent_phone(rs.getString("student_phone"));
				m.setStudent_regdate(rs.getString("student_regdate"));
				m.setFinish_day(rs.getString("finish_day"));

				result.add(m);
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

		return result;

	}

	///////////////////////// 강의 중/////////////////////////////////////////

	// 특정 강사의 강의스케쥴 > 강의 중 출력
	// 과목ID 과목명 과목기간 과정명 과정기간 강의실 교재명 수강생인원 수강생명단(수) 세부 명단은 별도의 액션으로 처리한다.
	public List<Subject> teachinsub(String value) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n" + "WHERE o7.TEACHER_ID = ?\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') <= TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND TO_CHAR(sub_end_day, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 과목 검색
	public List<Subject> teachinsubname(String teahcer_id, String subject_name) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n"
					+ "WHERE o7.TEACHER_ID = ? AND INSTR(subject_name, ?)>0\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') <= TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND TO_CHAR(sub_end_day, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teahcer_id);
			pstmt.setString(2, subject_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 과정검색
	public List<Subject> teachinsubcoursename(String teahcer_id, String subject_name) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n"
					+ "WHERE o7.TEACHER_ID = ? AND INSTR(course_name, ?)>0\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') <= TO_CHAR(SYSDATE, 'YYYY-MM-DD') AND TO_CHAR(sub_end_day, 'YYYY-MM-DD') >= TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teahcer_id);
			pstmt.setString(2, subject_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	///////////////////////// 강의 종료/////////////////////////////////////////

	// 특정 강사의 강의스케쥴 > 강의 종료 출력
	// 과목ID 과목명 과목기간 과정명 과정기간 강의실 교재명 수강생인원 수강생명단(수) 세부 명단은 별도의 액션으로 처리한다.
	public List<Subject> teachendsub(String value) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n" + "WHERE o7.TEACHER_ID = ?\r\n"
					+ "AND TO_CHAR(sub_end_day,'YYYY-MM-DD') < TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 과목 검색
	public List<Subject> teachendsubname(String teahcer_id, String subject_name) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n"
					+ "WHERE o7.TEACHER_ID = ? AND INSTR(subject_name, ?)>0\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') < TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teahcer_id);
			pstmt.setString(2, subject_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}

	// 과정검색
	public List<Subject> teachendcoursename(String teahcer_id, String subject_name) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT o4.open_sub_id,subject_name, TO_CHAR(sub_start_day,'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day,\r\n"
					+ "o1.open_course_id, course_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day,  class_name, DECODE(book_name, null, '교재없음', book_name) AS book_name\r\n"
					+ ",(SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.open_course_id) AS student_count, jungwon\r\n"
					+ "FROM open_course o1\r\n" + "LEFT OUTER JOIN course o2 ON o1.COURSE_ID = o2.COURSE_ID\r\n"
					+ "LEFT OUTER JOIN class_ o3 ON o1.CLASS_ID = o3.CLASS_ID\r\n"
					+ "LEFT OUTER JOIN open_sub o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID\r\n"
					+ "LEFT OUTER JOIN subject o5 ON o4.subject_id = o5.subject_id\r\n"
					+ "LEFT OUTER JOIN teach_sub o6 ON o5.subject_id = o6.subject_id\r\n"
					+ "LEFT OUTER JOIN teacher o7 ON o6.teacher_id = o7.teacher_id AND o7.teacher_id = o4.teacher_id\r\n"
					+ "LEFT OUTER JOIN book o8 ON o4.BOOK_ID = o8.BOOK_ID\r\n"
					+ "WHERE o7.TEACHER_ID = ? AND INSTR(course_name, ?)>0\r\n"
					+ "AND TO_CHAR(sub_start_day,'YYYY-MM-DD') < TO_CHAR(SYSDATE, 'YYYY-MM-DD')\r\n"
					+ "ORDER BY o1.open_course_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teahcer_id);
			pstmt.setString(2, subject_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Subject m = new Subject();
				m.setOpen_sub_id(rs.getString("open_sub_id"));
				m.setSubject_name(rs.getString("subject_name"));
				m.setSub_start_day(rs.getString("sub_start_day"));
				m.setSub_end_day(rs.getString("sub_end_day"));
				m.setOpen_course_id(rs.getString("open_course_id"));
				m.setCourse_name(rs.getString("course_name"));
				m.setCourse_start_day(rs.getString("course_start_day"));
				m.setCourse_end_day(rs.getString("course_end_day"));
				m.setClass_name(rs.getString("class_name"));
				m.setBook_name(rs.getString("book_name"));
				m.setStudent_count(rs.getString("student_count"));
				m.setJungwon(rs.getString("jungwon"));

				result.add(m);
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

		return result;

	}
//------------------------재홍--------------------------------------------------------------------
//------------------------예리--------------------------------------------------------------------


	public List<Subject> courseScheduleList(String key, String teacher_id) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();
			// 과목번호, 과목명, 과목기간, 과정명, 과정기간, 강의실, 교재명, 수강생인원
			// 강의 예정, 강의 중, 강의 종료
			String sql = "SELECT open_sub_id, sub_name, to_char(sub_start_day, 'YYYY/MM/DD') AS sub_start_day, to_char(sub_end_day, 'YYYY/MM/DD') AS sub_end_day, course_name, TO_CHAR(course_start_day, 'YYYY/DD/MM') AS course_start_day, TO_CHAR(course_end_day, 'YYYY/MM/DD') AS course_end_day, class_name, book_name, count_ FROM teach_schedule";

			switch (key) {
			case "due":
				sql += " WHERE to_char(sub_start_day, 'YYYY/MM/DD') > to_char(sysdate, 'YYYY/MM/dd') and teacher_id = ?";
				break;
			case "ing":
				sql += " WHERE to_char(sub_start_day, 'YYYY/MM/DD') <= TO_CHAR(SYSDATE, 'YYYY/MM/dd') AND to_char(sub_end_day, 'YYYY/MM/DD') >= TO_CHAR(SYSDATE, 'YYYY/MM/DD') and teacher_id = ?";
				break;
			case "end":
				sql += " WHERE to_char(sub_end_day, 'YYYY/MM/DD') < TO_CHAR(SYSDATE, 'YYYY/MM/dd') AND teacher_id = ?";
				break;
			}

			sql += " ORDER BY sub_start_day, sub_end_day";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String open_sub_id = rs.getString("open_sub_id");
				String sub_name = rs.getString("sub_name");
				String sub_start_day = rs.getString("sub_start_day");
				String sub_end_day = rs.getString("sub_end_day");
				String course_name = rs.getString("course_name");
				String course_start_day = rs.getString("course_start_day");
				String course_end_day = rs.getString("course_end_day");
				String class_name = rs.getString("class_name");
				String book_name = rs.getString("book_name");
				String count_ = rs.getString("count_");

				Subject tb = new Subject();
				tb.setOpen_sub_id(open_sub_id);
				tb.setSubject_name(sub_name);
				tb.setSub_start_day(sub_start_day);
				tb.setSub_end_day(sub_end_day);
				tb.setCourse_name(course_name);
				tb.setCourse_start_day(course_start_day);
				tb.setCourse_end_day(course_end_day);
				tb.setClass_name(class_name);
				tb.setBook_name(book_name);
				tb.setStudent_count(count_);
				tb.setTeacher_id(teacher_id);

				result.add(tb);
			}

			rs.close();

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
				// nothing we can do
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

//-------------여기서부터 합쳐야함	

	// 배점관리, 성적관리에서 종료된 과목 목록 출력을 위한 메소드
	public List<Subject> bajum_list(String teacher_id, String sub_id, String key) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();
			// 과목번호, 과목명, 과목기간, 과정명, 과정기간, 강의실, 교재명, 수강생인원
			// 강의 예정, 강의 중, 강의 종료
			String sql = "SELECT open_sub_id, sub_name, TO_CHAR(sub_start_day,'YYYY/MM/DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY/MM/DD') AS sub_end_day, course_name, TO_CHAR(course_start_day,'YYYY/MM/DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY/MM/DD') AS course_end_day, class_name, book_name, b_chulsuk, b_filki, b_silki, test_date, test_munje FROM GRADE_VIEW WHERE teacher_id = ?";
			switch (key) {
			case "select":
				sql += " AND open_sub_id = ?";
				break;
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher_id);
			switch (key) {
			case "select":
				pstmt.setString(2, sub_id);
				break;
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String open_sub_id = rs.getString("open_sub_id");
				String sub_name = rs.getString("sub_name");
				String sub_start_day = rs.getString("sub_start_day");
				String sub_end_day = rs.getString("sub_end_day");
				String course_name = rs.getString("course_name");
				String course_start_day = rs.getString("course_start_day");
				String course_end_day = rs.getString("course_end_day");
				String class_name = rs.getString("class_name");
				String book_name = rs.getString("book_name");
				String b_chulsuk = rs.getString("b_chulsuk");
				String b_filki = rs.getString("b_filki");
				String b_silki = rs.getString("b_silki");
				String test_date = rs.getString("test_date");
				String test_munje = rs.getString("test_munje");

				Subject tb = new Subject();
				tb.setOpen_sub_id(open_sub_id);
				tb.setSubject_name(sub_name);
				tb.setSub_start_day(sub_start_day);
				tb.setSub_end_day(sub_end_day);
				tb.setCourse_name(course_name);
				tb.setCourse_start_day(course_start_day);
				tb.setCourse_end_day(course_end_day);
				tb.setClass_name(class_name);
				tb.setBook_name(book_name);
				tb.setB_chulsuk(b_chulsuk);
				tb.setB_filki(b_filki);
				tb.setB_silki(b_silki);
				tb.setTest_date(test_date);
				tb.setTest_munje(test_munje);

				result.add(tb);
			}

			rs.close();

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
				// nothing we can do
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}

	// 배점 입력
	public String bajuminsert(Subject info) {
		String result = "201";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();
			String sql = "INSERT INTO baejum (open_sub_id, chulsuk_total_grade, filki_total_grade, silki_total_grade, test_date, test_munje) values (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, info.getOpen_sub_id());
			pstmt.setInt(2, Integer.parseInt(info.getB_chulsuk()));
			pstmt.setInt(3, Integer.parseInt(info.getB_filki()));
			pstmt.setInt(4, Integer.parseInt(info.getB_silki()));
			pstmt.setString(5, info.getTest_date());
			pstmt.setString(6, info.getTest_munje());
			pstmt.executeUpdate();
			result = "200";
			System.out.println(sql);

		} catch (ClassNotFoundException | SQLException e1) {
			if (e1.getMessage().contains("ORA-00001")) {
				result = "202";
			} else {
				e1.getStackTrace();
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
				// nothing we can do
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

	// 배점 수정
	public String bajumupdate(Subject info) {
		String result = "301";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();
			String sql = "UPDATE baejum SET chulsuk_total_grade = ?, filki_total_grade = ?, silki_total_grade = ?, test_date = ?, test_munje = ? WHERE open_sub_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(info.getB_chulsuk()));
			pstmt.setInt(2, Integer.parseInt(info.getB_filki()));
			pstmt.setInt(3, Integer.parseInt(info.getB_silki()));
			pstmt.setString(4, info.getTest_date());
			pstmt.setString(5, info.getTest_munje());
			pstmt.setString(6, info.getOpen_sub_id());
			pstmt.executeUpdate();
			result = "300";
			System.out.println(sql);

		} catch (ClassNotFoundException | SQLException e1) {
			if (e1.getMessage().contains("ORA-00001")) {
				result = "302";
			} else {
				e1.getStackTrace();
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
				// nothing we can do
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

	public List<Subject> studentList(String open_sub_id) {
		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();

			String sql = "SELECT student_id, student_name, student_phone,  TO_CHAR(student_regdate,'YYYY/MM/DD') AS student_regdate, pass_drop, TO_CHAR(pass_date,'YYYY/MM/DD') AS pass_date, g_chulsuk, g_filki, g_silki, sum_grade FROM teacher_stu_info_grade_view WHERE open_sub_id = ? ORDER BY student_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, open_sub_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String student_id = rs.getString("student_id");
				String student_name = rs.getString("student_name");
				String pass_drop = rs.getString("pass_drop");
				String finish_day = rs.getString("pass_date");
				String g_chulsuk = rs.getString("g_chulsuk");
				String g_filki = rs.getString("g_filki");
				String g_silki = rs.getString("g_silki");
				String total = rs.getString("sum_grade");
				String student_phone = rs.getString("student_phone");
				String student_regdate = rs.getString("student_regdate");

				Subject tb = new Subject();
				tb.setStudent_id(student_id);
				tb.setStudent_name(student_name);
				tb.setPass_drop(pass_drop);
				tb.setFinish_day(finish_day);
				tb.setG_chulsuk(g_chulsuk);
				tb.setG_filki(g_filki);
				tb.setG_silki(g_silki);
				tb.setTotal(total);
				tb.setStudent_phone(student_phone);
				tb.setStudent_regdate(student_regdate);

				result.add(tb);
			}
			rs.close();

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
				// nothing we can do
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}

	// 성적 관리
	public int gradeinsert(Subject s) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();

			String sql = "INSERT INTO grade (student_id, open_sub_id, filki_grade, silki_grade, chulsuk_grade) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getStudent_id());
			pstmt.setString(2, s.getOpen_sub_id());
			pstmt.setInt(3, Integer.parseInt(s.getG_filki()));
			pstmt.setInt(4, Integer.parseInt(s.getG_silki()));
			pstmt.setInt(5, Integer.parseInt(s.getG_chulsuk()));
			System.out.println(s.getStudent_id());
			System.out.println(s.getOpen_sub_id());
			result = pstmt.executeUpdate();

			result = 1;

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

		}
		return result;
	}

	// 배점 수정
	public String gradeupdate(Subject s) {
		String result = "401";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBConnection.connect();
			String sql = "UPDATE grade SET chulsuk_grade = ?, filki_grade = ?, silki_grade = ? WHERE student_id = ? and open_sub_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(s.getG_chulsuk()));
			pstmt.setInt(2, Integer.parseInt(s.getG_filki()));
			pstmt.setInt(3, Integer.parseInt(s.getG_silki()));
			pstmt.setString(4, s.getStudent_id());
			pstmt.setString(5, s.getOpen_sub_id());
			pstmt.executeUpdate();
			result = "400";
			System.out.println(sql);

		} catch (ClassNotFoundException | SQLException e1) {
			if (e1.getMessage().contains("ORA-00001")) {
				result = "402";
			} else {
				e1.getStackTrace();
			}
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
				// nothing we can do
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}
}
