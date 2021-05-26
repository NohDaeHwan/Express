package com.java.express.seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java.express.config.DBConnection;
import com.java.express.seat.dto.Seat;

public class SeatDao {
	
	// 우등 또는 일반 좌석의 열과 행을 가져옴
	public Seat selectSeat(int busRating) {
		String sql ="SELECT * FROM seat WHERE id = ?";
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, busRating);
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				Seat seat = new Seat();
				seat.setRow(rs.getInt("row"));
				seat.setCol(rs.getInt("col"));
				
				return seat;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return null;
	}

}
