package com.java.express.gui.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.java.express.bus.Bus;
import com.java.express.bus.BusDao;
import com.java.express.gui.user.CustomCalendar;
import com.java.express.reserve.ReserveDao;
import com.java.express.reserve.dto.ReservesReqDto;
import com.java.express.user.User;
import com.java.express.user.UserDao;

@SuppressWarnings("serial")
public class Main extends CustomUIMain {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel, homePanel, userPanel, bookPanel;
	private JLabel lbBusCalendar, lbBookCalendar;
	private JTextField txtCompany, txtDepartDate, txtSeat, txtPrice;
	private JComboBox<String> cbDepartTime, cbDepart, cbArrive, cbRating; 
	private JButton btnBusAdd, btnBusDelete, btnBusUpdate, btnBusSearch;
	private JButton btnLogout, btnHome, btnUser, btnBook;
	
	private JTextField txtUserId, txtPassword, txtBirthDate, txtPhone, txtEmail;
	private JButton btnUserSearch, btnUserDelete, btnUserUpdate, btnUserAdd;
	private JTable busTable, userTable, bookTable;
	
	private JTextField txtBUserId, txtBCompany, txtBDepartDate, txtBSeat, txtBPrice;
	private JComboBox<String> cbBDepartTime, cbBDepart, cbBArrive, cbBRating; 
	private JButton btnBookSearch, btnBookDelete;

