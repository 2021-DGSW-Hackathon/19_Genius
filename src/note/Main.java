package note;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.SQLite;
import db.Style;
import gui.BaseJFrame;
import gui.BaseJPanel;

public class Main extends BaseJFrame {
	JScrollPane scroll;
	JTextField text = new JTextField(20);
	JPanel note = new BaseJPanel(new GridLayout(0, 1), this);
	public Main() {
		setFrame(500, 700);
		setBackground(new Color(252, 238, 142));

		JPanel panel = new BaseJPanel(new FlowLayout(), this);
		panel.add(combine());
		add(panel);

		panel.setBackground(getBackground());
		setVisible(true);
	}

	private JPanel combine() {
		JPanel panel = new BaseJPanel(new BorderLayout(), this);

		panel.add(getTop(), BorderLayout.NORTH);
		panel.add(getCenter(), BorderLayout.CENTER);
		
		panel.setBackground(getBackground());
		return panel;
	}
	
	private JPanel getCenter() {
		JPanel panel = new BaseJPanel(new FlowLayout(), this);
		text.setPreferredSize(new Dimension(490, 40));
		text.setBorder(new LineBorder(getBackground()));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					SQLite.insertNote(text.getText());
					setCenter();
				}
			}
		});
		setCenter();
		
		JPanel p = new BaseJPanel(new FlowLayout(), this);
		p.setBackground(getBackground());
		p.add(note);
		
		note.setBackground(getBackground());
		scroll = new JScrollPane(p);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(new LineBorder(getBackground()));
		scroll.setBackground(getBackground());
		scroll.setPreferredSize(new Dimension(540, 660));
		
		panel.add(scroll);
		panel.setBackground(getBackground());
		return panel;
	}
	
	private void setCenter() {
		note.removeAll();
		
		try {
			ResultSet rs = SQLite.getResultSet("select * from note");
			while(rs.next()) {
				JPanel p = new BaseJPanel(new FlowLayout(FlowLayout.LEFT), this);
				JLabel label = new JLabel(rs.getString("n_text"));
				label.setFont(Style.getFont(30));
				label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
				p.add(label);
				p.setBackground(getBackground());
				p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
				p.setPreferredSize(new Dimension(490, 40));
				note.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JPanel p = new BaseJPanel(new FlowLayout(FlowLayout.LEFT), this);
		p.add(text);
		p.setBackground(getBackground());
		note.add(p);
		
		note.revalidate();
		note.repaint();
	}

	private JPanel getTop() {
		JPanel panel = new BaseJPanel(new FlowLayout(), this);

		panel.setPreferredSize(new Dimension(500, 40));
		panel.setBackground(new Color(43, 39, 37));
		return panel;
	}
}
