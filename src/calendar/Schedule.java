package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.SQLite;
import db.Style;
import gui.BaseJPanel;

public class Schedule extends BaseJPanel {
	Main jf = null;
	int[] colors = new int[24];
	JPanel schedule = new BaseJPanel(new GridLayout(2, 1, 10, 10), jf);
	JButton addBtn = new JButton("추가");
	JTextField text = new JTextField(20);
	List<Integer> check = new ArrayList<>();
	JPanel[] dates = new JPanel[24];
	int index = 1;

	public Schedule(Main jf) {
		super(new FlowLayout(), jf);
		this.jf = jf;

		add(combine());
		setBackground(jf.getBackground());
	}

	private JPanel getSchedulePanelElement(int index) {
		JPanel panel = new BaseJPanel(new BorderLayout(10, 10), jf);

		JPanel choice = new BaseJPanel(new GridLayout(1, 12), jf);
		JPanel time = new BaseJPanel(new GridLayout(1, 12), jf);
		
		choice.setBackground(jf.getBackground());
		time.setBackground(jf.getBackground());
		
		for (int i = index; i < index + 12; i++) {
			JPanel date = dates[i];
			choice.add(date);

			JLabel label = new JLabel(Integer.toString(i + 1));
			label.setFont(Style.getFont(20));
			time.add(label);
			date.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					clickSchedule(date);
				}
			});
		}
		panel.add(choice, BorderLayout.CENTER);
		panel.add(time, BorderLayout.SOUTH);
		panel.setBackground(jf.getBackground());
		return panel;
	}

	private JPanel getSchedulePanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);

		setSchedulePanel();
		panel.add(schedule);
		
		panel.setBackground(jf.getBackground());
		return panel;
	}

	public void setSchedulePanel() {
		schedule.removeAll();
		check = new ArrayList<Integer>();

		initSchedulePanels();
		for (int i = 0; i < 2; i++)
			schedule.add(getSchedulePanelElement(i * 12));
		setSchedulePanelElement();
		schedule.setBackground(jf.getBackground());
		schedule.revalidate();
		schedule.repaint();
	}

	private void setSchedulePanelElement() {
		try {
			ResultSet rs = SQLite.getResultSet("select * from schedule where s_date = '" + jf.getDate() + "'");
			while (rs.next()) {
				for (int i = Integer.parseInt(rs.getString("s_time").split(" ~ ")[0]) - 1; i < Integer
						.parseInt(rs.getString("s_time").split(" ~ ")[1]); i++) {
					dates[i].setBackground(Style.getSchedulColor(rs.getInt("s_color") % 11));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void clickSchedule(JPanel date) {
		if (addBtn.getText().equals("결정")) {
			if (check.contains(Integer.parseInt(date.getName()))) {
				index += ((index + 1) % 11 == 0 ? 2 : 1);
				setPanelColor(check.get(0) - 1, check.get(check.size() - 1) - 1);
			}
		} else {
			if(check.contains(Integer.parseInt(date.getName()))) {
				date.setBackground(Style.getSchedulColor(0));
				check.remove(((Integer) Integer.parseInt(date.getName())));				
			} else if(date.getBackground().equals(Style.getSchedulColor(0))){
				date.setBackground(Style.getSchedulColor(1));
				check.add(Integer.parseInt(date.getName()));
			}
		}
	}

	private void setPanelColor(int start, int end) {
		for (int i = start; i <= end; i++) {
			dates[i].setBackground(Style.getSchedulColor(index));
		}
	}

	private JPanel combine() {
		JPanel panel = new BaseJPanel(new BorderLayout(), jf);

		panel.add(getInfoPanel(), BorderLayout.NORTH);
		panel.add(getSchedulePanel(), BorderLayout.CENTER);

		panel.setBackground(jf.getBackground());
		return panel;
	}

	private JPanel getInfoPanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(FlowLayout.RIGHT), jf);

		initAddBtn();
		initText();
		panel.add(text);
		panel.add(addBtn);

		panel.setBackground(jf.getBackground());
		return panel;
	}

	private void clickAddBtn() {
		if (addBtn.getText().equals("추가")) {
			if (check.size() == 0)
				return;
			Collections.sort(check);
			if (check.get(0) + check.size() - 1 != check.get(check.size() - 1))
				return;
			addBtn.setText("결정");
		} else {
			if (text.getText().isEmpty() || text.getText().equals(""))
				return;
			SQLite.insertSchedule(text.getText().substring(0, (text.getText().length() >= 12 ? 12 : text.getText().length())), jf.getDate(),
					String.format("%02d ~ %02d", check.get(0), check.get(check.size() - 1)), Integer.toString(index));
			jf.setDate(jf.getDate());
			index = 1;
			addBtn.setText("추가");
			text.setText("");
			jf.setSheduleInfo();
		}
	}

	private void initText() {
		text.setPreferredSize(new Dimension(600, 50));
	}

	private void initAddBtn() {
		addBtn.setPreferredSize(new Dimension(100, 50));
		addBtn.setBackground(Style.main_color);
		addBtn.setFocusPainted(false);
		addBtn.setFont(Style.getFont(20));
		addBtn.setForeground(Color.white);
		addBtn.addActionListener(v -> {
			clickAddBtn();
		});
	}

	private void initSchedulePanels() {
		for (int i = 0; i < 24; i++) {
			dates[i] = new JPanel(new FlowLayout());
			dates[i].setBackground(Style.getSchedulColor(0));
			dates[i].setPreferredSize(new Dimension(80, 100));
			dates[i].setName(Integer.toString(i + 1));
			dates[i].setBorder(new LineBorder(new Color(220, 220, 220)));
		}
	}
}
