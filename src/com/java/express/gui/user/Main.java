package com.java.express.gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
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
import com.java.express.bus.dto.SearchReqDto;
import com.java.express.reserve.ReserveDao;
import com.java.express.reserve.dto.ReSearchReqDto;
import com.java.express.user.User;
import com.java.express.user.UserDao;
import com.java.express.user.dto.JoinReqDto;

@SuppressWarnings("serial")
public class Main extends CustomUIMain {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel, homePanel, bookPanel, updatePanel, paymentPanel;
	private JLabel lbCalendar;
	private JTextField txtDepartDate;
	private JComboBox<String> cbDepart, cbArrive, cbRating, cbBuying; 
	private JButton btnSearch, btnReset, btnBook;
	private JButton btnLogout, btnHome, btnBookSearch, btnUpdate;
	@SuppressWarnings("unused")
	private JTable busTable, bookSearchTable, bookContentTable;
	
	private JTextField txtUserId, txtPassword, txtPasswordCheck, txtBirthDate, txtPhone, txtEmail;
	private JButton btnUserUpdate, btnBookCancel;
	
	private int buying = 0;
	private String departDate = "";
	private String seat = "";
	private int busRating = 1;
	
	User user = new User();
	Bus bus = new Bus();

	public Main(User user) {
		this.user = user;
		
		init();
		listener();		
	}
	
