package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.*;

public class AdminDAO {
	
	//---------------------경훈부분-------------------------------

	public List<BasicInfo> techerlist() {
		List<BasicInfo> result = new ArrayList<BasicInfo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS hiredate,"
					+ " (SELECT count(*) FROM teach_sub WHERE teach_sub.teacher_id = teacher.teacher_id ) AS count1_,"
					+ " (SELECT COUNT(*) FROM open_sub WHERE open_sub.TEACHER_ID = teacher.TEACHER_ID) AS count2_"
					+ " FROM teacher ";
			System.out.println(sql);

			sql += "ORDER BY teacher_id";

			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("teacher_id");
				String name = rs.getString("teacher_name");
				int ssn = rs.getInt("teacher_ssn");
				String phone = rs.getString("teacher_phone");
				String regDate = rs.getString("hiredate");
				int count1 = rs.getInt("count1_");
				int count2 = rs.getInt("count2_");

				BasicInfo b = new BasicInfo();

				b.setId(id);
				b.setName(name);
				b.setSsn(ssn);
				b.setPhone(phone);
				b.setRegDate(regDate);
				b.setSub_count(count1);
				b.setOpen_sub_count(count2);

				result.add(b);
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
		}

		return result;
	}

	public List<Subject> sublist(String teacher_id) {

		List<Subject> result = new ArrayList<Subject>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT subject.subject_id AS subject_id, subject.subject_name AS subject_name FROM subject inner join teach_sub on subject.SUBJECT_ID = teach_sub.SUBJECT_ID"
					+ " WHERE teach_sub.teacher_id=?";
			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, teacher_id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String subject_id = rs.getString("subject_id");
				String subject_name = rs.getString("subject_name");

				Subject s = new Subject();
				s.setSubject_id(subject_id);
				s.setSubject_name(subject_name);

				result.add(s);
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
		}

		return result;
	}

	public List<BasicInfo> addsubList() {
		List<BasicInfo> result = new ArrayList<BasicInfo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT subject_id, subject_name FROM subject";

			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String subject_id = rs.getString("subject_id");
				String subject_name = rs.getString("subject_name");

				BasicInfo s = new BasicInfo();
				s.setId(subject_id);
				s.setName(subject_name);

				result.add(s);
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
		}

		return result;
	}

	public String sub_add1(String name, String phone, String ssn, String[] subject_id) {
		String temp = "101";
		String newId = "";

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;

		try {
			conn = DBConnection.connect();

			conn.setAutoCommit(false);

			String sql1 = "select concat('TCH',lpad(substr(NVL(MAX(teacher_id),0),-3,3)+1,3,000)) as newid from teacher";
			pstmt1 = conn.prepareStatement(sql1);
			ResultSet rs1 = pstmt1.executeQuery();

			while (rs1.next()) {
				newId = rs1.getString("newid");
			}
			rs1.close();

			String sql2 = "insert into teacher (teacher_id,teacher_name,teacher_ssn,teacher_phone,teacher_hiredate) values (?,?,?,?,sysdate)";
			System.out.println(sql2);

			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, newId);
			pstmt2.setString(2, name);
			pstmt2.setString(3, ssn);
			pstmt2.setString(4, phone);
			pstmt2.executeUpdate();

			if (subject_id != null) {
				for (int i = 0; i < subject_id.length; ++i) {

					String sql3 = "insert into teach_sub (teacher_id, subject_id) values (upper(?),upper(?))";
					pstmt3 = conn.prepareStatement(sql3);

					pstmt3.setString(1, newId);
					pstmt3.setString(2, subject_id[i]);

					pstmt3.executeUpdate();
				}
			}

			conn.commit();

		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} finally {
			try {
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (pstmt3 != null)
					pstmt3.close();

			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return temp;
	}

	public String teacherupdate(String teacher_id, String name, String phone, String ssn, String[] subject_id) {

		String temp = "101";

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;

		try {
			conn = DBConnection.connect();

			conn.setAutoCommit(false);

			String sql3 = "delete from teach_sub where teacher_id = ?";
			pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setString(1, teacher_id);
			pstmt3.executeUpdate();

			String sql1 = "delete from teacher where teacher_id = ?";
			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, teacher_id);
			pstmt1.executeUpdate();

			String sql2 = "insert into teacher (teacher_id,teacher_name,teacher_ssn,teacher_phone,teacher_hiredate) values (?,?,?,?,sysdate)";

			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, teacher_id);
			pstmt2.setString(2, name);
			pstmt2.setString(3, ssn);
			pstmt2.setString(4, phone);
			pstmt2.executeUpdate();

			if (subject_id != null) {
				for (int i = 0; i < subject_id.length; ++i) {

					String sql4 = "insert into teach_sub (teacher_id, subject_id) values (upper(?),upper(?))";
					pstmt4 = conn.prepareStatement(sql4);

					pstmt4.setString(1, teacher_id);
					pstmt4.setString(2, subject_id[i]);

					pstmt4.executeUpdate();
				}
			}

			conn.commit();

		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} finally {
			try {
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();
				if (pstmt3 != null)
					pstmt3.close();
				if (pstmt4 != null)
					pstmt4.close();

			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return temp;

	}

	public String teacherdelete(String teacher_id) {

		String temp = "101";

		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			conn = DBConnection.connect();

			conn.setAutoCommit(false);

			String sql1 = "delete from teach_sub where teacher_id = ?";

			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, teacher_id);
			pstmt1.executeUpdate();

			String sql2 = "delete from teacher where teacher_id = ?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, teacher_id);
			pstmt2.executeUpdate();

			conn.commit();

		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} finally {
			try {

				if (pstmt1 != null)
					pstmt1.close();
				if (pstmt2 != null)
					pstmt2.close();

			} catch (SQLException se2) {
			}
			try {
				DBConnection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return temp;

	}
	
	//---------------------경훈부분-------------------------------
	
	//---------------------------------------명욱이형부분------------------------------------
	
	// 개설과정목록
		public List<Subject> adminOpenCourseList() {
			List<Subject> result = new ArrayList<Subject>();
			Connection conn = null;
			PreparedStatement pstmt01 = null;
			ResultSet rs01 = null;

			try {
				conn = DBConnection.connect();
				String sql01 = "select open_course_id,course_name,class_name,jungwon, (select count(*) from course_list where open_course.open_course_id=course_list.open_course_id) as student_count"
						+ ",to_char(course_start_day,'YYYY-MM-DD') as course_start_day,to_char(course_end_day,'YYYY-MM-DD') as course_end_day,"
						+ "(select count(*) from open_sub where open_course.open_course_id=open_sub.open_course_id) as subject_count"
						+ " from open_course" + " inner join course on open_course.COURSE_ID = course.COURSE_ID"
						+ " inner join class_ on open_course.CLASS_ID = class_.CLASS_ID order by open_course_id desc";
				pstmt01 = conn.prepareStatement(sql01);
				rs01 = pstmt01.executeQuery();
				while (rs01.next()) {
					// open_course_id,course_name,class_name,jungwon,
					// student_count,course_start_day,course_end_day,subject_count
					Subject subject = new Subject();
					subject.setOpen_course_id(rs01.getString("open_course_id"));
					subject.setCourse_name(rs01.getString("course_name"));
					subject.setClass_name(rs01.getString("class_name"));
					subject.setCourse_start_day(rs01.getString("course_start_day"));
					subject.setCourse_end_day(rs01.getString("course_end_day"));
					subject.setJungwon(rs01.getString("jungwon"));
					subject.setStudent_count(rs01.getString("student_count"));
					subject.setSubject_count(rs01.getString("subject_count"));
					result.add(subject);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs01 != null) {
						rs01.close();
					}
					if (pstmt01 != null) {
						pstmt01.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 개설과정단일정보
		public Subject adminOpenCourseInfo(String open_course_id) {
			Subject result = new Subject();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql01 = "select open_course_id,course_name,class_name,jungwon, (select count(*) from course_list where open_course.open_course_id=course_list.open_course_id) as student_count"
						+ ",to_char(course_start_day,'YYYY-MM-DD') as course_start_day,to_char(course_end_day,'YYYY-MM-DD') as course_end_day,"
						+ "(select count(*) from open_sub where open_course.open_course_id=open_sub.open_course_id) as subject_count"
						+ " from open_course" + " inner join course on open_course.COURSE_ID = course.COURSE_ID"
						+ " inner join class_ on open_course.CLASS_ID = class_.CLASS_ID where open_course.open_course_id=? order by open_course_id desc";
				pstmt = conn.prepareStatement(sql01);
				pstmt.setString(1, open_course_id);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					// open_course_id,course_name,class_name,jungwon,
					// student_count,course_start_day,course_end_day,subject_count

					result.setOpen_course_id(rs.getString("open_course_id"));
					result.setCourse_name(rs.getString("course_name"));
					result.setClass_name(rs.getString("class_name"));
					result.setCourse_start_day(rs.getString("course_start_day"));
					result.setCourse_end_day(rs.getString("course_end_day"));
					result.setJungwon(rs.getString("jungwon"));
					result.setStudent_count(rs.getString("student_count"));
					result.setSubject_count(rs.getString("subject_count"));
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 개설과목 목록
		public List<Subject> adminOpenSubList(String open_course_id) {
			List<Subject> result = new ArrayList<Subject>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql = "select open_sub.open_sub_id,open_sub.subject_id,subject_name,teacher_name,NVL(book_name,'교재없음') as book_name ,open_course_id\r\n"
						+ ",to_char(sub_start_day,'YYYY-MM-DD') as sub_start_day,to_char(sub_end_day,'YYYY-MM-DD') as  sub_end_day\r\n"
						+ ",(select count(*) from grade where grade.open_sub_id=open_sub.open_sub_id) as grade_count\r\n"
						+ ",filki_total_grade,silki_total_grade,chulsuk_total_grade,test_munje\r\n"
						+ ",to_char(test_date,'YYYY-MM-DD') as test_date\r\n" + " from open_sub\r\n"
						+ " inner join subject on subject.SUBJECT_ID = open_sub.SUBJECT_ID\r\n"
						+ " inner join  TEACHER on TEACHER.TEACHER_ID = open_sub.TEACHER_ID\r\n"
						+ " left outer join book on open_sub.BOOK_ID = book.BOOK_ID\r\n"
						+ " left outer join baejum on open_sub.open_sub_id=baejum.open_sub_id\r\n"
						+ " where open_course_id=? \r\n" + " order by open_sub_id desc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, open_course_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					// open_course_id,course_name,class_name,jungwon,
					// student_count,course_start_day,course_end_day,subject_count
					Subject subject = new Subject();
					subject.setOpen_sub_id(rs.getString("open_sub_id"));
					subject.setSubject_name(rs.getString("subject_name"));
					subject.setSubject_id(rs.getString("subject_id"));
					subject.setSub_start_day(rs.getString("sub_start_day"));
					subject.setSub_end_day(rs.getString("sub_end_day"));
					subject.setBook_name(rs.getString("book_name"));
					subject.setTeacher_name(rs.getString("teacher_name"));

					result.add(subject);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 개설과정을 수강하는 수강생 목록 검색 및 페이징
		public List<BasicInfo> adminStudentList(String open_course_id, String key, String value, int startRow, int endRow) {
			List<BasicInfo> result = new ArrayList<BasicInfo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				// String sql = "select student_id,
				// student_name,student_phone,to_char(student_regdate,'YYYY-MM-DD') as
				// student_regdate ,student_ssn,(select count(*) from course_list where
				// student.STUDENT_ID = course_list.STUDENT_ID ) as open_course_count from
				// student ";
				String sql = "select * \r\n"
						+ " from (select temp.*,rownum as rownum_ from (select student.student_id, student_name,student_phone,to_char(student_regdate,'YYYY-MM-DD') as student_regdate ,student_ssn,(select count(*) from course_list where student.STUDENT_ID = course_list.STUDENT_ID ) as open_course_count,to_char(finish_day,'YYYY-MM-DD') as finish_day from student inner join course_list on student.STUDENT_ID = course_list.STUDENT_ID left outer join  fail_check on  student.student_id=fail_check.student_id and course_list.open_course_id=fail_check.open_course_id";
				switch (key) {
				case "all":
					sql += " where course_list.open_course_id =?";
					break;
				case "id":
					sql += " where course_list.open_course_id =? and student_id=upper(?)";
					break;
				case "name":
					sql += " where course_list.open_course_id =? and INSTR(student_name, ?) > 0";
					break;
				case "phone":
					sql += " where course_list.open_course_id =? and INSTR(student_phone, ?) > 0 ";
					break;
				case "regDate":
					sql += " where course_list.open_course_id =? and instr((to_char(student_regdate,'YYYY-MM-DD')),?)>0";
					break;
				}
				sql += ") temp where rownum <=?) where rownum_>=? order by student_id";

				pstmt = conn.prepareStatement(sql);
				switch (key) {
				case "all":
					pstmt.setString(1, open_course_id);
					pstmt.setInt(2, endRow);
					pstmt.setInt(3, startRow);
					break;
				case "id":
				case "name":
				case "phone":
				case "regDate":
					pstmt.setString(1, open_course_id);
					pstmt.setString(2, value);
					pstmt.setInt(3, endRow);
					pstmt.setInt(4, startRow);
					break;
				}

				rs = pstmt.executeQuery();
				while (rs.next()) {
					BasicInfo basicInfo = new BasicInfo();
					basicInfo.setId(rs.getString("student_id"));
					basicInfo.setName(rs.getString("student_name"));
					basicInfo.setSsn(rs.getInt("student_ssn"));
					basicInfo.setPhone(rs.getString("student_phone"));
					basicInfo.setRegDate(rs.getString("student_regdate"));
					basicInfo.setCount(rs.getInt("open_course_count"));
					basicInfo.setFinish_day(rs.getString("finish_day")!=null?"중도탈락("+rs.getString("finish_day")+")":null);
					result.add(basicInfo);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 개설과정을 수강하는 수강생 페이징 처리를 위한 총 ROW수 얻기 검색 가능
		public int getStudentTotalRow(String open_course_id, String key, String value) {
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql = "select count(*) from student inner join course_list on student.STUDENT_ID = course_list.STUDENT_ID left outer join  fail_check on  student.student_id=fail_check.student_id and course_list.open_course_id=fail_check.open_course_id";
				switch (key) {
				case "all":
					sql += " where course_list.open_course_id =?";
					break;
				case "id":
					sql += " where course_list.open_course_id =? and student_id=upper(?)";
					break;
				case "name":
					sql += " where course_list.open_course_id =? and INSTR(student_name, ?) > 0";
					break;
				case "phone":
					sql += " where course_list.open_course_id =? and INSTR(student_phone, ?) > 0 ";
					break;
				case "regDate":
					sql += " where course_list.open_course_id =? and instr((to_char(student_regdate,'YYYY-MM-DD')),?)>0";
					break;
				}

				pstmt = conn.prepareStatement(sql);
				switch (key) {
				case "all":
					pstmt.setString(1, open_course_id);
					break;
				case "id":
				case "name":
				case "phone":
				case "regDate":
					pstmt.setString(1, open_course_id);
					pstmt.setString(2, value);
					break;
				}
				rs = pstmt.executeQuery();
				if (rs.next()) {
					result = rs.getInt(1);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 기초정보과정 목록
		public List<BasicInfo> adminCourseList() {
			List<BasicInfo> result = new ArrayList<BasicInfo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql = "select * from course order by course_id";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// open_course_id,course_name,class_name,jungwon,
					// student_count,course_start_day,course_end_day,subject_count
					BasicInfo basicInfo = new BasicInfo();
					basicInfo.setId(rs.getString("course_id"));
					basicInfo.setName(rs.getString("course_name"));
					result.add(basicInfo);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 기초정보 강의실 목록
		public List<BasicInfo> adminClassList() {
			List<BasicInfo> result = new ArrayList<BasicInfo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql = "select * from class_ order by class_id";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// open_course_id,course_name,class_name,jungwon,
					// student_count,course_start_day,course_end_day,subject_count
					BasicInfo basicInfo = new BasicInfo();
					basicInfo.setId(rs.getString("class_id"));
					basicInfo.setName(rs.getString("class_name"));
					basicInfo.setJungwon(rs.getInt("jungwon"));
					result.add(basicInfo);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 개설과정 등록
		public int adminOpenCourseInsert(Subject arg0) {
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBConnection.connect();
				String sql = "insert into OPEN_COURSE(open_course_id,course_id,class_id,course_start_day,course_end_day) values((select concat('OCU',lpad(substr(NVL(MAX(open_course_id),0),-3,3)+1,3,000))from open_course),?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, arg0.getCourse_id());
				pstmt.setString(2, arg0.getClass_id());
				pstmt.setString(3, arg0.getCourse_start_day());
				pstmt.setString(4, arg0.getCourse_end_day());
				result = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return result;
		}

		// 개설과목 등록
		public int adminOpenSubInsert(Subject arg0) {
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			// System.out.println("OpenSub >> "+arg0);
			try {
				conn = DBConnection.connect();
				String sql = "insert into open_sub(open_sub_id, teacher_id, subject_id,book_id,open_course_id,sub_start_day,sub_end_day) values((select concat('OUB',lpad(substr(NVL(MAX(open_sub_id),0),-3,3)+1,3,000))from open_sub),?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, arg0.getTeacher_id());
				pstmt.setString(2, arg0.getSubject_id());
				pstmt.setString(3, arg0.getBook_id());
				pstmt.setString(4, arg0.getOpen_course_id());
				pstmt.setString(5, arg0.getSub_start_day());
				pstmt.setString(6, arg0.getSub_end_day());
				result = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return result;
		}

		// 기초정보과목 목록
		public List<BasicInfo> adminSubjectList(String open_course_id) {
			
			List<BasicInfo> result = new ArrayList<BasicInfo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql = "select subject.subject_id,subject_check as subject_id,subject_name from subject\r\n" + 
						"inner join  teach_sub on subject.subject_id=teach_sub.subject_id \r\n" + 
						"left outer join (select subject_id as subject_check from open_sub where open_course_id=?) temp on subject.subject_id=temp.subject_check\r\n" + 
						"where subject_check is null \r\n" + 
						"GROUP BY subject.subject_id,subject_name,subject_check order by subject.subject_id \r\n";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, open_course_id);
				
				rs = pstmt.executeQuery();
				while (rs.next()) {

					BasicInfo basicInfo = new BasicInfo();
					basicInfo.setId(rs.getString("subject_id"));
					basicInfo.setName(rs.getString("subject_name"));
					result.add(basicInfo);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 기초정보교재 목록
		public List<BasicInfo> adminBookList() {
			List<BasicInfo> result = new ArrayList<BasicInfo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql01 = "select * from book order by book_id ";
				pstmt = conn.prepareStatement(sql01);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					BasicInfo basicInfo = new BasicInfo();
					basicInfo.setId(rs.getString("book_id"));
					basicInfo.setName(rs.getString("book_name"));
					basicInfo.setPublisher(rs.getString("publisher"));
					basicInfo.setCover_img(rs.getString("cover_img"));

					result.add(basicInfo);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 기초정보강사 목록
		public List<BasicInfo> adminTeacherList(String subject_id) {
			List<BasicInfo> result = new ArrayList<BasicInfo>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.connect();
				String sql = "select teacher.teacher_id,teacher_name,teacher_ssn,teacher_phone,teacher_hiredate"
						+ " from teacher" + " inner join teach_sub on teacher.teacher_id=teach_sub.teacher_id"
						+ " where teach_sub.subject_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, subject_id);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					BasicInfo test003 = new BasicInfo();
					test003.setId(rs.getString("teacher_id"));
					test003.setName(rs.getString("teacher_name"));
					result.add(test003);
				}

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return result;
		}

		// 개설과정 삭제
		public int adminOpenCourseDelete(String open_course_id) {
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBConnection.connect();
				String sql = "delete from open_course where open_course_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, open_course_id);
				result = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		// 개설과목 삭제
		public int adminOpenSubDelete(String open_sub_id) {
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBConnection.connect();
				String sql = "delete from open_sub where open_sub_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, open_sub_id);
				result = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		// 개설과정 수정
		public int adminOpenCourseUpdate(Subject openCourse) {
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBConnection.connect();
				String sql = "update open_course set class_id= ? ,course_start_day=?, course_end_day=? where open_course_id=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, openCourse.getClass_id());
				pstmt.setString(2, openCourse.getCourse_start_day());
				pstmt.setString(3, openCourse.getCourse_end_day());
				pstmt.setString(4, openCourse.getOpen_course_id());
				result = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		// 개설과목 수정
		public int adminOpenSubUpdate(Subject openSub) {
			System.out.println("openSub >>> "+openSub);
			int result = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBConnection.connect();
				String sql = "update open_sub set book_id= ? ,teacher_id=?,sub_start_day=?, sub_end_day=? where open_sub_id=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, openSub.getBook_id());
				pstmt.setString(2, openSub.getTeacher_id());
				pstmt.setString(3, openSub.getSub_start_day());
				pstmt.setString(4, openSub.getSub_end_day());
				pstmt.setString(5, openSub.getOpen_sub_id());
				result = pstmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {

					if (pstmt != null) {
						pstmt.close();
					}
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		//---------------------------------------명욱이형부분------------------------------------
		//---------------------------------------김정은------------------------------------------
		public int basicInfoAddIntoCourse(Subject basicInfo) {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";

				sql = "insert into course (COURSE_ID,course_name) values ((select concat('CU',lpad(substr(NVL(MAX(course_id),0),-3,3)+1,3,000))from course),?)";
				
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getCourse_name());
				pstmt.executeUpdate();
				result = 1;

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		public int basicInfoAddIntoSubject(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "insert into subject (subject_id,subject_name) values ((select concat('SUB',lpad(substr(NVL(MAX(subject_id),0),-3,3)+1,3,000))from subject),?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getSubject_name());
				pstmt.executeUpdate();
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		
		

		public int basicInfoAddIntoBook(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "insert into book (book_id,BOOK_NAME,publisher, cover_img) values ((select concat('B',lpad(substr(NVL(MAX(book_id),0),-3,3)+1,3,000))from book),?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getBook_name());
				pstmt.setString(2, basicInfo.getPublisher());
				pstmt.setString(3, basicInfo.getCover_img());
				pstmt.executeUpdate();
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}


		public int basicInfoAddIntoClass_(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "insert into class_ (class_id,class_NAME,jungwon) values ((select concat('CA',lpad(substr(NVL(MAX(class_id),0),-3,3)+1,3,000))from class_),?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getClass_name());
				pstmt.setString(2, basicInfo.getJungwon());
				pstmt.executeUpdate();
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		
		
		


		

		public List<Subject> basicInfoListFromSubject(int key, String value) {
			List<Subject> result = new ArrayList<Subject>();

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBConnection.connect();
				String sql = "";
				switch (key) {

				case 1:
					sql = "select subject_id,subject_name from subject order by subject_id";
					break;
				case 2:
					sql = "select subject.subject_id,subject.subject_name " + " from subject"
							+ " inner join teach_sub on teach_sub.subject_id=subject.subject_id"
							+ " where teach_sub.teacher_id=upper(?)" + " order by subject_id";
					break;
				default:
					break;
				}
				pstmt = conn.prepareStatement(sql);

				switch (key) {
				case 1:

					break;
				case 2:
					pstmt.setString(1, value);
					break;
				default:
					break;
				}
				// System.out.println("sql>>>>>>" + sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Subject basicInfo = new Subject();
					basicInfo.setSubject_id(rs.getString("subject_id"));
					basicInfo.setSubject_name(rs.getString("subject_name"));
					result.add(basicInfo);
				}
				rs.close();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		public List<Subject> basicInfoListFromBook(int key, String value) {
			List<Subject> result = new ArrayList<Subject>();

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBConnection.connect();
				String sql = "";
				switch (key) {

				case 1:
					sql = "select book_id,book_name,PUBLISHER from book order by book_id";
					break;
				case 2:
					break;
				default:
					break;
				}
				pstmt = conn.prepareStatement(sql);

				switch (key) {
				case 1:

					break;
				case 2:
					pstmt.setString(1, value);
					break;
				default:
					break;
				}
				// System.out.println("sql>>>>>>" + sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Subject basicInfo = new Subject();
					basicInfo.setBook_id(rs.getString("book_id"));
					basicInfo.setBook_name(rs.getString("book_name"));
					basicInfo.setPublisher(rs.getString("PUBLISHER"));

					result.add(basicInfo);
				}
				rs.close();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		public List<Subject> basicInfoListFromCourse(int key, String value) {
			List<Subject> result = new ArrayList<Subject>();

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBConnection.connect();
				String sql = "";
				switch (key) {

				case 1:
					sql = "select course_id,course_name from course order by course_id";
					break;
				case 2:
					sql = "select course_id,course_name from course where course_id=upper(?) order by course_id";
					break;
				default:
					break;
				}
				pstmt = conn.prepareStatement(sql);

				switch (key) {
				case 1:

					break;
				case 2:
					pstmt.setString(1, value);
					break;
				default:
					break;
				}
				// System.out.println("sql>>>>>>" + sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Subject basicInfo = new Subject();
					basicInfo.setCourse_id(rs.getString("course_id"));
					basicInfo.setCourse_name(rs.getString("course_name"));

					result.add(basicInfo);
				}
				rs.close();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		public List<Subject> basicInfoListFromClass_(int key, String value) {
			List<Subject> result = new ArrayList<Subject>();

			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DBConnection.connect();
				String sql = "";
				switch (key) {

				case 1:
					sql = "select class_id,class_name,jungwon from class_ order by class_id";
					break;
				case 2:
					break;
				default:
					break;
				}
				pstmt = conn.prepareStatement(sql);

				switch (key) {
				case 1:

					break;
				case 2:
					pstmt.setString(1, value);
					break;
				default:
					break;
				}
				// System.out.println("sql>>>>>>" + sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Subject basicInfo = new Subject();
					basicInfo.setClass_id(rs.getString("class_id"));
					basicInfo.setClass_name(rs.getString("class_name"));
					basicInfo.setJungwon(rs.getString("jungwon"));

					result.add(basicInfo);
				}
				rs.close();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}

		

		public int basicInfoUpdateCourse(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "update course set course_name = ?  where course_id=?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getCourse_name());
				pstmt.setString(2, basicInfo.getCourse_id());
				
				pstmt.executeUpdate();
				
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		public int basicInfoUpdateSubject(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "update subject set subject_name=? where subject_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getSubject_name());
				pstmt.setString(2, basicInfo.getSubject_id());
				pstmt.executeUpdate();
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		public int basicInfoUpdateBook(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "update book set book_name=? , publisher=? , cover_img=? where book_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getBook_name());
				pstmt.setString(2, basicInfo.getPublisher());
				pstmt.setString(3, basicInfo.getCover_img());
				pstmt.setString(4, basicInfo.getBook_id());
				pstmt.executeUpdate();
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		
		public int basicInfoUpdateClass_(Subject basicInfo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				conn = DBConnection.connect();
				String sql = "";
				sql = "update class_ set class_name=? , jungwon=?  where class_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, basicInfo.getClass_name());
				pstmt.setString(2, basicInfo.getJungwon());
				pstmt.setString(3, basicInfo.getClass_id());
				pstmt.executeUpdate();
				result = 1;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					DBConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return result;
		}
		

			public String basicInfoDeleteCourse(Subject basicInfo) {

				//삭제 진행시 반환값이 '0'일수도 있기 때문에 기본값으로 '201'을 지정한다.
				String temp = "201";

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "DELETE FROM course WHERE course_id=?";
					//주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
							
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, basicInfo.getCourse_id());
					
					pstmt.executeUpdate();
					
					temp="200";
				} catch (ClassNotFoundException | SQLException e) {
					//삭제 진행시 입력과 다르게 구문 오류가 발생할 가능성이 낮다.
					//단, 데이터베이스 연결이 실패할 수는 있다.
					temp = "201";
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

				return temp;
			}
			
			public String basicInfoDeleteSubject(Subject basicInfo) {

				//삭제 진행시 반환값이 '0'일수도 있기 때문에 기본값으로 '201'을 지정한다.
				String temp = "201";

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "DELETE FROM subject WHERE subject_id=?";
					//주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
							
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, basicInfo.getSubject_id());
					
					pstmt.executeUpdate();
					
					
				} catch (ClassNotFoundException | SQLException e) {
					//삭제 진행시 입력과 다르게 구문 오류가 발생할 가능성이 낮다.
					//단, 데이터베이스 연결이 실패할 수는 있다.
					temp = "201";
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

				return temp;
			}
			
			public String basicInfoDeleteBook(Subject basicInfo) {

				//삭제 진행시 반환값이 '0'일수도 있기 때문에 기본값으로 '201'을 지정한다.
				String temp = "201";

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "DELETE FROM book WHERE book_id=?";
					//주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
							
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, basicInfo.getBook_id());
					
					pstmt.executeUpdate();
					
					
				} catch (ClassNotFoundException | SQLException e) {
					//삭제 진행시 입력과 다르게 구문 오류가 발생할 가능성이 낮다.
					//단, 데이터베이스 연결이 실패할 수는 있다.
					temp = "201";
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

				return temp;
			}
			
			public String basicInfoDeleteClass_(Subject basicInfo) {

				//삭제 진행시 반환값이 '0'일수도 있기 때문에 기본값으로 '201'을 지정한다.
				String temp = "201";

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "DELETE FROM class_ WHERE class_id=?";
					//주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
							
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, basicInfo.getClass_id());
					
					pstmt.executeUpdate();
					
					
				} catch (ClassNotFoundException | SQLException e) {
					//삭제 진행시 입력과 다르게 구문 오류가 발생할 가능성이 낮다.
					//단, 데이터베이스 연결이 실패할 수는 있다.
					temp = "201";
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

				return temp;
			}
			
			public String basicInfoPicture(Subject basicInfo) {
				String cover_img = "";

			
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DBConnection.connect();
					String sql = "SELECT cover_img FROM book WHERE book_id = ?";
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, basicInfo.getBook_id());
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
			//-----------------------김정은-------------------------------------------------
			//--------------------------이지혜----------------------------------------------
			// 수강생관리 수강생정보
			public List<BasicInfo> studentlist(String key, String value) {

				List<BasicInfo> result = new ArrayList<BasicInfo>();

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					// 주의) userGuestbookView 사용할 것.
					// 전체 + 검색 쿼리 준비
					String sql = "select student_id, student_name, student_ssn, student_phone, student_regdate, course_count from admin_student_view";

					// WHERE 조건절 추가 -> 검색 기능 -> 전체 출력인 경우 WHERE 조건절 생략
					switch (key) {
					case "all":
						break;
					case "student_id":
						sql += " WHERE INSTR(student_id, ?) > 0";
						break;
					case "student_name":
						sql += " WHERE INSTR(student_name, ?) > 0";
						break;
					case "student_regdate":
						sql += " WHERE INSTR(TO_CHAR(regDate , 'YYYY-MM-DD'), ?) > 0";
						break;
					}

					// 정렬 기능 추가
					sql += " ORDER BY student_id";

					// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					// 전체 + 검색 진행시 데이터 바인딩 과정 추가
					switch (key) {
					case "all":
						break;
					case "student_id":
					case "student_name":
					case "student_regdate":
						pstmt.setString(1, value);
						break;
					}

					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						String student_id = rs.getString("student_id");
						String student_name = rs.getString("student_name");
						String student_ssn = rs.getString("student_ssn");
						String student_phone = rs.getString("student_phone");
						String student_regdate = rs.getString("student_regdate");
						String course_count = rs.getString("course_count");

						BasicInfo g = new BasicInfo();
						g.setId(rs.getString("student_id"));
						g.setName(rs.getString("student_name"));
						g.setSsn(rs.getInt("student_ssn"));
						g.setPhone(rs.getString("student_phone"));
						g.setRegDate(rs.getString("student_regdate"));
						g.setCount(rs.getInt("course_count"));

						result.add(g);
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
				}

				return result;

			}

			// 수강생 전체 숫자
			public int studentlistCount() {
				int result = 0;

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "SELECT COUNT(*) AS count_ FROM student";
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						result = rs.getInt("count_");
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
				}

				return result;

			}

			// 수강생 관리 수강생 등록
			public String studentAdd(BasicInfo g) {
				String temp = "101"; // 등록 실패

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "INSERT into student (student_id, student_name, student_ssn, student_phone, student_regdate) values((select concat('STU',lpad(substr(NVL(MAX(student_id),0),-3,3)+1,3,000)) from student), ? , ? , ? , sysdate)";
					// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setInt(2, g.getSsn());
					pstmt.setString(3, g.getPhone());

					pstmt.executeUpdate();
					temp = "100"; // 등록 성공

				} catch (ClassNotFoundException | SQLException e) {
					temp = "101"; // 등록 실패
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

				return temp;
			}

			// 수강 과정 등록
			public String studentocuAdd(BasicInfo g) {
				String temp = "101"; // 등록 실패

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "INSERT into student (student_id, student_name, student_ssn, student_phone, student_regdate) values((select concat('STU',lpad(substr(NVL(MAX(student_id),0),-3,3)+1,3,000)) from student), ? , ? , ? , sysdate)";
					// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setInt(2, g.getSsn());
					pstmt.setString(3, g.getPhone());

					pstmt.executeUpdate();
					temp = "100"; // 등록 성공

				} catch (ClassNotFoundException | SQLException e) {
					temp = "101"; // 등록 실패
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

				return temp;
			}

			// 수강생 관리 수강생 수정
			public String studentUpdate(BasicInfo g) {
				String temp = "301"; // 수정 실패

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "update student set student_name = ?, student_ssn = ?, student_phone = ? where student_id = ?";
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, g.getName());
					pstmt.setInt(2, g.getSsn());
					pstmt.setString(3, g.getPhone());

					pstmt.setString(4, g.getId());

					pstmt.executeUpdate();
					temp = "300"; // 수정 성공

				} catch (ClassNotFoundException | SQLException e) {
					temp = "301"; // 수정 실패
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

				return temp;
			}

			// 수강생 관리 수강생 삭제
			public String studentDelete(BasicInfo g) {
				String temp = "201"; // 삭제 실패

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "Delete from student where student_id = ?";
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, g.getId());

					int result = pstmt.executeUpdate();

					if (result > 0) {
						temp = "200"; // 삭제 성공
					}

				} catch (ClassNotFoundException | SQLException e) {
					temp = "201"; // 삭제 실패
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

				return temp;
			}

			// 수강생관리 수강이력
			public List<Subject> studentoculist(String key, String value) {

				List<Subject> result = new ArrayList<Subject>();

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					// 주의) userGuestbookView 사용할 것.
					// 전체 + 검색 쿼리 준비
					String sql = "select student_id, open_course_id, course_id, course_name, class_id, class_name, jungwon,  course_start_day, course_end_day, finish_day from admin_studentoculist_view where student_id = ?";


					// 정렬 기능 추가
					sql += " ORDER BY open_course_id";

					// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, value); // open_course_id


					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						String student_id = rs.getString("student_id");
						String open_course_id = rs.getString("open_course_id");
						String course_id = rs.getString("course_id");
						String course_name = rs.getString("course_name");
						String class_id = rs.getString("class_id");
						String class_name = rs.getString("class_name");
						String jungwon = rs.getString("jungwon");
						String course_start_day = rs.getString("course_start_day");
						String course_end_day = rs.getString("course_end_day");
						String finish_day = rs.getString("finish_day");

						Subject s = new Subject();
						s.setStudent_id(rs.getString("student_id"));
						s.setOpen_course_id(rs.getString("open_course_id"));
						s.setCourse_id(rs.getString("course_id"));
						s.setCourse_name(rs.getString("course_name"));
						s.setClass_id(rs.getString("class_id"));
						s.setClass_name(rs.getString("class_name"));
						s.setJungwon(rs.getString("jungwon"));
						s.setCourse_start_day(rs.getString("course_start_day"));
						s.setCourse_end_day(rs.getString("course_end_day"));
						s.setFinish_day(rs.getString("finish_day"));

						result.add(s);
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
				}

				return result;

			}

			// 성적관리 과목별 리스트
			public List<Subject> admingradeoculist(String key, String value) {

				List<Subject> result = new ArrayList<Subject>();

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "SELECT OPEN_COURSE_ID, COURSE_NAME, CLASS_NAME, JUNGWON, count01_, COURSE_START_DAY, COURSE_END_DAY, COUNT02_ FROM open_course_view";

					// WHERE 조건절 추가 -> 검색 기능 -> 전체 출력인 경우 WHERE 조건절 생략
					switch (key) {
					case "all":
						break;
					case "open_course_id":
						sql += " WHERE INSTR(open_course_id, ?) > 0";
						break;
					case "course_name":
						sql += " WHERE INSTR(course_name, ?) > 0";
						break;
					case "class_name":
						sql += " WHERE INSTR(class_name, ?) > 0";
						break;
					}

					// 정렬 기능 추가
					sql += " ORDER BY open_course_id";

					// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					// 전체 + 검색 진행시 데이터 바인딩 과정 추가
					switch (key) {
					case "all":
						break;
					case "open_course_id":
					case "course_name":
					case "class_name":
						pstmt.setString(1, value);
						break;
					}

					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						String OPEN_COURSE_ID = rs.getString("OPEN_COURSE_ID");
						String COURSE_NAME = rs.getString("COURSE_NAME");
						String CLASS_NAME = rs.getString("CLASS_NAME");
						String JUNGWON = rs.getString("JUNGWON");
						String count01_ = rs.getString("count01_");
						String COURSE_START_DAY = rs.getString("COURSE_START_DAY");
						String COURSE_END_DAY = rs.getString("COURSE_END_DAY");
						String COUNT02_ = rs.getString("COUNT02_");

						Subject s = new Subject();
						s.setOpen_course_id(rs.getString("open_course_id"));
						s.setCourse_name(rs.getString("course_name"));
						s.setClass_name(rs.getString("class_name"));
						s.setJungwon(rs.getString("jungwon")); // 총정원
						s.setStudent_count(rs.getString("count01_")); // 학생수
						s.setCourse_start_day(rs.getString("course_start_day"));
						s.setCourse_end_day(rs.getString("course_end_day"));
						s.setSubject_count(rs.getString("count02_")); // 과목수

						result.add(s);
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
				}

				return result;

			}

			// 성적관리 개설과정 전체 숫자
			public int admingradeoculistCount() {
				int result = 0;

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "SELECT COUNT(*) AS count_ FROM open_course_view";
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						result = rs.getInt("count_");
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
				}

				return result;

			}

			// 성적관리 과목별 개설과목 리스트
			public List<Subject> admingradeoublist(String key, String value) {

				List<Subject> result = new ArrayList<Subject>();

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DBConnection.connect();

					String sql = "SELECT open_sub_id, subject_id, subject_name, sub_start_day, sub_end_day, book_id, book_name, teacher_id, teacher_name, gradecount, open_course_id FROM admin_gradeoublist_view where open_course_id = ?";

					// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, value); // open_course_id
					
					System.out.println(value);
					
					ResultSet rs = pstmt.executeQuery();

					while (rs.next()) {
						String open_sub_id = rs.getString("open_sub_id");
						String subject_id = rs.getString("subject_id");
						String subject_name = rs.getString("subject_name");
						String sub_start_day = rs.getString("sub_start_day");
						String sub_end_day = rs.getString("sub_end_day");
						String book_id = rs.getString("book_id");
						String book_name = rs.getString("book_name");
						String teacher_id = rs.getString("teacher_id");
						String teacher_name = rs.getString("teacher_name");
						String gradecount = rs.getString("gradecount");
						String open_course_id = rs.getString("open_course_id");

						Subject s = new Subject();
						s.setOpen_sub_id(rs.getString("open_sub_id"));
						s.setSubject_id(rs.getString("subject_id"));
						s.setSubject_name(rs.getString("subject_name"));
						s.setSub_start_day(rs.getString("sub_start_day"));
						s.setSub_end_day(rs.getString("sub_end_day"));
						s.setBook_id(rs.getString("book_id"));
						s.setBook_name(rs.getString("book_name"));
						s.setTeacher_id(rs.getString("teacher_id"));
						s.setTeacher_name(rs.getString("teacher_name"));
						s.setStudent_count(rs.getString("gradecount"));
						s.setOpen_course_id(rs.getString("open_course_id"));

						result.add(s);
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
				}

				return result;

			}
			
			
			// 수강생별 수강내역 과목확인
				public List<Subject> studentOubGradelist(String key, String value1, String value2) {

					List<Subject> result = new ArrayList<Subject>();

					Connection conn = null;
					PreparedStatement pstmt = null;
					try {
						conn = DBConnection.connect();

						String sql = "select OPEN_SUB_ID, SUBJECT_NAME, CHULSUK_TOTAL_GRADE, FILKI_TOTAL_GRADE, SILKI_TOTAL_GRADE, CHULSUK_GRADE, FILKI_GRADE, SILKI_GRADE, TEST_DATE, TEST_MUNJE, open_course_id, student_id from GRADE01_VIEW where open_course_id= ? and student_id = ?";

						// 정렬 기능 추가
						sql += " ORDER BY OPEN_SUB_ID";

						// 주의) 이 부분에 sql 구문 출력할 것. 액션 진행 상황 분석용.
						System.out.println(sql);

						pstmt = conn.prepareStatement(sql);

						pstmt.setString(1, value1);
						pstmt.setString(2, value2);
						
						System.out.println(value1);
						System.out.println(value2);
						
						ResultSet rs = pstmt.executeQuery();

						while (rs.next()) {
							String OPEN_SUB_ID = rs.getString("OPEN_SUB_ID");
							String SUBJECT_NAME = rs.getString("SUBJECT_NAME");
							String CHULSUK_TOTAL_GRADE = rs.getString("CHULSUK_TOTAL_GRADE");
							String FILKI_TOTAL_GRADE = rs.getString("FILKI_TOTAL_GRADE");
							String SILKI_TOTAL_GRADE = rs.getString("SILKI_TOTAL_GRADE");
							String CHULSUK_GRADE = rs.getString("CHULSUK_GRADE");
							String FILKI_GRADE = rs.getString("FILKI_GRADE");
							String SILKI_GRADE = rs.getString("SILKI_GRADE");
							String TEST_DATE = rs.getString("TEST_DATE");
							String TEST_MUNJE = rs.getString("TEST_MUNJE");
							String open_course_id = rs.getString("open_course_id");
							String student_id = rs.getString("student_id");

							Subject s = new Subject();
							s.setOpen_sub_id(rs.getString("OPEN_SUB_ID"));
							s.setSubject_name(rs.getString("SUBJECT_NAME"));
							s.setB_chulsuk(rs.getString("CHULSUK_TOTAL_GRADE"));
							s.setB_filki(rs.getString("FILKI_TOTAL_GRADE"));
							s.setB_silki(rs.getString("SILKI_TOTAL_GRADE"));
							s.setG_chulsuk(rs.getString("CHULSUK_GRADE"));
							s.setG_filki(rs.getString("FILKI_GRADE"));
							s.setG_silki(rs.getString("SILKI_GRADE"));
							s.setTest_date(rs.getString("TEST_DATE"));
							s.setTest_munje(rs.getString("TEST_MUNJE"));
							s.setOpen_course_id(rs.getString("open_course_id"));
							s.setStudent_id(rs.getString("student_id"));

							result.add(s);
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
					}

					return result;

				}
			
			

}
