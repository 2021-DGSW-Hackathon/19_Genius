package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.Style;
import gui.BaseJPanel;

public class CategoryCard extends BaseJPanel {
	JFrame jf;
	JPanel subPanel = new BaseJPanel(new FlowLayout(), jf);
	String title;
	String sub;
	String image;
	
	public CategoryCard(Main jf, String title, String image, String sub, Color color) {
		super(new FlowLayout(), jf);
		this.jf = jf;
		this.title = title;
		this.image = image;
		this.sub = sub;
		
		setBackground(color);
		add(combine());
	}
	
	private JPanel combine() {
		JPanel panel = new BaseJPanel(new BorderLayout(25, 25), jf);
		
		panel.add(getTitlePanel(), BorderLayout.NORTH);
		panel.add(new JLabel(getImage(image, 80, 90)), BorderLayout.CENTER);
		if(sub != null) {
			setSubPanel(sub);
			panel.add(subPanel, BorderLayout.SOUTH);
		}
		panel.setBackground(getBackground());
		return panel;
	}
	
	public void setSubPanel(String sub) {
		subPanel.removeAll();
		JLabel label = new JLabel(sub);
		label.setFont(Style.getFont(20));
		label.setForeground(Color.white);
		
		subPanel.add(label);
		subPanel.setBackground(getBackground());
		subPanel.revalidate();
		subPanel.repaint();
	}
	
	private JPanel getTitlePanel() {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		JLabel label = new JLabel(title);
		label.setFont(Style.getFont(30));
		label.setForeground(Color.white);
		panel.add(label);
		panel.setBackground(getBackground());
		return panel;
	}
	
	
}
