package com.java.express.gui.user;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

@SuppressWarnings("serial")
class CustomUILogin extends JFrame {
	JPanel backgroundPanel, topBluePanel, topGrayPanel;
	
	public CustomUILogin() {}
	
	public CustomUILogin(JPanel backgroundPanel) {
		this.backgroundPanel = backgroundPanel;
	}

	protected void setPanel() {
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Color.WHITE);
		
		this.topBluePanel = new JPanel();
		topBluePanel.setBounds(0, 0, 1710, 70);
		topBluePanel.setBackground(new Color(26, 27, 32));
		topBluePanel.setLayout(null);
		backgroundPanel.add(topBluePanel);
		
		this.topGrayPanel = new JPanel();
		topGrayPanel.setBounds(0, 70, 1710, 50);
		topGrayPanel.setBackground(new Color(230, 236, 240));
		backgroundPanel.add(topGrayPanel);
	}
	
	protected JTextField setTextField(String name, String placeholder, int x, int y, int width, int height) {
		JTextField txt = new JTextField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}
		
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());
		
		txt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if(tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}
			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});
		
		txt.setBounds(x, y, width, height);
		backgroundPanel.add(txt);
		txt.setName(name);
		
		return txt;
	}
	
	protected JPasswordField setPasswordField(String name, String placeholder, int x, int y, int width, int height) {
		JPasswordField txt = new JPasswordField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}
		
		Font tfFont = new Font("Arial", Font.PLAIN, 20);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());
		
		txt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if(tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}
			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField)e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here") || tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});
		
		txt.setBounds(x, y, width, height);
		backgroundPanel.add(txt);
		txt.setName(name);
		
		return txt;
	}

	protected JButton setBtnBlack(String name, String text, int x, int y, int width, int height) {

		class RoundedButton extends JButton {
			public RoundedButton() {
				super();
				decorate();
			}

			protected void decorate() {
				setBorderPainted(false);
				setOpaque(false);
			}

			protected void paintComponent(Graphics g) {
				int width = getWidth();
				int height = getHeight();
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (getModel().isArmed()) {
					graphics.setColor(getBackground().darker());
				} else if (getModel().isRollover()) {
					graphics.setColor(getBackground().brighter());
				} else {
					graphics.setColor(getBackground());
				}
				graphics.fillRoundRect(0, 0, width, height, 15, 15);
				FontMetrics fontMetrics = graphics.getFontMetrics();
				Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
				int textX = (width - stringBounds.width) / 2;
				int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
				graphics.setColor(getForeground());
				graphics.setFont(getFont());
				graphics.drawString(getText(), textX, textY);
				super.paintComponent(g);
			}
		}

		RoundedButton btn = new RoundedButton();
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 20);
		btn.setFont(btnFont);
		btn.setBackground(new Color(26, 27, 32));
		btn.setForeground(Color.WHITE);
		btn.setBounds(x, y, width, height);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);

		return btn;
	}

	protected JButton setBtnWhite(String name, String text, int x, int y, int width, int height, String weight) {

		class RoundedBorder implements Border {
			int radius;

			RoundedBorder(int radius) {
				this.radius = radius;
			}

			public Insets getBorderInsets(Component c) {
				return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
			}

			public boolean isBorderOpaque() {
				return true;
			}

			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
			}
		}

		JButton btn = new JButton();
		btn.setBorder(new RoundedBorder(15));
		Font btnFont = new Font("맑은 고딕", setWeight(weight), 20);
		btn.setFont(btnFont);
		btn.setBackground(Color.WHITE);
		btn.setForeground(new Color(26, 27, 32));
		btn.setBounds(x, y, width, height);
		btn.setText(text);
		backgroundPanel.add(btn);
		btn.setName(name);

		return btn;
	}
	
	protected JLabel setLb(String name, String text, int x, int y, int width, int height, int fontSize) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.BOLD, fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(26, 27, 32));
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}

	protected JLabel setLb(String name, String text, int x, int y, int width, int height, String alignment, int fontSize, String weight) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", setWeight(weight), fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(26, 27, 32));
		lb.setHorizontalAlignment(setAlign(alignment));	
		lb.setBounds(x, y, width, height);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JLabel setLbImg(String name, int iconNum, int x, int y) {
		JLabel lb = new JLabel();

		ImageIcon imgIc = new ImageIcon("img/icon"+iconNum+".jpg");
		Image img = imgIc.getImage();
		if(iconNum == 1) {
			img = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		} else if (iconNum == 2) {
			img = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		} else if(iconNum == 3) {
			img = img.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
		} else if(iconNum == 4) {
			img = img.getScaledInstance(80, 56, Image.SCALE_SMOOTH);
		} else if(iconNum == 5) {
			img = img.getScaledInstance(60, 55, Image.SCALE_SMOOTH);
		}
		ImageIcon resizeImgIc = new ImageIcon(img);
		lb.setIcon(resizeImgIc);
		lb.setHorizontalAlignment(SwingConstants.CENTER);		
		lb.setBounds(x, y, 100, 100);
		backgroundPanel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	protected JCheckBox setCheckBox(String name, String text, int x, int y) {
		JCheckBox cb = new JCheckBox();
		cb.setBackground(Color.WHITE);
		cb.setBounds(x, y, 30, 30);
		backgroundPanel.add(cb);
		cb.setName(name);
		
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.PLAIN, 16);
		lb.setFont(lbFont);
		lb.setForeground(new Color(114, 114, 114));
		lb.setLocation(x+35, y-3);
		lb.setSize(300, 35);
		backgroundPanel.add(lb);
		
		return cb;
	}
	
	private int setAlign(String alignment) {
		if(alignment.toUpperCase().equals("CENTER")) {
			return 0;
		} else if(alignment.toUpperCase().equals("LEFT")) {
			return 2;
		}  else if(alignment.toUpperCase().equals("RIGHT")) {
			return 4;
		} else {
			return 0;
		}
	}
	
	private int setWeight(String weight) {
		if(weight.toUpperCase().equals("BOLD")) {
			return 1;
		}  else if(weight.toUpperCase().equals("ITALIC")) {
			return 2;
		} else {
			return 0;
		}
	}
	
}