package com.java.express.gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.java.express.user.User;
import com.java.express.user.UserDao;
import com.java.express.user.dto.LoginReqDto;

@SuppressWarnings("serial")
public class Login extends CustomUILogin {

	private JFrame frame = new JFrame();
	private JPanel backgroundPanel;
	private JTextField txtUserId;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnJoin;
	private JLabel lbLogo, lbSearch;
	UserDao userDao = new UserDao();

	public Login() {
		
		init();
		
		lbSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(frame, "관리자에게 문의하세요.", "오류", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		txtPassword.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String userId = txtUserId.getText();
				String password = String.valueOf(txtPassword.getPassword());
				
				LoginReqDto dto = new LoginReqDto(userId, password);
				User user = userDao.userLogin(dto);
				
				if (user == null) {
					JOptionPane.showMessageDialog(frame, "일치하는 사용자가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(user.getUserRole().equals("ADMIN")) {
					new com.java.express.gui.admin.Main();
					frame.dispose();
				} else {
					new com.java.express.gui.user.Main(user);
					frame.dispose();
				}

			}
		});
		
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Join();
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
		txtUserId = custom.setTextField("txtUserId", "아이디", 680, 290, 350, 45);
		txtPassword = custom.setPasswordField("txtPassword", "비밀번호", 680, 345, 350, 45);
		btnLogin = custom.setBtnBlack("btnLogin", "로그인", 680, 425, 350, 45);
		btnJoin = custom.setBtnWhite("btnJoin", "회원가입", 680, 480, 350, 45, "PLAIN");
		lbSearch = custom.setLb("lbSearch", "아이디 찾기 ｜ 비밀번호 찾기", 750, 535, 220, 40, "center", 15, "plain");
	}
	
	public JLabel getLbLogo() {
		return lbLogo;
	}

	public void setLbLogo(JLabel lbLogo) {
		this.lbLogo = lbLogo;
	}

	public static void main(String[] args) {
		new Login();
	}

}