	private void listener() {
		// 예매하기 화면
				btnHome.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						homePanel.setVisible(true);
						bookPanel.setVisible(false);
						updatePanel.setVisible(false);
						paymentPanel.setVisible(false);
					}	
				});
				
				// 캘린더열기
				lbCalendar.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						new CustomCalendar(txtDepartDate);
					}		
				});
				
				// 예매내역조회 화면
				btnBookSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						homePanel.setVisible(false);
						bookPanel.setVisible(true);
						updatePanel.setVisible(false);
						paymentPanel.setVisible(false);
						
						DefaultTableModel model = (DefaultTableModel) bookSearchTable.getModel();
						model.getDataVector().removeAllElements();
						revalidate();
						
						ReserveDao dao = new ReserveDao();
						ArrayList<ReSearchReqDto> dtos = dao.userReserveSearch(user.getId());
						for (int i = 0; i < dtos.size(); i++) {
							model.addRow(new Object[] {dtos.get(i).getId(), dtos.get(i).getDepartDate() + " " + dtos.get(i).getDepartTime(), dtos.get(i).getCompany(), dtos.get(i).getDepart(), 
									dtos.get(i).getArrive(), dtos.get(i).getRating(), dtos.get(i).getBuying(), dtos.get(i).getPrice(), 
									dtos.get(i).getReserveState(), dtos.get(i).getSeat()});
						}
					}
				});
				
				// 유저정보수정 화면
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						homePanel.setVisible(false);
						bookPanel.setVisible(false);
						updatePanel.setVisible(true);
						paymentPanel.setVisible(false);
						
						txtUserId.setText(user.getUserId());
						txtBirthDate.setText(user.getBirthDate());
						txtPhone.setText(user.getPhone());
						txtEmail.setText(user.getEmail());
					}
				});
				
				// 로그아웃
				btnLogout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new Login();
						frame.dispose();
					}
				});
				
				// 리셋 
				btnReset.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cbDepart.setSelectedIndex(0);
						cbArrive.setSelectedIndex(0);
						txtDepartDate.setText("");
						cbRating.setSelectedIndex(0);
						cbBuying.setSelectedIndex(0);
					}
				});	
				
				// 예매조회 (busTable에 버스 정보 표시)
				btnSearch.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// 유효성검사
						if (cbDepart.getSelectedItem() == "") {
							JOptionPane.showMessageDialog(frame, "출발지를 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (cbArrive.getSelectedItem() == "") {
							JOptionPane.showMessageDialog(frame, "도착지를 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (txtDepartDate.getText().length() == 0) {
							JOptionPane.showMessageDialog(frame, "출발일을 선택해주세요", "오류", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (cbRating.getSelectedItem() == "") {
							JOptionPane.showMessageDialog(frame, "등급을 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (cbBuying.getSelectedItem() == "") {
							JOptionPane.showMessageDialog(frame, "매수를 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						SearchReqDto dto = new SearchReqDto();
						dto.setDepart(cbDepart.getSelectedItem().toString());
						dto.setArrive(cbArrive.getSelectedItem().toString());
						dto.setDepartDate(Date.valueOf(txtDepartDate.getText()));
						if (cbRating.getSelectedItem() == "전체") dto.setRating("");
						else dto.setRating(cbRating.getSelectedItem().toString());
						buying = Integer.parseInt(cbBuying.getSelectedItem().toString()); 
						departDate = txtDepartDate.getText();
						
						BusDao dao = new BusDao();
						ArrayList<Bus> buses = dao.userBusSearch(dto);
						
						if (buses.size() != 0) {
							DefaultTableModel model = (DefaultTableModel) busTable.getModel();
							model.getDataVector().removeAllElements();
							revalidate();
							for (int i = 0; i < buses.size(); i++) {
								model.addRow(new Object[] {buses.get(i).getId(), buses.get(i).getDepartTime(), buses.get(i).getDepart(), buses.get(i).getArrive(), 
										buses.get(i).getRating(), buses.get(i).getCompany(), buses.get(i).getSeat(), buses.get(i).getPrice()});
							}
						} else {
							JOptionPane.showMessageDialog(frame, "일치하는 정보가 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}	
				});
				
				// 예매하기 (결제화면으로 이동)
				btnBook.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (busTable.getSelectedRow() < busTable.getRowCount() && busTable.getSelectedRow() > -1) {
							int brow = busTable.getSelectedRow();
							int remainSeat = Integer.parseInt(busTable.getValueAt(brow, 6).toString());
							if (remainSeat >= buying) {
								bus.setId(Integer.parseInt(busTable.getValueAt(brow, 0).toString()));
								bus.setDepartTime(Time.valueOf(busTable.getValueAt(brow, 1).toString()));
								bus.setDepart(busTable.getValueAt(brow, 2).toString());
								bus.setArrive(busTable.getValueAt(brow, 3).toString());
								bus.setRating(busTable.getValueAt(brow, 4).toString());
								bus.setCompany(busTable.getValueAt(brow, 5).toString());
								bus.setSeat(remainSeat);		
								bus.setPrice(Integer.parseInt(busTable.getValueAt(brow, 7).toString()));
								
								if (busTable.getValueAt(brow, 4).toString().equals("우등")) {
									busRating = 1;
									seat = ((28 - bus.getSeat()) + 1) + "";
									for (int i = 1; i < buying; i++) {
										seat = seat + "," + ((28 - bus.getSeat()) + (i + 1)) ;
									}
								} else {
									busRating = 2;
									seat = ((45 - bus.getSeat()) + 1) + "";
									for (int i = 1; i < buying; i++) {
										seat = seat + "," + ((45 - bus.getSeat()) + (i + 1)) ;
									}
								}
								
								new Payment(user, bus, departDate, seat, buying, busRating);
								frame.dispose();
							} else {
								JOptionPane.showMessageDialog(frame, "남은 좌석이 " + remainSeat + "석 입니다 (선택한 예매수: "+ buying +")", "오류", JOptionPane.ERROR_MESSAGE);
							}			
						} else {
							JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
						}	
					}
				});
				
				btnBookCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (bookSearchTable.getSelectedRow() < bookSearchTable.getRowCount() && bookSearchTable.getSelectedRow() > -1) {
							int select = JOptionPane.showConfirmDialog(frame, "예매 정보를 삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
							if (select == JOptionPane.YES_OPTION) {
								DefaultTableModel model = (DefaultTableModel) bookSearchTable.getModel();
								ReserveDao reserveDao = new ReserveDao();
								int row = bookSearchTable.getSelectedRow();
								String reserveId = bookSearchTable.getValueAt(row, 0).toString();
								int result = reserveDao.reserveDelete(reserveId);
								if (result == 1) {
									UserDao userDao = new UserDao();
									userDao.userBookCountUpdate(user.getUserId(), -1);
									BusDao busDao = new BusDao();
									String[] date = bookSearchTable.getValueAt(row, 1).toString().split(" ");
									busDao.busSeatUpdate(date[0], date[1], bookSearchTable.getValueAt(row, 3).toString(), bookSearchTable.getValueAt(row, 4).toString(), 
											bookSearchTable.getValueAt(row, 2).toString(), bookSearchTable.getValueAt(row, 5).toString(), Integer.parseInt(bookSearchTable.getValueAt(row, 6).toString()));
									JOptionPane.showMessageDialog(frame, "예매 정보를 삭제했습니다", "삭제성공", JOptionPane.INFORMATION_MESSAGE);
									model.removeRow(bookSearchTable.getSelectedRow());
								} else {
									JOptionPane.showMessageDialog(frame, "예매 정보 삭제에 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							JOptionPane.showMessageDialog(frame, "선택된 항목이 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				
				// 유저 정보 수정
				btnUserUpdate.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int select = JOptionPane.showConfirmDialog(frame, "유저 정보를 수정하시겠습니까?", "수정확인", JOptionPane.YES_NO_OPTION);
						if (select == JOptionPane.YES_OPTION) {	
							// 유효성 검사			
							String password = txtPassword.getText();
							if (password.length() < 8) {
								JOptionPane.showMessageDialog(null, "비밀번호는 8글자 이상 입력해주세요");
								return;
							}
							
							String passwordCheck = txtPasswordCheck.getText();
							if (!(password.equals(passwordCheck))) {
								JOptionPane.showMessageDialog(null, "비밀번호가 동일하지 않습니다.");
								txtPassword.setText("");
								txtPasswordCheck.setText("");
								return;
							}

							String regExpNumber = "^[0-9]+$";
							String birthDate = txtBirthDate.getText();
							if (!(birthDate.matches(regExpNumber))) {
								JOptionPane.showMessageDialog(null, "생년월일은 숫자만 입력할 수 있습니다");
								return;
							} else if (birthDate.length() != 8) {
								JOptionPane.showMessageDialog(null, "생년월일은 8글자로 입력해주세요 ex)19981130");
								return;
							}
							
							String phone = txtPhone.getText();
							if (!(phone.matches(regExpNumber))) {
								JOptionPane.showMessageDialog(null, "전화번호는 숫자만 입력할 수 있습니다");
								return;
							} else if (!(phone.length() == 10 || phone.length() == 11)) {
								JOptionPane.showMessageDialog(null, "전화번호는 10-11자리만 가능합니다");
								return;
							}
							
							String regExpEmail = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
							String email = txtEmail.getText();
							if (!(email.matches(regExpEmail))) {
								JOptionPane.showMessageDialog(null, "유효한 이메일을 입력해주세요");
								return;
							}				
							
							JoinReqDto dto = new JoinReqDto();
							dto.setUserId(txtUserId.getText());
							dto.setPassword(txtPassword.getText());
							dto.setBirthDate(txtBirthDate.getText());
							dto.setPhone(txtPhone.getText());
							dto.setEmail(txtEmail.getText());
							UserDao dao = new UserDao();
							int result = dao.userUpdate(dto);
							if (result == 1) {
								user.setPassword(password);
								user.setBirthDate(birthDate);
								user.setPhone(phone);
								user.setEmail(email);
								txtPassword.setText("");
								txtPasswordCheck.setText("");
								JOptionPane.showMessageDialog(frame, "유저 정보를 수정했습니다", "수정성공", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(frame, "유저 정보 수정에 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
							}
					    }
					}
				});				
	}

	private void init() {	
		backgroundPanel = new JPanel();
		homePanel = new JPanel();
		bookPanel = new JPanel();
		updatePanel = new JPanel();
		paymentPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("고속버스 예약 프로그램 ver1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1920, 1080);
		frame.setResizable(false);
		frame.setVisible(true);

		CustomUIMain custom = new CustomUIMain(backgroundPanel, homePanel, bookPanel, updatePanel, paymentPanel);
		custom.setPanel();
		
		// 상단바
		btnHome = custom.setBtnMenu("btnHome", "예매하기", 465, 12, 180, 45, "BOLD");
		btnBookSearch = custom.setBtnMenu("btnBook", "예매내역 조회", 665, 12, 180, 45, "BOLD");
		btnUpdate = custom.setBtnMenu("btnUpdate", "회원정보 수정", 865, 12, 180, 45, "BOLD");
		btnLogout = custom.setBtnMenu("btnLogout", "로그아웃", 1065, 12, 180, 45, "BOLD");
		
		// 버스 시간 조회
		String[] depart = { "", "동서울", "서울경부", "센트럴시티(서울)", "의정부", "인천", "고양백석", "광주(유·스퀘어)", "부산", "부산사상",
				"대전복합", "전주", "유성", "천안", "동대구", "강릉", "안양", "속초"};
		String[] rating = { "", "전체", "우등", "일반" };
		String[] buying = { "", "1", "2", "3", "4" };
		String[] header = { " ", "출발시간", "출발지", "도착지", "등급", "회사", "잔여좌석", "가격" };
		
		custom.setLbImg("lbLogo", homePanel, 1, 485, 100);
		custom.setLb("lbDepart", "출발지", homePanel, 260, 240, 100, 20, 22);
		custom.setLb("lbArrive", "도착지", homePanel, 260, 300, 100, 20, 22);
		custom.setLb("lbDepartDate", "출발일", homePanel, 260, 360, 100, 20, 22);
		custom.setLb("lbRating", "등급", homePanel, 280, 420, 100, 20, 22);
		custom.setLb("lbBuying", "매수", homePanel, 280, 480, 100, 20, 22);
		
		btnSearch = custom.setBtnBlack("btnSearch", "조회하기", homePanel, 360, 560, 350, 45);
		btnReset = custom.setBtnWhite("btnReset", "초기화", homePanel, 360, 620, 350, 45, "PLAIN");
		btnBook = custom.setBtnWhite("btnBook", "예매하기", homePanel, 1075, 700, 350, 45, "PLAIN");
		
		cbDepart = custom.setCombo("cbDepart", depart, homePanel, 360, 235, 350, 30);
		cbArrive = custom.setCombo("cbArrive", depart, homePanel, 360, 295, 350, 30);
		txtDepartDate = custom.setTextField("txtDepratDate", false, homePanel, 360, 355, 310, 30);
		cbRating = custom.setCombo("cbRating", rating, homePanel, 360, 415, 350, 30);
		cbBuying = custom.setCombo("cbBuying", buying, homePanel, 360, 475, 350, 30);
		
		lbCalendar = custom.setlbImg("lbCalendar", homePanel, 670, 348);
		busTable = custom.setTable("busTable", homePanel, header, 900, 80, 700, 600);	
		
		// 예매내역조회
		String[] book = { "예매번호", "출발일시", "회사", "출발지", "도착지", "등급", "매수", "가격", "예매상태", "좌석번호"};
		custom.setLb("lbBook", "-예매내역-", bookPanel, 740, 50, 200, 50, 35);
		btnBookCancel = custom.setBtnWhite("btnBookCancel", "예매 취소", bookPanel, 1200, 60, 350, 45, "PLAIN");
		bookSearchTable = custom.setTable("bookSearchTable", bookPanel, book, 100, 150, 1450, 700);	
		
		// 회원정보수정
		custom.setLbImg("lbLogo", updatePanel, 1, 805, 50);
		custom.setLb("lbUserId", "아이디", updatePanel, 680, 190, 100, 20, 18);
		txtUserId = custom.setTextField("txtUserId", false, updatePanel, 680, 215, 350, 40);
		
		custom.setLb("lbPassword", "비밀번호", updatePanel, 680, 265, 100, 20, 18);
		txtPassword = custom.setPasswordField("txtPassword", updatePanel, 680, 290, 350, 40);
		
		custom.setLb("lbPasswordCheck", "비밀번호 확인", updatePanel, 680, 340, 150, 20, 18);
		txtPasswordCheck = custom.setPasswordField("txtPasswordCheck", updatePanel, 680, 365, 350, 40);
		
		custom.setLb("lbBirthDate", "생년월일", updatePanel, 680, 415, 100, 20, 18);
		txtBirthDate = custom.setTextField("txtBirthDate", true, updatePanel, 680, 440, 350, 40);
		
		custom.setLb("lbPhone", "휴대전화", updatePanel,680, 490, 100, 20, 18);
		txtPhone = custom.setTextField("txtPhone", true, updatePanel, 680, 515, 350, 40);
		
		custom.setLb("lbEmail", "이메일", updatePanel,680, 565, 100, 20, 18);
		txtEmail = custom.setTextField("txtEmail", true, updatePanel, 680, 590, 350, 40);

		btnUserUpdate = custom.setBtnBlack("btnUserUpdate", "회원정보수정", updatePanel, 680, 680, 350, 45);
		
	}

}