package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.Style;
import gui.BaseJPanel;

public class InformationCard extends BaseJPanel {
	JFrame jf;
	JPanel title;
	JPanel image = new BaseJPanel(new FlowLayout(), jf);
	JPanel text = new BaseJPanel(new FlowLayout(), jf);
	String titleStr;
	
	public InformationCard(Main jf, String titleStr) {
		super(new BorderLayout(), jf);
		this.jf = jf;
		
		setBaseData();
	
		this.titleStr = titleStr;
		combine();
	}
	
	public void setInformationCard(String img, int w, int h, String text, Color color) {
		removeAll();
		
		title.setBackground(getBackground());
		setBackground(color);
		setImage(img, w, h);
		setText(text);
		
		combine();
		
		revalidate();
		repaint();
	}
	
	private void setText(String t) {
		text.removeAll();
		
		JLabel label = new JLabel(t);
		label.setForeground(Color.white);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setFont(Style.getFont((t.equals("데이터가 없습니다.") ? 30 : 40)));
		
		text.add(label);
		text.setBackground(getBackground());
		text.revalidate();
		text.repaint();
	}
	
	private void setBaseData() {
		setBackground(new Color(200, 200, 200));
		setImage("noData", 120, 120);
		setText("데이터가 없습니다.");
	}
	
	private void combine() {
		add((title = getTitle(titleStr)), BorderLayout.NORTH);
		add(image, BorderLayout.CENTER);
		add(text, BorderLayout.SOUTH);
	}
	
	private JPanel getTitle(String title) {
		JPanel panel = new BaseJPanel(new FlowLayout(), jf);
		
		JLabel label = new JLabel(title);
		label.setForeground(Color.white);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setFont(Style.getFont(30));
		
		panel.add(label);
		panel.setBackground(getBackground());
		return panel;
	}
	
	private void setImage(String uri, int w, int h) {
		image.removeAll();
		
		image.add(new JLabel(getImage(uri, w, h)));
		image.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		image.setBackground(getBackground());
		image.revalidate();
		image.repaint();
	}
}
