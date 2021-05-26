package com.java.express.gui.user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.java.express.bus.Bus;
import com.java.express.bus.BusDao;
import com.java.express.reserve.ReserveDao;
import com.java.express.reserve.dto.ReserveReqDto;
import com.java.express.seat.SeatDao;
import com.java.express.seat.dto.Seat;
import com.java.express.user.User;
import com.java.express.user.UserDao;

@SuppressWarnings("serial")
public class Payment extends CustomUILogin {	
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel, topBluePanel, topGrayPanel;
	
	private JTextField txtPay, txtCardNum, txtCVC;
	private JPasswordField txtCardPassword;
	private JComboBox<String> cbCard, cbPeriod;
	private JButton btnPayment, btnBack, btnScreen;
	private JButton[] btnSeat;
	private JTable bookContentTable;
	
	@SuppressWarnings("unused")
	private int buying;
	@SuppressWarnings("unused")
	private String departDate = "";
	@SuppressWarnings("unused")
	private String seat;
	private int busRating;
	private int selectedCnt;
	private ArrayList<Integer> selectedSeatNum = new ArrayList<Integer>();
	private ActionListener btnListener;
	
	User user = new User();
	Bus bus = new Bus();

	public Payment(User user, Bus bus, String departDate, String seat, int buying, int busRating) {
		this.user = user;
		this.bus = bus;
		this.departDate = departDate;
		this.seat = seat;		
		this.buying = buying;
		this.busRating = busRating;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton)e.getSource();
					int btnNum = Integer.parseInt(btn.getText());
					if(btn.getBackground().equals(Color.ORANGE)) {
							btn.setBackground(new Color(230, 236, 240));
							int arrayIndex = selectedSeatNum.indexOf(btnNum);
							selectedSeatNum.remove(arrayIndex);
							selectedCnt--;
					} else {
						if(selectedCnt < buying && selectedCnt >= 0) {
							btn.setBackground(Color.ORANGE);
							selectedSeatNum.add(btnNum);
							selectedCnt++;
						} else {
							JOptionPane.showMessageDialog(frame, "선택한 인원 " + buying + "명을 초과하여 선택할 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
						}
					}
	            }
			}
		};
		
		init();
		btnScreen.setEnabled(false);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				DefaultTableModel model = (DefaultTableModel) bookContentTable.getModel();
				model.addRow(new Object[] {bus.getCompany(), departDate, bus.getDepartTime(), bus.getDepart(), bus.getArrive(),
						bus.getRating(), buying, ""});
				int pay = bus.getPrice() * buying;
				txtPay.setText(String.valueOf(pay));
			}			
		});
		
		// 결제하기 (예매내역 화면으로 이동)
		btnPayment.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// 유효성검사
				if (cbCard.getSelectedItem() == "") {
					JOptionPane.showMessageDialog(frame, "카드사를 선택해주세요", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (txtCardNum.getText().length() == 0 || txtCardNum.getText().length() != 16) {
					JOptionPane.showMessageDialog(frame, "카드번호를 정확히 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (cbPeriod.getSelectedItem() == "") {
					JOptionPane.showMessageDialog(frame, "할부기간을 선택해주세요", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (txtCVC.getText().length() == 0 || txtCVC.getText().length() != 3) {
					JOptionPane.showMessageDialog(frame, "CVC를 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (txtCardPassword.getText().length() == 0 || txtCardPassword.getText().length() != 4) {
					JOptionPane.showMessageDialog(frame, "비밀번호를 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(selectedSeatNum.size() == buying) {
					String selectedSeat = selectedSeatNum.get(0).toString();
					for (int i = 1; i < selectedSeatNum.size(); i++) {
						selectedSeat = selectedSeat + "," + selectedSeatNum.get(i);
					}
					System.out.println(selectedSeat);
					
					ReserveReqDto dto = new ReserveReqDto();
					ReserveDao reserveDao = new ReserveDao();
					dto.setUserId(user.getId());
					dto.setBusId(bus.getId());
					dto.setReserveState("예매완료");
					dto.setBuying(buying);
					dto.setSeat(selectedSeat);
					dto.setPrice(bus.getPrice() * buying);
					int result = reserveDao.ReserveRegister(dto);
					if (result == 1) {
						UserDao dao = new UserDao();
						dao.userBookCountUpdate(user.getUserId(), 1);
						BusDao busDao = new BusDao();
						busDao.busSeatUpdate(bus.getId(), bus.getSeat() - buying);
						JOptionPane.showMessageDialog(frame, "결제에 성공했습니다", "결제성공", JOptionPane.INFORMATION_MESSAGE);
						new Main(user);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "결제를 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "선택한 인원 " + buying + "명만큼의 좌석을 선택하지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// 결제화면 -> 예매하기 화면으로 이동 (뒤로가기)
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(bus.getDepart());
				new Main(user);
				frame.dispose();
			}
		});

		frame.setSize(1920, 1080);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {		
		backgroundPanel = new JPanel();
		frame.setContentPane(backgroundPanel);
		frame.setTitle("고속버스 예약 프로그램 ver1.0");

		CustomUIMain custom = new CustomUIMain(backgroundPanel);
		
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
		
		topBluePanel = new JPanel();
		topBluePanel.setBounds(0, 0, 1710, 70);
		topBluePanel.setBackground(new Color(26, 27, 32));
		topBluePanel.setLayout(null);
		backgroundPanel.add(topBluePanel); 
		
		topGrayPanel = new JPanel();
		topGrayPanel.setBounds(0, 70, 1710, 50);
		topGrayPanel.setBackground(new Color(230, 236, 240));
		topGrayPanel.setLayout(null);
		backgroundPanel.add(topGrayPanel);
			
		// 결제
		String[] bookContent = { "회사", "출발일", "출발시각", "출발지", "도착지", "등급", "매수", "좌석번호" };
		String[] card = { "", "신한카드", "KB국민카드", "BC카드", "현대카드", "롯데카드", "하나카드", "삼성카드", "우리카드",
				"씨티카드", "NH카드", "카카오뱅크카드", "케이뱅크카드", "기타" };
		String[] period = { "", "일시불", "1개월", "2개월", "3개월", "4개월", "5개월", "6개월", "7개월", "8개월", "9개월", "10개월", "11개월", "12개월" };
		custom.setLb("lbBook", "-예매내용 확인-", backgroundPanel, 1000, 150, 300, 50, 35);
		bookContentTable = custom.setTable("bookContentTable", backgroundPanel, bookContent, 800, 220, 700, 100);
		
		custom.setLb("lbPay", "결제금액", backgroundPanel, 910, 405, 100, 20, 22);
		custom.setLb("lbArrive", "카드사", backgroundPanel, 910, 455, 100, 20, 22);
		custom.setLb("lbDepartDate", "카드번호", backgroundPanel, 910, 505, 100, 20, 22);
		custom.setLb("lbPeriod", "할부기간", backgroundPanel, 910, 555, 100, 20, 22);
		custom.setLb("lbCVC", "CVC", backgroundPanel, 910, 605, 100, 20, 22);
		custom.setLb("lbCardPassword", "비밀번호", backgroundPanel, 910, 655, 100, 20, 22);
		
		txtPay = custom.setTextField("txtPay", false, backgroundPanel, 1030, 400, 350, 30);
		cbCard = custom.setCombo("cbCard", card, backgroundPanel, 1030, 450, 350, 30);
		txtCardNum = custom.setTextField("txtCardNum", true, backgroundPanel, 1030, 500, 350, 30);
		cbPeriod = custom.setCombo("cbPeriod", period, backgroundPanel, 1030, 550, 350, 30);
		txtCVC = custom.setTextField("txtCVC", true, backgroundPanel, 1030, 600, 350, 30);
		txtCardPassword = custom.setPasswordField("txtCardPassword", backgroundPanel, 1030, 650, 350, 30);
		
		btnPayment = custom.setBtnBlack("btnPayment", "결제하기", backgroundPanel, 1030, 710, 350, 45);
		btnBack = custom.setBtnWhite("btnBack", "취소", backgroundPanel, 1030, 770, 350, 45, "PLAIN");
		
		custom.setLb("lbBook", "-좌석 선택-", backgroundPanel, 300, 150, 300, 50, 35);
		btnScreen = custom.setbtnBar("screenBar", "운전석", backgroundPanel, 250);

		SeatDao dao = new SeatDao();
		Seat busSeat = dao.selectSeat(busRating);
		int row = busSeat.getRow();
		int col = busSeat.getCol();
		btnSeat = new JButton[(row * col) - (row - 1)];
		int seatNum = 1;
		for(int r = 1; r <= row; r++) {
			for(int c = 1; c <= col; c++) {
				int moveX = 90;
				int moveY = 60;
				if (c % 3 == 0 && r != row) {			
				} else {
					btnSeat[seatNum-1] = custom.setBtnSeat("btnSeat"+seatNum, seatNum+"", backgroundPanel, 200 + (moveX * (c - 1)), 300 + (moveY * (r - 1)));
					btnSeat[seatNum-1].addActionListener(btnListener);
					seatNum++;
				}
				
			}	
		}
		
		ReserveDao rDao = new ReserveDao();
		String selectedSeat = rDao.selectedSeat(bus.getId());
		
		if(selectedSeat != null) {
			String splitAlredySelectedSeat[] = selectedSeat.split("\\,");
			for(int i=0; i<btnSeat.length; i++) {
				String num = btnSeat[i].getText();
				for(int j=0; j<splitAlredySelectedSeat.length; j++) {
					if(splitAlredySelectedSeat[j].equals(num)) {
						btnSeat[i].setBackground(new Color(204, 204, 204));
						btnSeat[i].setEnabled(false);
					}
				}
			}
		}
		
		
	}

}