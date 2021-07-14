package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import db.Style;
import gui.BaseJPanel;

public class Calendar extends BaseJPanel {
	java.util.Calendar today = java.util.Calendar.getInstance();
	
	JFrame jf;
	public Calendar(Main jf) {
		super(new FlowLayout(), jf);
		this.jf = jf;
		
		today.set(today.get(java.util.Calendar.YEAR), today.get(java.util.Calendar.MONTH), 1);
		setBackground(Color.white);
		clickThenShowCalendar(this);
		add(combine());
	}
	
	private JPanel combine() {
		JPanel panel = new BaseJPanel(new BorderLayout(), jf);
		
		panel.add(getMonthPanel(), BorderLayout.NORTH);
		panel.add(getDatePanel(), BorderLayout.CENTER);
		
		clickThenShowCalendar(panel);
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setBackground(Color.white);
		return panel;
	}
	
	private JPanel getDatePanel() {
		JPanel panel = new BaseJPanel(new GridLayout(0, 7), jf);
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(today.get(java.util.Calendar.YEAR), today.get(java.util.Calendar.MONTH), 1);
		
		int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int max = cal.getMaximum(java.util.Calendar.DAY_OF_MONTH);
		
		String[] split = "일,월,화,수,목,금,토".split(",");
		for(int i = 0; i < split.length; i++) panel.add(getDateElement(split[i], getDateColor(i), false));
		
		int day = 0;
		for(int i = 0; day <= max; i++) {
			if(i >= start) {
				panel.add(getDateElement(Integer.toString(++day), getDateColor(i), true));
			}
			else {
				JPanel p = new BaseJPanel(new FlowLayout(), jf);
				p.setBackground(Color.white);
				clickThenShowCalendar(p);
				panel.add(p);
			}
		}
		
		panel.setBackground(Color.white);
		clickThenShowCalendar(panel);
		return panel;
	}
	
	private JPanel getDateElement(String text, Color color, boolean isDate) {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		JLabel label = new JLabel(text);
		label.setFont(Style.getFont(20));
		label.setForeground(color);
		panel.setBackground(Color.WHITE);
		
		if(isDate) {
			panel.setName(text);
			if(today.get(java.util.Calendar.DATE) == Integer.parseInt(text)) {
				panel.setBackground(new Color(43, 233, 148));
			}
		}
		
		clickThenShowCalendar(panel);		
		panel.add(label);
		panel.setBackground(Color.white);
		return panel;
	}
	
	private Color getDateColor(int index) {
		if(index % 7 == 0) return Color.RED;
		else if(index % 7 == 6) return Color.BLUE;
		else return Color.BLACK;
	}
	
	private JPanel getMonthPanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		JLabel month = new JLabel(Integer.toString(today.get(java.util.Calendar.MONTH) + 1) + "월");
		month.setFont(Style.getFont(35));
		month.setForeground(Color.white);
		clickThenShowCalendar(month);
		
		clickThenShowCalendar(panel);
		panel.add(month);
		panel.setBackground(Style.main_color);
		return panel;
	}
	
	private void clickThenShowCalendar(Component obj) {
		obj.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new calendar.Main();
			}
		});
	}
}
