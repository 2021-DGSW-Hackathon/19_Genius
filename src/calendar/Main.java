package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.BaseJFrame;
import gui.BaseJPanel;

public class Main extends BaseJFrame {
	private ToDoListPanel toDo = null;
	private Schedule schedule = null;
	
	private static final long serialVersionUID = 1L;
	static JPanel clickDatePanel = null;
	private String date;

	public Main() {
		setFrame(1000, 800);
		setBackground(new Color(250, 250, 250));
		
		JPanel panel = new BaseJPanel(new BorderLayout(), this);
		
		panel.add(getTitlePanel(), BorderLayout.NORTH);
		panel.add(new Calendar(this), BorderLayout.WEST);
		panel.add((schedule = new Schedule(this)), BorderLayout.SOUTH);
		panel.add((toDo = new ToDoListPanel(this)), BorderLayout.CENTER);
		
		add(panel);
		setVisible(true);
	}
	
	public JPanel getTitlePanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(FlowLayout.RIGHT), this);
		
		JLabel close = new JLabel(getImage("close", 20, 20));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		
		panel.add(close);
		panel.setBackground(getBackground());
		
		return panel;
	}

	public void setDate(String date) {
		this.date = date;
		if(schedule != null) schedule.setSchedulePanel();
		if(toDo != null) {
			toDo.setSchdulePanel();
			toDo.setDateLabel();
		}
	}
	
	public void setSheduleInfo() {
		
	}
	
	public String getDate() {
		return date;
	}
	
}
