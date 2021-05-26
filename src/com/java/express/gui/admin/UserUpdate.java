package com.java.express.gui.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.java.express.user.UserDao;
import com.java.express.user.dto.JoinReqDto;

public class UserUpdate extends CustomUIMain {
	private static final long serialVersionUID = 1L;
	
	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId, txtPassword, txtPasswordCheck, txtBirthDate, txtPhone, txtEmail;
	private JButton btnUserUpdate;
	ArrayList<String> data;
	
	public UserUpdate(ArrayList<String> data) {
		init();
		this.data = data;
		
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
						JOptionPane.showMessageDialog(frame, "유저 정보를 수정했습니다", "수정성공", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "유저 정보 수정에 실패했습니다", "오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			}	
		}); 
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				txtUserId.setText(data.get(1));
				txtPassword.setText(data.get(2));
				txtPasswordCheck.setText(data.get(2));
				txtBirthDate.setText(data.get(3));
				txtPhone.setText(data.get(4));
				txtEmail.setText(data.get(5));
			}			
		});
		
		frame.setSize(600, 900);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private void init() {
		backgroundPanel = new JPanel();

		frame.setContentPane(backgroundPanel);
		frame.setTitle("고속버스 예약 프로그램 ver1.0");

		CustomUIMain custom = new CustomUIMain(backgroundPanel);
		custom.setModalPanel();
		
		custom.setLbImg("lbLogo", backgroundPanel, 1, 250, 150);
		custom.setLb("lbUserId", "아이디", backgroundPanel, 130, 305, 100, 25, 22);
		custom.setLb("lbDepartDate", "비밀번호", backgroundPanel, 110, 355, 100, 25, 22);
		custom.setLb("lbDepartTime", "비밀번호 재확인", backgroundPanel, 35, 405, 190, 25, 22);
		custom.setLb("lbDepartTime", "생년월일", backgroundPanel, 110, 455, 100, 25, 22);
		custom.setLb("lbDepart", "휴대전화", backgroundPanel, 110, 505, 100, 25, 22);
		custom.setLb("lbArrive", "이메일", backgroundPanel, 130, 555, 100, 25, 22);
		
		btnUserUpdate = custom.setBtnBlack("btnUserUpdate", "수정", backgroundPanel, 125, 650, 350, 45);
		
		txtUserId = custom.setTextField("txtUserId", false, backgroundPanel, 240, 300, 240, 30);
		txtPassword = custom.setTextField("txtPassword", true, backgroundPanel, 240, 350, 240, 30);
		txtPasswordCheck = custom.setTextField("txtPasswordCheck", true, backgroundPanel, 240, 400, 240, 30);
		txtBirthDate = custom.setTextField("txtBirthDate", true, backgroundPanel, 240, 450, 240, 30);
		txtPhone = custom.setTextField("txtPhone", true, backgroundPanel, 240, 500, 240, 30);
		txtEmail = custom.setTextField("txtEmail", true, backgroundPanel, 240, 550, 240, 30);
	}
}
