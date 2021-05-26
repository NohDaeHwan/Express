package com.java.express.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.express.config.DBConnection;
import com.java.express.user.dto.JoinReqDto;
import com.java.express.user.dto.LoginReqDto;

public class UserDao {
	
	// 회원가입하기, Admin 유저 정보 등록
	public int userJoin(JoinReqDto dto) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO user (userId, password, birthDate, phone, email, userRole, createDate) VALUES (?, ?, ?, ?, ?, 'USER', now())";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getBirthDate());
			pstmt.setString(4, dto.getPhone());
			pstmt.setString(5, dto.getEmail());

			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	// 회원가입 아이디 중복 체크
	public int userIdCheck(String userId) {
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM user WHERE userId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	// 로그인하기
	public User userLogin(LoginReqDto dto) {
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM user WHERE userId = ? AND password = ?";		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getPassword());
			rs = pstmt.executeQuery();
			System.out.println(dto.getUserId());
			System.out.println(dto.getPassword());
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserId(rs.getString("userId"));
				user.setPassword(rs.getString("password"));
				user.setBirthDate(rs.getString("birthDate"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setUserRole(rs.getString("userRole"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				user.setDeleteDate(rs.getTimestamp("deleteDate"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// Admin 유저 정보 조회
	public ArrayList<User> userSearch(String userId, String password, String birthDate, String phone, String email) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> useres = new ArrayList<User>();
		String sql = "SELECT * FROM user WHERE userId LIKE ? AND password LIKE ? AND birthDate LIKE ? AND phone LIKE ? AND email LIKE ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+userId+"%");
			pstmt.setString(2, "%"+password+"%");
			pstmt.setString(3, "%"+birthDate+"%");
			pstmt.setString(4, "%"+phone+"%");
			pstmt.setString(5, "%"+email+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserId(rs.getString("userId"));
				user.setPassword(rs.getString("password"));
				user.setBirthDate(rs.getString("birthDate"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setBookCount(rs.getInt("bookCount"));
				user.setCreateDate(rs.getTimestamp("createDate"));
				useres.add(user);
			}
			return useres;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	// 유저 정보 수정, Admin 유저 정보 수정
	public int userUpdate(JoinReqDto dto) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE user SET password = ?, birthDate = ?, phone = ?, email = ? WHERE userId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPassword());
			pstmt.setString(2, dto.getBirthDate());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getUserId());

			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	// Admin 유저 정보 삭제
	public int userDelete(String userId) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM user WHERE userId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	// 유저가 예매시 예매수 증가
	public int userBookCountUpdate(String userId, int count) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE user SET bookCount = bookCount + ? WHERE userId = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, userId);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
}
