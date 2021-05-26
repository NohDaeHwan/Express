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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class CustomUIMain extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel backgroundPanel, topBluePanel, topGrayPanel, homePanel, bookPanel, updatePanel, paymentPanel;
	
	private int setWeight(String weight) {
		if(weight.toUpperCase().equals("BOLD")) {
			return 1;
		}  else if(weight.toUpperCase().equals("ITALIC")) {
			return 2;
		} else {
			return 0;
		}
	}
	
	public CustomUIMain() {}
	
	public CustomUIMain(JPanel backgroundPanel) {
		this.backgroundPanel = backgroundPanel;
	}
	
	public CustomUIMain(JPanel backgroundPanel, JPanel homePanel, JPanel bookPanel, JPanel updatePanel, JPanel paymentPanel) {
		this.backgroundPanel = backgroundPanel;
		this.homePanel = homePanel;
		this.bookPanel = bookPanel;
		this.updatePanel = updatePanel;
		this.paymentPanel = paymentPanel;
	}

	public void setPanel() {
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
		topGrayPanel.setLayout(null);
		backgroundPanel.add(topGrayPanel);
		
		homePanel.setVisible(true);
		homePanel.setBounds(25, 150, 1650, 860);
		homePanel.setBackground(Color.WHITE);
		homePanel.setLayout(null);
		backgroundPanel.add(homePanel);
		
		bookPanel.setVisible(false);
		bookPanel.setBounds(25, 150, 1650, 860);
		bookPanel.setBackground(Color.WHITE);
		bookPanel.setLayout(null);
		backgroundPanel.add(bookPanel);
		
		updatePanel.setVisible(false);
		updatePanel.setBounds(25, 150, 1650, 860);
		updatePanel.setBackground(Color.WHITE);
		updatePanel.setLayout(null);
		backgroundPanel.add(updatePanel);
		
		paymentPanel.setVisible(false);
		paymentPanel.setBounds(25, 150, 1650, 860);
		paymentPanel.setBackground(Color.WHITE);
		paymentPanel.setLayout(null);
		backgroundPanel.add(paymentPanel);
	}
	
	public JButton setBtnMenu(String name, String text, int x, int y, int width, int height, String weight) {
		JButton btn = new JButton();
		Font btnFont = new Font("맑은 고딕", setWeight(weight), 20);
		btn.setFont(btnFont);
		btn.setBackground(Color.WHITE);
		btn.setForeground(new Color(26, 27, 32));
		btn.setBounds(x, y, width, height);
		btn.setText(text);
		topBluePanel.add(btn);
		btn.setName(name);

		return btn;
	}
	
	protected JButton setBtnBlack(String name, String text, JPanel panel, int x, int y, int width, int height) {

		class RoundedButton extends JButton {
			private static final long serialVersionUID = 1L;

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
		btn.setName(name);
		panel.add(btn);

		return btn;
	}
	
	protected JButton setTableBtnWhite(String name, String text, String weight) {

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
		btn.setText(text);
		btn.setName(name);

		return btn;
	}

	protected JButton setBtnWhite(String name, String text, JPanel panel, int x, int y, int width, int height, String weight) {

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
		btn.setName(name);
		panel.add(btn);

		return btn;
	}
	
	public JLabel setlbImg(String name, JPanel panel, int x, int y) {
		JLabel lb = new JLabel();
		ImageIcon icon = new ImageIcon("img/calendar.jpg");
		Image img = icon.getImage();
		img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizeImgIc = new ImageIcon(img);
		lb.setIcon(resizeImgIc);
		lb.setHorizontalAlignment(SwingConstants.CENTER);		
		lb.setBackground(Color.WHITE);
		lb.setBounds(x, y, 40, 40);
		lb.setName(name);		
		panel.add(lb);

		return lb;
	}
	
	public JLabel setLbImg(String name, JPanel panel, int iconNum, int x, int y) {
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
		panel.add(lb);
		lb.setName(name);
		
		return lb;
	}
	
	public JLabel setLb(String name, String text, JPanel panel, int x, int y, int width, int height, int fontSize) {
		JLabel lb = new JLabel(text);
		Font lbFont = new Font("맑은 고딕", Font.BOLD, fontSize);
		lb.setFont(lbFont);
		lb.setForeground(new Color(26, 27, 32));	
		lb.setBounds(x, y, width, height);
		lb.setName(name);
		panel.add(lb);
		
		return lb;
	}
	
	public JComboBox<String> setCombo(String name, String[] text, JPanel panel, int x, int y, int width, int height) {
		JComboBox<String> combo = new JComboBox<String>(text);
		combo.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		combo.setForeground(new Color(26, 27, 32));	
		combo.setBackground(Color.white);
		combo.setBounds(x, y, width, height);
		combo.setName(name);
		panel.add(combo);
		
		return combo;
	}
	
	public JTextField setTextField(String name, boolean enabled,JPanel panel, int x, int y, int width, int height) {
		JTextField txt = new JTextField();
			
		txt.setBackground(Color.white);
		txt.setBounds(x, y, width, height);
		txt.setName(name);
		txt.setEnabled(enabled);
		panel.add(txt);
		
		return txt;
	}
	
	protected JPasswordField setPasswordField(String name, JPanel panel, int x, int y, int width, int height) {
		JPasswordField txt = new JPasswordField();
		
		Font tfFont = new Font("Arial", Font.PLAIN, 20);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());	
		txt.setBounds(x, y, width, height);
		txt.setName(name);
		panel.add(txt);
		
		return txt;
	}
	
	public JTable setTable(String name, JPanel panel, String[] header, int x, int y, int width, int height) {
		DefaultTableModel tm = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {  
				return false;
			}
		};

		for (int i = 0; i < header.length; i++) {
			tm.addColumn(header[i]);
		}
		
		JTable table = new JTable(tm);
		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(x, y, width, height);
		scrollPane.setName(name);
		
		panel.add(scrollPane);
		
		return table;
	}
	
	protected JButton setbtnBar(String name, String text, JPanel panel, int y) {
		JButton btn = new JButton();
		
		Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
		btn.setFont(btnFont);
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(150, y, 520, 40);
		btn.setText(text);
		btn.setName(name);
		panel.add(btn);
		
		return btn;
	}
	
	protected JButton setBtnSeat(String name, String seat, JPanel panel, int x, int y) {
		JButton btn = new JButton(seat);
		
		Font btnFont = new Font("맑은 고딕", Font.BOLD, 14);
		btn.setFont(btnFont);
		btn.setBackground(new Color(230, 236, 240));
		btn.setForeground(new Color(114, 114, 114));
		btn.setBorderPainted(false);
		btn.setBounds(x, y, 50, 50);
		btn.setName(name);
		panel.add(btn);
		
		return btn;
	}

}
