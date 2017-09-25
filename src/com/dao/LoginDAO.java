package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.LoginInfo;

public class LoginDAO {
	
	//id, pw가 일치하는 회원정보 하나를 출력. m_id가 수강생이나 강사의 pk가 된다.
	public LoginInfo login(String id, String pw) {
		LoginInfo result =  new LoginInfo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.connect();

			String sql = "SELECT id_, pw, grade, m_id FROM login WHERE id_ =? AND pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.setId_(rs.getString("id_"));
				result.setPw(rs.getString("pw"));
				result.setGrade(rs.getInt("grade"));
				result.setM_id(rs.getString("m_id"));
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
	
	
	//강사 pw가 일치 한다면 암호를 변경한다.
	public LoginInfo teachpwmodify(String pw, String teacher_id) {
		LoginInfo result =  new LoginInfo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.connect();

			String sql = "UPDATE login SET pw=? WHERE m_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, teacher_id);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
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

}
