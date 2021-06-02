package com.java.express.reserve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.java.express.config.DBConnection;
import com.java.express.reserve.dto.ReSearchReqDto;
import com.java.express.reserve.dto.ReserveReqDto;
import com.java.express.reserve.dto.ReservesReqDto;

public class ReserveDao {
	
	// 예매정보 등록
	public int ReserveRegister(ReserveReqDto dto) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO book (userId, busId, reserveState, buying, seat, price, createDate) VALUES (?, ?, ?, ?, ?, ?, now())";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setInt(2, dto.getBusId());
			pstmt.setString(3, dto.getReserveState());
			pstmt.setInt(4, dto.getBuying());
			pstmt.setString(5, dto.getSeat());
			pstmt.setInt(6, dto.getPrice());

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
	
	// 유저 예매내역 보여주기
	public ArrayList<ReSearchReqDto> userReserveSearch(int userId) {		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ReSearchReqDto> dtos = new ArrayList<ReSearchReqDto>();
		String sql = "SELECT b.id, u.departDate, u.departTime, u.company, u.depart, u.arrive, u.rating, b.buying, b.price, b.reserveState, b.seat "
				+ "FROM book b inner join bustime u on b.busId = u.id WHERE userId = ? ORDER BY id";		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReSearchReqDto dto = new ReSearchReqDto();
				dto.setId(rs.getInt("id"));
				dto.setDepartDate(rs.getDate("departDate"));
				dto.setDepartTime(rs.getTime("departTime"));
				dto.setCompany(rs.getString("company"));
				dto.setDepart(rs.getString("depart"));
				dto.setArrive(rs.getString("arrive"));
				dto.setRating(rs.getString("rating"));
				dto.setBuying(rs.getInt("buying"));
				dto.setPrice(rs.getInt("price"));
				dto.setReserveState(rs.getString("reserveState"));
				dto.setSeat(rs.getString("seat"));
				dtos.add(dto);
			}
			return dtos;
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
	
	// Admin 예매내역 조회
	public ArrayList<ReservesReqDto> userReservesSearch(String userId, String company, String departDate, String departTime, String depart, String arrive, String rating, String seat, String price, String reserveState) {		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ReservesReqDto> dtos = new ArrayList<ReservesReqDto>();
		String sql = "SELECT b.id, s.userId, b.reserveState, u.departDate, u.departTime, u.depart, u.arrive, u.company, u.rating, b.buying, b.price, b.seat "
				+ "FROM book b inner join bustime u on b.busId = u.id inner join user s on b.userId = s.id "
				+ "WHERE s.userId LIKE ? AND u.company LIKE ? AND u.departDate LIKE ? AND u.departTime LIKE ? "
				+ "AND u.depart LIKE ? AND u.arrive LIKE ? AND u.rating LIKE ? AND b.seat LIKE ? AND b.price LIKE ? AND b.reserveState LIKE ?";	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+userId+"%");
			pstmt.setString(2, "%"+company+"%");
			pstmt.setString(3, "%"+departDate+"%");
			pstmt.setString(4, "%"+departTime+"%");
			pstmt.setString(5, "%"+depart+"%");
			pstmt.setString(6, "%"+arrive+"%");
			pstmt.setString(7, "%"+rating+"%");
			pstmt.setString(8, "%"+seat+"%");
			pstmt.setString(9, "%"+price+"%");
			pstmt.setString(10, "%"+reserveState+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReservesReqDto dto = new ReservesReqDto();
				dto.setBookId(rs.getInt("b.id"));
				dto.setUserId(rs.getString("s.userId"));
				dto.setReserveState(rs.getString("b.reserveState"));
				Timestamp sum = Timestamp.valueOf(rs.getDate("u.departDate") + " " + rs.getTime("u.departTime"));
				dto.setDepartDate(sum);
				dto.setDepart(rs.getString("u.depart"));
				dto.setArrive(rs.getString("u.arrive"));
				dto.setCompany(rs.getString("u.company"));
				dto.setRating(rs.getString("u.rating"));
				dto.setBuying(rs.getInt("b.buying"));
				dto.setPrice(rs.getInt("b.price"));
				dto.setSeat(rs.getString("b.seat"));
				dtos.add(dto);
			}
			return dtos;
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
	
	// 예매된 좌석 보여주기
	public String selectedSeat(int busId) {		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT seat FROM book WHERE busId = ? AND reserveState = ?";		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, busId);
			pstmt.setString(2, "예매완료");
			rs = pstmt.executeQuery();
			String seat = "";
			if (rs.next()) {
				seat = rs.getString("seat");
			}		
			while (rs.next()) {
				seat = seat + "," + rs.getString("seat");
			}
			return seat;
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
	
	// 예매정보 등록
	public int reserveDelete(String reserveId) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE book SET reserveState = ? WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "예매취소");
			pstmt.setString(2, reserveId);

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
