package com.java.express.bus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.java.express.bus.dto.RegisterReqDto;
import com.java.express.bus.dto.SearchReqDto;
import com.java.express.config.DBConnection;

public class BusDao {
	
	// Admin 버스 정보 등록
	public int busRegister(RegisterReqDto dto) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO bustime (company, departDate, departTime, depart, arrive, rating, seatId, seat, price, createDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now())";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCompany());
			pstmt.setDate(2, dto.getDepartDate());
			pstmt.setTime(3, dto.getDepartTime());
			pstmt.setString(4, dto.getDepart());
			pstmt.setString(5, dto.getArrive());
			pstmt.setString(6, dto.getRating());			
			pstmt.setInt(7, dto.getSeatId());
			pstmt.setInt(8, dto.getSeat());
			pstmt.setInt(9, dto.getPrice());

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
	
	// Admin 버스 정보 수정
	public int busUpdate(RegisterReqDto dto, int id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE bustime SET company = ?, departDate = ?, departTime = ?, depart = ?, arrive = ?, rating = ?, seatId = ?, seat = ?, price = ? WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCompany());
			pstmt.setDate(2, dto.getDepartDate());
			pstmt.setTime(3, dto.getDepartTime());
			pstmt.setString(4, dto.getDepart());
			pstmt.setString(5, dto.getArrive());
			pstmt.setString(6, dto.getRating());
			pstmt.setInt(7, dto.getSeatId());
			pstmt.setInt(8, dto.getSeat());
			pstmt.setInt(9, dto.getPrice());
			pstmt.setInt(10, id);

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
	
	// Admin 버스 정보 삭제
	public int busDelete(int id) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM bustime WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

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
	
	// Admin 버스 정보 조회
	public ArrayList<Bus> busSearch(String company, String departDate, String departTime, String depart, String arrive, String rating, String seat, String price) {		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Bus> buses = new ArrayList<Bus>();
		String sql = "SELECT * FROM bustime WHERE company LIKE ? AND departDate LIKE ? AND departTime LIKE ? AND depart LIKE ? AND "
				+ "arrive LIKE ? AND rating LIKE ? AND seat LIKE ? AND price LIKE ?";		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+company+"%");
			pstmt.setString(2, "%"+departDate+"%");
			pstmt.setString(3, "%"+departTime+"%");
			pstmt.setString(4, "%"+depart+"%");
			pstmt.setString(5, "%"+arrive+"%");
			pstmt.setString(6, "%"+rating+"%");
			pstmt.setString(7, "%"+seat+"%");
			pstmt.setString(8, "%"+price+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bus bus = new Bus();
				bus.setId(rs.getInt("id"));
				bus.setCompany(rs.getString("company"));
				bus.setDepartDate(rs.getDate("departDate"));
				bus.setDepartTime(rs.getTime("departTime"));
				bus.setDepart(rs.getString("depart"));
				bus.setArrive(rs.getString("arrive"));
				bus.setRating(rs.getString("rating"));
				bus.setSeat(rs.getInt("seat"));
				bus.setPrice(rs.getInt("price"));
				bus.setCreateDate(rs.getTimestamp("createDate"));
				buses.add(bus);
			}
			return buses;
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
	
	// 유저에게 버스시간 보여주기
	public ArrayList<Bus> userBusSearch(SearchReqDto dto) {		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Bus> buses = new ArrayList<Bus>();
		String sql = "SELECT * FROM bustime WHERE depart LIKE ? AND arrive LIKE ? AND departDate LIKE ? AND rating LIKE ?";		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+dto.getDepart()+"%");
			pstmt.setString(2, "%"+dto.getArrive()+"%");
			pstmt.setString(3, "%"+dto.getDepartDate()+"%");
			pstmt.setString(4, "%"+dto.getRating()+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bus bus = new Bus();
				bus.setId(rs.getInt("id"));
				bus.setCompany(rs.getString("company"));
				bus.setDepartDate(rs.getDate("departDate"));
				bus.setDepartTime(rs.getTime("departTime"));
				bus.setDepart(rs.getString("depart"));
				bus.setArrive(rs.getString("arrive"));
				bus.setRating(rs.getString("rating"));
				bus.setSeat(rs.getInt("seat"));
				bus.setPrice(rs.getInt("price"));
				bus.setCreateDate(rs.getTimestamp("createDate"));
				buses.add(bus);
			}
			return buses;
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
	
	// 예매시 남은 좌석 감소
	public int busSeatUpdate(int id, int seat) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE bustime SET seat = ? WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seat);
			pstmt.setInt(2, id);

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
	
	// 예매 취소시 남은 좌석수 증가
	public int busSeatUpdate(String date, String time, String depart, String arrive, String company, String rating, int buying) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE bustime SET seat = seat + ? WHERE departDate = ? AND departTime = ? AND depart = ? AND arrive = ? AND company = ? AND rating = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, buying);
			pstmt.setString(2, date);
			pstmt.setString(3, time);
			pstmt.setString(4, depart);
			pstmt.setString(5, arrive);
			pstmt.setString(6, company);
			pstmt.setString(7, rating);

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
