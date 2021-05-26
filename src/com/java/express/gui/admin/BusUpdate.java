package com.java.express.gui.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.java.express.bus.BusDao;
import com.java.express.bus.dto.RegisterReqDto;
import com.java.express.gui.user.CustomCalendar;

public class BusUpdate extends CustomUIMain {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JLabel lbBusCalendar;
	private JTextField txtCompany, txtDepartDate, txtSeat, txtPrice;
	private JComboBox<String> cbDepartTime, cbDepart, cbArrive, cbRating; 
	private JButton btnBusUpdate;
	@SuppressWarnings("unused")
	private ArrayList<String> data;
	
	public BusUpdate(ArrayList<String> data) {
		init();
		this.data = data;
		lbBusCalendar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new CustomCalendar(txtDepartDate);
			}	
		});
		
		btnBusUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int select = JOptionPane.showConfirmDialog(frame, "버스 정보를 수정하시겠습니까?", "수정확인", JOptionPane.YES_NO_OPTION);
				if (select == JOptionPane.YES_OPTION) {
					int seatId = 1;
					if (cbRating.getSelectedItem().toString().equals("우등")) {
						seatId = 1;
					} else {
						seatId = 2;
					}
					RegisterReqDto dto = new RegisterReqDto(txtCompany.getText(), Date.valueOf(txtDepartDate.getText()), 
							Time.valueOf(cbDepartTime.getSelectedItem().toString()), cbDepart.getSelectedItem().toString(), cbArrive.getSelectedItem().toString(), 
							cbRating.getSelectedItem().toString(), seatId, Integer.parseInt(txtSeat.getText()), Integer.parseInt(txtPrice.getText()));
					BusDao dao = new BusDao();
					int result = dao.busUpdate(dto, Integer.parseInt(data.get(0)));
					if (result == 1) {
						JOptionPane.showMessageDialog(frame, "버스 정보를 수정했습니다", "수정성공", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "버스 정보 수정에 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			}	
		}); 
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				txtCompany.setText(data.get(1));
				txtDepartDate.setText(data.get(2));
				cbDepartTime.setSelectedItem(data.get(3));
				cbDepart.setSelectedItem(data.get(4));
				cbArrive.setSelectedItem(data.get(5));
				cbRating.setSelectedItem(data.get(6));
				txtSeat.setText(data.get(7));
				txtPrice.setText(data.get(8));
			}			
		});
		
		frame.setSize(600, 1000);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void init() {
		backgroundPanel = new JPanel();

		frame.setContentPane(backgroundPanel);
		frame.setTitle("고속버스 예약 프로그램 ver1.0");

		CustomUIMain custom = new CustomUIMain(backgroundPanel);
		custom.setModalPanel();
		
		
		String[] depart = { "", "동서울", "서울경부", "센트럴시티(서울)", "의정부", "인천", "고양백석", "광주(유·스퀘어)", "부산", "부산사상",
				"대전복합", "전주", "유성", "천안", "동대구", "강릉", "안양", "속초"};
		String[] rating = { "", "우등", "일반" };
		String[] time = { "", "09:00:00", "11:00:00", "13:00:00", "15:00:00", "17:00:00", "19:00:00", "21:00:00", "23:00:00" };
		custom.setLbImg("lbLogo", backgroundPanel, 1, 250, 150);
		custom.setLb("lbCompany", "회사", backgroundPanel, 150, 305, 100, 20, 22);
		custom.setLb("lbDepartDate", "출발일", backgroundPanel, 130, 355, 100, 20, 22);
		custom.setLb("lbDepartTime", "출발시각", backgroundPanel, 110, 405, 100, 20, 22);
		custom.setLb("lbDepart", "출발지", backgroundPanel, 130, 455, 100, 20, 22);
		custom.setLb("lbArrive", "도착지", backgroundPanel, 130, 505, 100, 20, 22);
		custom.setLb("lbRating", "등급", backgroundPanel, 150, 555, 100, 20, 22);
		custom.setLb("lbSeat", "잔여좌석", backgroundPanel, 110, 605, 100, 20, 22);
		custom.setLb("lbPrice", "가격", backgroundPanel, 150, 655, 100, 20, 22);
		
		btnBusUpdate = custom.setBtnBlack("btnBusUpdate", "수정", backgroundPanel, 125, 750, 350, 45);
		
		txtCompany = custom.setTextField("txtCompany", true, backgroundPanel, 240, 300, 240, 30);
		txtDepartDate = custom.setTextField("txtDepratDate", true, backgroundPanel, 240, 350, 210, 30);
		cbDepartTime = custom.setCombo("txtDepartTime", time, backgroundPanel, 240, 400, 240, 30);
		cbDepart = custom.setCombo("cbDepart", depart, backgroundPanel, 240, 450, 240, 30);
		cbArrive = custom.setCombo("cbArrive", depart, backgroundPanel, 240, 500, 240, 30);
		cbRating = custom.setCombo("cbRating", rating, backgroundPanel, 240, 550, 240, 30);
		txtSeat = custom.setTextField("txtSeat", true, backgroundPanel, 240, 600, 240, 30);   
		txtPrice = custom.setTextField("txtPrice", true, backgroundPanel, 240, 650, 240, 30);
		
		lbBusCalendar = custom.setlbImg("lbBusCalendar", backgroundPanel, 450, 343);
	}
}
