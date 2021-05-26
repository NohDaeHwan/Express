package com.java.express.gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.java.express.user.UserDao;
import com.java.express.user.dto.JoinReqDto;

@SuppressWarnings("serial")
public class Join extends CustomUILogin {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId, txtPassword, txtPasswordCheck, txtBirth, txtMobile, txtEmail;
	private JButton btnJoinComplete, btnPrev;
	private JCheckBox cbAgree;
	@SuppressWarnings("unused")
	private JLabel lbLogo, lbId, lbPassword, lbPasswordCheck, lbBirth, lbMobile, lbEmail;

	UserDao userDao = new UserDao();
	
	public Join() {	
		
		init();

		btnJoinComplete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 유효성 검사
				String userId = txtUserId.getText();
				if (userId.length() < 8) {
					JOptionPane.showMessageDialog(null, "아이디는 8글자 이상 입력해주세요");
					return;
				}
				
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
				String birthDate = txtBirth.getText();
				if (!(birthDate.matches(regExpNumber))) {
					JOptionPane.showMessageDialog(null, "생년월일은 숫자만 입력할 수 있습니다");
					return;
				} else if (birthDate.length() != 8) {
					JOptionPane.showMessageDialog(null, "생년월일은 8글자로 입력해주세요 ex)19981130");
					return;
				}
				
				String mobile = txtMobile.getText();
				if (!(mobile.matches(regExpNumber))) {
					JOptionPane.showMessageDialog(null, "전화번호는 숫자만 입력할 수 있습니다");
					return;
				} else if (!(mobile.length() == 10 || mobile.length() == 11)) {
					JOptionPane.showMessageDialog(null, "전화번호는 10-11자리만 가능합니다");
					return;
				}
				
				String regExpEmail = "\\w+@\\w+\\.\\w+(\\.\\w+)?";
				String email = txtEmail.getText();
				if (!(email.matches(regExpEmail))) {
					JOptionPane.showMessageDialog(null, "유효한 이메일을 입력해주세요");
					return;
				}
				
				if (cbAgree.isSelected() == false) {
					JOptionPane.showMessageDialog(null, "개인정보 수집 및 이용 약관에 동의해주세요");
					return;
				}
				
				// 아이디 중복 체크			
				int result = userDao.userIdCheck(userId);			
				if (result == -1) {
					JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다.\n다른 아이디를 이용해주세요.");
					return;
				}
					
				// 회원가입
				JoinReqDto dto = new JoinReqDto();
				dto.setUserId(userId);
				dto.setPassword(password);
				dto.setBirthDate(birthDate);
				dto.setPhone(mobile);
				dto.setEmail(email);
				result = userDao.userJoin(dto);
				if(result == 1) {
					JOptionPane.showMessageDialog(null, "회원가입 완료");
					new Login();
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "회원가입 실패, 다시 시도해 주세요.");
				}
				
			}
		});
		
		cbAgree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String consent = "[필수] 개인정보 수집 및 이용 동의\r\n" + "\r\n" + "아래의 목적으로 개인정보를 수집 및 이용합니다\r\n" + "\r\n"
						+ "1. 목적 : 개인 식별, 프로그램 기능 사용\r\n" + "2. 항목 : 아이디, 휴대폰 번호, 생년월일\r\n"
						+ "3. 개인정보의 수집 및 이용에 대한 동의를 거부할 수 있으나, 기능 사용이 제한됩니다.";
				int result = JOptionPane.showConfirmDialog(null, consent, "개인정보 이용약관", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
					cbAgree.setSelected(true);
				} else if (result == JOptionPane.NO_OPTION) {
					cbAgree.setSelected(false);
				}
			}
		});

		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login();
				frame.dispose();
			}
		});

		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				frame.requestFocus();
			}
		});

		frame.setSize(1920, 1080);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void init() {
		backgroundPanel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(backgroundPanel);
		frame.setTitle("고속버스 예약 프로그램 ver1.0");

		CustomUILogin custom = new CustomUILogin(backgroundPanel);
		custom.setPanel();

		setLbLogo(custom.setLbImg("lbLogo", 1, 805, 150));
		lbId = custom.setLb("lbUserId", "아이디", 680, 290, 100, 20, 18);
		txtUserId = custom.setTextField("txtUserId", "", 680, 315, 350, 40);
		lbPassword = custom.setLb("lbPassword", "비밀번호", 680, 365, 100, 20, 18);
		txtPassword = custom.setPasswordField("txtPassword", "", 680, 390, 350, 40);
		lbPasswordCheck = custom.setLb("lbPasswordCheck", "비밀번호 재확인", 680, 440, 150, 20, 18);
		txtPasswordCheck = custom.setPasswordField("txtPasswordCheck", "", 680, 465, 350, 40);
		lbBirth = custom.setLb("lbBirth", "생년월일", 680, 515, 100, 20, 18);
		txtBirth = custom.setTextField("txtBirth", "8자리로 입력해주세요", 680, 540, 350, 40);
		lbMobile = custom.setLb("lbMobile", "휴대전화", 680, 590, 100, 20, 18);
		txtMobile = custom.setTextField("txtMobile", "- 없이 입력해주세요", 680, 615, 350, 40);
		lbEmail = custom.setLb("lbEmail", "이메일", 680, 665, 100, 20, 18);
		txtEmail = custom.setTextField("txtEmail", "", 680, 690, 350, 40);
		cbAgree = custom.setCheckBox("cbAgree", "개인정보 이용약관에 동의합니까?", 680, 760);

		btnJoinComplete = custom.setBtnBlack("btnJoin", "회원가입완료", 680, 820, 350, 45);
		btnPrev = custom.setBtnWhite("btnPrev", "이전으로", 680, 875, 350, 45, "FLAIN");
	}

	public JLabel getLbLogo() {
		return lbLogo;
	}

	public void setLbLogo(JLabel lbLogo) {
		this.lbLogo = lbLogo;
	}
}