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
	private JComboBox<String> cbBDepartTime, cbBDepart, cbBArrive, cbBRating, cbReserveState; 
	private JButton btnBookSearch, btnBookDelete;

	public Main() {	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		busTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		busTable.getColumnModel().getColumn(9).setPreferredWidth(180);
		
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePanel.setVisible(true);
				userPanel.setVisible(false);
				bookPanel.setVisible(false);
				
				busTable.getColumnModel().getColumn(2).setPreferredWidth(100);
				busTable.getColumnModel().getColumn(9).setPreferredWidth(180);
			}
		});
		
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePanel.setVisible(false);
				userPanel.setVisible(true);
				bookPanel.setVisible(false);
				
				userTable.getColumnModel().getColumn(5).setPreferredWidth(150);
				userTable.getColumnModel().getColumn(7).setPreferredWidth(150);
			}
		});
		
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homePanel.setVisible(false);
				userPanel.setVisible(false);
				bookPanel.setVisible(true);
				
				bookTable.getColumnModel().getColumn(3).setPreferredWidth(200);
				bookTable.getColumnModel().getColumn(10).setPreferredWidth(100);
				bookTable.getColumnModel().getColumn(1).setPreferredWidth(100);
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new com.java.express.gui.user.Login();
				frame.dispose();
			}
		});
		
		// ?????? ????????? ??????
		lbBusCalendar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new CustomCalendar(txtDepartDate);
			}		
		});
		
		// ?????? ????????? ??????
		lbBookCalendar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new CustomCalendar(txtBDepartDate);
			}		
		});
		
		// ?????? ????????????
		btnBusAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BusRegister();
			}
		});
		
		// ?????? ????????????
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
					JOptionPane.showMessageDialog(frame, "???????????? ????????? ????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
				}						
			}
		});
		
		// ?????? ????????????
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
						int select = JOptionPane.showConfirmDialog(frame, "?????? ????????? ???????????????~!! ?????????????????????????", "????????????", JOptionPane.YES_NO_OPTION);
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
					JOptionPane.showMessageDialog(frame, "????????? ????????? ????????????", "??????", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		// ?????? ????????????
		btnBusDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
					if (busTable.getSelectedRow() < busTable.getRowCount() && busTable.getSelectedRow() > -1) {
						int row = busTable.getSelectedRow();
						if (busTable.getValueAt(row, 7).toString().equals("45") || busTable.getValueAt(row, 7).toString().equals("28")) {
							int select = JOptionPane.showConfirmDialog(frame, "?????? ????????? ?????????????????????????", "????????????", JOptionPane.YES_NO_OPTION);
							if (select == JOptionPane.YES_OPTION) {
								DefaultTableModel model = (DefaultTableModel) busTable.getModel();
								BusDao busDao = new BusDao();
								
								int id = Integer.parseInt(busTable.getValueAt(row, 0).toString());
								int result = busDao.busDelete(id);
								if (result == 1) {
									JOptionPane.showMessageDialog(frame, "?????? ????????? ??????????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
									model.removeRow(busTable.getSelectedRow());
								} else {
									JOptionPane.showMessageDialog(frame, "?????? ?????? ????????? ??????????????????", "??????", JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							int select = JOptionPane.showConfirmDialog(frame, "?????? ????????? ???????????????~!! ?????????????????????????", "????????????", JOptionPane.YES_NO_OPTION);
							if (select == JOptionPane.YES_OPTION) {
								DefaultTableModel model = (DefaultTableModel) busTable.getModel();
								BusDao busDao = new BusDao();
								
								int id = Integer.parseInt(busTable.getValueAt(row, 0).toString());
								int result = busDao.busDelete(id);
								if (result == 1) {
									JOptionPane.showMessageDialog(frame, "?????? ????????? ??????????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
									model.removeRow(busTable.getSelectedRow());
								} else {
									JOptionPane.showMessageDialog(frame, "?????? ?????? ????????? ??????????????????", "??????", JOptionPane.ERROR_MESSAGE);
								}
							}
						}					
					} else {
						JOptionPane.showMessageDialog(frame, "????????? ????????? ????????????", "??????", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		// ?????? ????????????
		btnUserAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserRegister();
			}
		});
		
		// ?????? ????????????
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
					JOptionPane.showMessageDialog(frame, "???????????? ????????? ????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
				}				
			}	
		});
		
		// ?????? ????????????
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
					JOptionPane.showMessageDialog(frame, "????????? ????????? ????????????", "??????", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		
		// ?????? ????????????
		btnUserDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (userTable.getSelectedRow() < userTable.getRowCount() && userTable.getSelectedRow() > -1) {
					int select = JOptionPane.showConfirmDialog(frame, "?????? ????????? ?????????????????????????", "????????????", JOptionPane.YES_NO_OPTION);
					if (select == JOptionPane.YES_OPTION) {
						DefaultTableModel model = (DefaultTableModel) userTable.getModel();
						UserDao userDao = new UserDao();
						int row = userTable.getSelectedRow();
						String userId = userTable.getValueAt(row, 1).toString();
						int result = userDao.userDelete(userId);
						if (result == 1) {
							JOptionPane.showMessageDialog(frame, "?????? ????????? ??????????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
							model.removeRow(userTable.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(frame, "?????? ?????? ????????? ??????????????????", "??????", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "????????? ????????? ????????????", "??????", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// ?????? ?????? ?????? ??????
		btnBookSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<ReservesReqDto> dtos = new ArrayList<ReservesReqDto>();
				ReserveDao dao = new ReserveDao();
				dtos = dao.userReservesSearch(txtBUserId.getText(), txtBCompany.getText(), txtBDepartDate.getText(), cbBDepartTime.getSelectedItem().toString(),
						cbBDepart.getSelectedItem().toString(), cbBArrive.getSelectedItem().toString(), cbBRating.getSelectedItem().toString(),
						txtBSeat.getText(), txtBPrice.getText(), cbReserveState.getSelectedItem().toString());
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
					JOptionPane.showMessageDialog(frame, "???????????? ??????????????? ????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
				}				
			}	
		});
		
		// ?????? ?????? ?????? ????????????
		btnBookDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (bookTable.getSelectedRow() < bookTable.getRowCount() && bookTable.getSelectedRow() > -1) {
					int select = JOptionPane.showConfirmDialog(frame, "????????? ?????????????????????????", "????????????", JOptionPane.YES_NO_OPTION);
					if (select == JOptionPane.YES_OPTION) {
						DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
						ReserveDao reserveDao = new ReserveDao();
						int row = bookTable.getSelectedRow();
						String reserveId = model.getValueAt(row, 0).toString();
						int result = reserveDao.reserveDelete(reserveId);
						if (result == 1) {
							UserDao dao = new UserDao();
							dao.userBookCountUpdate(bookTable.getValueAt(row, 1).toString(), -1);
							BusDao busDao = new BusDao();
							String[] date = bookTable.getValueAt(row, 3).toString().split(" ");
							busDao.busSeatUpdate(date[0], date[1], bookTable.getValueAt(row, 4).toString(), bookTable.getValueAt(row, 5).toString(), 
									bookTable.getValueAt(row, 6).toString(), bookTable.getValueAt(row, 7).toString(), Integer.parseInt(bookTable.getValueAt(row, 8).toString()));
							JOptionPane.showMessageDialog(frame, "????????? ??????????????????", "????????????", JOptionPane.INFORMATION_MESSAGE);
							bookTable.setValueAt("????????????", row, 2);
						} else {
							JOptionPane.showMessageDialog(frame, "?????? ?????? ????????? ??????????????????", "??????", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, "????????? ????????? ????????????", "??????", JOptionPane.ERROR_MESSAGE);
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
		frame.setTitle("???????????? ?????? ???????????? ver1.0");

		CustomUIMain custom = new CustomUIMain(backgroundPanel, homePanel, userPanel, bookPanel);
		custom.setMainPanel();
		
		// ????????? UI
		btnHome = custom.setBtnMenu("btnHome", "????????????", 465, 12, 180, 45, "BOLD");
		btnUser = custom.setBtnMenu("btnUser", "????????????", 665, 12, 180, 45, "BOLD");
		btnBook = custom.setBtnMenu("btnBook", "????????????", 865, 12, 180, 45, "BOLD");
		btnLogout = custom.setBtnMenu("btnLogout", "????????????", 1065, 12, 180, 45, "BOLD");
		
		
		// ???????????? UI
		String[] depart = { "", "?????????", "????????????", "???????????????(??????)", "?????????", "??????", "????????????", "??????(??????????????)", "??????", "????????????",
				"????????????", "??????", "??????", "??????", "?????????", "??????", "??????", "??????"};
		String[] time = { "", "09:00:00", "11:00:00", "13:00:00", "15:00:00", "17:00:00", "19:00:00", "21:00:00", "23:00:00" };
		String[] rating = { "", "??????", "??????" };
		String[] busHeader = { "????????????", "??????", "?????????", "????????????", "?????????", "?????????", "??????", "????????????", "??????", "????????????" };
		
		custom.setLbImg("lbLogo", homePanel, 1, 425, 100);
		custom.setLb("lbCompany", "??????", homePanel, 120, 245, 100, 20, 22);
		custom.setLb("lbDepartDate", "?????????", homePanel, 100, 305, 100, 20, 22);
		custom.setLb("lbDepartTime", "????????????", homePanel, 80, 365, 100, 20, 22);
		custom.setLb("lbDepart", "?????????", homePanel, 100, 425, 100, 20, 22);
		custom.setLb("lbArrive", "?????????", homePanel, 465, 245, 100, 20, 22);
		custom.setLb("lbRating", "??????", homePanel, 485, 305, 100, 20, 22);
		custom.setLb("lbSeat", "????????????", homePanel, 445, 365, 100, 20, 22);
		custom.setLb("lbPrice", "??????", homePanel, 485, 425, 100, 20, 22);
		
		btnBusSearch = custom.setBtnBlack("btnBusSearch", "??????", homePanel, 300, 600, 350, 45);
		btnBusAdd = custom.setBtnWhite("btnBusAdd", "??????", homePanel, 900, 700, 200, 45, "PLAIN");
		btnBusUpdate = custom.setBtnWhite("btnBusUpdate", "??????", homePanel, 1140, 700, 200, 45, "PLAIN");
		btnBusDelete = custom.setBtnWhite("btnBusDelete", "??????", homePanel, 1380, 700, 200, 45, "PLAIN");
		
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
		
		
		// ???????????? UI
		String[] userHeader = { "????????????", "?????????", "????????????", "????????????", "???????????????", "?????????", "?????????","????????????" };
		
		custom.setLbImg("lbLogo", userPanel, 1, 355, 100);
		custom.setLb("lbUserId", "?????????", userPanel, 250, 245, 100, 20, 22);
		custom.setLb("lbPassword", "????????????", userPanel, 230, 305, 100, 20, 22);
		custom.setLb("lbBrithDate", "????????????", userPanel, 230, 365, 100, 20, 22);
		custom.setLb("lbPhone", "?????????", userPanel, 250, 425, 100, 20, 22);
		custom.setLb("lbEmail", "?????????", userPanel, 250, 485, 100, 20, 22);
		
		btnUserSearch = custom.setBtnBlack("btnUserSearch", "??????", userPanel, 230, 600, 350, 45);
		btnUserAdd = custom.setBtnWhite("btnUserAdd", "??????", userPanel, 800, 700, 200, 45, "PLAIN");
		btnUserUpdate = custom.setBtnWhite("btnUserUpdate", "??????", userPanel, 1040, 700, 200, 45, "PLAIN");
		btnUserDelete = custom.setBtnWhite("btnUserDelete", "??????", userPanel, 1280, 700, 200, 45, "PLAIN");
		
		txtUserId = custom.setTextField("txtUserId", true, userPanel, 350, 240, 240, 30);
		txtPassword = custom.setTextField("txtPassword", true, userPanel, 350, 300, 240, 30);
		txtBirthDate = custom.setTextField("txtBirthDate", true, userPanel, 350, 360, 240, 30);
		txtPhone = custom.setTextField("txtPhone", true, userPanel, 350, 420, 240, 30);   
		txtEmail = custom.setTextField("txtEmail", true, userPanel, 350, 480, 240, 30);
		
		userTable = custom.setTable("userTable", userPanel, userHeader, 800, 80, 800, 600);
		
		
		// ???????????? UI
		String[] bookHeader = { "????????????", "?????????", "????????????", "????????????", "?????????", "?????????", "??????", "??????", "??????", "??????", "????????????" };
		String[] rserveState = {"", "????????????", "????????????"};
		
		custom.setLbImg("lbLogo", bookPanel, 1, 425, 100);
		custom.setLb("lbUserId", "?????????", bookPanel, 100, 245, 100, 20, 22);
		custom.setLb("lbCompany", "??????", bookPanel, 120, 305, 100, 20, 22);
		custom.setLb("lbDepartDate", "?????????", bookPanel, 100, 365, 100, 20, 22);
		custom.setLb("lbDepartTime", "????????????", bookPanel, 80, 425, 100, 20, 22);
		custom.setLb("lbDepart", "?????????", bookPanel, 100, 485, 100, 20, 22);
		custom.setLb("lbArrive", "?????????", bookPanel, 465, 245, 100, 20, 22);
		custom.setLb("lbRating", "??????", bookPanel, 485, 305, 100, 20, 22);
		custom.setLb("lbSeat", "????????????", bookPanel, 445, 365, 100, 20, 22);
		custom.setLb("lbPrice", "??????", bookPanel, 485, 425, 100, 20, 22);
		custom.setLb("lbPrice", "????????????", bookPanel, 445, 485, 100, 20, 22);
		
		btnBookSearch = custom.setBtnBlack("btnBookSearch", "??????", bookPanel, 300, 600, 350, 45);
		btnBookDelete = custom.setBtnWhite("btnBookDelete", "??????", bookPanel, 900, 700, 200, 45, "PLAIN");
		
		txtBUserId = custom.setTextField("txtUserId", true, bookPanel, 200, 240, 240, 30);
		txtBCompany = custom.setTextField("txtCompany", true, bookPanel, 200, 300, 240, 30);
		txtBDepartDate = custom.setTextField("txtDepratDate", false, bookPanel, 200, 360, 210, 30);
		cbBDepartTime = custom.setCombo("cbDepartTime", time, bookPanel, 200, 420, 240, 30);
		cbBDepart = custom.setCombo("cbDepart", depart, bookPanel, 200, 480, 240, 30);
		cbBArrive = custom.setCombo("cbArrive", depart, bookPanel, 555, 240, 240, 30);
		cbBRating = custom.setCombo("cbRating", rating, bookPanel, 555, 300, 240, 30);
		txtBSeat = custom.setTextField("txtSeat", true, bookPanel, 555, 360, 240, 30);   
		txtBPrice = custom.setTextField("txtPrice", true, bookPanel, 555, 420, 240, 30);
		cbReserveState = custom.setCombo("cbReserveState", rserveState, bookPanel, 555, 480, 240, 30);
		
		lbBookCalendar = custom.setlbImg("lbBookCalendar", bookPanel, 410, 355);
		bookTable = custom.setTable("bookTable", bookPanel, bookHeader, 900, 80, 700, 600);
		
	}

}