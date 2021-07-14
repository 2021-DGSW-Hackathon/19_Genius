package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import db.SQLite;
import db.Style;
import gui.BaseJPanel;

public class ToDoListPanel extends BaseJPanel {
	Main jf = null;
	JPanel toDoPanel = new BaseJPanel(new GridLayout(0, 1), jf);
	JLabel date = new JLabel();
	JScrollPane scroll;
	
	public ToDoListPanel(Main jf) {
		super(new FlowLayout(), jf);
		this.jf = jf;
		
		add(combine());
		setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 20));
		setBackground(jf.getBackground());
	}
	
	private JPanel combine() {
		JPanel panel = new BaseJPanel(new BorderLayout(), jf);
		
		panel.add(getDatePanel(), BorderLayout.NORTH);
		panel.add(getSchedulePanel(), BorderLayout.CENTER);
		
		panel.setBackground(Style.main_color);
		return panel;
	}
	
	private JPanel getDatePanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(FlowLayout.LEFT), jf);
		setDateLabel();
		date.setFont(Style.getFont(30));
		date.setForeground(Color.white);
		
		panel.add(date);
		panel.setBackground(Style.main_color);
		return panel;
	}
	
	public void setDateLabel() {
		String[] split = jf.getDate().split("-");
		date.setText(split[0] + "년 " + (Integer.parseInt(split[1]) + 1) + "월 " + split[2] + "일");
	}
	
	private JPanel getSchedulePanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		setSchdulePanel();
		
		toDoPanel.setBackground(Color.white);
		scroll = new JScrollPane(toDoPanel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(590, 300));
		scroll.setBorder(new LineBorder(new Color(00000000), 0));
		
		panel.setBackground(Style.main_color);
		panel.add(scroll);
		panel.setPreferredSize(new Dimension(550, 300));
		
		return panel;
	}
	public void setSchdulePanel() {
		toDoPanel.removeAll();
		
		try {
			ResultSet rs = SQLite.getResultSet("select * from schedule where s_date = '" + jf.getDate() + "';");
			while(rs.next()) {
				toDoPanel.add(getSchedulePanelElement(rs.getString("s_no"), rs.getString("s_time"), rs.getString("s_name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		toDoPanel.revalidate();
		toDoPanel.repaint();
	}
	
	private JPanel getSchedulePanelElement(String no, String time, String text) {
		JPanel panel = new BaseJPanel(new BorderLayout(10, 10), jf);
		
		JLabel close = new JLabel(getImage("close", 20, 20));
		close.setName(no);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				removeSchedule(close.getName());
			}
		});
		
		panel.add(getJLabel(time + " : "), BorderLayout.WEST);
		panel.add(getJLabel(text), BorderLayout.CENTER);
		panel.add(close, BorderLayout.EAST);
		panel.setName(no);
		
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		panel.setBackground(Color.white);
		panel.setPreferredSize(new Dimension(450, 50));
		
		JPanel flow = new BaseJPanel(new FlowLayout(), jf);
		
		flow.setBackground(Color.white);
		flow.add(panel);
		flow.setPreferredSize(new Dimension(450, 50));
		return flow;
	}
	
	private void removeSchedule(String no) {
		
	}
	
	private JLabel getJLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(Style.getFont(25));
		return label;
	}
	

}