	public Main() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePanel.setVisible(true);
				userPanel.setVisible(false);
				bookPanel.setVisible(false);
			}
		});
		
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePanel.setVisible(false);
				userPanel.setVisible(true);
				bookPanel.setVisible(false);
			}
		});
		
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePanel.setVisible(false);
				userPanel.setVisible(false);
				bookPanel.setVisible(true);
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new com.java.express.gui.user.Login();
				frame.dispose();
			}
		});
		
		// 버스 캘린더 열기
		lbBusCalendar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new CustomCalendar(txtDepartDate);
			}		
		});
		
		// 예매 캘린더 열기
		lbBookCalendar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new CustomCalendar(txtBDepartDate);
			}		
		});
		
		// 버스 등록하기
		btnBusAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BusRegister();
			}
		});
		
		// 버스 조회하기
		btnBusSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Bus> buses = new ArrayList<Bus>();
				BusDao dao = new BusDao();
				buses = dao.busSearch(txtCompany.getText(), txtDepartDate.getText(), cbDepartTime.getSelectedItem().toString(),
						cbDepart.getSelectedItem().toString(), cbArrive.getSelectedItem().toString(), cbRating.getSelectedItem().toString(),
						txtSeat.getText(), txtPrice.getText());
				DefaultTableModel model = (DefaultTableModel) busTable.getModel();
				if (buses.size() != 0) {
					model.getDataVector().removeAllElements();
					revalidate();
					for (int i = 0; i < buses.size(); i++) {
						model.addRow(new Object[] {buses.get(i).getId(), buses.get(i).getCompany(), buses.get(i).getDepartDate(), 
								buses.get(i).getDepartTime(), buses.get(i).getDepart(), buses.get(i).getArrive(), buses.get(i).getRating(), 
								buses.get(i).getSeat(), buses.get(i).getPrice(), buses.get(i).getCreateDate()});
					}
				} else {
					JOptionPane.showMessageDialog(frame, "일치하는 정보가 없습니다", "조회실패", JOptionPane.INFORMATION_MESSAGE);
				}						
			}
		});
		
		// 버스 수정하기
		btnBusUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (busTable.getSelectedRow() < busTable.getRowCount() && busTable.getSelectedRow() > -1) {
					int row = busTable.getSelectedRow();
					if (busTable.getValueAt(row, 7).toString().equals("45") || busTable.getValueAt(row, 7).toString().equals("28")) {
						ArrayList<String> data = new ArrayList<String>();			
						int column = busTable.getColumnCount();
						for (int i = 0; i < column; i++) {
							data.add(busTable.getValueAt(row, i).toString());
						}
						new BusUpdate(data);
					} else {
						int select = JOptionPane.showConfirmDialog(frame, "이미 예매된 버스입니다~!! 삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
						if (select == JOptionPane.YES_OPTION) {
							ArrayList<String> data = new ArrayList<String>();			
							int column = busTable.getColumnCount();
							for (int i = 0; i < column; i++) {
								data.add(busTable.getValueAt(row, i).toString());
							}
							new BusUpdate(data);
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		// 버스 삭제하기
		btnBusDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
					if (busTable.getSelectedRow() < busTable.getRowCount() && busTable.getSelectedRow() > -1) {
						int row = busTable.getSelectedRow();
						if (busTable.getValueAt(row, 7).toString().equals("45") || busTable.getValueAt(row, 7).toString().equals("28")) {
							int select = JOptionPane.showConfirmDialog(frame, "버스 정보를 삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
							if (select == JOptionPane.YES_OPTION) {
								DefaultTableModel model = (DefaultTableModel) busTable.getModel();
								BusDao busDao = new BusDao();
								
								int id = Integer.parseInt(busTable.getValueAt(row, 0).toString());
								int result = busDao.busDelete(id);
								if (result == 1) {
									JOptionPane.showMessageDialog(frame, "버스 정보를 삭제했습니다", "삭제성공", JOptionPane.INFORMATION_MESSAGE);
									model.removeRow(busTable.getSelectedRow());
								} else {
									JOptionPane.showMessageDialog(frame, "버스 정보 삭제를 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							int select = JOptionPane.showConfirmDialog(frame, "이미 예매된 버스입니다~!! 삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
							if (select == JOptionPane.YES_OPTION) {
								DefaultTableModel model = (DefaultTableModel) busTable.getModel();
								BusDao busDao = new BusDao();
								
								int id = Integer.parseInt(busTable.getValueAt(row, 0).toString());
								int result = busDao.busDelete(id);
								if (result == 1) {
									JOptionPane.showMessageDialog(frame, "버스 정보를 삭제했습니다", "삭제성공", JOptionPane.INFORMATION_MESSAGE);
									model.removeRow(busTable.getSelectedRow());
								} else {
									JOptionPane.showMessageDialog(frame, "버스 정보 삭제를 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
								}
							}
						}					
					} else {
						JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		// 유저 등록하기
		btnUserAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserRegister();
			}
		});
		
		// 유저 조회하기
		btnUserSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<User> useres = new ArrayList<User>();
				UserDao dao = new UserDao();
				useres = dao.userSearch(txtUserId.getText(), txtPassword.getText(), txtBirthDate.getText(), txtPhone.getText(), txtEmail.getText());
				DefaultTableModel model = (DefaultTableModel) userTable.getModel();
				if (useres.size() != 0) {
					model.getDataVector().removeAllElements();
					revalidate();
					for (int i = 0; i < useres.size(); i++) {
						model.addRow(new Object[] {useres.get(i).getId(), useres.get(i).getUserId(), useres.get(i).getPassword(), 
								useres.get(i).getBirthDate(), useres.get(i).getPhone(), useres.get(i).getEmail(), useres.get(i).getBookCount(), 
								useres.get(i).getCreateDate()});
					}
				} else {
					JOptionPane.showMessageDialog(frame, "일치하는 유저가 없습니다", "조회실패", JOptionPane.INFORMATION_MESSAGE);
				}				
			}	
		});
		
		// 유저 수정하기
		btnUserUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (userTable.getSelectedRow() < userTable.getRowCount() && userTable.getSelectedRow() > -1) {
					ArrayList<String> data = new ArrayList<String>();
					int row = userTable.getSelectedRow();
					int column = userTable.getColumnCount();
					for (int i = 0; i < column; i++) {
						data.add(userTable.getValueAt(row, i).toString());
					}
					new UserUpdate(data);
				} else {
					JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		
		// 유저 삭제하기
		btnUserDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (userTable.getSelectedRow() < userTable.getRowCount() && userTable.getSelectedRow() > -1) {
					int select = JOptionPane.showConfirmDialog(frame, "유저 정보를 삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
					if (select == JOptionPane.YES_OPTION) {
						DefaultTableModel model = (DefaultTableModel) userTable.getModel();
						UserDao userDao = new UserDao();
						int row = userTable.getSelectedRow();
						String userId = userTable.getValueAt(row, 1).toString();
						int result = userDao.userDelete(userId);
						if (result == 1) {
							JOptionPane.showMessageDialog(frame, "유저 정보를 삭제했습니다", "삭제성공", JOptionPane.INFORMATION_MESSAGE);
							model.removeRow(userTable.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(frame, "유저 정보 삭제를 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// 버스 예매 기록 보기
		btnBookSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<ReservesReqDto> dtos = new ArrayList<ReservesReqDto>();
				ReserveDao dao = new ReserveDao();
				dtos = dao.userReservesSearch(txtBUserId.getText(), txtBCompany.getText(), txtBDepartDate.getText(), cbBDepartTime.getSelectedItem().toString(),
						cbBDepart.getSelectedItem().toString(), cbBArrive.getSelectedItem().toString(), cbBRating.getSelectedItem().toString(),
						txtBSeat.getText(), txtBPrice.getText());
				DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
				if (dtos.size() != 0) {
					model.getDataVector().removeAllElements();
					revalidate();
					for (int i = 0; i < dtos.size(); i++) {
						model.addRow(new Object[] {dtos.get(i).getBookId(), dtos.get(i).getUserId(), dtos.get(i).getReserveState(), 
								dtos.get(i).getDepartDate(), dtos.get(i).getDepart(), dtos.get(i).getArrive(), dtos.get(i).getCompany(), 
								dtos.get(i).getRating(), dtos.get(i).getBuying(), dtos.get(i).getPrice(), dtos.get(i).getSeat()});
					}
				} else {
					JOptionPane.showMessageDialog(frame, "일치하는 예매정보가 없습니다", "조회실패", JOptionPane.INFORMATION_MESSAGE);
				}				
			}	
		});
		
		// 버스 예매 기록 삭제하기
		btnBookDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (bookTable.getSelectedRow() < bookTable.getRowCount() && bookTable.getSelectedRow() > -1) {
					int select = JOptionPane.showConfirmDialog(frame, "예매 정보를 삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
					if (select == JOptionPane.YES_OPTION) {
						DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
						ReserveDao reserveDao = new ReserveDao();
						int row = bookTable.getSelectedRow();
						String reserveId = bookTable.getValueAt(row, 0).toString();
						int result = reserveDao.reserveDelete(reserveId);
						if (result == 1) {
							UserDao dao = new UserDao();
							dao.userBookCountUpdate(bookTable.getValueAt(row, 1).toString(), -1);
							BusDao busDao = new BusDao();
							String[] date = bookTable.getValueAt(row, 3).toString().split(" ");
							busDao.busSeatUpdate(date[0], date[1], bookTable.getValueAt(row, 4).toString(), bookTable.getValueAt(row, 5).toString(), 
									bookTable.getValueAt(row, 6).toString(), bookTable.getValueAt(row, 7).toString(), Integer.parseInt(bookTable.getValueAt(row, 8).toString()));
							JOptionPane.showMessageDialog(frame, "예매 정보를 삭제했습니다", "삭제성공", JOptionPane.INFORMATION_MESSAGE);
							model.removeRow(bookTable.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(frame, "예매 정보 삭제에 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});		

		frame.setSize(1920, 1080);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		backgroundPanel = new JPanel();
		homePanel = new JPanel();
		userPanel = new JPanel();
		bookPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("고속버스 예약 프로그램 ver1.0");

		CustomUIMain custom = new CustomUIMain(backgroundPanel, homePanel, userPanel, bookPanel);
		custom.setMainPanel();
		
		// 상단바 UI
		btnHome = custom.setBtnMenu("btnHome", "버스정보", 465, 12, 180, 45, "BOLD");
		btnUser = custom.setBtnMenu("btnUser", "회원정보", 665, 12, 180, 45, "BOLD");
		btnBook = custom.setBtnMenu("btnBook", "예매정보", 865, 12, 180, 45, "BOLD");
		btnLogout = custom.setBtnMenu("btnLogout", "로그아웃", 1065, 12, 180, 45, "BOLD");
		
		
		// 버스정보 UI
		String[] depart = { "", "동서울", "서울경부", "센트럴시티(서울)", "의정부", "인천", "고양백석", "광주(유·스퀘어)", "부산", "부산사상",
				"대전복합", "전주", "유성", "천안", "동대구", "강릉", "안양", "속초"};
		String[] time = { "", "09:00:00", "11:00:00", "13:00:00", "15:00:00", "17:00:00", "19:00:00", "21:00:00", "23:00:00" };
		String[] rating = { "", "우등", "일반" };
		String[] busHeader = { "버스번호", "회사", "출발일", "출발시간", "출발지", "도착지", "등급", "잔여좌석", "가격", "생성날짜" };
		
		custom.setLbImg("lbLogo", homePanel, 1, 425, 100);
		custom.setLb("lbCompany", "회사", homePanel, 120, 245, 100, 20, 22);
		custom.setLb("lbDepartDate", "출발일", homePanel, 100, 305, 100, 20, 22);
		custom.setLb("lbDepartTime", "출발시각", homePanel, 80, 365, 100, 20, 22);
		custom.setLb("lbDepart", "출발지", homePanel, 100, 425, 100, 20, 22);
		custom.setLb("lbArrive", "도착지", homePanel, 465, 245, 100, 20, 22);
		custom.setLb("lbRating", "등급", homePanel, 485, 305, 100, 20, 22);
		custom.setLb("lbSeat", "잔여좌석", homePanel, 445, 365, 100, 20, 22);
		custom.setLb("lbPrice", "가격", homePanel, 485, 425, 100, 20, 22);
		
		btnBusSearch = custom.setBtnBlack("btnBusSearch", "조회", homePanel, 300, 600, 350, 45);
		btnBusAdd = custom.setBtnWhite("btnBusAdd", "등록", homePanel, 900, 700, 200, 45, "PLAIN");
		btnBusUpdate = custom.setBtnWhite("btnBusUpdate", "수정", homePanel, 1140, 700, 200, 45, "PLAIN");
		btnBusDelete = custom.setBtnWhite("btnBusDelete", "삭제", homePanel, 1380, 700, 200, 45, "PLAIN");
		
		txtCompany = custom.setTextField("txtCompany", true, homePanel, 200, 240, 240, 30);
		txtDepartDate = custom.setTextField("txtDepratDate", false, homePanel, 200, 300, 210, 30);
		cbDepartTime = custom.setCombo("cbDepartTime", time, homePanel, 200, 360, 240, 30);
		cbDepart = custom.setCombo("cbDepart", depart, homePanel, 200, 420, 240, 30);
		cbArrive = custom.setCombo("cbArrive", depart, homePanel, 555, 240, 240, 30);
		cbRating = custom.setCombo("cbRating", rating, homePanel, 555, 300, 240, 30);
		txtSeat = custom.setTextField("txtSeat", true, homePanel, 555, 360, 240, 30);   
		txtPrice = custom.setTextField("txtPrice", true, homePanel, 555, 420, 240, 30);
		
		lbBusCalendar = custom.setlbImg("lbBusCalendar", homePanel, 410, 295);
		busTable = custom.setTable("busTable", homePanel, busHeader, 900, 80, 700, 600);
		
		
		// 유저정보 UI
		String[] userHeader = { "유저번호", "아이디", "패스워드", "생년월일", "핸드폰번호", "이메일", "예매수","생성일자" };
		
		custom.setLbImg("lbLogo", userPanel, 1, 355, 100);
		custom.setLb("lbUserId", "아이디", userPanel, 250, 245, 100, 20, 22);
		custom.setLb("lbPassword", "패스워드", userPanel, 230, 305, 100, 20, 22);
		custom.setLb("lbBrithDate", "생년월일", userPanel, 230, 365, 100, 20, 22);
		custom.setLb("lbPhone", "핸드폰", userPanel, 250, 425, 100, 20, 22);
		custom.setLb("lbEmail", "이메일", userPanel, 250, 485, 100, 20, 22);
		
		btnUserSearch = custom.setBtnBlack("btnUserSearch", "조회", userPanel, 230, 600, 350, 45);
		btnUserAdd = custom.setBtnWhite("btnUserAdd", "등록", userPanel, 800, 700, 200, 45, "PLAIN");
		btnUserUpdate = custom.setBtnWhite("btnUserUpdate", "수정", userPanel, 1040, 700, 200, 45, "PLAIN");
		btnUserDelete = custom.setBtnWhite("btnUserDelete", "삭제", userPanel, 1280, 700, 200, 45, "PLAIN");
		
		txtUserId = custom.setTextField("txtUserId", true, userPanel, 350, 240, 240, 30);
		txtPassword = custom.setTextField("txtPassword", true, userPanel, 350, 300, 240, 30);
		txtBirthDate = custom.setTextField("txtBirthDate", true, userPanel, 350, 360, 240, 30);
		txtPhone = custom.setTextField("txtPhone", true, userPanel, 350, 420, 240, 30);   
		txtEmail = custom.setTextField("txtEmail", true, userPanel, 350, 480, 240, 30);
		
		userTable = custom.setTable("userTable", userPanel, userHeader, 800, 80, 800, 600);
		
		
		// 예매정보 UI
		String[] bookHeader = { "예매번호", "아이디", "예매상태", "출발일시", "출발지", "도착지", "회사", "등급", "매수", "가격", "좌석번호" };
		
		custom.setLbImg("lbLogo", bookPanel, 1, 425, 100);
		custom.setLb("lbUserId", "아이디", bookPanel, 100, 245, 100, 20, 22);
		custom.setLb("lbCompany", "회사", bookPanel, 120, 305, 100, 20, 22);
		custom.setLb("lbDepartDate", "출발일", bookPanel, 100, 365, 100, 20, 22);
		custom.setLb("lbDepartTime", "출발시각", bookPanel, 80, 425, 100, 20, 22);
		custom.setLb("lbDepart", "출발지", bookPanel, 100, 485, 100, 20, 22);
		custom.setLb("lbArrive", "도착지", bookPanel, 465, 245, 100, 20, 22);
		custom.setLb("lbRating", "등급", bookPanel, 485, 305, 100, 20, 22);
		custom.setLb("lbSeat", "좌석번호", bookPanel, 445, 365, 100, 20, 22);
		custom.setLb("lbPrice", "가격", bookPanel, 485, 425, 100, 20, 22);
		
		btnBookSearch = custom.setBtnBlack("btnBookSearch", "조회", bookPanel, 300, 600, 350, 45);
		btnBookDelete = custom.setBtnWhite("btnBookDelete", "삭제", bookPanel, 900, 700, 200, 45, "PLAIN");
		
		txtBUserId = custom.setTextField("txtUserId", true, bookPanel, 200, 240, 240, 30);
		txtBCompany = custom.setTextField("txtCompany", true, bookPanel, 200, 300, 240, 30);
		txtBDepartDate = custom.setTextField("txtDepratDate", false, bookPanel, 200, 360, 210, 30);
		cbBDepartTime = custom.setCombo("cbDepartTime", time, bookPanel, 200, 420, 240, 30);
		cbBDepart = custom.setCombo("cbDepart", depart, bookPanel, 200, 480, 240, 30);
		cbBArrive = custom.setCombo("cbArrive", depart, bookPanel, 555, 240, 240, 30);
		cbBRating = custom.setCombo("cbRating", rating, bookPanel, 555, 300, 240, 30);
		txtBSeat = custom.setTextField("txtSeat", true, bookPanel, 555, 360, 240, 30);   
		txtBPrice = custom.setTextField("txtPrice", true, bookPanel, 555, 420, 240, 30);
		
		lbBookCalendar = custom.setlbImg("lbBookCalendar", bookPanel, 410, 355);
		bookTable = custom.setTable("bookTable", bookPanel, bookHeader, 900, 80, 700, 600);
		
	}

}