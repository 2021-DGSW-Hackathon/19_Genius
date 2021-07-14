package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.Style;
import gui.BaseJFrame;
import gui.BaseJPanel;

public class Calendar extends BaseJPanel {
	private static final long serialVersionUID = 1L;
	java.util.Calendar today = java.util.Calendar.getInstance();
	Main jf;
	JPanel datePanel = new BaseJPanel(new GridLayout(0, 7), jf);
	JLabel monthLabel = new JLabel();
	int year = 0, month = 0;
	
	public Calendar(Main jf) {
		super(new FlowLayout(), jf);
		this.jf = jf;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		year = cal.get(java.util.Calendar.YEAR);
		month = cal.get(java.util.Calendar.MONTH);
		
		add(combine());
		setBackground(jf.getBackground());
		setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 0));
	}
	
	private JPanel combine() {
		JPanel panel = new BaseJPanel(new BorderLayout(), jf);
		
		panel.add(getMonthPanel(), BorderLayout.NORTH);
		panel.add(getDatePanel(), BorderLayout.CENTER);
		panel.setBackground(Color.white);
		
		return panel;
	}
	
	private void setMonthPanel() {
		monthLabel.setText(Integer.toString(month + 1) + "월");
	}
	
	private JPanel getMonthPanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		JLabel left = new JLabel(getImage("left", 30, 30));
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				month--;
				if(month == -1) {
					
					month = 11;
					year--;
				}
				clickArrow();
			}
		});
		panel.add(left);
		
		setMonthPanel();
		monthLabel.setFont(Style.getFont(35));
		monthLabel.setForeground(Color.white);
		panel.add(monthLabel);
		
		JLabel right = new JLabel(getImage("right", 30, 30));
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(month++ == 11) {
					month = 0;
					year++;
				}
				
				
				clickArrow();
			}
		});
		panel.add(right);
		
		panel.setBackground(Style.main_color);
		return panel;
	}
	
	private void clickArrow() {
		setMonthPanel();
		setDatePanel();
	}
	
	private JPanel getDatePanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		setDatePanel();
		datePanel.setBackground(Color.white);
		datePanel.setPreferredSize(new Dimension(400, 300));
		
		JPanel flow = new BaseJPanel(new FlowLayout(), jf);
		flow.add(datePanel);
		flow.setBackground(Color.white);
		
		panel.add(flow);
		panel.setPreferredSize(new Dimension(400, 300));
		panel.setBackground(Color.white);
		
		return panel;
	}
	
	private void setDatePanel() {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, 1);
		int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int max = cal.getMaximum(java.util.Calendar.DAY_OF_MONTH);
		datePanel.removeAll();
		
		String[] split = "일,월,화,수,목,금,토".split(",");
		for(int i = 0; i < split.length; i++) datePanel.add(getDateElement(split[i], getDateColor(i), false));
		
		int day = 0;
		for(int i = 0; day <= max; i++) {
			if(i >= start) {
				datePanel.add(getDateElement(Integer.toString(++day), getDateColor(i), true));
			}
			else {
				JPanel p = new BaseJPanel(new FlowLayout(), jf);
				p.setBackground(Color.white);
				datePanel.add(p);
			}
		}
		
		datePanel.revalidate();
		datePanel.repaint();
	}
	
	private Color getDateColor(int index) {
		if(index % 7 == 0) return Color.RED;
		else if(index % 7 == 6) return Color.BLUE;
		else return Color.BLACK;
	}
	
	private JPanel getDateElement(String text, Color color, boolean isDate) {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		JLabel label = new JLabel(text);
		label.setFont(Style.getFont(20));
		label.setForeground(color);
		panel.setBackground(Color.WHITE);
		
		if(isDate) {
			panel.setName(text);
			if(today.get(java.util.Calendar.YEAR) == year && today.get(java.util.Calendar.MONTH) == month && today.get(java.util.Calendar.DATE) == Integer.parseInt(text)) {
				actDateElement(panel);
			}
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					actDateElement(panel);
				}
			});
		}
		
		panel.add(label);
		
		return panel;
	}
	
	private void actDateElement(JPanel panel) {
		if(Main.clickDatePanel != null) Main.clickDatePanel.setBackground(Color.white);
		Main.clickDatePanel = panel;
		Main.clickDatePanel.setBackground(new Color(43, 233, 148));
		jf.setDate(String.format("%04d-%02d-%02d", year, month, Integer.parseInt(panel.getName())));
	}
}
