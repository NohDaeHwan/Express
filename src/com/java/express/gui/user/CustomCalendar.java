package com.java.express.gui.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomCalendar extends JFrame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private int calendarWindowTest = 0;
	
	// 상단바
	JPanel bar = new JPanel();
	JButton lastMonth = new JButton("◀");
	
	JComboBox<Integer> cbYear = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> mdYear = new DefaultComboBoxModel<Integer>();
	JLabel lbYear = new JLabel("년 ");
	
	JComboBox<Integer> cbMonth = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> mdMonth = new DefaultComboBoxModel<Integer>();
	JLabel lbMonth = new JLabel("월 ");
	JButton nextMonth = new JButton("▶");
	
	JTextField txtDepartDate;
	
	// 중앙 
	JPanel center = new JPanel(new BorderLayout());
	
	JPanel cntNorth = new JPanel(new GridLayout(0, 7));
	
	JPanel cntCenter = new JPanel(new GridLayout(0, 7));
	
	String[] dw = { "일", "월", "화", "수", "목", "금", "토"};
	
	Calendar now = Calendar.getInstance();
	int year, month, date;
	
	public CustomCalendar(JTextField txtDepartDate) {
		this.txtDepartDate = txtDepartDate;
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH) + 1;
		date = now.get(Calendar.DATE);
		
		for (int i = year; i <= year + 20; i++) {
			mdYear.addElement(i);
		}
		for (int i = 1; i <= 12; i++) {
			mdMonth.addElement(i);
		}
		
		// 상단 지역
		add("North", bar);
		bar.setLayout(new FlowLayout());
		bar.setSize(300, 400);
		bar.add(lastMonth);
		
		bar.add(cbYear);
		cbYear.setModel(mdYear);
		cbYear.setSelectedItem(year);
		
		bar.add(lbYear);
		bar.add(cbMonth);
		cbMonth.setModel(mdMonth);
		cbMonth.setSelectedItem(month);
		
		bar.add(lbMonth);
		bar.add(nextMonth);
		bar.setBackground(new Color(230, 236, 240));
		
		// 중앙 지역
		add("Center", center); 
		// 중앙 상단 지역 
		center.add("North",cntNorth); 
		for(int i = 0; i < dw.length; i++) { 
			JLabel dayOfWeek = new JLabel(dw[i], JLabel.CENTER); 
			if(i==0) dayOfWeek.setForeground(Color.red); 
			else if(i==6) dayOfWeek.setForeground(Color.blue); 
			cntNorth.add(dayOfWeek); 
		} 
		// 중앙 중앙 지역 
		center.add("Center",cntCenter); 
		dayPrint(year,month);
		
		cbYear.addActionListener(this); 
		cbMonth.addActionListener(this); 
		lastMonth.addActionListener(this); 
		nextMonth.addActionListener(this); 
		addWindowListener(this); 
		
		// frame 기본 셋팅 
		setSize(400,300); 
		setVisible(true); 
		setResizable(false); 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void dayPrint(int y, int m) {
		Calendar cal = Calendar.getInstance(); 
		cal.set(y, m-1, 1); 
		int week = cal.get(Calendar.DAY_OF_WEEK); // 1일에 대한 요일 
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 1월에 대한 마지막 요일 
		for(int i = 1; i < week; i++) { // 1월 1일 전까지 공백을 표시해라 
			cntCenter.add(new JLabel("")); 
		} 
		if (now.get(Calendar.YEAR) == y) {
			for(int i = 0; i <= lastDate - 1; i++) { // 1월 마지막 날까지 숫자를 적어라, 그리고 토요일 일요일은 색깔을 입혀라 
				JLabel day = new JLabel(); 
				day.setHorizontalAlignment(JLabel.CENTER); 
				if (now.get(Calendar.MONTH) < m-1) {
					if((week + i) % 7 == 0) { 
						cntCenter.add(day).setForeground(Color.blue); 
						day.setText(1 + i + ""); 
					} else if((week + i) % 7 == 1) { 
						cntCenter.add(day).setForeground(Color.red); 
						day.setText(1 + i + ""); 
					} else { 
						cntCenter.add(day); 
						day.setText(1 + i + ""); 
					}
					
					day.addMouseListener(new MouseAdapter() { 
						public void mouseClicked(MouseEvent me) { 
							JLabel mouseClick = (JLabel) me.getSource(); 
							String str = mouseClick.getText(); 
							String y = "" + cbYear.getSelectedItem(); 
							String m = "" + cbMonth.getSelectedItem(); 
							
							// 받은 "요일"이 1자리면 0을 붙여라 
							if(str.equals("")) ; 
							else if(str.length() == 1) str = "0" + str; 
							
							// 받은 "월"이 1자리면 0을 붙여라 
							if(m.length() == 1) m = "0" + m; 
							
							txtDepartDate.setText( y + "-" + m + "-" + str);
							dispose();
						} 
					}); 
				} else if (now.get(Calendar.MONTH) == m-1) {
					if (now.get(Calendar.DATE) <= i + 1) {
						if((week + i) % 7 == 0) { 
							cntCenter.add(day).setForeground(Color.blue); 
							day.setText(1 + i + ""); 
						} else if((week + i) % 7 == 1) { 
							cntCenter.add(day).setForeground(Color.red); 
							day.setText(1 + i + ""); 
						} else { 
							cntCenter.add(day); 
							day.setText(1 + i + ""); 
						}
						
						day.addMouseListener(new MouseAdapter() { 
							public void mouseClicked(MouseEvent me) { 
								JLabel mouseClick = (JLabel) me.getSource(); 
								String str = mouseClick.getText(); 
								String y = "" + cbYear.getSelectedItem(); 
								String m = "" + cbMonth.getSelectedItem(); 
								
								// 받은 "요일"이 1자리면 0을 붙여라 
								if(str.equals("")) ; 
								else if(str.length() == 1) str = "0" + str; 
								
								// 받은 "월"이 1자리면 0을 붙여라 
								if(m.length() == 1) m = "0" + m; 
								
								txtDepartDate.setText( y + "-" + m + "-" + str);
								dispose();
							} 
						}); 
					} else {
						cntCenter.add(day).setForeground(Color.GRAY); 
						day.setText(1 + i + "");
					}
				} else {
					cntCenter.add(day).setForeground(Color.GRAY); 
					day.setText(1 + i + "");
				}
				
			}
		} else {
			for(int i = 0; i <= lastDate - 1; i++) { // 1월 마지막 날까지 숫자를 적어라, 그리고 토요일 일요일은 색깔을 입혀라 
				JLabel day = new JLabel(); 
				day.setHorizontalAlignment(JLabel.CENTER); 
				if((week + i) % 7 == 0) { 
					cntCenter.add(day).setForeground(Color.blue); 
					day.setText(1 + i + ""); 
				} else if((week + i) % 7 == 1) { 
					cntCenter.add(day).setForeground(Color.red); 
					day.setText(1 + i + ""); 
				} else { 
					cntCenter.add(day); 
					day.setText(1 + i + ""); 
				}
				
				day.addMouseListener(new MouseAdapter() { 
					public void mouseClicked(MouseEvent me) { 
						JLabel mouseClick = (JLabel) me.getSource(); 
						String str = mouseClick.getText(); 
						String y = "" + cbYear.getSelectedItem(); 
						String m = "" + cbMonth.getSelectedItem(); 
						
						// 받은 "요일"이 1자리면 0을 붙여라 
						if(str.equals("")) ; 
						else if(str.length() == 1) str = "0" + str; 
						
						// 받은 "월"이 1자리면 0을 붙여라 
						if(m.length() == 1) m = "0" + m; 
						
						txtDepartDate.setText( y + "-" + m + "-" + str);
						dispose();
					} 
				}); 
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); 
		if (obj instanceof JButton) { 
			JButton eventBtn = (JButton)obj; 
			int yy = (Integer) cbYear.getSelectedItem(); 
			int mm = (Integer) cbMonth.getSelectedItem(); 
			if (eventBtn.equals(lastMonth)) { //전달 
				if (mm == 1 && yy == year ) {
				} else if (mm == 1) { 
					yy--; mm = 12; 
				} else { 
					mm--; 
				} 
			}else if (eventBtn.equals(nextMonth)) { //다음달 
				if(mm == 12) { 
					yy++; mm = 1; 
				}else { 
					mm++; 
				} 
			} 
			cbYear.setSelectedItem(yy); 
			cbMonth.setSelectedItem(mm); 
		}else if (obj instanceof JComboBox) { //콤보박스 이벤트 발생시 
			createDayStart(); 
		}		
	}
	
	public void createDayStart() { 
		cntCenter.setVisible(false); //패널 숨기기 
		cntCenter.removeAll(); //날짜 출력한 라벨 지우기 
		dayPrint((Integer) cbYear.getSelectedItem(), (Integer) cbMonth.getSelectedItem()); 
		cntCenter.setVisible(true); //패널 재출력 
	}


	@Override
	public void windowOpened(WindowEvent e) {
		calendarWindowTest = 1;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		calendarWindowTest = 0;
	}

	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}

}
